DROP TABLE CART;
DROP TABLE SOLD;
DROP TABLE REMOVED;
DROP TABLE BOOK;
DROP TABLE BUYER;
DROP TABLE SELLER;
DROP TABLE BOOKINFO;
DROP TABLE PICTURE;


CREATE TABLE BUYER (
	BUYER_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
	NAME VARCHAR(20) NOT NULL UNIQUE,
	PASSWORD VARCHAR(20) NOT NULL,
	EMAIL VARCHAR(20) NOT NULL,
	NICKNAME VARCHAR(20),
	FIRSTNAME VARCHAR(20),
	LASTNAME VARCHAR(20),
	BIRTH DATE,
	ADDRESS VARCHAR(20),
	CARD VARCHAR(20),
	STATUS INTEGER NOT NULL ,
	PRIMARY KEY (BUYER_ID)
);

CREATE TABLE SELLER (
	SELLER_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
	NAME VARCHAR(20) NOT NULL UNIQUE,
	PASSWORD VARCHAR(20) NOT NULL,
	EMAIL VARCHAR(20) NOT NULL,
	NICKNAME VARCHAR(20),
	FIRSTNAME VARCHAR(20),
	LASTNAME VARCHAR(20),
	BIRTH DATE,
	ADDRESS VARCHAR(20),
	CARD VARCHAR(20),
	STATUS INTEGER NOT NULL ,
	PRIMARY KEY (SELLER_ID)
);

CREATE TABLE BOOKINFO(
	BOOKINFO_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
	BOOKTYPE VARCHAR(20) NOT NULL,
	author VARCHAR(200),
	title VARCHAR(200) NOT NULL,
	booktitle VARCHAR(100),
	publisher VARCHAR(20),
	journal VARCHAR(20),
	volume VARCHAR(10),
	pages VARCHAR(10),
	years VARCHAR(10),
	isbn VARCHAR(20),
	series VARCHAR(50),
	school VARCHAR(20),
	address VARCHAR(100),
	number VARCHAR(10),
	month VARCHAR(10),
	editor VARCHAR(200),
	url VARCHAR(50),
	ee VARCHAR(50),
	cdrom VARCHAR(50),
	cite VARCHAR(100),
	note VARCHAR(100),
	crossref VARCHAR(100),
	chapter VARCHAR(100),
	PRIMARY KEY (BOOKINFO_ID)
);
CREATE TABLE PICTURE(
	PIC_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
	NAME VARCHAR(32) NOT NULL,
	PIC BLOB(16M),
	PRIMARY KEY (PIC_ID)
);

CREATE TABLE BOOK(
	BOOK_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
	INF_ID  INTEGER NOT NULL ,
	VENDER INTEGER NOT NULL  ,
	PRICE FLOAT NOT NULL,
	NUMBER INTEGER NOT NULL,
	STATUS INTEGER NOT NULL,
	PIC_ID1 INTEGER,
	PIC_ID2 INTEGER,
	PIC_ID3 INTEGER,
	PIC_ID4 INTEGER,
	PRIMARY KEY (BOOK_ID)
);

CREATE TABLE CART(
	CART_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
	C_BUYER  INTEGER NOT NULL ,
	C_BOOK  INTEGER NOT NULL ,
	NUMBER INTEGER NOT NULL,
	TIME_A TIMESTAMP NOT NULL,
	PRIMARY KEY (CART_ID)
);

CREATE TABLE SOLD(
	SOLD_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
	S_BUYER  INTEGER NOT NULL ,
	S_BOOK  INTEGER NOT NULL  ,
	NUMBER INTEGER NOT NULL,
	TIME_S TIMESTAMP NOT NULL,
	PRIMARY KEY (SOLD_ID)
);

CREATE TABLE REMOVED(
	REMOVED_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
	R_BUYER  INTEGER NOT NULL  ,
	R_BOOK  INTEGER NOT NULL ,
	TIME_ADD TIMESTAMP NOT NULL,
	TIME_RMV TIMESTAMP NOT NULL,
	PRIMARY KEY (REMOVED_ID)
);



