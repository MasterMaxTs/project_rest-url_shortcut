CREATE TABLE IF NOT EXISTS url_codes
(
    id   SERIAL,
    code VARCHAR,
    CONSTRAINT codes_id_pk PRIMARY KEY (id),
    CONSTRAINT codes_code_uniq UNIQUE (code)
);

comment on table url_codes is 'Уникальные коды для URL';
comment on column url_codes.id is 'Идентификатор кода';
comment on column url_codes.code is 'Значение уникального кода';
