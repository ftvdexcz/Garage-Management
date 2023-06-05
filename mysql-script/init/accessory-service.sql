CREATE DATABASE garage_management_accessory_service;

USE garage_management_accessory_service;

CREATE TABLE supplier (
  `id` varchar(36) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone` varchar(10) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `supplier_name` (`name`)
);

CREATE TABLE accessory (
  `id` varchar(36) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `quantity` int DEFAULT NULL,
  image_source varchar(255) DEFAULT NULL,
  supplier_id varchar(36) not null,
  
  PRIMARY KEY (`id`),
  CONSTRAINT FK_Supplier FOREIGN KEY (supplier_id) REFERENCES supplier(id),
  UNIQUE KEY `accessory_name` (`name`)
);

CREATE TABLE accessory_purchased (
  `id` varchar(36) NOT NULL,
  `quantity` int NOT NULL,
  amount double NOT NULL,
  purchased_date date NOT NULL, 
  accessory_id varchar(36) not null,
  employee_id varchar(36) not null,
  
  PRIMARY KEY (`id`),
  FOREIGN KEY (accessory_id) REFERENCES accessory(id)
);

CREATE TABLE service (
  `id` varchar(36) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` double NOT NULL,
  note varchar(255) DEFAULT NULL,
  
  PRIMARY KEY (`id`),
  UNIQUE KEY `service_name` (`name`)
);


