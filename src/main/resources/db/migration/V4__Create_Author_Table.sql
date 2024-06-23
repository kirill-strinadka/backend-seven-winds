create table Author
(
    id serial primary key,
    full_name text not null unique,
    created_at timestamp not null default current_timestamp
);
