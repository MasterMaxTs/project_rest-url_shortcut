CREATE TABLE IF NOT EXISTS sites
(
    id            SERIAL,
    url           VARCHAR NOT NULL ,
    registration  BOOLEAN NOT NULL,
    credential_id INT NOT NULL,
    code_id       INT NOT NULL,
    CONSTRAINT sites_id_pk PRIMARY KEY (id),
    CONSTRAINT sites_cred_id_fk FOREIGN KEY (credential_id) REFERENCES credentials (id),
    CONSTRAINT sites_code_id_fk FOREIGN KEY (code_id) REFERENCES url_codes (id)
);


comment on table sites is 'Зарегистрированные в приложении сайты';
comment on column sites.id is 'Идентификатор сайта';
comment on column sites.url is 'URL сайта';
comment on column sites.registration is 'флаг, показывающий регистрацию сайта в приложении';
comment on column sites.credential_id is 'Идентификатор учётных данных для аутентификации в приложении - внешний ключ';
comment on column sites.code_id is 'Идентификатор уникального кода для URL сайта - внешний ключ';