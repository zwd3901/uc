DROP TABLE IF EXISTS t_user;
CREATE TABLE `t_user` (
   `id` varchar(50) NOT NULL,
   `login_name` varchar(30) NOT NULL,
   `login_password` varchar(100) NOT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `login_name` (`login_name`)
 );
