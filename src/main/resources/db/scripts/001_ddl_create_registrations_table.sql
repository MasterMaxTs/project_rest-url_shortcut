CREATE TABLE IF NOT EXISTS credentials
(
    id       SERIAL,
    login    VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    CONSTRAINT credentials_id_pk PRIMARY KEY (id),
    CONSTRAINT credentials_login_uniq UNIQUE (login),
    CONSTRAINT credentials_password_uniq UNIQUE (password)
);

comment on table credentials is 'Регистрационные данные для сайтов';
comment on column credentials.id is 'Идентификатор регистрационных данных - первичный ключ';
comment on column credentials.login is 'Уникальный логин';
comment on column credentials.password is 'Уникальный пароль';
