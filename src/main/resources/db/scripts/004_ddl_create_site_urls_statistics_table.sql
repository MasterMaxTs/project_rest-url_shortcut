CREATE TABLE IF NOT EXISTS site_urls_statistics
(
    id            SERIAL,
    total         INT NOT NULL,
    url_id        INT     NOT NULL,
    CONSTRAINT statistics_id_pk PRIMARY KEY (id),
    CONSTRAINT statistics_url_id_fk FOREIGN KEY (url_id) REFERENCES site_urls (id)
);


comment on table site_urls_statistics is 'Статистика посещённых URLs сайтов';
comment on column site_urls_statistics.id is 'Идентификатор статиcтических данных - первичный ключ';
comment on column site_urls_statistics.total is 'Счётчик посещения URL сайта';
comment on column site_urls_statistics.url_id is 'Идентификатор URL сайта - внешний ключ';