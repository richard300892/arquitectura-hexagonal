SELECT id, id_brand, id_product, price_list, price
FROM price
WHERE id_brand = :idBrand
	AND id_product = :idProduct
    AND :dateApplication
    between start_date AND end_date