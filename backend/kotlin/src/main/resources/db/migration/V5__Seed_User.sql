-- Insert into users table, ensuring the user does not already exist
-- Password is poke_lab!
INSERT INTO users (username, password, name, avatar)
SELECT 'sam_oak@pokemail.com',
       '$2a$10$auRfXTQP1QDyKVcUdPLahO0rb/wJj7CKcTCs6ZtqhQMXMKvFWSoQO',
       'Sam Oak',
       '/images/avatars/sam_oak.webp'
WHERE NOT EXISTS (SELECT 1
                  FROM users
                  WHERE username = 'sam_oak@pokemail.com');

-- Insert roles for the user into user_roles table
INSERT INTO user_roles (user_id, role)
SELECT id, 'ADMIN'
FROM users
WHERE username = 'sam_oak@pokemail.com'
  AND NOT EXISTS (SELECT 1
                  FROM user_roles ur
                  WHERE ur.user_id = (SELECT id FROM users WHERE username = 'sam_oak@pokemail.com')
                    AND ur.role = 'ADMIN');

-- Insert trainer for the user into trainers table
INSERT INTO trainers (user_id, title, city, region, bio)
SELECT u.id,
       'Professor',
       'Pallet Town',
       'Kanto',
       'The world-renowned Pokémon Professor. I have dedicated my life to studying the relationships between humans and Pokémon. It''s time to fill that Pokédex!'
FROM users u
WHERE u.username = 'sam_oak@pokemail.com'
  AND NOT EXISTS (SELECT 1
                  FROM trainers t
                  WHERE t.user_id = u.id);

-- Migration to seed Professor Oak's caught Pokemon
INSERT INTO caught_pokemons (trainer_id, pokemon_id)
SELECT t.id, p.id
FROM trainers t,
     pokemons p
WHERE t.user_id = (SELECT id FROM users WHERE username = 'sam_oak@pokemail.com')
  AND p.name IN (
                 'Bulbasaur',
                 'Charmander',
                 'Squirtle',
                 'Arcanine',
                 'Gyarados',
                 'Exeggutor',
                 'Pidgeot',
                 'Dragonite'
    )
  -- Prevent duplicate entries if the migration is re-run
  AND NOT EXISTS (SELECT 1
                  FROM caught_pokemons cp
                  WHERE cp.trainer_id = t.id
                    AND cp.pokemon_id = p.id);