DROP TABLE IF EXISTS MovieGenres;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS Credits;
DROP TABLE IF EXISTS Certifications;
DROP TABLE IF EXISTS board_files;
DROP TABLE IF EXISTS events_backup;
DROP TABLE IF EXISTS inquiry_responses;
DROP TABLE IF EXISTS reportBoard_response;
DROP TABLE IF EXISTS search_history;
DROP TABLE IF EXISTS wishlist;
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
	member_id BIGINT NOT NULL,
	FOREIGN KEY (kategorie_id) REFERENCES board_kategories(id),
	FOREIGN KEY (movie_id) REFERENCES Movies(id),
	FOREIGN KEY (member_id) REFERENCES Member(id)
);

CREATE TABLE Likes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    board_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (board_id) REFERENCES Board(id),
    FOREIGN KEY (member_id) REFERENCES Member(id)
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
	admin_id BIGINT NOT NULL,
	FOREIGN KEY (admin_id) REFERENCES Member(id)
);

CREATE TABLE store_kategories (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(20) UNIQUE
);

CREATE TABLE store (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	admin_id BIGINT NOT NULL,
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
	FOREIGN KEY (member_id) REFERENCES Member(id)
);

CREATE TABLE inquiry_responses (
	id BIGINT NOT NULL,
	responses_content VARCHAR(255) NOT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	admin_id BIGINT NOT NULL,
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
	FOREIGN KEY (board_id) REFERENCES Board(id)
);

CREATE TABLE Board_reply (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	content VARCHAR(255),
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP,
	member_id BIGINT NOT NULL,
	board_id BIGINT NOT NULL,
	FOREIGN KEY (member_id) REFERENCES Member(id),
	FOREIGN KEY (board_id) REFERENCES Board(id)
);

CREATE TABLE reportBoards (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	content VARCHAR(255),
	menu VARCHAR(50) NOT NULL,
	report_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	board_id BIGINT NOT NULL,
	reporter_id BIGINT NOT NULL,
	FOREIGN KEY (board_id) REFERENCES Board(id),
	FOREIGN KEY (reporter_id) REFERENCES Member(id)
);

CREATE TABLE reportBoard_response (
	reportBoard_id BIGINT NOT NULL,
	content VARCHAR(255),
	response_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	admin_id BIGINT NOT NULL,
	PRIMARY KEY (reportBoard_id),
	FOREIGN KEY (reportBoard_id) REFERENCES reportBoards(id),
	FOREIGN KEY (admin_id) REFERENCES Member(id)
);

CREATE TABLE reportReply (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	report_content VARCHAR(255),
	report_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	reporter_id BIGINT NOT NULL,
	reply_id BIGINT NOT NULL,
	menu VARCHAR(50) NOT NULL,
	FOREIGN KEY (reporter_id) REFERENCES Member(id),
	FOREIGN KEY (reply_id) REFERENCES Board_reply(id)
);

CREATE TABLE search_history (
	member_id BIGINT NOT NULL,
	movie_id BIGINT NOT NULL,
	search_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	PRIMARY KEY (member_id, movie_id),
	FOREIGN KEY (member_id) REFERENCES Member(id),
	FOREIGN KEY (movie_id) REFERENCES Movies(id)
);

CREATE TABLE wishlist (
	member_id BIGINT NOT NULL,
	movie_id BIGINT NOT NULL,
	PRIMARY KEY (member_id, movie_id),
	FOREIGN KEY (member_id) REFERENCES Member(id),
	FOREIGN KEY (movie_id) REFERENCES Movies(id)
);

INSERT INTO Member (EMAIL, NAME, NICKNAME, PASSWORD, PHONE_NUM) VALUES('','','','','');

INSERT INTO board_kategories (name) VALUES('영화');
INSERT INTO board_kategories (name) VALUES('공지');
INSERT INTO board_kategories (name) VALUES('잡담');

INSERT INTO STORE_KATEGORIES (name) VALUES('티켓');
INSERT INTO STORE_KATEGORIES (name) VALUES('팝콘/음료');
INSERT INTO STORE_KATEGORIES (name) VALUES('상품권');
INSERT INTO STORE_KATEGORIES (name) VALUES('기프티콘');

INSERT INTO Board (title, content, favorites, view_count, created_at, updated_at, kategorie_id, movie_id, member_id)
VALUES
('첫 번째 게시글', '이것은 첫 번째 게시글입니다.', 10, 100, CURRENT_TIMESTAMP, NULL, 1, 671, 2),
('두 번째 게시글', '이것은 두 번째 게시글입니다.', 5, 50, CURRENT_TIMESTAMP, NULL, 2, 672, 2),
('세 번째 게시글', '이것은 세 번째 게시글입니다.', 20, 200, CURRENT_TIMESTAMP, NULL, 2, 673, 3),
('네 번째 게시글', '이것은 네 번째 게시글입니다.', 0, 10, CURRENT_TIMESTAMP, NULL, 3, 674, 5),
('다섯 번째 게시글', '이것은 다섯 번째 게시글입니다.', 3, 30, CURRENT_TIMESTAMP, NULL, 3, 675, 3);

INSERT INTO events (title, start_at, end_at, created_at, updated_at, thumbnail_path, contents_path, admin_id)
VALUES
('이벤트 1', '2023-11-01 09:00:00', '2023-11-02 17:00:00', CURRENT_TIMESTAMP, NULL, 'https://image.tmdb.org/t/p/w154/sfs4U6XpiKFngbbSzrpZbkM1ySI.jpg', 'https://image.tmdb.org/t/p/w154/sfs4U6XpiKFngbbSzrpZbkM1ySI.jpg', 3),
('이벤트 2', '2023-11-03 10:00:00', '2023-11-05 18:00:00', CURRENT_TIMESTAMP, NULL, 'https://image.tmdb.org/t/p/w154/sfs4U6XpiKFngbbSzrpZbkM1ySI.jpg', 'https://image.tmdb.org/t/p/w154/sfs4U6XpiKFngbbSzrpZbkM1ySI.jpg', 3),
('이벤트 3', '2023-11-06 11:00:00', '2023-11-08 19:00:00', CURRENT_TIMESTAMP, NULL, 'https://image.tmdb.org/t/p/w154/sfs4U6XpiKFngbbSzrpZbkM1ySI.jpg', 'https://image.tmdb.org/t/p/w154/sfs4U6XpiKFngbbSzrpZbkM1ySI.jpg', 3),
('이벤트 4', '2023-11-10 12:00:00', '2023-11-12 20:00:00', CURRENT_TIMESTAMP, NULL, 'https://image.tmdb.org/t/p/w154/sfs4U6XpiKFngbbSzrpZbkM1ySI.jpg', 'https://image.tmdb.org/t/p/w154/sfs4U6XpiKFngbbSzrpZbkM1ySI.jpg', 3),
('이벤트 5', '2023-11-15 13:00:00', '2023-11-17 21:00:00', CURRENT_TIMESTAMP, NULL, 'https://image.tmdb.org/t/p/w154/sfs4U6XpiKFngbbSzrpZbkM1ySI.jpg', 'https://image.tmdb.org/t/p/w154/sfs4U6XpiKFngbbSzrpZbkM1ySI.jpg', 3);

INSERT INTO store (admin_id, title, content, price, usage_location, poster_path, kategorie_id)
VALUES
(3, '상품 1', '이것은 상품 1입니다.', '10000', '서울', 'https://image.tmdb.org/t/p/w154/sfs4U6XpiKFngbbSzrpZbkM1ySI.jpg', 1),
(3, '상품 2', '이것은 상품 2입니다.', '20000', '부산', 'https://image.tmdb.org/t/p/w154/sfs4U6XpiKFngbbSzrpZbkM1ySI.jpg', 2),
(3, '상품 3', '이것은 상품 3입니다.', '30000', '대구', 'https://image.tmdb.org/t/p/w154/sfs4U6XpiKFngbbSzrpZbkM1ySI.jpg', 3),
(3, '상품 4', '이것은 상품 4입니다.', '40000', '광주', 'https://image.tmdb.org/t/p/w154/sfs4U6XpiKFngbbSzrpZbkM1ySI.jpg', 4),
(3, '상품 5', '이것은 상품 5입니다.', '50000', '대전', 'https://image.tmdb.org/t/p/w154/sfs4U6XpiKFngbbSzrpZbkM1ySI.jpg', 1);

INSERT INTO inquiries (title, content, member_id)
VALUES
('문의 1', '이것은 문의 내용 1입니다.', 2),
('문의 2', '이것은 문의 내용 2입니다.', 5),
('문의 3', '이것은 문의 내용 3입니다.', 2),
('문의 4', '이것은 문의 내용 4입니다.', 5),
('문의 5', '이것은 문의 내용 5입니다.', 2);

INSERT INTO reportBoards (content, menu, board_id, reporter_id)
VALUES
('이 글은 부적절한 내용을 포함하고 있습니다.', '신고 메뉴 1', 6, 2),
('이 글은 광고를 포함하고 있습니다.', '신고 메뉴 2', 7, 5),
('이 글은 허위 정보를 포함하고 있습니다.', '신고 메뉴 3', 8, 2),
('이 글은 저작권을 침해하고 있습니다.', '신고 메뉴 4', 9, 5),
('이 글은 개인정보를 무단으로 사용하고 있습니다.', '신고 메뉴 1', 10, 2);

INSERT INTO Board_reply (content, member_id, board_id)
VALUES
('댓글 내용 1', 2, 6),
('댓글 내용 2', 3, 6),
('댓글 내용 3', 5, 6),
('댓글 내용 4', 2, 7),
('댓글 내용 5', 3, 7),
('댓글 내용 6', 5, 7),
('댓글 내용 7', 2, 8),
('댓글 내용 8', 3, 8),
('댓글 내용 9', 5, 8),
('댓글 내용 10', 2, 9);

INSERT INTO reportReply (report_content, reporter_id, reply_id, menu)
VALUES
('이 댓글은 부적절한 내용을 포함하고 있습니다.', 2, 1, '신고 메뉴 1'),
('이 댓글은 광고를 포함하고 있습니다.', 5, 2, '신고 메뉴 2'),
('이 댓글은 허위 정보를 포함하고 있습니다.', 2, 3, '신고 메뉴 3'),
('이 댓글은 저작권을 침해하고 있습니다.', 5, 4, '신고 메뉴 4'),
('이 댓글은 개인정보를 무단으로 사용하고 있습니다.', 2, 5, '신고 메뉴 1');
