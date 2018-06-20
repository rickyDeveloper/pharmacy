
DROP TABLE users;

CREATE TABLE users (
   id INT NOT NULL,
   user_name VARCHAR(50) NOT NULL,
   first_name VARCHAR(50) NOT NULL,
   last_name VARCHAR(50) NOT NULL,
   role VARCHAR(50) NOT NULL,
   PRIMARY  KEY(id)
);

INSERT INTO users ( id, user_name, first_name,last_name, role) VALUES (1,'doctor', 'Dhaval', 'Shah', 'DOCTOR');

INSERT INTO users ( id, user_name, first_name,last_name, role) VALUES (2,'patient', 'Vikas', 'Naiyar', 'PATIENT');

INSERT INTO users ( id, user_name, first_name,last_name, role) VALUES (3,'pharmacist', 'Nishu', 'Goyal', 'PHARMACIST');


DROP TABLE prescriptions;

CREATE TABLE prescriptions (
   id INT NOT NULL,
   user_id INT NOT NULL,
   text VARCHAR(500) NOT NULL,
   PRIMARY  KEY(id)
);

INSERT INTO prescriptions ( id, user_id, text) VALUES (1,2,  'Viral Fever, Paracetamol 500 mg 1-0-1, Calpol 0-1-0');
INSERT INTO prescriptions ( id, user_id, text) VALUES (2,2,  'Dengu, Dengu Injection 500 mg, Panadol 1-1-1');
INSERT INTO prescriptions ( id, user_id, text) VALUES (3,2,  'Cough & Cold, Vicks action 500 tablet, Hot water garling twice a day');
INSERT INTO prescriptions ( id, user_id, text) VALUES (4,2,  'Jaundice, Drink hot water, Eat less spicy food');


DROP TABLE view_prescriptions;

CREATE TABLE view_prescriptions (
   id INT NOT NULL,
   prescription_id INT NOT NULL,
   user_id INT NOT NULL,
   status VARCHAR(50) NOT NULL,
   PRIMARY  KEY(id)
);

--INSERT INTO view_prescriptions ( id, prescription_id, user_id, status) VALUES (1,1,2,'PENDING');
--INSERT INTO view_prescriptions ( id, prescription_id, user_id, status) VALUES (2,2,2,'ACCEPTED');
--INSERT INTO view_prescriptions ( id, prescription_id, user_id, status) VALUES (3,3,2,'DECLINED');
