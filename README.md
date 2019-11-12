# 说明
    构建简单的用户管理中心，以实现多应用管理，统一登录
    
## 表结构

    CREATE TABLE `t_user` (
       `id` varchar(50) NOT NULL COMMENT '编号',
       `login_name` varchar(50) DEFAULT NULL COMMENT '登录名称',
       `login_password` varchar(100) DEFAULT NULL COMMENT '密码',
       `real_name` varchar(50) DEFAULT NULL COMMENT '真名称',
       `telephone` varchar(50) DEFAULT NULL COMMENT '联系电话',
       `work_place` varchar(200) DEFAULT NULL COMMENT '工作单位',
       `create_id` varchar(50) DEFAULT NULL COMMENT '创建人',
       `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
       `last_update_id` varchar(50) DEFAULT NULL COMMENT '修改人',
       `last_update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
       `remark` varchar(200) DEFAULT NULL COMMENT '备注',
       `status` tinyint(4) DEFAULT NULL COMMENT '数据状态',
       PRIMARY KEY (`id`)
     ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户';
     
    CREATE TABLE `t_app` (
       `id` varchar(50) NOT NULL COMMENT '编号',
       `name` varchar(100) DEFAULT NULL COMMENT '名称',
       `url` varchar(200) DEFAULT NULL COMMENT '应用地址',
       `cn_name` varchar(200) DEFAULT NULL COMMENT '应用中文名称',
       `create_id` varchar(50) DEFAULT NULL COMMENT '创建人',
       `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
       `last_update_id` varchar(50) DEFAULT NULL COMMENT '修改人',
       `last_update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
       `remark` varchar(200) DEFAULT NULL COMMENT '备注',
       `status` tinyint(4) DEFAULT NULL COMMENT '数据状态',
       PRIMARY KEY (`id`)
     ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='应用表';
     
    CREATE TABLE `t_user_app` (
       `id` varchar(50) NOT NULL COMMENT '编号',
       `user_id` varchar(50) DEFAULT NULL COMMENT '用户id',
       `app_id` varchar(50) DEFAULT NULL COMMENT '应用id',
       `login_name` varchar(50) DEFAULT NULL COMMENT '关联应用的登录名',
       `login_password` varchar(100) DEFAULT NULL COMMENT '关联应用的登录密码',
       `create_id` varchar(50) DEFAULT NULL COMMENT '创建人',
       `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
       `last_update_id` varchar(50) DEFAULT NULL COMMENT '修改人',
       `last_update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
       `remark` varchar(200) DEFAULT NULL COMMENT '备注',
       `status` tinyint(4) DEFAULT NULL COMMENT '数据状态',
       PRIMARY KEY (`id`)
     ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户-应用关联表';
     
    CREATE TABLE `t_on_line` (
       `id` varchar(50) NOT NULL COMMENT '编号',
       `user_id` varchar(50) DEFAULT NULL COMMENT '用户id',
       `user_real_name` varchar(50) DEFAULT NULL COMMENT '用户真名',
       `token` varchar(500) DEFAULT NULL,
       `create_id` varchar(50) DEFAULT NULL COMMENT '创建人',
       `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
       `last_update_id` varchar(50) DEFAULT NULL COMMENT '修改人',
       `last_update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
       `remark` varchar(200) DEFAULT NULL COMMENT '备注',
       `status` tinyint(4) DEFAULT NULL COMMENT '数据状态',
       PRIMARY KEY (`id`)
     ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='在线用户';     
     
## 代码

    SignController：登录

         