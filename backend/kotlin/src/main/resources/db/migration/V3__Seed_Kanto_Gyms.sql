-- Seed Gyms for Indigo League
-- Add users
INSERT INTO users (username, password, name, avatar)
SELECT 'brock@pokemail.com',
       '$2a$10$auRfXTQP1QDyKVcUdPLahO0rb/wJj7CKcTCs6ZtqhQMXMKvFWSoQO',
       'Brock',
       '/images/avatars/Brock.png'
WHERE NOT EXISTS (SELECT 1
                  FROM users
                  WHERE username = 'brock@pokemail.com');

INSERT INTO users (username, password, name, avatar)
SELECT 'misty@pokemail.com',
       '$2a$10$auRfXTQP1QDyKVcUdPLahO0rb/wJj7CKcTCs6ZtqhQMXMKvFWSoQO',
       'Misty',
       '/images/avatars/Misty.png'
WHERE NOT EXISTS (SELECT 1
                  FROM users
                  WHERE username = 'misty@pokemail.com');

INSERT INTO users (username, password, name, avatar)
SELECT 'lt.surge@pokemail.com',
       '$2a$10$auRfXTQP1QDyKVcUdPLahO0rb/wJj7CKcTCs6ZtqhQMXMKvFWSoQO',
       'Lt. Surge',
       '/images/avatars/Lt_Surge.png'
WHERE NOT EXISTS (SELECT 1
                  FROM users
                  WHERE username = 'lt.surge@pokemail.com');

INSERT INTO users (username, password, name, avatar)
SELECT 'erika@pokemail.com',
       '$2a$10$auRfXTQP1QDyKVcUdPLahO0rb/wJj7CKcTCs6ZtqhQMXMKvFWSoQO',
       'Erika',
       '/images/avatars/Erika.png'
WHERE NOT EXISTS (SELECT 1
                  FROM users
                  WHERE username = 'erika@pokemail.com');

INSERT INTO users (username, password, name, avatar)
SELECT 'koga@pokemail.com',
       '$2a$10$auRfXTQP1QDyKVcUdPLahO0rb/wJj7CKcTCs6ZtqhQMXMKvFWSoQO',
       'Koga',
       '/images/avatars/Koga.png'
WHERE NOT EXISTS (SELECT 1
                  FROM users
                  WHERE username = 'koga@pokemail.com');

INSERT INTO users (username, password, name, avatar)
SELECT 'sabrina@pokemail.com',
       '$2a$10$auRfXTQP1QDyKVcUdPLahO0rb/wJj7CKcTCs6ZtqhQMXMKvFWSoQO',
       'Sabrina',
       '/images/avatars/Sabrina.png'
WHERE NOT EXISTS (SELECT 1
                  FROM users
                  WHERE username = 'sabrina@pokemail.com');

INSERT INTO users (username, password, name, avatar)
SELECT 'blaine@pokemail.com',
       '$2a$10$auRfXTQP1QDyKVcUdPLahO0rb/wJj7CKcTCs6ZtqhQMXMKvFWSoQO',
       'Blaine',
       '/images/avatars/Blaine.png'
WHERE NOT EXISTS (SELECT 1
                  FROM users
                  WHERE username = 'blaine@pokemail.com');

INSERT INTO users (username, password, name, avatar)
SELECT 'giovanni@pokemail.com',
       '$2a$10$auRfXTQP1QDyKVcUdPLahO0rb/wJj7CKcTCs6ZtqhQMXMKvFWSoQO',
       'Giovanni',
       '/images/avatars/Giovanni.png'
WHERE NOT EXISTS (SELECT 1
                  FROM users
                  WHERE username = 'giovanni@pokemail.com');

-- TRAINER role for each leader
INSERT INTO user_roles (user_id, role)
SELECT u.id, 'TRAINER'
FROM users u
WHERE u.username = 'brock@pokemail.com'
  AND NOT EXISTS (SELECT 1
                  FROM user_roles ur
                  WHERE ur.user_id = u.id
                    AND ur.role = 'TRAINER');

INSERT INTO user_roles (user_id, role)
SELECT u.id, 'TRAINER'
FROM users u
WHERE u.username = 'misty@pokemail.com'
  AND NOT EXISTS (SELECT 1
                  FROM user_roles ur
                  WHERE ur.user_id = u.id
                    AND ur.role = 'TRAINER');

INSERT INTO user_roles (user_id, role)
SELECT u.id, 'TRAINER'
FROM users u
WHERE u.username = 'lt.surge@pokemail.com'
  AND NOT EXISTS (SELECT 1
                  FROM user_roles ur
                  WHERE ur.user_id = u.id
                    AND ur.role = 'TRAINER');

INSERT INTO user_roles (user_id, role)
SELECT u.id, 'TRAINER'
FROM users u
WHERE u.username = 'erika@pokemail.com'
  AND NOT EXISTS (SELECT 1
                  FROM user_roles ur
                  WHERE ur.user_id = u.id
                    AND ur.role = 'TRAINER');

INSERT INTO user_roles (user_id, role)
SELECT u.id, 'TRAINER'
FROM users u
WHERE u.username = 'koga@pokemail.com'
  AND NOT EXISTS (SELECT 1
                  FROM user_roles ur
                  WHERE ur.user_id = u.id
                    AND ur.role = 'TRAINER');

INSERT INTO user_roles (user_id, role)
SELECT u.id, 'TRAINER'
FROM users u
WHERE u.username = 'sabrina@pokemail.com'
  AND NOT EXISTS (SELECT 1
                  FROM user_roles ur
                  WHERE ur.user_id = u.id
                    AND ur.role = 'TRAINER');

INSERT INTO user_roles (user_id, role)
SELECT u.id, 'TRAINER'
FROM users u
WHERE u.username = 'blaine@pokemail.com'
  AND NOT EXISTS (SELECT 1
                  FROM user_roles ur
                  WHERE ur.user_id = u.id
                    AND ur.role = 'TRAINER');

INSERT INTO user_roles (user_id, role)
SELECT u.id, 'TRAINER'
FROM users u
WHERE u.username = 'giovanni@pokemail.com'
  AND NOT EXISTS (SELECT 1
                  FROM user_roles ur
                  WHERE ur.user_id = u.id
                    AND ur.role = 'TRAINER');

-- Trainers
WITH trainer_data (email, bio_text, city) AS (VALUES ('brock@pokemail.com',
                                                      'The Rock-Solid Pokémon Trainer! My determination is as hard as a diamond.',
                                                      'Pewter City'),
                                                     ('misty@pokemail.com',
                                                      'The Tomboyish Mermaid! I specialize in Water-type Pokémon. Don''t get soaked!',
                                                      'Cerulean City'),
                                                     ('lt.surge@pokemail.com',
                                                      'The Lightning American! Ten-hut! My Electric Pokémon will zap you into submission!',
                                                      'Vermilion City'),
                                                     ('erika@pokemail.com',
                                                      'The Nature-Loving Princess. My Grass-type Pokémon thrive in the beautiful Celadon City sun.',
                                                      'Celadon City'),
                                                     ('koga@pokemail.com',
                                                      'The Poisonous Ninja Master. You shall succumb to my techniques and toxic Pokémon!',
                                                      'Fuchsia City'),
                                                     ('sabrina@pokemail.com',
                                                      'The Master of Psychic Pokémon. I had a vision of your defeat before you even walked in.',
                                                      'Saffron City'),
                                                     ('blaine@pokemail.com',
                                                      'The Hot-Headed Quiz Master! My Fire-type Pokémon are burning with passion!',
                                                      'Cinnabar Island'),
                                                     ('giovanni@pokemail.com',
                                                      'The Greatest Trainer. Ground-type Pokémon are my specialty, and power is my goal.',
                                                      'Viridian City'))
INSERT
INTO trainers (user_id, title, city, region, bio)
SELECT u.id, 'Gym Leader', td.city, 'Kanto', td.bio_text
FROM users u
     JOIN trainer_data td ON u.username = td.email
WHERE NOT EXISTS (SELECT 1
                  FROM trainers t
                  WHERE t.user_id = u.id);

-- Gyms from the Indigo League / Kanto circuit
INSERT INTO gyms (trainer_id, league, region, city, badge, image)
SELECT t.id, 'Indigo', 'Kanto', 'Pewter City', 'Boulder Badge', '/images/badges/Boulder_Badge.png'
FROM trainers t
     JOIN users u ON u.id = t.user_id
WHERE u.username = 'brock@pokemail.com'
  AND NOT EXISTS (SELECT 1
                  FROM gyms g
                  WHERE g.city = 'Pewter City');

INSERT INTO gyms (trainer_id, league, region, city, badge, image)
SELECT t.id, 'Indigo', 'Kanto', 'Cerulean City', 'Cascade Badge', '/images/badges/Cascade_Badge.png'
FROM trainers t
     JOIN users u ON u.id = t.user_id
WHERE u.username = 'misty@pokemail.com'
  AND NOT EXISTS (SELECT 1
                  FROM gyms g
                  WHERE g.city = 'Cerulean City');

INSERT INTO gyms (trainer_id, league, region, city, badge, image)
SELECT t.id, 'Indigo', 'Kanto', 'Vermilion City', 'Thunder Badge', '/images/badges/Thunder_Badge.png'
FROM trainers t
     JOIN users u ON u.id = t.user_id
WHERE u.username = 'lt.surge@pokemail.com'
  AND NOT EXISTS (SELECT 1
                  FROM gyms g
                  WHERE g.city = 'Vermilion City');

INSERT INTO gyms (trainer_id, league, region, city, badge, image)
SELECT t.id, 'Indigo', 'Kanto', 'Celadon City', 'Rainbow Badge', '/images/badges/Rainbow_Badge.png'
FROM trainers t
     JOIN users u ON u.id = t.user_id
WHERE u.username = 'erika@pokemail.com'
  AND NOT EXISTS (SELECT 1
                  FROM gyms g
                  WHERE g.city = 'Celadon City');

INSERT INTO gyms (trainer_id, league, region, city, badge, image)
SELECT t.id, 'Indigo', 'Kanto', 'Fuchsia City', 'Soul Badge', '/images/badges/Soul_Badge.png'
FROM trainers t
     JOIN users u ON u.id = t.user_id
WHERE u.username = 'koga@pokemail.com'
  AND NOT EXISTS (SELECT 1
                  FROM gyms g
                  WHERE g.city = 'Fuchsia City');

INSERT INTO gyms (trainer_id, league, region, city, badge, image)
SELECT t.id, 'Indigo', 'Kanto', 'Saffron City', 'Marsh Badge', '/images/badges/Marsh_Badge.png'
FROM trainers t
     JOIN users u ON u.id = t.user_id
WHERE u.username = 'sabrina@pokemail.com'
  AND NOT EXISTS (SELECT 1
                  FROM gyms g
                  WHERE g.city = 'Saffron City');

INSERT INTO gyms (trainer_id, league, region, city, badge, image)
SELECT t.id, 'Indigo', 'Kanto', 'Cinnabar Island', 'Volcano Badge', '/images/badges/Volcano_Badge.png'
FROM trainers t
     JOIN users u ON u.id = t.user_id
WHERE u.username = 'blaine@pokemail.com'
  AND NOT EXISTS (SELECT 1
                  FROM gyms g
                  WHERE g.city = 'Cinnabar Island');

INSERT INTO gyms (trainer_id, league, region, city, badge, image)
SELECT t.id, 'Indigo', 'Kanto', 'Viridian City', 'Earth Badge', '/images/badges/Earth_Badge.png'
FROM trainers t
     JOIN users u ON u.id = t.user_id
WHERE u.username = 'giovanni@pokemail.com'
  AND NOT EXISTS (SELECT 1
                  FROM gyms g
                  WHERE g.city = 'Viridian City');

-- Add pokemons to gym leaers
INSERT INTO caught_pokemons (trainer_id, pokemon_id)
SELECT t.id, p.id
FROM trainers t
     JOIN users u ON t.user_id = u.id
     CROSS JOIN pokemons p
WHERE (
    (u.username = 'brock@pokemail.com' AND p.name IN ('Geodude', 'Onix')) OR
    (u.username = 'misty@pokemail.com' AND p.name IN ('Staryu', 'Starmie')) OR
    (u.username = 'lt.surge@pokemail.com' AND p.name IN ('Voltorb', 'Pikachu', 'Raichu')) OR
    (u.username = 'erika@pokemail.com' AND p.name IN ('Victreebel', 'Tangela', 'Vileplume')) OR
    (u.username = 'koga@pokemail.com' AND p.name IN ('Koffing', 'Muk', 'Weezing')) OR
    (u.username = 'sabrina@pokemail.com' AND p.name IN ('Kadabra', 'Mr. Mime', 'Venomoth', 'Alakazam')) OR
    (u.username = 'blaine@pokemail.com' AND p.name IN ('Growlithe', 'Ponyta', 'Rapidash', 'Arcanine')) OR
    (u.username = 'giovanni@pokemail.com' AND p.name IN ('Rhyhorn', 'Dugtrio', 'Nidoqueen', 'Nidoking', 'Rhydon'))
    )
  AND NOT EXISTS (SELECT 1
                  FROM caught_pokemons cp
                  WHERE cp.trainer_id = t.id
                    AND cp.pokemon_id = p.id);