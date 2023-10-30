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
	name VARCHAR(255)
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
	password VARCHAR(20) NOT NULL,
	phone_num VARCHAR(50) NOT NULL,
	join_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	last_login_date TIMESTAMP,
	role VARCHAR(20) DEFAULT 'ROLE_MEMBER' NOT NULL,
	status VARCHAR(50) DEFAULT '활동' NOT NULL,
	address VARCHAR(100),
	birthday DATE
);

CREATE TABLE board_kategories (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(20) DEFAULT '영화' NOT NULL
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

CREATE TABLE events (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	title VARCHAR(50) NOT NULL,
	start_at TIMESTAMP NOT NULL,
	end_at TIMESTAMP NOT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP,
	thumbnail_path VARCHAR(255) NOT NULL,
	contents_path VARCHAR(255),
	member_id BIGINT NOT NULL,
	FOREIGN KEY (member_id) REFERENCES Member(id)
);

CREATE TABLE store_kategories (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(20)
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

CREATE TABLE events_backup (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	title VARCHAR(50) NOT NULL,
	event_path VARCHAR(255) NOT NULL,
	start_at TIMESTAMP NOT NULL,
	end_at TIMESTAMP NOT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP,
	member_id BIGINT NOT NULL,
	FOREIGN KEY (member_id) REFERENCES Member(id)
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
	content VARCHAR(255),
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