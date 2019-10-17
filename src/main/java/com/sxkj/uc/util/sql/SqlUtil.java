package com.sxkj.uc.util.sql;

import com.sxkj.uc.entity.User;
import com.sxkj.uc.entity.base.BaseEntity;
import lombok.Data;
import org.thymeleaf.spring5.expression.Fields;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author zwd
 */
public class SqlUtil {

    private String tableName = null;

    private List<String> idFieldList = null;

    private Map<String,Model> fieldMap = null;

    private Object object;

    public SqlUtil(Object object){
        this.object = object;
        parseEntity();
    }

    public String insert() throws Exception{
        Class clazz = object.getClass();
        StringBuilder sb = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        for (Map.Entry<String, Model> map : fieldMap.entrySet()) {
            Model entity = map.getValue();
            if(sb.length()>0) {
                sb.append(",");
                sb3.append(",");
            }
            sb.append(entity.getTableFieldName());
            Method getMethod = clazz.getMethod("get"+firstUpper(entity.getEntityFieldName()));
            Object value = getMethod.invoke(object);
            sb3.append("'").append(value).append("'");
        }
        String sql = "INSERT INTO " + tableName + "(" + sb.toString() + ") VALUES (" + sb3.toString() + ")";

        return sql;
    }

    private void parseEntity() {
        if(isBaseEntity(object)){
            tableName = getTableName(object);

            Class clazz = object.getClass();
            Field[] fields = clazz.getDeclaredFields();
            // 主键字段名称和值
            idFieldList = getIdFields(fields);
            // 其他字段
            fieldMap = getFieldsModel(fields);
        }
    }

    /**
     * 对象实例类型检查，只处理继承了BaseEntity的
     * @param object
     * @return
     */
    private static boolean isBaseEntity(Object object) {
        return object instanceof BaseEntity;
    }
    /**
     * 获取class的table注解
     * @param object
     * @return 返回表名
     */
    private String getTableName(Object object) {
        Table table = object.getClass().getAnnotation(Table.class);
        if (table != null) {
            return table.name();
        }
        return null;
    }

    /**
     * 获取字段的column注解
     * @param field
     * @return
     */
    private Model getModel(Field field) {
        Column column = field.getAnnotation(Column.class);
        if (column.updatable() || column.insertable()) {
            Model entity = new Model();
            entity.setEntityFieldName(field.getName());
            entity.setTableFieldName(column.name());

            return entity;
        }
        return null;
    }

    private Map<String, Model> getFieldsModel(Field[] fields) {
        Map<String, Model> map = new HashMap<>(16);
        for (Field field : fields) {
            Model entity = getModel(field);
            if (entity != null) {
                map.put(field.getName(),entity);
            }
        }
        return map;
    }
    /**
     * 获取有id注解的字段
     * @param fields
     * @return 所有有id注解的字段
     */
    private static List<String> getIdFields(Field[] fields) {
        List<String> idList = new ArrayList<>(16);
        for (Field field : fields) {
            Id id = field.getAnnotation(Id.class);
            if (id != null) {
                idList.add(field.getName());
            }
        }
        return idList;
    }

    private String firstUpper(String s){
        return s.substring(0,1).toUpperCase()+s.substring(1);
    }
    @Data
    class Model{
        private String tableFieldName;
        private String entityFieldName;
    }

    public static void main(String[] args) throws Exception{
        User user = new User();
        user.setId("1111");
        user.setLoginPassword("22222");
        user.setLoginName("3333333");

        String sql = new SqlUtil(user).insert();
        System.err.println(sql);
    }
}
