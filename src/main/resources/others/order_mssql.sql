CREATE TABLE [order]
(
    id INT NOT NULL IDENTITY (1,1) PRIMARY KEY ,
    date_time DATETIME not null,
    user_id integer not null,
    total_amount BIGINT,
    order_status integer not null,

    constraint fk_user_id foreign key (user_id) references utente(ID),
    constraint fr_order_status foreign key (order_status) references order_status(id)
);