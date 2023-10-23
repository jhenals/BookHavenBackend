
CREATE TABLE book
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    title     VARCHAR(255) NULL,
    author    VARCHAR(255) NULL,
    image_url VARCHAR(255) NULL,
    CONSTRAINT pk_book PRIMARY KEY (id)
);

CREATE TABLE category
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NULL,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE `order`
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    user_id      BIGINT         NULL,
    date_time    datetime       NULL,
    total_amount DECIMAL(19, 2) NULL,
    order_status VARCHAR(255)   NOT NULL,
    CONSTRAINT pk_order PRIMARY KEY (id)
);

CREATE TABLE order_detail
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    order_id   BIGINT         NULL,
    book_id    BIGINT         NULL,
    quantity   INT            NOT NULL,
    unit_price DECIMAL(19, 2) NULL,
    CONSTRAINT pk_order_detail PRIMARY KEY (id)
);

CREATE TABLE review
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    user_id          BIGINT       NULL,
    book_id          BIGINT       NULL,
    rating           INT          NOT NULL,
    comment          VARCHAR(255) NULL,
    date_time_review datetime     NULL,
    CONSTRAINT pk_review PRIMARY KEY (id)
);

CREATE TABLE user
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    email     VARCHAR(255) NULL,
    password  VARCHAR(255) NOT NULL,
    role_name VARCHAR(255) NULL,
    firstname VARCHAR(255) NULL,
    lastname  VARCHAR(255) NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE user
    ADD CONSTRAINT uc_user_password UNIQUE (password);

ALTER TABLE order_detail
    ADD CONSTRAINT FK_ORDER_DETAIL_ON_BOOK FOREIGN KEY (book_id) REFERENCES book (id);

ALTER TABLE order_detail
    ADD CONSTRAINT FK_ORDER_DETAIL_ON_ORDER FOREIGN KEY (order_id) REFERENCES `order` (id);

ALTER TABLE `order`
    ADD CONSTRAINT FK_ORDER_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE review
    ADD CONSTRAINT FK_REVIEW_ON_BOOK FOREIGN KEY (book_id) REFERENCES book (id);

ALTER TABLE review
    ADD CONSTRAINT FK_REVIEW_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);