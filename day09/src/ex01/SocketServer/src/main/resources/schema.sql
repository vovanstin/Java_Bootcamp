drop table if exists Users;

CREATE TABLE IF NOT EXISTS Users (
    id SERIAL PRIMARY KEY,
    username text UNIQUE NOT NULL,
    password text NOT NULL
);

drop table if exists Messages;

CREATE TABLE IF NOT EXISTS Messages (
    id SERIAL PRIMARY KEY,
    author INTEGER REFERENCES chat.users(id) NOT NULL,
    text TEXT NOT NULL,
    dateTime TIMESTAMP
);