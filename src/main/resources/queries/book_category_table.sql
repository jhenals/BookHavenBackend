create table book_categories
(
    book_id     bigint not null,
    category_id bigint not null,
    constraint book_categories_pk
        primary key (category_id, book_id),
    constraint book_categories_book_id_fk
        foreign key (book_id) references book (id),
    constraint book_categories_category_id_fk
        foreign key (category_id) references category (id)
);

