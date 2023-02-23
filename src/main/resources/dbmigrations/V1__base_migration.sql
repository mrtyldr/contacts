create table contact(
    id uuid primary key,
    name varchar(25) not null,
    surname varchar(25),
    full_name varchar(51) not null,
    phone_number varchar(20) not null,
    gender varchar(10) not null
);