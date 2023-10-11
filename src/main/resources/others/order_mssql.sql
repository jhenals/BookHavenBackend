CREATE TABLE [dbo].[order]
(
    id BIGINT NOT NULL IDENTITY (1,1) PRIMARY KEY ,
    date_time DATETIME not null,
    user_id BIGINT not null,
    total_amount DECIMAL,
    order_status NVARCHAR(15) not null,

    constraint fk_user_id foreign key (user_id) references utente(id),
    constraint fr_order_status foreign key (order_status) references  [OrderStatus]([Type])
);