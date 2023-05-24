CREATE TABLE users (
                       username VARCHAR(128) PRIMARY KEY,
                       firstname VARCHAR(128),
                       lastname VARCHAR(128),
                       birth_date DATE,
                       role VARCHAR(32),
                       info JSONB
);

create table if not exists users
(
    id BIGSERIAL primary key,
    username   varchar(128) UNIQUE,
    firstname  varchar(128),
    lastname   varchar(128),
    birth_date date,
    role       varchar(32),
    info       jsonb
);

create table if not exists users
(
    username   varchar(128) unique,
    firstname  varchar(128),
    lastname   varchar(128),
    birth_date date,
    role       varchar(32),
    info       jsonb,
    PRIMARY KEY (firstname, lastname, birth_date)
);
---

CREATE TABLE company (
                         id SERIAL PRIMARY KEY ,
                         name VARCHAR(64) NOT NULL UNIQUE
);

create table if not exists users
(
    id BIGSERIAL primary key,
    username   varchar(128),
    firstname  varchar(128),
    lastname   varchar(128),
    birth_date date,
    role       varchar(32),
    info       jsonb,
    company_id INT REFERENCES company (id)
);