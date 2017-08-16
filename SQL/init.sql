create table product (
	id int PRIMARY KEY auto_increment,
	ean varchar(12),
	name varchar(20),
	usdprice decimal(10,2)
);

create table package_object (
	id int PRIMARY KEY auto_increment,
	name varchar(50),
	description int,
	price decimal(10,2)
);

create table package_product (
	product_id int,
	package_id int
);