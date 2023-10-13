CREATE TABLE `order`
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT,
    date_time DATETIME,
    total_amount DECIMAL(19,2),
    order_status ENUM ('CREATED','PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELED' ),

    PRIMARY KEY (id),
    FOREIGN KEY (user_id)
        REFERENCES utente(id)
);