-- Insert into users table, ensuring the user does not already exist
-- Password is poke_lab!
INSERT INTO users (username, password, name, avatar)
SELECT 'sam_oak@pokemail.com',
       '$2a$10$auRfXTQP1QDyKVcUdPLahO0rb/wJj7CKcTCs6ZtqhQMXMKvFWSoQO',
       'Sam Oak',
       '/images/avatar/sam_oak.webp'
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
INSERT INTO trainers (user_id, title, region)
SELECT u.id, 'Professor', 'Kanto'
FROM users u
WHERE u.username = 'sam_oak@pokemail.com'
  AND NOT EXISTS (
    SELECT 1
    FROM trainers t
    WHERE t.user_id = u.id
);