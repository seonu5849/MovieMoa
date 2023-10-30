-- OracleDB

CREATE TABLE Movies (
	id NUMBER PRIMARY KEY,
	original_title VARCHAR2(255) NOT NULL,
	title VARCHAR2(255),
	release_date DATE,
	runtime NUMBER,
	poster_path VARCHAR2(255),
	vote_average NUMBER(4,2),
	popularity NUMBER(10,3),
	backdrop_path VARCHAR2(255),
	overview VARCHAR2(4000),
	status VARCHAR2(255),
	tagline VARCHAR2(1000),
	homepage VARCHAR2(1000)
);

CREATE TABLE genres (
	id NUMBER PRIMARY KEY,
	name VARCHAR2(255)
);

CREATE TABLE MovieGenres (
	movie_id NUMBER NOT NULL,
	genre_id NUMBER NOT NULL,
	CONSTRAINT pk_moviegenres PRIMARY KEY (movie_id, genre_id),
	FOREIGN KEY (movie_id) REFERENCES Movies(id),
	FOREIGN KEY (genre_id) REFERENCES genres(id)
);

CREATE TABLE Credits (
	credits_id NUMBER PRIMARY KEY,
	id NUMBER NOT NULL,
	name VARCHAR2(255),
	profile_path VARCHAR2(255),
	character VARCHAR2(255),
	department VARCHAR2(255),
	job VARCHAR2(255),
	movie_id NUMBER NOT NULL,
	FOREIGN KEY (movie_id) REFERENCES Movies(id)
);

CREATE TABLE Certifications (
	id NUMBER PRIMARY KEY,
	iso_3166_1 VARCHAR2(10) NOT NULL,
	certification VARCHAR2(20),
	movie_id NUMBER NOT NULL,
	FOREIGN KEY (movie_id) REFERENCES Movies(id)
);

CREATE TABLE Member (
	id NUMBER PRIMARY KEY,
	email VARCHAR2(100) NOT NULL UNIQUE,
	name VARCHAR2(20) NOT NULL,
	nickname VARCHAR2(20) NOT NULL,
	password VARCHAR2(20) NOT NULL,
	phone_number VARCHAR2(50) NOT NULL,
	join_date TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
	last_login_date TIMESTAMP,
	role VARCHAR2(20) DEFAULT 'ROLE_MEMBER' NOT NULL,
	status VARCHAR2(50) DEFAULT '활동' NOT NULL,
	address VARCHAR2(100),
	birthday DATE
);

CREATE TABLE board_kategories (
	id NUMBER PRIMARY KEY,
	name VARCHAR2(20) DEFAULT '영화' NOT NULL
);

CREATE TABLE Board (
	id NUMBER PRIMARY KEY,
	title VARCHAR2(50) NOT NULL,
	content VARCHAR2(255),
	favorites NUMBER DEFAULT 0,
	view_count NUMBER DEFAULT 0,
	created_at TIMESTAMP DEFAULT SYSTIMESTAMP,
	updated_at TIMESTAMP,
	kategorie_id NUMBER NOT NULL,
	movie_id NUMBER,
	member_id NUMBER NOT NULL,
	FOREIGN KEY (kategorie_id) REFERENCES board_kategories(id),
	FOREIGN KEY (movie_id) REFERENCES Movies(id),
	FOREIGN KEY (member_id) REFERENCES Member(id)
);

CREATE TABLE events (
	id NUMBER PRIMARY KEY,
	title VARCHAR2(50) NOT NULL,
	start_at TIMESTAMP NOT NULL,
	end_at TIMESTAMP NOT NULL,
	created_at TIMESTAMP DEFAULT SYSTIMESTAMP,
	updated_at TIMESTAMP,
	thumbnail_path VARCHAR2(255) NOT NULL,
	contents_path VARCHAR2(255),
	member_id NUMBER NOT NULL,
	FOREIGN KEY (member_id) REFERENCES Member(id)
);

CREATE TABLE store_kategories (
	id NUMBER PRIMARY KEY,
	name VARCHAR2(20)
);

CREATE TABLE store (
	id NUMBER PRIMARY KEY,
	admin_id NUMBER NOT NULL,
	title VARCHAR2(50) NOT NULL,
	content VARCHAR2(255),
	price VARCHAR2(100),
	usage_location VARCHAR2(255) NOT NULL,
	poster_path VARCHAR2(255) NOT NULL,
	created_at TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
	updated_at TIMESTAMP,
	kategorie_id NUMBER NOT NULL,
	FOREIGN KEY (admin_id) REFERENCES Member(id),
	FOREIGN KEY (kategorie_id) REFERENCES store_kategories(id)
);

CREATE TABLE events_backup (
	id NUMBER PRIMARY KEY,
	title VARCHAR2(50) NOT NULL,
	event_path VARCHAR2(255) NOT NULL,
	start_at TIMESTAMP NOT NULL,
	end_at TIMESTAMP NOT NULL,
	created_at TIMESTAMP DEFAULT SYSTIMESTAMP,
	updated_at TIMESTAMP,
	member_id NUMBER NOT NULL,
	FOREIGN KEY (member_id) REFERENCES Member(id)
);

CREATE TABLE inquiries (
	id NUMBER PRIMARY KEY,
	title VARCHAR2(50) NOT NULL,
	content VARCHAR2(255) NOT NULL,
	created_at TIMESTAMP DEFAULT SYSTIMESTAMP,
	member_id NUMBER NOT NULL,
	FOREIGN KEY (member_id) REFERENCES Member(id)
);

CREATE TABLE inquiry_responses (
	id NUMBER NOT NULL,
	responses_content VARCHAR2(255) NOT NULL,
	created_at TIMESTAMP DEFAULT SYSTIMESTAMP,
	admin_id NUMBER NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (id) REFERENCES inquiries(id),
	FOREIGN KEY (admin_id) REFERENCES Member(id)
);

CREATE TABLE board_files (
	id NUMBER PRIMARY KEY,
	name VARCHAR2(50) NOT NULL,
	file_path VARCHAR2(255) NOT NULL,
	created_at TIMESTAMP,
	board_id NUMBER NOT NULL,
	FOREIGN KEY (board_id) REFERENCES Board(id)
);

CREATE TABLE Board_reply (
	id NUMBER PRIMARY KEY,
	content VARCHAR2(255),
	created_at TIMESTAMP DEFAULT SYSTIMESTAMP,
	updated_at TIMESTAMP,
	member_id NUMBER NOT NULL,
	board_id NUMBER NOT NULL,
	FOREIGN KEY (member_id) REFERENCES Member(id),
	FOREIGN KEY (board_id) REFERENCES Board(id)
);

CREATE TABLE reportBoards (
	id NUMBER PRIMARY KEY,
	content VARCHAR2(255),
	menu VARCHAR2(50) NOT NULL,
	report_date TIMESTAMP DEFAULT SYSTIMESTAMP,
	board_id NUMBER NOT NULL,
	reporter_id NUMBER NOT NULL,
	FOREIGN KEY (board_id) REFERENCES Board(id),
	FOREIGN KEY (reporter_id) REFERENCES Member(id)
);

CREATE TABLE reportBoard_response (
	reportBoard_id NUMBER NOT NULL,
	content VARCHAR2(255),
	response_date TIMESTAMP DEFAULT SYSTIMESTAMP,
	admin_id NUMBER NOT NULL,
	PRIMARY KEY (reportBoard_id),
	FOREIGN KEY (reportBoard_id) REFERENCES reportBoards(id),
	FOREIGN KEY (admin_id) REFERENCES Member(id)
);

CREATE TABLE reportReply (
	id NUMBER PRIMARY KEY,
	content VARCHAR2(255),
	report_date TIMESTAMP DEFAULT SYSTIMESTAMP,
	reporter_id NUMBER NOT NULL,
	reply_id NUMBER NOT NULL,
	menu VARCHAR2(50) NOT NULL,
	FOREIGN KEY (reporter_id) REFERENCES Member(id),
	FOREIGN KEY (reply_id) REFERENCES Board_reply(id)
);

CREATE TABLE search_history (
	member_id NUMBER NOT NULL,
	movie_id NUMBER NOT NULL,
	search_date TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
	PRIMARY KEY (member_id, movie_id),
	FOREIGN KEY (member_id) REFERENCES Member(id),
	FOREIGN KEY (movie_id) REFERENCES Movies(id)
);

CREATE TABLE wishlist (
	member_id NUMBER NOT NULL,
	movie_id NUMBER NOT NULL,
	PRIMARY KEY (member_id, movie_id),
	FOREIGN KEY (member_id) REFERENCES Member(id),
	FOREIGN KEY (movie_id) REFERENCES Movies(id)
);

-- Credits 테이블 시퀀스 및 트리거
CREATE SEQUENCE credits_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER credits_bir
BEFORE INSERT ON Credits
FOR EACH ROW
BEGIN
  SELECT credits_seq.NEXTVAL INTO :new.credits_id FROM dual;
END;
/

-- Certifications 테이블 시퀀스 및 트리거
CREATE SEQUENCE certifications_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER certifications_bir
BEFORE INSERT ON Certifications
FOR EACH ROW
BEGIN
  SELECT certifications_seq.NEXTVAL INTO :new.id FROM dual;
END;
/

-- Events 테이블 시퀀스 및 트리거
CREATE SEQUENCE events_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER events_bir
BEFORE INSERT ON Events
FOR EACH ROW
BEGIN
  SELECT events_seq.NEXTVAL INTO :new.id FROM dual;
END;
/

-- Events_backup 테이블 시퀀스 및 트리거
CREATE SEQUENCE events_backup_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER events_backup_bir
BEFORE INSERT ON Events_backup
FOR EACH ROW
BEGIN
  SELECT events_backup_seq.NEXTVAL INTO :new.id FROM dual;
END;
/

-- Inquiries 테이블 시퀀스 및 트리거
CREATE SEQUENCE inquiries_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER inquiries_bir
BEFORE INSERT ON Inquiries
FOR EACH ROW
BEGIN
  SELECT inquiries_seq.NEXTVAL INTO :new.id FROM dual;
END;
/

-- Board_reply 테이블 시퀀스 및 트리거
CREATE SEQUENCE board_reply_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER board_reply_bir
BEFORE INSERT ON Board_reply
FOR EACH ROW
BEGIN
  SELECT board_reply_seq.NEXTVAL INTO :new.id FROM dual;
END;
/

-- ReportBoards 테이블 시퀀스 및 트리거
CREATE SEQUENCE reportboards_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER reportboards_bir
BEFORE INSERT ON ReportBoards
FOR EACH ROW
BEGIN
  SELECT reportboards_seq.NEXTVAL INTO :new.id FROM dual;
END;
/

-- ReportReply 테이블 시퀀스 및 트리거
CREATE SEQUENCE reportreply_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER reportreply_bir
BEFORE INSERT ON ReportReply
FOR EACH ROW
BEGIN
  SELECT reportreply_seq.NEXTVAL INTO :new.id FROM dual;
END;
/

-- Store 테이블 시퀀스 및 트리거
CREATE SEQUENCE store_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER store_bir
BEFORE INSERT ON Store
FOR EACH ROW
BEGIN
  SELECT store_seq.NEXTVAL INTO :new.id FROM dual;
END;
/

-- Store_kategories 테이블 시퀀스 및 트리거
CREATE SEQUENCE store_kategories_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER store_kategories_bir
BEFORE INSERT ON Store_kategories
FOR EACH ROW
BEGIN
  SELECT store_kategories_seq.NEXTVAL INTO :new.id FROM dual;
END;
/

-- Board_kategories 테이블 시퀀스 및 트리거
CREATE SEQUENCE board_kategories_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER board_kategories_bir
BEFORE INSERT ON Board_kategories
FOR EACH ROW
BEGIN
  SELECT board_kategories_seq.NEXTVAL INTO :new.id FROM dual;
END;
/

-- Member 테이블 시퀀스 및 트리거
CREATE SEQUENCE member_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER member_bir
BEFORE INSERT ON Member
FOR EACH ROW
BEGIN
  SELECT member_seq.NEXTVAL INTO :new.id FROM dual;
END;
/

-- Board 테이블 시퀀스 및 트리거
CREATE SEQUENCE board_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER board_bir
BEFORE INSERT ON Board
FOR EACH ROW
BEGIN
  SELECT board_seq.NEXTVAL INTO :new.id FROM dual;
END;
/
