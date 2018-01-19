CREATE TABLE IF NOT EXISTS `{tasks_table_name}` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `command` varchar(255) NOT NULL,
  `sender` varchar(255),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `{conditions_table_name}` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `task_id` int(11) unsigned NOT NULL,
  `condition` varchar(255) NOT NULL,
  `value` varchar(255),
  PRIMARY KEY (`id`),
  CONSTRAINT `{conditions_table_name}` FOREIGN KEY (task_id) REFERENCES `{tasks_table_name}` (id) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;