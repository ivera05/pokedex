-- 1. Create Users for Johto Gym Leaders
INSERT INTO users (username, password, name, avatar)
VALUES ('falkner@pokemail.com', '$2a$10$auRfXTQP1QDyKVcUdPLahO0rb/wJj7CKcTCs6ZtqhQMXMKvFWSoQO', 'Falkner',
        '/images/avatars/Falkner.png'),
       ('bugsy@pokemail.com', '$2a$10$auRfXTQP1QDyKVcUdPLahO0rb/wJj7CKcTCs6ZtqhQMXMKvFWSoQO', 'Bugsy',
        '/images/avatars/Bugsy.png'),
       ('whitney@pokemail.com', '$2a$10$auRfXTQP1QDyKVcUdPLahO0rb/wJj7CKcTCs6ZtqhQMXMKvFWSoQO', 'Whitney',
        '/images/avatars/Whitney.png'),
       ('morty@pokemail.com', '$2a$10$auRfXTQP1QDyKVcUdPLahO0rb/wJj7CKcTCs6ZtqhQMXMKvFWSoQO', 'Morty',
        '/images/avatars/Morty.png'),
       ('chuck@pokemail.com', '$2a$10$auRfXTQP1QDyKVcUdPLahO0rb/wJj7CKcTCs6ZtqhQMXMKvFWSoQO', 'Chuck',
        '/images/avatars/Chuck.png'),
       ('jasmine@pokemail.com', '$2a$10$auRfXTQP1QDyKVcUdPLahO0rb/wJj7CKcTCs6ZtqhQMXMKvFWSoQO', 'Jasmine',
        '/images/avatars/Jasmine.png'),
       ('pryce@pokemail.com', '$2a$10$auRfXTQP1QDyKVcUdPLahO0rb/wJj7CKcTCs6ZtqhQMXMKvFWSoQO', 'Pryce',
        '/images/avatars/Pryce.png'),
       ('clair@pokemail.com', '$2a$10$auRfXTQP1QDyKVcUdPLahO0rb/wJj7CKcTCs6ZtqhQMXMKvFWSoQO', 'Clair',
        '/images/avatars/Clair.png')
ON CONFLICT (username) DO NOTHING;

-- 2. Assign TRAINER roles
INSERT INTO user_roles (user_id, role)
SELECT id, 'TRAINER'
FROM users
WHERE username IN
      ('falkner@pokemail.com', 'bugsy@pokemail.com', 'whitney@pokemail.com', 'morty@pokemail.com', 'chuck@pokemail.com',
       'jasmine@pokemail.com', 'pryce@pokemail.com', 'clair@pokemail.com')
  AND NOT EXISTS (SELECT 1 FROM user_roles ur WHERE ur.user_id = users.id);

-- 3. Create Trainer Profiles
WITH trainer_data (email, bio_text) AS (VALUES ('falkner@pokemail.com',
                                                'The Elegant Exterminator! I’m carrying on my father’s legacy with the finest Flying-type Pokémon.'),
                                               ('bugsy@pokemail.com',
                                                'The Walking Bug Encyclopedia! My research into Bug-type Pokémon has made me a master of the hive.'),
                                               ('whitney@pokemail.com',
                                                'The Incredibly Pretty Girl! Don''t let my Normal-type Pokémon fool you—they''re tougher than they look!'),
                                               ('morty@pokemail.com',
                                                'The Mystic Seer of the Future. I have spent my life training in Ecruteak to see what others cannot.'),
                                               ('chuck@pokemail.com',
                                                'His Roar Banishis Evil! I spend 24 hours a day training alongside my Fighting-type Pokémon.'),
                                               ('jasmine@pokemail.com',
                                                'The Steel-Clad Defense Girl. My Pokémon have a heart of iron and a will that cannot be broken.'),
                                               ('pryce@pokemail.com',
                                                'The Teacher of Winter''s Harshness. I have lived with Pokémon for many years, and I know the cold truth of battle.'),
                                               ('clair@pokemail.com',
                                                'The Blessed User of Dragons! I hold the lineage of the world''s greatest Dragon-type masters.'))
INSERT
INTO trainers (user_id, title, region, bio)
SELECT u.id, 'Gym Leader', 'Johto', td.bio_text
FROM users u
     JOIN trainer_data td ON u.username = td.email
WHERE NOT EXISTS (SELECT 1
                  FROM trainers t
                  WHERE t.user_id = u.id);

-- 4. Seed Johto Gyms
INSERT INTO gyms (trainer_id, league, region, city, badge, image)
SELECT t.id, 'Johto', 'Johto', 'Violet City', 'Zephyr Badge', '/images/badges/Zephyr_Badge.png'
FROM trainers t
     JOIN users u ON u.id = t.user_id
WHERE u.username = 'falkner@pokemail.com'
  AND NOT EXISTS (SELECT 1 FROM gyms WHERE city = 'Violet City');

INSERT INTO gyms (trainer_id, league, region, city, badge, image)
SELECT t.id, 'Johto', 'Johto', 'Azalea Town', 'Hive Badge', '/images/badges/Hive_Badge.png'
FROM trainers t
     JOIN users u ON u.id = t.user_id
WHERE u.username = 'bugsy@pokemail.com'
  AND NOT EXISTS (SELECT 1 FROM gyms WHERE city = 'Azalea Town');

INSERT INTO gyms (trainer_id, league, region, city, badge, image)
SELECT t.id, 'Johto', 'Johto', 'Goldenrod City', 'Plain Badge', '/images/badges/Plain_Badge.png'
FROM trainers t
     JOIN users u ON u.id = t.user_id
WHERE u.username = 'whitney@pokemail.com'
  AND NOT EXISTS (SELECT 1 FROM gyms WHERE city = 'Goldenrod City');

INSERT INTO gyms (trainer_id, league, region, city, badge, image)
SELECT t.id, 'Johto', 'Johto', 'Ecruteak City', 'Fog Badge', '/images/badges/Fog_Badge.png'
FROM trainers t
     JOIN users u ON u.id = t.user_id
WHERE u.username = 'morty@pokemail.com'
  AND NOT EXISTS (SELECT 1 FROM gyms WHERE city = 'Ecruteak City');

INSERT INTO gyms (trainer_id, league, region, city, badge, image)
SELECT t.id, 'Johto', 'Johto', 'Cianwood City', 'Storm Badge', '/images/badges/Storm_Badge.png'
FROM trainers t
     JOIN users u ON u.id = t.user_id
WHERE u.username = 'chuck@pokemail.com'
  AND NOT EXISTS (SELECT 1 FROM gyms WHERE city = 'Cianwood City');

INSERT INTO gyms (trainer_id, league, region, city, badge, image)
SELECT t.id, 'Johto', 'Johto', 'Olivine City', 'Mineral Badge', '/images/badges/Mineral_Badge.png'
FROM trainers t
     JOIN users u ON u.id = t.user_id
WHERE u.username = 'jasmine@pokemail.com'
  AND NOT EXISTS (SELECT 1 FROM gyms WHERE city = 'Olivine City');

INSERT INTO gyms (trainer_id, league, region, city, badge, image)
SELECT t.id, 'Johto', 'Johto', 'Mahogany Town', 'Glacier Badge', '/images/badges/Glacier_Badge.png'
FROM trainers t
     JOIN users u ON u.id = t.user_id
WHERE u.username = 'pryce@pokemail.com'
  AND NOT EXISTS (SELECT 1 FROM gyms WHERE city = 'Mahogany Town');

INSERT INTO gyms (trainer_id, league, region, city, badge, image)
SELECT t.id, 'Johto', 'Johto', 'Blackthorn City', 'Rising Badge', '/images/badges/Rising_Badge.png'
FROM trainers t
     JOIN users u ON u.id = t.user_id
WHERE u.username = 'clair@pokemail.com'
  AND NOT EXISTS (SELECT 1 FROM gyms WHERE city = 'Blackthorn City');

-- 5. Seed Gym Leader Pokemon (GSC/HGSS Signature Teams)
INSERT INTO caught_pokemons (trainer_id, pokemon_id)
SELECT t.id, p.id
FROM trainers t
     JOIN users u ON t.user_id = u.id
     CROSS JOIN pokemons p
WHERE (
    (u.username = 'falkner@pokemail.com' AND p.name IN ('Pidgey', 'Pidgeotto')) OR
    (u.username = 'bugsy@pokemail.com' AND p.name IN ('Scyther', 'Metapod', 'Kakuna')) OR
    (u.username = 'whitney@pokemail.com' AND p.name IN ('Clefairy', 'Miltank')) OR
    (u.username = 'morty@pokemail.com' AND p.name IN ('Gastly', 'Haunter', 'Gengar')) OR
    (u.username = 'chuck@pokemail.com' AND p.name IN ('Primeape', 'Poliwrath')) OR
    (u.username = 'jasmine@pokemail.com' AND p.name IN ('Magnemite', 'Steelix')) OR
    (u.username = 'pryce@pokemail.com' AND p.name IN ('Seel', 'Dewgong', 'Piloswine')) OR
    (u.username = 'clair@pokemail.com' AND p.name IN ('Dragonair', 'Kingdra', 'Gyarados'))
    )
  AND NOT EXISTS (SELECT 1
                  FROM caught_pokemons cp
                  WHERE cp.trainer_id = t.id
                    AND cp.pokemon_id = p.id);