
CREATE TABLE IF NOT EXISTS users (
    id          BIGSERIAL   PRIMARY KEY,
    username    VARCHAR     UNIQUE  NOT NULL,
    password    VARCHAR     NOT NULL,
    role        VARCHAR     NOT NULL DEFAULT 'USER',
    created_at  TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    deleted_at  TIMESTAMP   NULL
);
