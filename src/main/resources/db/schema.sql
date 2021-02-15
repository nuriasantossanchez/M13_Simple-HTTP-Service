DROP TABLE IF EXISTS `employee`;
DROP table IF EXISTS  `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `jobtitle` varchar(250) DEFAULT NULL,
  `annualsalary` decimal(9,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(250) DEFAULT NULL,
  `lastname` varchar(250) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `role_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
);


