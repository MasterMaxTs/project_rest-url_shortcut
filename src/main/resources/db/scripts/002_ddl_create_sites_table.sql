CREATE TABLE IF NOT EXISTS sites
(
    id            SERIAL,
    site          VARCHAR NOT NULL,
    registration  BOOLEAN,
    credential_id INT     NOT NULL,
    CONSTRAINT sites_id_pk PRIMARY KEY (id),
    CONSTRAINT sites_site_uniq UNIQUE (site),
    CONSTRAINT sites_credential_id_fk FOREIGN KEY (credential_id) REFERENCES credentials (id)
);


comment on table sites is 'Зарегистрированные в приложении сайты';
comment on column sites.id is 'Идентификатор сайта';
comment on column sites.site is 'Название сайта - уникальное значение';
comment on column sites.registration is 'Флаг, показывающий регистрацию сайта в приложении';
comment on column sites.credential_id is 'Идентификатор регистрационных данных для сайта - внешний ключ';
