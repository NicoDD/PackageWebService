create database package_manager;

use package_manager;

create table product (
	id int PRIMARY KEY auto_increment,
	ean varchar(12) unique,
	name varchar(20),
	usdprice decimal(10,2)
);

create table package_object (
	id int PRIMARY KEY auto_increment,
	name varchar(50),
	description varchar(300),
	price decimal(10,2)
);

create table package_product (
	product_id int,
	package_id int,
    primary key (product_id, package_id),
    foreign key (product_id) references product(id),
    foreign key (package_id) references package_object(id)
);