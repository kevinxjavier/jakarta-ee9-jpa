DROP DATABASE enterprise;

------------------------------------------

CREATE DATABASE enterprise;

------------------------------------------

USE enterprise;

------------------------------------------

CREATE TABLE product (
	id int PRIMARY KEY AUTO_INCREMENT, 
	name VARCHAR(30), 
	price FLOAT, 
	date DATE
);

INSERT INTO product (name, price, date) VALUES ('TV Samsung TH580', 568.99, '2023-05-28'), ('Raspberry Pi 4 8GB', 89.56, '2023-05-28');

------------------------------------------

CREATE TABLE category (
	id int PRIMARY KEY AUTO_INCREMENT, 
	name VARCHAR(30)
);

INSERT INTO category (name) VALUES ('Deporte'), ('Tecnologia'), ('Computacion'); 

------------------------------------------

ALTER TABLE product ADD COLUMN category_id int;

ALTER TABLE product ADD INDEX product_category_idx (category_id);  

ALTER TABLE product 
ADD CONSTRAINT product_category 
FOREIGN KEY (category_id) 
REFERENCES category(id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

------------------------------------------

UPDATE product SET category_id = 3;

------------------------------------------

ALTER TABLE product ADD COLUMN sku VARCHAR(10) NULL AFTER category_id, ADD UNIQUE INDEX sku_unique (sku ASC);

UPDATE product SET sku = 'abcde12345' WHERE id = 1;
UPDATE product SET sku = 'abcdf12345' WHERE id = 2;

------------------------------------------
-- RESUME
DROP TABLE product;
TRUNCATE category;
CREATE TABLE product(id int PRIMARY KEY auto_increment, name VARCHAR(30), prise FLOAT, date DATE);
ALTER TABLE product ADD COLUMN category_id int;
ALTER TABLE product ADD INDEX producto_category_idx (category_id);  
ALTER TABLE product ADD CONSTRAINT product_category FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE product ADD COLUMN sku VARCHAR(10) NULL AFTER category_id, ADD UNIQUE INDEX sku_unique (sku ASC) VISIBLE;
INSERT INTO category (name) VALUES ('Deporte'), ('Tecnologia'), ('Computacion');
INSERT INTO product (name, prise, date) VALUES ('TV Samsung TH580', 568.99, '2023-05-28'), ('Raspberry Pi 4 8GB', 89.56, '2023-05-28'); 
UPDATE product SET sku = 'abcde12345' WHERE id = 1;
UPDATE product SET sku = 'abcdf12345' WHERE id = 2;
UPDATE product set category_id = 3;
SELECT * FROM product;
