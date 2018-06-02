DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_code` varchar(255) DEFAULT NULL,
  `account_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO `account` VALUES ('1', 'tianyalan', 'tianmin zheng');
INSERT INTO `account` VALUES ('2', 'tianyalan2', 'tianmin zheng2');