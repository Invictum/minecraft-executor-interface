CREATE TABLE IF NOT EXISTS `{schedule_table_name}` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `schedule` varchar(255) NOT NULL,
  `command` varchar(255) NOT NULL,
  `conditions` varchar(255),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;