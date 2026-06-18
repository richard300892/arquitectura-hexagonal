SELECT r.role_name
FROM app_role r
INNER JOIN app_user_role ur ON ur.role_id = r.id
WHERE ur.user_id = :userId
  AND r.enabled = 1
