CREATE TABLE IF NOT EXISTS site_urls
(
    id   SERIAL,
    url VARCHAR NOT NULL,
    code VARCHAR NOT NULL,
    site_id INT NOT NULL,
    CONSTRAINT site_urls_id_pk PRIMARY KEY (id),
    CONSTRAINT site_urls_url_uniq UNIQUE (url),
    CONSTRAINT site_urls_code_uniq UNIQUE (code),
    CONSTRAINT site_urls_site_id_fk FOREIGN KEY (site_id) REFERENCES sites (id)
);

comment on table site_urls is 'URLs зарегистрированных в приложении сайтов';
comment on column site_urls.id is 'Идентификатор URL - первичный ключ';
comment on column site_urls.url is 'URL сайта';
comment on column site_urls.code is 'Значение уникального кода для URL сайта';
comment on column site_urls.site_id is 'Идентификатор домена - внешний ключ';
