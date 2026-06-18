SELECT COUNT(1)
FROM customer
WHERE document_type_id = :documentTypeId
  AND document_number = :documentNumber
