DROP TABLE IF EXISTS MovieGenres;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS Credits;
DROP TABLE IF EXISTS Certifications;
DROP TABLE IF EXISTS board_files;
DROP TABLE IF EXISTS events_backup;
DROP TABLE IF EXISTS inquiry_responses;
DROP TABLE IF EXISTS likes;
DROP TABLE IF EXISTS search_history;
DROP TABLE IF EXISTS wishlist;
DROP TABLE IF EXISTS report_kategories;
DROP TABLE IF EXISTS reportReply_response;
DROP TABLE IF EXISTS reportBoard_response;
DROP TABLE IF EXISTS reportReply;
DROP TABLE IF EXISTS reportBoards;
DROP TABLE IF EXISTS inquiries;
DROP TABLE IF EXISTS Board_reply;
DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS store;
DROP TABLE IF EXISTS store_kategories;
DROP TABLE IF EXISTS Board;
DROP TABLE IF EXISTS board_kategories;
DROP TABLE IF EXISTS Member;
DROP TABLE IF EXISTS Movies;

CREATE TABLE Movies (
	id BIGINT PRIMARY KEY,
	original_title VARCHAR(255) NOT NULL,
	title VARCHAR(255),
	release_date DATE,
	runtime INT,
	poster_path VARCHAR(255),
	vote_average DECIMAL(4,2),
	popularity DECIMAL(10,3),
	backdrop_path VARCHAR(255),
	overview VARCHAR(4000),
	status VARCHAR(255),
	tagline VARCHAR(1000),
	homepage VARCHAR(1000)
);

CREATE TABLE genres (
	id BIGINT PRIMARY KEY,
	name VARCHAR(255) UNIQUE
);

CREATE TABLE MovieGenres (
	movie_id BIGINT NOT NULL,
	genre_id BIGINT NOT NULL,
	PRIMARY KEY (movie_id, genre_id),
	FOREIGN KEY (movie_id) REFERENCES Movies(id),
	FOREIGN KEY (genre_id) REFERENCES genres(id)
);

CREATE TABLE Credits (
	credits_id BIGINT AUTO_INCREMENT PRIMARY KEY,
	id BIGINT NOT NULL,
	name VARCHAR(255),
	profile_path VARCHAR(255),
	character VARCHAR(255),
	department VARCHAR(255),
	job VARCHAR(100),
	movie_id BIGINT NOT NULL,
	FOREIGN KEY (movie_id) REFERENCES Movies(id)
);

CREATE TABLE Certifications (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	iso_3166_1 VARCHAR(10) NOT NULL,
	certification VARCHAR(20),
	movie_id BIGINT NOT NULL,
	FOREIGN KEY (movie_id) REFERENCES Movies(id)
);

CREATE TABLE Member (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	email VARCHAR(100) NOT NULL UNIQUE,
	name VARCHAR(20) NOT NULL,
	nickname VARCHAR(20) NOT NULL,
	password VARCHAR(100) NOT NULL,
	phone_num VARCHAR(50) NOT NULL,
	join_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	last_login_date TIMESTAMP,
	role VARCHAR(20) DEFAULT 'ROLE_MEMBER' NOT NULL,
	status VARCHAR(50) DEFAULT '활동' NOT NULL,
	address VARCHAR(100),
	birthday DATE,
	suspension_period DATE
);

CREATE TABLE board_kategories (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(20) DEFAULT '영화' UNIQUE NOT NULL
);

CREATE TABLE Board (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	title VARCHAR(50) NOT NULL,
	content VARCHAR(255),
	favorites BIGINT DEFAULT 0,
	view_count BIGINT DEFAULT 0,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP,
	kategorie_id BIGINT NOT NULL,
	movie_id BIGINT,
	member_id BIGINT,
	FOREIGN KEY (kategorie_id) REFERENCES board_kategories(id),
	FOREIGN KEY (movie_id) REFERENCES Movies(id),
	FOREIGN KEY (member_id) REFERENCES Member(id)
);

CREATE TABLE Likes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    board_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (board_id) REFERENCES Board(id) ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES Member(id) ON DELETE CASCADE
);

CREATE TABLE events (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	title VARCHAR(50) NOT NULL,
	start_at TIMESTAMP NOT NULL,
	end_at TIMESTAMP NOT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP,
	thumbnail_path VARCHAR(255) NOT NULL,
	contents_path VARCHAR(255),
	admin_id BIGINT,
	FOREIGN KEY (admin_id) REFERENCES Member(id)
);

CREATE TABLE store_kategories (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(20) UNIQUE
);

CREATE TABLE store (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	admin_id BIGINT,
	title VARCHAR(50) NOT NULL,
	content VARCHAR(255),
	price VARCHAR(100),
	usage_location VARCHAR(255) NOT NULL,
	poster_path VARCHAR(255) NOT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updated_at TIMESTAMP,
	kategorie_id BIGINT NOT NULL,
	FOREIGN KEY (admin_id) REFERENCES Member(id),
	FOREIGN KEY (kategorie_id) REFERENCES store_kategories(id)
);

CREATE TABLE inquiries (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	title VARCHAR(50) NOT NULL,
	content VARCHAR(255) NOT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	member_id BIGINT NOT NULL,
	FOREIGN KEY (member_id) REFERENCES Member(id) ON DELETE CASCADE
);

CREATE TABLE inquiry_responses (
	id BIGINT NOT NULL,
	responses_content VARCHAR(255) NOT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	admin_id BIGINT,
	PRIMARY KEY (id),
	FOREIGN KEY (id) REFERENCES inquiries(id),
	FOREIGN KEY (admin_id) REFERENCES Member(id)
);

CREATE TABLE board_files (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	file_path VARCHAR(255) NOT NULL,
	created_at TIMESTAMP,
	board_id BIGINT NOT NULL,
	FOREIGN KEY (board_id) REFERENCES Board(id) ON DELETE CASCADE
);

CREATE TABLE Board_reply (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	content VARCHAR(255),
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP,
	member_id BIGINT NOT NULL,
	board_id BIGINT NOT NULL,
	FOREIGN KEY (member_id) REFERENCES Member(id),
	FOREIGN KEY (board_id) REFERENCES Board(id) ON DELETE CASCADE
);

CREATE TABLE report_kategories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) DEFAULT '부적절한 내용' UNIQUE NOT NULL
);

CREATE TABLE reportBoards (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	content TEXT,
	menu BIGINT NOT NULL,
	report_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	board_id BIGINT NOT NULL,
	reporter_id BIGINT NOT NULL,
	complete varchar(30),
	result varchar(20),
	reason_for_change varchar(255)
	FOREIGN KEY (board_id) REFERENCES Board(id) ON DELETE CASCADE,
	FOREIGN KEY (menu) REFERENCES report_kategories(id),
	FOREIGN KEY (reporter_id) REFERENCES Member(id)
);

/*
alter table reportBoards
add column reason_for_change varchar(255);
alter table reportBoards
add column complete varchar(30);
alter table reportBoards
add column result varchar(20);*/

CREATE TABLE reportReply (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	report_content TEXT,
	report_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	reporter_id BIGINT NOT NULL,
	reply_id BIGINT NOT NULL,
	menu BIGINT NOT NULL,
	complete varchar(30),
	result varchar(20),
	reason_for_change varchar(255)
	FOREIGN KEY (reporter_id) REFERENCES Member(id),
	FOREIGN KEY (menu) REFERENCES report_kategories(id),
	FOREIGN KEY (reply_id) REFERENCES Board_reply(id) ON DELETE CASCADE
);

/*
alter table reportReply
add column reason_for_change varchar(255);
alter table reportReply
add column complete varchar(30);
alter table reportReply
add column result varchar(20);*/

CREATE TABLE search_history (
	member_id BIGINT NOT NULL,
	movie_id BIGINT NOT NULL,
	search_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	PRIMARY KEY (member_id, movie_id),
	FOREIGN KEY (member_id) REFERENCES Member(id) ON DELETE CASCADE,
	FOREIGN KEY (movie_id) REFERENCES Movies(id) ON DELETE CASCADE
);

CREATE TABLE wishlist (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    movie_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (member_id) REFERENCES Member(id) ON DELETE CASCADE,
	FOREIGN KEY (movie_id) REFERENCES Movies(id) ON DELETE CASCADE
);

INSERT INTO Member (EMAIL, NAME, NICKNAME, PASSWORD, PHONE_NUM)
VALUES('admin1@admin.com','adminName1','adminNickname1','admin1','010-1111-0001'),
('user1@user.com','userName1','userNickname1','user1','010-0000-0001'),
('user2@user.com','userName2','userNickname2','user2','010-0000-0002'),
('user3@user.com','userName3','userNickname3','user3','010-0000-0003');

UPDATE Member SET ROLE = 'ROLE_ADMIN' WHERE id=1;
UPDATE Member SET ROLE = 'ROLE_LOCKED' WHERE id=3;

INSERT INTO board_kategories (name) VALUES('영화'),('공지'),('잡담');

INSERT INTO STORE_KATEGORIES (name) VALUES('티켓'),('팝콘/음료'),('상품권'),('기프티콘');

INSERT INTO REPORT_KATEGORIES (name) VALUES('부적절한 내용'),('광고글'),('허위 정보'),('저작권 침해'),('개인정보 무단 사용');

INSERT INTO events (title, start_at, end_at, created_at, updated_at, thumbnail_path, contents_path, admin_id)
VALUES
('이벤트 1', '2023-11-01 09:00:00', '2023-11-02 17:00:00', CURRENT_TIMESTAMP, NULL, 'https://image.tmdb.org/t/p/w154/sfs4U6XpiKFngbbSzrpZbkM1ySI.jpg', 'https://image.tmdb.org/t/p/w154/sfs4U6XpiKFngbbSzrpZbkM1ySI.jpg', 1),
('이벤트 2', '2023-11-03 10:00:00', '2023-11-05 18:00:00', CURRENT_TIMESTAMP, NULL, 'https://image.tmdb.org/t/p/w154/sfs4U6XpiKFngbbSzrpZbkM1ySI.jpg', 'https://image.tmdb.org/t/p/w154/sfs4U6XpiKFngbbSzrpZbkM1ySI.jpg', 1),
('이벤트 3', '2023-10-06 11:00:00', '2023-11-08 19:00:00', CURRENT_TIMESTAMP, NULL, 'https://image.tmdb.org/t/p/w154/sfs4U6XpiKFngbbSzrpZbkM1ySI.jpg', 'https://image.tmdb.org/t/p/w154/sfs4U6XpiKFngbbSzrpZbkM1ySI.jpg', 1),
('이벤트 4', '2023-10-10 12:00:00', '2023-11-12 20:00:00', CURRENT_TIMESTAMP, NULL, 'https://image.tmdb.org/t/p/w154/sfs4U6XpiKFngbbSzrpZbkM1ySI.jpg', 'https://image.tmdb.org/t/p/w154/sfs4U6XpiKFngbbSzrpZbkM1ySI.jpg', 1),
('이벤트 5', '2023-10-15 13:00:00', '2023-11-17 21:00:00', CURRENT_TIMESTAMP, NULL, 'https://image.tmdb.org/t/p/w154/sfs4U6XpiKFngbbSzrpZbkM1ySI.jpg', 'https://image.tmdb.org/t/p/w154/sfs4U6XpiKFngbbSzrpZbkM1ySI.jpg', 1);

INSERT INTO store (admin_id, title, content, price, usage_location, poster_path, kategorie_id)
VALUES
(1, '상품 1', '이것은 상품 1입니다.', '10000', '서울', 'https://image.tmdb.org/t/p/w154/sfs4U6XpiKFngbbSzrpZbkM1ySI.jpg', 1),
(1, '상품 2', '이것은 상품 2입니다.', '20000', '부산', 'https://image.tmdb.org/t/p/w154/sfs4U6XpiKFngbbSzrpZbkM1ySI.jpg', 2),
(1, '상품 3', '이것은 상품 3입니다.', '30000', '대구', 'https://image.tmdb.org/t/p/w154/sfs4U6XpiKFngbbSzrpZbkM1ySI.jpg', 3),
(1, '상품 4', '이것은 상품 4입니다.', '40000', '광주', 'https://image.tmdb.org/t/p/w154/sfs4U6XpiKFngbbSzrpZbkM1ySI.jpg', 4),
(1, '상품 5', '이것은 상품 5입니다.', '50000', '대전', 'https://image.tmdb.org/t/p/w154/sfs4U6XpiKFngbbSzrpZbkM1ySI.jpg', 1);

INSERT INTO inquiries (title, content, member_id)
VALUES
('문의 1', '이것은 문의 내용 1입니다.', 2),
('문의 2', '이것은 문의 내용 2입니다.', 2),
('문의 3', '이것은 문의 내용 3입니다.', 2),
('문의 4', '이것은 문의 내용 4입니다.', 3),
('문의 5', '이것은 문의 내용 5입니다.', 3);

-- 자동번호를 재시작하게 하는 쿼리문 (예: 1부터 재생성)
-- ALTER TABLE Board AUTO_INCREMENT = 1;
