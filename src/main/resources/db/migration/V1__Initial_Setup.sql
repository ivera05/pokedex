CREATE TABLE IF NOT EXISTS trainers(
    id      BIGSERIAL      PRIMARY KEY,
    name    VARCHAR     NOT NULL
);

CREATE TABLE IF NOT EXISTS pokemon_types (
    id      BIGSERIAL      PRIMARY KEY,
    name    VARCHAR     NOT NULL,
    effective JSONB     NOT NULL,
    ineffective JSONB   NOT NULL,
    no_effect JSONB     NOT NULL
);

CREATE TABLE IF NOT EXISTS pokemons (
    id          BIGSERIAL      PRIMARY KEY,
    name        VARCHAR     NOT NULL,
    species     VARCHAR     NOT NULL,
    description VARCHAR     NOT NULL,
    evolutions  JSONB       NOT NULL,
    base        JSONB       NOT NULL,
    profile     JSONB       NOT NULL,
    image       JSONB       NOT NULL
);

CREATE TABLE IF NOT EXISTS trainer_pokemons (
    id          SERIAL,
    trainer_id  INT NOT NULL,
    pokemon_id  INT NOT NULL,
    PRIMARY KEY (trainer_id, pokemon_id),
    CONSTRAINT fk_trainers
        FOREIGN KEY (trainer_id) REFERENCES trainers(id) ON DELETE CASCADE,
    CONSTRAINT fk_pokemons
        FOREIGN KEY (pokemon_id) REFERENCES pokemons(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS pokemon_type_types (
    id          SERIAL,
    pokemon_id  INT NOT NULL,
    type_id     INT NOT NULL,
    PRIMARY KEY (pokemon_id, type_id),
    CONSTRAINT fk_pokemon_types
        FOREIGN KEY (pokemon_id) REFERENCES pokemons(id) ON DELETE CASCADE,
    CONSTRAINT fk_types
        FOREIGN KEY (type_id) REFERENCES pokemon_types(id) ON DELETE CASCADE
);
