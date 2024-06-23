alter table budget
    add column author_id int null,
    add constraint fk_author
        foreign key (author_id) references Author(ID);
