INSERT INTO `client` (`id`, `name`, `surname`, `payment_type`) VALUES ("1", "kevin", "pi?a", "visa");
INSERT INTO `client` (`id`, `name`, `surname`, `payment_type`) VALUES ("2", "javier", "calatrava", "master");
INSERT INTO `client` (`id`, `name`, `surname`, `payment_type`) VALUES ("3", "anna", "boron", "american express");
INSERT INTO `client` (`id`, `name`, `surname`, `payment_type`) VALUES ("4", "monika", "boron", "master");
INSERT INTO `client` (`id`, `name`, `surname`, `payment_type`) VALUES ("5", "Franz", "Schubert", "wechat");
INSERT INTO `client` (`id`, `name`, `surname`, `payment_type`) VALUES ("6", "ludwig", "van bethoveen", "paypal");
INSERT INTO `client` (`id`, `name`, `surname`, `payment_type`) VALUES ("7", "George", "Bizete", "paypal");
INSERT INTO `client` (`id`, `name`, `surname`, `payment_type`) VALUES ("8", "Gustav", "Mahler", "wechat");
INSERT INTO `client` (`id`, `name`, `surname`, `payment_type`) VALUES ("9", "guillermo", "rossini", "master");

INSERT INTO `address` (`streetName`, `number`) VALUES ("Lomas del Avila, Loma Suite 2, A-14", 2);
INSERT INTO `address` (`streetName`, `number`) VALUES ("Carmen 120, 703", 120);
INSERT INTO `address` (`streetName`, `number`) VALUES ("Alonso Núñez 29, 1C", 29);

INSERT INTO `tbl_client_address` (`id_client`, `id_address`) VALUES (1, 1);
INSERT INTO `tbl_client_address` (`id_client`, `id_address`) VALUES (1, 2);
INSERT INTO `tbl_client_address` (`id_client`, `id_address`) VALUES (2, 3);

INSERT INTO `document` (`type`, client_id) VALUES(1, 1);

INSERT INTO `student` (`name`, `surname`) VALUES ("kevin", "piña");
INSERT INTO `student` (`name`, `surname`) VALUES ("javier", "calatrava");
INSERT INTO `student` (`name`, `surname`) VALUES ("anna", "boron");
INSERT INTO `student` (`name`, `surname`) VALUES ("monika", "boron");
INSERT INTO `course` (`id`, `title`, `professor`) VALUES (1, "Species Origin", "Charles Darwin");
INSERT INTO `course` (`id`, `title`, `professor`) VALUES (2, "Relative Theory", "Albert Einstein");
INSERT INTO `tbl_student_course` (`student_id`, `course_id`) VALUES (1, 1);
INSERT INTO `tbl_student_course` (`student_id`, `course_id`) VALUES (1, 2);
INSERT INTO `tbl_student_course` (`student_id`, `course_id`) VALUES (2, 1);
INSERT INTO `tbl_student_course` (`student_id`, `course_id`) VALUES (3, 1);
INSERT INTO `tbl_student_course` (`student_id`, `course_id`) VALUES (3, 2);
INSERT INTO `tbl_student_course` (`student_id`, `course_id`) VALUES (4, 2);

INSERT INTO `invoice` (`description`, `total`, `id_client`) VALUES ('office', 4000, 1);
INSERT INTO `invoice` (`description`, `total`, `id_client`) VALUES ('house', 2000, 1);
INSERT INTO `invoice` (`description`, `total`, `id_client`) VALUES ('sports', 3500, 1);
INSERT INTO `invoice` (`description`, `total`, `id_client`) VALUES ('computer', 7125, 2);