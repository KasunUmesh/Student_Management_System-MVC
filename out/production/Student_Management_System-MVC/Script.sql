DROP DATABASE IF EXISTS ijse;
CREATE DATABASE IF NOT EXISTS ijse;
SHOW DATABASES;
USE ijse; 

------------------------------------------------

DROP TABLE IF EXISTS Student;
CREATE TABLE IF NOT EXISTS Student(
	student_id VARCHAR(45),
	student_name VARCHAR(45),
	email TEXT,
	contact VARCHAR(20),
	address TEXT,
	nic VARCHAR(45),
	CONSTRAINT PRIMARY KEY (student_id)

);
SHOW TABLES;
DESCRIBE Student;

----------------------------------------------------

DROP TABLE IF EXISTS Teacher;
CREATE TABLE IF NOT EXISTS Teacher(
	teacher_id VARCHAR(45),
	name VARCHAR(45),
	nic VARCHAR(45),
	contact VARCHAR(45),
	address TEXT,
	CONSTRAINT PRIMARY KEY (teacher_id)

);
SHOW TABLES;
DESCRIBE Teacher;

-------------------------------------------------------

DROP TABLE IF EXISTS Subject;
CREATE TABLE IF NOT EXISTS Subject(
	subject_id VARCHAR(45),
	subject_name VARCHAR(45),
	credit DOUBLE,
	teacher_id VARCHAR(45),
	CONSTRAINT PRIMARY KEY (subject_id),
	CONSTRAINT FOREIGN KEY (teacher_id) REFERENCES Teacher (teacher_id) ON DELETE CASCADE ON UPDATE CASCADE

);
SHOW TABLES;
DESCRIBE Subject;

--------------------------------------------------------------

DROP TABLE IF EXISTS Course;
CREATE TABLE IF NOT EXISTS Course(
	course_id VARCHAR(45),
	course_name VARCHAR(45),
	cost DOUBLE,
	duration VARCHAR(45),
	subject_id VARCHAR(45),
	CONSTRAINT PRIMARY KEY (course_id),
	CONSTRAINT FOREIGN KEY (subject_id) REFERENCES Subject (subject_id) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES;
DESCRIBE Course;

-------------------------------------------------------

DROP TABLE IF EXISTS Intake;
CREATE TABLE IF NOT EXISTS Intake(
	intake_id VARCHAR(45),
	start_date DATE,
	intakecol VARCHAR(45),
	description VARCHAR(45),
	course_id VARCHAR(45),
	CONSTRAINT PRIMARY KEY (intake_id),
	CONSTRAINT FOREIGN KEY (course_id) REFERENCES Course (course_id) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES;
DESCRIBE Intake;

-------------------------------------------------------

DROP TABLE IF EXISTS Registration;
CREATE TABLE IF NOT EXISTS Registration(
	registration_id VARCHAR(45),
	reg_date DATE,
	student_id VARCHAR(45),
	intake_id VARCHAR(45),
	CONSTRAINT PRIMARY KEY (registration_id, intake_id),
	CONSTRAINT FOREIGN KEY (intake_id) REFERENCES Intake (intake_id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT FOREIGN KEY (student_id) REFERENCES Student (student_id) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES;
DESCRIBE Registration;

--------------------------------------------------------------

DROP TABLE IF EXISTS Payment;
CREATE TABLE IF NOT EXISTS Payment(
	payment_id VARCHAR(45),
	date DATE,
	cost DOUBLE,
	registration_id VARCHAR(45),
	CONSTRAINT PRIMARY KEY (payment_id),
	CONSTRAINT FOREIGN KEY (registration_id) REFERENCES Registration (registration_id) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES;
DESCRIBE Payment;