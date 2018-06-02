DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `product_code` varchar(255) DEFAULT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO `product` VALUES ('1', 'New Book For Microservie By Tianyalan', '100', 'code1', 'Microservie Practices');