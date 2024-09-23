CREATE TABLE IF NOT EXISTS pokemon_types (
    id      BIGSERIAL   PRIMARY KEY,
    name    VARCHAR     NOT NULL,
    effective JSONB     NULL,
    ineffective JSONB   NULL,
    no_effect JSONB     NULL,
    CONSTRAINT name_unique UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS pokemons (
    id          BIGSERIAL   PRIMARY KEY,
    name        VARCHAR     NOT NULL,
    species     VARCHAR     NOT NULL,
    description VARCHAR     NOT NULL,
    hp          INT         NULL,
    attack      INT         NULL,
    defense     INT         NULL,
    special_attack  INT     NULL,
    special_defense INT     NULL,
    speed       INT         NULL,
    height      VARCHAR     NOT NULL,
    weight      VARCHAR     NOT NULL,
    sprite      VARCHAR     NULL,
    thumbnail   VARCHAR     NULL,
    hires       VARCHAR     NULL
);

CREATE TABLE IF NOT EXISTS pokemon_evolutions (
    id          BIGSERIAL   PRIMARY KEY,
    pokemon_id  INT         NOT NULL,
    evolution_id INT       NOT NULL,
    condition   VARCHAR     NOT NULL,
    is_prev     BOOLEAN     NOT NULL,
    is_next     BOOLEAN     NOT NULL
);

CREATE TABLE IF NOT EXISTS pokemon_abilities (
    id          BIGSERIAL   PRIMARY KEY,
    pokemon_id  INT         NOT NULL,
    ability     INT         NOT NULL,
    is_hidden   BOOLEAN     NOT NULL
);

CREATE TABLE IF NOT EXISTS pokemon_type_types (
    id          BIGSERIAL,
    pokemon_id  INT NOT NULL,
    type_id     INT NOT NULL,
    PRIMARY KEY (pokemon_id, type_id),
    CONSTRAINT fk_pokemon_types
        FOREIGN KEY (pokemon_id) REFERENCES pokemons(id) ON DELETE CASCADE,
    CONSTRAINT fk_types
        FOREIGN KEY (type_id) REFERENCES pokemon_types(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS trainers(
    id      BIGSERIAL   PRIMARY KEY,
    name    VARCHAR     NOT NULL
);

CREATE TABLE IF NOT EXISTS trainer_pokemons (
    id          BIGSERIAL,
    trainer_id  INT         NOT NULL,
    pokemon_id  INT         NOT NULL,
    created_at  TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (trainer_id, pokemon_id),
    CONSTRAINT fk_trainers
        FOREIGN KEY (trainer_id) REFERENCES trainers(id) ON DELETE CASCADE,
    CONSTRAINT fk_pokemons
        FOREIGN KEY (pokemon_id) REFERENCES pokemons(id) ON DELETE CASCADE
);