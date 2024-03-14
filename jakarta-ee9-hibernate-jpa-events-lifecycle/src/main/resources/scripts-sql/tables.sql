-- Execute manually this script otherwise see resources/META-INF/persistence.xml the property:
-- <property name="jakarta.persistence.schema-generation.database.action" value="drop-and-create"/>
-- will execute the @Entity classes and resources/import.sql file in the Database.
CREATE TABLE client (
	id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(45) NOT NULL,
	surname VARCHAR(45) NOT NULL,
	payment_type VARCHAR(45) NOT NULL
);

INSERT INTO client (name, surname, payment_type) VALUES ("kevin", "piña", "visa");
INSERT INTO client (name, surname, payment_type) VALUES ("javier", "calatrava", "master");
INSERT INTO client (name, surname, payment_type) VALUES ("anna", "boron", "american express");
INSERT INTO client (name, surname, payment_type) VALUES ("monika", "boron", "master");

ALTER TABLE client ADD COLUMN created DATETIME NULL DEFAULT NULL AFTER payment_type;
ALTER TABLE client ADD COLUMN modified DATETIME NULL DEFAULT NULL AFTER created;

CREATE TABLE client_new (
	id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(45) NOT NULL,
	surname VARCHAR(45) NOT NULL,
	payment_type VARCHAR(45) NOT NULL
);

INSERT INTO client_new (name, surname, payment_type) VALUES ("kevin", "piña", "visa");
INSERT INTO client_new (name, surname, payment_type) VALUES ("javier", "calatrava", "master");
INSERT INTO client_new (name, surname, payment_type) VALUES ("anna", "boron", "american express");
INSERT INTO client_new (name, surname, payment_type) VALUES ("monika", "boron", "master");

ALTER TABLE client_new ADD COLUMN created DATETIME NULL DEFAULT NULL AFTER payment_type;
ALTER TABLE client_new ADD COLUMN modified DATETIME NULL DEFAULT NULL AFTER created;
