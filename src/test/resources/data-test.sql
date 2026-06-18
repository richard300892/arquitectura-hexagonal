INSERT INTO app_role (role_name, enabled) VALUES ('ROLE_ADMIN', 1);
INSERT INTO app_role (role_name, enabled) VALUES ('ROLE_USER_WEB', 1);
INSERT INTO app_user (username, password_hash, enabled)
VALUES ('admin', '$2a$10$DnrcSdA0odidFrccOFZz6OAzQtGdH/iJWEZt47YxqLEl4HVfPQnXW', 1);
INSERT INTO app_user (username, password_hash, enabled)
VALUES ('webuser', '$2a$10$DnrcSdA0odidFrccOFZz6OAzQtGdH/iJWEZt47YxqLEl4HVfPQnXW', 1);
INSERT INTO app_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO app_user_role (user_id, role_id) VALUES (2, 2);

INSERT INTO user_type (code, name, enabled) VALUES ('CLI', 'Client', 1);
INSERT INTO document_type (code, name, enabled) VALUES ('CC', 'Citizenship ID', 1);

INSERT INTO customer (
    user_type_id,
    document_type_id,
    document_number,
    first_name,
    last_name,
    email,
    phone,
    mobile_phone,
    accepts_marketing,
    enabled
)
VALUES (
    1,
    1,
    '1234567890',
    'Maria',
    'Gomez',
    'maria.gomez@example.com',
    '6041234567',
    '3001234567',
    1,
    1
);
