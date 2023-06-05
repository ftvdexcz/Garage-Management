CREATE DATABASE garage_management_car_repair_service;

USE garage_management_car_repair_service;

CREATE TABLE car(
	`id` varchar(36) NOT NULL,
	plate varchar(20) not null,
    
    customer_id varchar(36) not null,
    PRIMARY KEY (`id`),
	UNIQUE KEY `plate` (`plate`)
);

CREATE TABLE car_repair (
  `id` varchar(36) NOT NULL,
  `status` tinyint NOT NULL,
  `date` date DEFAULT NULL,
  note text default null,
  
  employee_id varchar(36) DEFAULT NULL,
  car_id varchar(36) not null,
	FOREIGN KEY (car_id) REFERENCES car(id),
  PRIMARY KEY (`id`)
);

CREATE TABLE accessory_used (
  `id` varchar(36) NOT NULL,
  
  car_repair_id varchar(36) not null,
  accessory_id varchar(36) not null,
  quantity int not null,
  amount double not null,
  
  PRIMARY KEY (`id`),
  FOREIGN KEY (car_repair_id) REFERENCES car_repair(id)
);

CREATE TABLE service_used (
  `id` varchar(36) NOT NULL,
  
  car_repair_id varchar(36) not null,
  service_id varchar(36) not null,
  amount double not null,
  
  PRIMARY KEY (`id`),
  FOREIGN KEY (car_repair_id) REFERENCES car_repair(id)
);

CREATE TABLE bill (
  `id` varchar(36) NOT NULL,
  amount double not null,
  `payment_date` date NOT NULL,
  car_repair_id varchar(36) not null,
  
  PRIMARY KEY (`id`),
  FOREIGN KEY (car_repair_id) REFERENCES car_repair(id)
);


