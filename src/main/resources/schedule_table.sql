CREATE TABLE IF NOT EXISTS `{queue_table_name}` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `command` varchar(255) NOT NULL,
  `conditions` varchar(255),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;