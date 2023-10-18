-- Dummy data for User Table
-- Default password: 123456
INSERT INTO user (user_id, password, username, role) VALUES (100001,'$2a$10$B4FvjVc2oXqDTFdGGZjT5urzHS6TSdzrQ7p/vFi1rUuZgjXR4.m5K', 'admin', 'Admin');
INSERT INTO user (user_id, password, username, role) VALUES (100002,'$2a$10$B4FvjVc2oXqDTFdGGZjT5urzHS6TSdzrQ7p/vFi1rUuZgjXR4.m5K', 'thaitd', 'User');
INSERT INTO user (user_id, password, username, role) VALUES (100003,'$2a$10$B4FvjVc2oXqDTFdGGZjT5urzHS6TSdzrQ7p/vFi1rUuZgjXR4.m5K', 'hungnt', 'User');
INSERT INTO user (user_id, password, username, role) VALUES (100004,'$2a$10$B4FvjVc2oXqDTFdGGZjT5urzHS6TSdzrQ7p/vFi1rUuZgjXR4.m5K', 'nguyendq', 'User');
INSERT INTO user (user_id, password, username, role) VALUES (100005,'$2a$10$B4FvjVc2oXqDTFdGGZjT5urzHS6TSdzrQ7p/vFi1rUuZgjXR4.m5K', 'huyvq', 'User');

-- Dummy data for Address
INSERT INTO address (address_id, area, city, pincode, state) VALUES (100001,'Hoang Quoc Viet','Nghia Do','198106','Cau Giay');
INSERT INTO address (address_id, area, city, pincode, state) VALUES (100002,'Lang Ha','Lang Ha','307102','Dong Da');
INSERT INTO address (address_id, area, city, pincode, state) VALUES (100003,'An Duong','Yen Phu','477002','Tay Ho');
INSERT INTO address (address_id, area, city, pincode, state) VALUES (100004,'Truong Dinh','Thinh Liet','597223','Hoang Mai');
INSERT INTO address (address_id, area, city, pincode, state) VALUES (100005,'Quang Trung','Yet Kieu','660525','Ha Dong');

-- Dummy data for User detail
INSERT INTO user_details (user_details_id, first_name, last_name, designation, dob, email_id, gender, phone_no, security_question, security_answer, address_id) VALUES (100001, 'Admin', 'Manager', 'Administrator', '1983-05-14', 'admin@gmail.com', 'Male', '8419969059', 'Default Question', 'answer', 100001);
INSERT INTO user_details (user_details_id, first_name, last_name, designation, dob, email_id, gender, phone_no, security_question, security_answer, address_id) VALUES (100002, 'Huy', 'Vu', 'Sales Manager', '1983-12-12', 'huy@mail.com', 'Female', '9876543210', 'Default Question', 'answer', 100002);
INSERT INTO user_details (user_details_id, first_name, last_name, designation, dob, email_id, gender, phone_no, security_question, security_answer, address_id) VALUES (100003, 'Hung', 'Nguyen', 'Sales Team', '1989-04-04', 'hung@mail.com', 'Female', '8877669059', 'Default Question', 'answer', 100003);
INSERT INTO user_details (user_details_id, first_name, last_name, designation, dob, email_id, gender, phone_no, security_question, security_answer, address_id) VALUES (100004, 'Thai', 'Tran', 'Sales Team', '1994-05-11', 'thai@mail.com', 'Male', '3443312345', 'Default Question', 'answer', 100004);
INSERT INTO user_details (user_details_id, first_name, last_name, designation, dob, email_id, gender, phone_no, security_question, security_answer, address_id) VALUES (100005, 'Nguyen', 'Do', 'Sales Team', '1995-01-19', 'nguyen@mail.com', 'Female', '7576788679', 'Default Question', 'answer', 100005);

-- MYSQL sequence
-- UPDATE user_id_sequence SET next_val = 100007;
-- UPDATE address_id_sequence SET next_val = 100007;
-- H2 sequence 
ALTER sequence user_id_sequence restart with 100007;
ALTER sequence address_id_sequence restart with 100007;
