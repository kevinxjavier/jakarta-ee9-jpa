-- Execute manually this script
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
