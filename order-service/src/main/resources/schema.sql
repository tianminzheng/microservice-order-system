DROP TABLE IF EXISTS `order`;

CREATE TABLE `order`
(
  id              BIGINT(20) PRIMARY KEY NOT NULL,
  created_at      BIGINT(20),
  accountId   BIGINT(20) NOT NULL,
  item         VARCHAR(255) NOT NULL
);
