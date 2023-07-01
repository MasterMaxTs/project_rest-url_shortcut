CREATE TABLE IF NOT EXISTS codes
(
    id   SERIAL,
    code VARCHAR,
    CONSTRAINT codes_id_pk PRIMARY KEY (id),
    CONSTRAINT codes_code_uniq UNIQUE (code)
);

comment on table codes is 'Уникальные коды для URL';
comment on column codes.id is 'Идентификатор кода';
comment on column codes.code is 'Значение уникального кода';
