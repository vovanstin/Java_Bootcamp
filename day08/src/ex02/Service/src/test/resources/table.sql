drop table if exists Users;

create table Users (
    id int identity primary key,
    email varchar(256) not null ,
    password varchar(256) not null
);
