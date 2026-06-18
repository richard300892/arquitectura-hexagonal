SELECT
    u.id,
    u.username,
    u.password_hash,
    u.enabled
FROM app_user u
WHERE u.username = :username
