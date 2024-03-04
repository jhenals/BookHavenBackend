#########################################################################################
###                                                                                   ###
### Author: Jhenalyn Subol                                                            ###
### License:                                                                          ###
### Date:                                                                             ###
### Version: 1.0                                                                      ###
###                                                                                   ###
#########################################################################################

CREATE SCHEMA IF NOT EXISTS bookhaven;

use bookhaven;

DROP TABLE IF EXISTS book;
CREATE TABLE book
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    title     VARCHAR(255) NULL,
    author    VARCHAR(255) NULL,
    image_url VARCHAR(255) NULL,
    category_id bigint null,
    price decimal(10, 2) not null,
    description longtext null,
    data_pubblicazione date null,
    editor varchar(255) null,
    discount decimal(10, 2) null,
    date_book_added date not null,
    number_buyers int null,

CONSTRAINT pk_book PRIMARY KEY (id)
);

DROP TABLE IF EXISTS category;
CREATE TABLE category
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

DROP TABLE IF EXISTS order;
CREATE TABLE `order`
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    user_id      BIGINT         NULL,
    date_time    datetime       NULL,
    total_amount DECIMAL(19, 2) NULL,
    order_status VARCHAR(255)   NOT NULL,
    CONSTRAINT pk_order PRIMARY KEY (id)
);

DROP TABLE IF EXISTS order_detail;
CREATE TABLE order_detail
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    order_id   BIGINT         NULL,
    book_id    BIGINT         NULL,
    quantity   INT            NOT NULL,
    unit_price DECIMAL(19, 2) NULL,
    CONSTRAINT pk_order_detail PRIMARY KEY (id)
);

DROP TABLE IF EXISTS user;
CREATE TABLE user
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    email     VARCHAR(255) NOT NULL,
    password  VARCHAR(255) NOT NULL,
    role_name VARCHAR(255) NULL,
    firstname VARCHAR(255) NULL,
    lastname  VARCHAR(255) NULL,
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT UQ_Users_Email UNIQUE (email)
);

DROP TABLE IF EXISTS role;
CREATE TABLE role
(
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    permissions VARCHAR(255) NOT NULL,
    CONSTRAINT UQ_Roles_Name UNIQUE(name)
);


ALTER TABLE user
    ADD CONSTRAINT uc_user_password UNIQUE (password);

ALTER TABLE order_detail
    ADD CONSTRAINT FK_ORDER_DETAIL_ON_BOOK FOREIGN KEY (book_id) REFERENCES book (id);

ALTER TABLE order_detail
    ADD CONSTRAINT FK_ORDER_DETAIL_ON_ORDER FOREIGN KEY (order_id) REFERENCES `order` (id);

ALTER TABLE `order`
    ADD CONSTRAINT FK_ORDER_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);
