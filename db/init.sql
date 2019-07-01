
CREATE TABLE IF NOT EXISTS `t_key_generator` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `business_id` varchar(128) NOT NULL DEFAULT '' COMMENT '业务id',
  `max_id` bigint(20) NOT NULL DEFAULT '1' COMMENT '最大id',
  `step` int(11) NOT NULL COMMENT '步长',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_business_id` (`business_id`)
) ENGINE=InnoDB  COMMENT='分布式自增主键' ;