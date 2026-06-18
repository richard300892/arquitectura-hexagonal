SELECT
    id,
    app_user_id,
    document_type_id,
    document_number,
    first_name,
    last_name,
    email,
    phone,
    mobile_phone,
    gender_id,
    marital_status_id,
    birth_date,
    anniversary_date,
    neighborhood_id,
    address_line,
    address_complement,
    postal_code,
    preferred_currency_id,
    accepts_marketing,
    notes,
    enabled
FROM customer
WHERE id = :id
