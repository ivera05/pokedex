-- Insert into users table, ensuring the user does not already exist
-- Password is poke_lab!
INSERT INTO users (username, password)
SELECT 'sam_oak', '$2a$10$auRfXTQP1QDyKVcUdPLahO0rb/wJj7CKcTCs6ZtqhQMXMKvFWSoQO'
    WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE username = 'sam_oak'
);

-- Insert roles for the user into user_roles table
INSERT INTO user_roles (user_id, role)
SELECT id, 'ADMIN'
FROM users
WHERE username = 'sam_oak'
  AND NOT EXISTS (
    SELECT 1 FROM user_roles ur
    WHERE ur.user_id = (SELECT id FROM users WHERE username = 'sam_oak')
      AND ur.role = 'ADMIN'
);