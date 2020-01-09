package com.sxkj.uc.util.sql;

import com.sxkj.uc.entity.User;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author zwd
 */
@Slf4j
public class SqlUtil {

    /**
     * 构造insert语句
     * 1、解析对象实例，获取entity中定义的所有添加了Column注解的字段，获取添加了Id注解的字段，获取表名
     *
     * @param object
     * @return
     * @throws Exception
     */
    public String insert(Object object) throws Exception {
        TableModel tableModel = parseEntity(object);
        if (tableModel == null) {
            throw new RuntimeException("table name is empty");
        }
        String tableName = tableModel.getTableName();
        Map<String, FieldModel> fieldMap = tableModel.getFieldMap();
        Class clazz = object.getClass();
        StringBuilder sb = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        // 遍历字段
        for (Map.Entry<String, FieldModel> map : fieldMap.entrySet()) {
            FieldModel fieldModel = map.getValue();
            if (insertable(tableModel, fieldModel.getEntityFieldName())) {
                if (sb.length() > 0) {
                    sb.append(",");
                    sb3.append(",");
                }
                // 拼接字段名
                sb.append(fieldModel.getTableFieldName());
                // 拼接字段的值
                Method getMethod = clazz.getMethod("get" + firstUpper(fieldModel.getEntityFieldName()));
                Object value = getMethod.invoke(object);
                sb3.append("'").append(value).append("'");
            }

        }
        String sql = "INSERT INTO " + tableName + "(" + sb.toString() + ") VALUES (" + sb3.toString() + ")";
        log.info("insert sql : {}", sql);
        return sql;
    }

    /**
     * 构造单表的update语句
     *
     * @param object
     */
    public String updateByPrimaryKey(Object object) throws Exception {
        TableModel tableModel = parseEntity(object);
        if (tableModel == null) {
            throw new RuntimeException("table name is empty");
        }
        String tableName = tableModel.getTableName();
        List<String> idFieldList = tableModel.getIdList();
        Map<String, FieldModel> fieldMap = tableModel.getFieldMap();
        StringBuilder sets = new StringBuilder();
        StringBuilder pks = new StringBuilder();
        // 遍历字段
        for (Map.Entry<String, FieldModel> map : fieldMap.entrySet()) {
            FieldModel entity = map.getValue();
            String entityFieldName = entity.getEntityFieldName();
            String tableFieldName = entity.getTableFieldName();
            if (idFieldList.contains(entityFieldName)) {
                if (pks.length() > 0) {
                    pks.append(" and ");
                }
                pks.append(tableFieldName).append("='")
                        .append(getFieldValue(object, entityFieldName)).append("'");
            } else {
                if (updatable(tableModel.getAllFieldList(), entityFieldName)) {
                    if (sets.length() > 0) {
                        sets.append(",");
                    }
                    sets.append(tableFieldName).append("='").append(getFieldValue(object, entityFieldName)).append("'");
                }
            }
        }
        if (pks.length() <= 0) {
            throw new RuntimeException("no where condition");
        }
        if (sets.length() <= 0) {
            throw new RuntimeException("no set statement");
        }

        String sql = "UPDATE " + tableName + " SET " + sets.toString() + " WHERE " + pks.toString();
        log.info("update sql : {}", sql);
        return sql;
    }


    public String findList(Object object) throws Exception {
        TableModel tableModel = parseEntity(object);
        if (tableModel == null) {
            throw new RuntimeException("table name is empty");
        }
        String tableName = tableModel.getTableName();
        Map<String, FieldModel> fieldMap = tableModel.getFieldMap();
        StringBuilder sb = new StringBuilder();
        StringBuilder pks = new StringBuilder();
        for (Map.Entry<String, FieldModel> map : fieldMap.entrySet()) {
            FieldModel fieldModel = map.getValue();
            String entityFieldName = fieldModel.getEntityFieldName();
            String tableFieldName = fieldModel.getTableFieldName();
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(tableFieldName).append(" AS ").append(entityFieldName);
            Object value = getFieldValue(object, entityFieldName);
            if (value != null && !"".equals(value.toString())) {
                if (pks.length() > 0) {
                    pks.append(" and ");
                }
                pks.append(tableFieldName).append("='")
                        .append(getFieldValue(object, entityFieldName)).append("'");
            }
        }
        String sql = "SELECT " + sb.toString() + " FROM " + tableName;
        if (pks.length() > 0) {
            sql = "SELECT " + sb.toString() + " FROM " + tableName + " WHERE " + pks.toString();
        }
        log.info(sql);
        return sql;
    }

    /**
     * 根据主键查找单条记录
     *
     * @param object
     * @return
     * @throws Exception
     */
    public String findByPrimaryKey(Object object) throws Exception {
        TableModel tableModel = parseEntity(object);
        if (tableModel == null) {
            throw new RuntimeException("table name is empty");
        }
        String tableName = tableModel.getTableName();
        List<String> idFieldList = tableModel.getIdList();
        Map<String, FieldModel> fieldMap = tableModel.getFieldMap();
        StringBuilder sb = new StringBuilder();
        StringBuilder pks = new StringBuilder();
        for (Map.Entry<String, FieldModel> map : fieldMap.entrySet()) {
            FieldModel fieldModel = map.getValue();
            String entityFieldName = fieldModel.getEntityFieldName();
            String tableFieldName = fieldModel.getTableFieldName();
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(tableFieldName).append(" AS ").append(entityFieldName);
            if (idFieldList.contains(entityFieldName)) {
                if (pks.length() > 0) {
                    pks.append(" and ");
                }
                pks.append(tableFieldName).append("='")
                        .append(getFieldValue(object, entityFieldName)).append("'");
            }
        }
        String sql = "SELECT " + sb.toString() + " FROM " + tableName + " WHERE " + pks.toString();
        log.info(sql);
        return sql;
    }

    /**
     * 根据主键物理删除记录
     *
     * @param object
     * @return
     * @throws Exception
     */
    public String deleteByPrimaryKey(Object object) throws Exception {
        TableModel tableModel = parseEntity(object);
        if (tableModel == null) {
            throw new RuntimeException("table name is empty");
        }
        String tableName = tableModel.getTableName();
        List<String> idFieldList = tableModel.getIdList();
        Map<String, FieldModel> fieldMap = tableModel.getFieldMap();
        StringBuilder pks = new StringBuilder();
        for (Map.Entry<String, FieldModel> map : fieldMap.entrySet()) {
            FieldModel fieldModel = map.getValue();
            String entityFieldName = fieldModel.getEntityFieldName();
            String tableFieldName = fieldModel.getTableFieldName();
            if (idFieldList.contains(entityFieldName)) {
                if (pks.length() > 0) {
                    pks.append(" and ");
                }
                Object value = getFieldValue(object, entityFieldName);
                if (value == null || "".equals(value.toString())) {
                    throw new RuntimeException("primary key not empty !");
                }
                pks.append(tableFieldName).append("='").append(value).append("'");
            }
        }
        if (pks.length() <= 0) {
            throw new RuntimeException("no where condition");
        }
        String sql = "DELETE FROM " + tableName + " WHERE " + pks.toString();
        log.info(sql);
        return sql;
    }

    /**
     * 删除
     *
     * @param object
     * @return
     * @throws Exception
     */
    public String delete(Object object) throws Exception {
        TableModel tableModel = parseEntity(object);
        if (tableModel == null) {
            throw new RuntimeException("table name is empty");
        }
        String tableName = tableModel.getTableName();
        Map<String, FieldModel> fieldMap = tableModel.getFieldMap();
        List<String> whereList = new ArrayList<String>(16);
        for (Map.Entry<String, FieldModel> map : fieldMap.entrySet()) {
            FieldModel fieldModel = map.getValue();
            String entityFieldName = fieldModel.getEntityFieldName();
            String tableFieldName = fieldModel.getTableFieldName();
            Object value = getFieldValue(object, entityFieldName);
            if (value != null && !"".equals(value.toString())) {
                whereList.add(tableFieldName + "='" + value.toString() + "'");
            }
        }
        if (whereList.size() <= 0) {
            throw new RuntimeException("no where condition");
        }
        StringBuilder where = new StringBuilder();
        for (String s : whereList) {
            if (where.length() > 0) {
                where.append(" and ");
            }
            where.append(s);
        }
        String sql = "DELETE FROM " + tableName + " WHERE " + where.toString();
        log.info(sql);
        return sql;
    }

    /**
     * 根据主键逻辑删除记录
     *
     * @param object              对象实例，已经为主键赋值
     * @param dataStatusFieldName 标识数据状态的列名
     * @param dataStatusValue     标识数据被删除的状态值
     * @return
     * @throws Exception
     */
    public String logicRemoveByPrimaryKey(Object object, String dataStatusFieldName, int dataStatusValue) throws Exception {
        TableModel tableModel = parseEntity(object);
        if (tableModel == null) {
            throw new RuntimeException("table name is empty");
        }
        String tableName = tableModel.getTableName();
        List<String> idFieldList = tableModel.getIdList();
        Map<String, FieldModel> fieldMap = tableModel.getFieldMap();
        StringBuilder pks = new StringBuilder();
        for (Map.Entry<String, FieldModel> map : fieldMap.entrySet()) {
            FieldModel fieldModel = map.getValue();
            String entityFieldName = fieldModel.getEntityFieldName();
            String tableFieldName = fieldModel.getTableFieldName();
            if (idFieldList.contains(entityFieldName)) {
                if (pks.length() > 0) {
                    pks.append(" and ");
                }
                Object value = getFieldValue(object, entityFieldName);
                if (value == null || "".equals(value.toString())) {
                    throw new RuntimeException("primary key value is empty");
                }
                pks.append(tableFieldName).append("='").append(value).append("'");
            }
        }
        if (pks.length() <= 0) {
            throw new RuntimeException("no where condition");
        }
        String sql = "UPDATE " + tableName + " SET " + dataStatusFieldName + "='" + dataStatusValue + "' WHERE " + pks.toString();
        log.info(sql);
        return sql;
    }

    /**
     * 获取有id注解的字段
     *
     * @param fieldList
     * @return 所有有id注解的字段
     */
    private static List<String> getIdFields(List<Field> fieldList) {
        List<String> idList = new ArrayList<String>(16);
        for (Field field : fieldList) {
            Id id = field.getAnnotation(Id.class);
            if (id != null) {
                idList.add(field.getName());
            }
        }
        return idList;
    }

    /**
     * 解析添加了Column注解的字段
     *
     * @param field
     * @return
     */
    private FieldModel getFieldModel(Field field) {
        // 获取字段Column注解
        Column column = field.getAnnotation(Column.class);
        if (column != null) {
            FieldModel model = new FieldModel();
            model.setEntityFieldName(field.getName());
            // 注解中没有name就使用字段名
            if ("".equals(column.name())) {
                model.setTableFieldName(field.getName());
            } else {
                model.setTableFieldName(column.name());
            }
            return model;
        } else { // 获取Id注解
            Id id = field.getAnnotation(Id.class);
            if (id != null) {
                FieldModel model = new FieldModel();
                model.setEntityFieldName(field.getName());
                model.setTableFieldName(field.getName());
                return model;
            }
        }
        return null;
    }

    /**
     * 获取字段模型
     *
     * @param fieldList
     * @return
     */
    private Map<String, FieldModel> getFieldModelMap(List<Field> fieldList) {
        Map<String, FieldModel> map = new HashMap<String, FieldModel>(16);
        for (Field field : fieldList) {
            FieldModel model = getFieldModel(field);
            if (model != null) {
                map.put(field.getName(), model);
            }
        }
        return map;
    }

    /**
     * 获取class的table注解
     *
     * @param object
     * @return 返回表名
     */
    private String getTableName(Object object) {
        Table table = object.getClass().getAnnotation(Table.class);
        if (table != null) {
            if ("".equals(table.name())) {
                // TODO table注解没有指定表名时使用类名，具体规则待完善
                return object.getClass().getSimpleName();
            }
            return table.name();
        }
        throw new RuntimeException("no table name");
    }

    /**
     * 解析entity实例，获取表名，字段
     *
     * @param object
     */
    private TableModel parseEntity(Object object) {
        TableModel tableModel = new TableModel();
        tableModel.setTableName(getTableName(object));

        Class clazz = object.getClass();
        List<Field> fieldList = new ArrayList<Field>(16);
        while (clazz != null) {
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        tableModel.setIdList(getIdFields(fieldList));
        tableModel.setFieldMap(getFieldModelMap(fieldList));
        tableModel.setAllFieldList(fieldList);
        return tableModel;
    }

    /**
     * 获取对象实例中指定字段的值
     *
     * @param object
     * @param fieldName
     * @return
     * @throws Exception
     */
    private Object getFieldValue(Object object, String fieldName) throws Exception {
        Method getMethod = object.getClass().getMethod("get" + firstUpper(fieldName));
        Object value = getMethod.invoke(object);
        return value;
    }

    /**
     * 字符串首字母大小
     *
     * @param s
     * @return
     */
    private String firstUpper(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * 判断字段是否需要在insert时写入sql语句
     *
     * @param tableModel      对象模型
     * @param entityFieldName 需要判断的字段名
     * @return false时不把字段写入到sql中
     * @throws Exception
     */
    private boolean insertable(TableModel tableModel, String entityFieldName) {
        for (Field field : tableModel.getAllFieldList()) {
            if (tableModel.getIdList().contains(entityFieldName)) {
                return true;
            }
            if (entityFieldName.equals(field.getName())) {
                Column column = field.getAnnotation(Column.class);
                return column != null && column.insertable();
            }
        }

        return false;
    }

    /**
     * 判断字段是否需要在update时写入sql语句
     *
     * @param fieldList       字段列表
     * @param entityFieldName 需要判断的字段名
     * @return false时不把字段写入到sql中
     * @throws Exception
     */
    private boolean updatable(List<Field> fieldList, String entityFieldName) {
        for (Field field : fieldList) {
            if (entityFieldName.equals(field.getName())) {
                Column column = field.getAnnotation(Column.class);
                return column != null && column.updatable();
            }
        }
        return false;
    }

    /**
     * 1、table注解没哟指定表名
     * 2、多个id注解
     * 3、column注解没有指定name
     * 4、column注解insertable、updatable为false
     * 5、父类字段
     *
     * @param args
     * @throws Exception
     */

    public static void main(String[] args) throws Exception {
        User user = new User();
        user.setId("1111");
        user.setPassword("22222");
        user.setUsername("3333333");
        user.setRemark("44444444444444444");

        String sql = new SqlUtil().insert(user);
        sql = new SqlUtil().updateByPrimaryKey(user);
        /*sql = new SqlUtil().findByPrimaryKey(user);
        sql = new SqlUtil().deleteByPrimaryKey(user);
        sql = new SqlUtil().findList(user);
        sql = new SqlUtil().findList(new User());*/
    }

    @Data
    class FieldModel {
        /**
         * 表中的列名
         */
        private String tableFieldName;
        /**
         * entity中的字段名
         */
        private String entityFieldName;
    }

    @Data
    class TableModel {
        /**
         * 表名
         */
        private String tableName;
        /**
         * 添加了id注解的字段List
         */
        private List<String> idList;
        /**
         * 字段及model模型
         */
        private Map<String, FieldModel> fieldMap;
        /**
         * 所有字段
         */
        private List<Field> allFieldList;
    }
}
