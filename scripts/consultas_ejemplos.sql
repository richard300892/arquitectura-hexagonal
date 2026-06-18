-- =====================================================
-- CONSULTAS EJEMPLOS ÚTILES PARA TRABAJAR CON LA BD
-- =====================================================

-- =====================================================
-- CONSULTAS DE LISTADOS BÁSICOS
-- =====================================================

-- 1. Ver todos los tipos de documento
SELECT * FROM tipo_documento WHERE activo = 'S';

-- 2. Ver todos los países
SELECT * FROM paises WHERE activo = 'S';

-- 3. Ver todos los departamentos de Colombia
SELECT d.*, p.nombre as pais
FROM departamentos d
JOIN paises p ON d.id_pais = p.id_pais
WHERE p.codigo_iso = 'CO' AND d.activo = 'S'
ORDER BY d.nombre;

-- 4. Ver todos los municipios de Antioquia
SELECT m.*, d.nombre as departamento
FROM municipios m
JOIN departamentos d ON m.id_departamento = d.id_departamento
WHERE d.nombre = 'Antioquia' AND m.activo = 'S'
ORDER BY m.nombre;

-- 5. Ver todos los barrios de Medellín
SELECT b.*, m.nombre as municipio
FROM barrios b
JOIN municipios m ON b.id_municipio = m.id_municipio
WHERE m.nombre = 'Medellín' AND b.activo = 'S'
ORDER BY b.nombre;

-- =====================================================
-- CONSULTAS CON JOINS COMPLEJOS
-- =====================================================

-- 6. Ver información completa de ubicación geográfica
SELECT
    p.nombre as pais,
    d.nombre as departamento,
    m.nombre as municipio,
    b.nombre as barrio
FROM barrios b
JOIN municipios m ON b.id_municipio = m.id_municipio
JOIN departamentos d ON m.id_departamento = d.id_departamento
JOIN paises p ON d.id_pais = p.id_pais
WHERE p.codigo_iso = 'CO'
ORDER BY p.nombre, d.nombre, m.nombre, b.nombre;

-- 7. Ver categorías de productos con estructura jerárquica
SELECT
    c.id_categoria,
    c.codigo,
    c.nombre,
    CASE WHEN c.id_categoria_padre IS NULL THEN 'Principal'
         ELSE 'Subcategoría' END as tipo,
    cp.nombre as categoria_padre
FROM categorias c
LEFT JOIN categorias cp ON c.id_categoria_padre = cp.id_categoria
WHERE c.activo = 'S'
ORDER BY c.nombre;

-- 8. Ver métodos de pago disponibles
SELECT
    codigo,
    nombre,
    CASE WHEN requiere_validacion = 'S' THEN 'Sí' ELSE 'No' END as requiere_validacion
FROM metodos_pago
WHERE activo = 'S'
ORDER BY nombre;

-- =====================================================
-- CONSULTAS ESTADÍSTICAS
-- =====================================================

-- 9. Contar registros por tabla
SELECT
    'TIPO_DOCUMENTO' as tabla, COUNT(*) as cantidad FROM tipo_documento
UNION ALL
SELECT 'PAISES', COUNT(*) FROM paises
UNION ALL
SELECT 'DEPARTAMENTOS', COUNT(*) FROM departamentos
UNION ALL
SELECT 'MUNICIPIOS', COUNT(*) FROM municipios
UNION ALL
SELECT 'BARRIOS', COUNT(*) FROM barrios
UNION ALL
SELECT 'GENEROS', COUNT(*) FROM generos
UNION ALL
SELECT 'ESTADOS_CIVILES', COUNT(*) FROM estados_civiles
UNION ALL
SELECT 'TIPOS_USUARIO', COUNT(*) FROM tipos_usuario
UNION ALL
SELECT 'CATEGORIAS', COUNT(*) FROM categorias
UNION ALL
SELECT 'MONEDAS', COUNT(*) FROM monedas
UNION ALL
SELECT 'ESTADOS_PEDIDO', COUNT(*) FROM estados_pedido
UNION ALL
SELECT 'METODOS_PAGO', COUNT(*) FROM metodos_pago
ORDER BY tabla;

-- 10. Contar municipios por departamento
SELECT
    d.nombre as departamento,
    COUNT(m.id_municipio) as cantidad_municipios
FROM departamentos d
LEFT JOIN municipios m ON d.id_departamento = m.id_departamento
GROUP BY d.nombre
ORDER BY cantidad_municipios DESC;

-- 11. Contar barrios por municipio
SELECT
    m.nombre as municipio,
    d.nombre as departamento,
    COUNT(b.id_barrio) as cantidad_barrios
FROM municipios m
LEFT JOIN departamentos d ON m.id_departamento = d.id_departamento
LEFT JOIN barrios b ON m.id_municipio = b.id_municipio
WHERE m.activo = 'S'
GROUP BY m.nombre, d.nombre
HAVING COUNT(b.id_barrio) > 0
ORDER BY cantidad_barrios DESC;

-- =====================================================
-- EJEMPLOS DE INSERCIONES (usar según necesidad)
-- =====================================================

-- 12. Insertar un nuevo país
-- INSERT INTO paises (id_pais, codigo_iso, nombre, gentilicio, activo)
-- VALUES (seq_paises.NEXTVAL, 'BR', 'Brasil', 'Brasileño', 'S');
-- COMMIT;

-- 13. Insertar un nuevo departamento
-- INSERT INTO departamentos (id_departamento, id_pais, codigo, nombre, activo)
-- VALUES (seq_departamentos.NEXTVAL, 1, '99', 'Nuevo Departamento', 'S');
-- COMMIT;

-- 14. Insertar un nuevo municipio
-- INSERT INTO municipios (id_municipio, id_departamento, codigo, nombre, activo)
-- VALUES (seq_municipios.NEXTVAL, 1, '05999', 'Nuevo Municipio', 'S');
-- COMMIT;

-- 15. Insertar un nuevo barrio
-- INSERT INTO barrios (id_barrio, id_municipio, codigo, nombre, activo)
-- VALUES (seq_barrios.NEXTVAL, 1, '05001999', 'Nuevo Barrio', 'S');
-- COMMIT;

-- =====================================================
-- EJEMPLOS DE ACTUALIZACIONES
-- =====================================================

-- 16. Activar/Desactivar un registro
-- UPDATE tipo_documento SET activo = 'N' WHERE codigo = 'PA';
-- COMMIT;

-- 17. Actualizar información
-- UPDATE categorias
-- SET descripcion = 'Categoría actualizada'
-- WHERE codigo = 'ELEC';
-- COMMIT;

-- =====================================================
-- EJEMPLOS DE BÚSQUEDAS CON FILTROS
-- =====================================================

-- 18. Buscar municipios por nombre
SELECT * FROM municipios
WHERE UPPER(nombre) LIKE '%MEDELL%'
AND activo = 'S';

-- 19. Buscar categorías por código
SELECT * FROM categorias
WHERE UPPER(codigo) LIKE '%CEL%'
AND activo = 'S';

-- 20. Buscar barrios que contengan una palabra
SELECT b.*, m.nombre as municipio
FROM barrios b
JOIN municipios m ON b.id_municipio = m.id_municipio
WHERE UPPER(b.nombre) LIKE '%POPULAR%'
AND b.activo = 'S';

-- =====================================================
-- CONSULTAS DE VALIDACIÓN DE DATOS
-- =====================================================

-- 21. Verificar integridad: Municipios sin departamento
SELECT * FROM municipios m
WHERE m.id_departamento NOT IN (SELECT id_departamento FROM departamentos);

-- 22. Verificar integridad: Barrios sin municipio
SELECT * FROM barrios b
WHERE b.id_municipio NOT IN (SELECT id_municipio FROM municipios);

-- 23. Verificar integridad: Categorías huérfanas
SELECT * FROM categorias c
WHERE c.id_categoria_padre NOT IN (SELECT id_categoria FROM categorias)
AND c.id_categoria_padre IS NOT NULL;

-- 24. Ver registros duplicados (si los hubiera)
SELECT codigo, COUNT(*)
FROM tipo_documento
GROUP BY codigo
HAVING COUNT(*) > 1;

-- =====================================================
-- LIMPIAR DATOS (usar con cuidado)
-- =====================================================

-- 25. Desactivar todos los registros de una tabla
-- UPDATE tipo_documento SET activo = 'N';
-- COMMIT;

-- 26. Borrar registros específicos (CUIDADO: esto es irreversible)
-- DELETE FROM barrios WHERE id_municipio = 999;
-- COMMIT;

-- =====================================================
-- EXPORTAR/RESPALDAR DATOS
-- =====================================================

-- 27. Ver la estructura de una tabla
DESC tipo_documento;

-- 28. Ver las columnas de una tabla
SELECT column_name, data_type, nullable
FROM user_tab_columns
WHERE table_name = 'TIPO_DOCUMENTO'
ORDER BY column_id;

-- 29. Ver las foreign keys de una tabla
SELECT constraint_name, table_name, column_name, r_table_name
FROM user_constraints uc
JOIN user_cons_columns ucc ON uc.constraint_name = ucc.constraint_name
WHERE uc.constraint_type = 'R'
AND table_name = 'MUNICIPIOS';

-- =====================================================
-- FIN DE CONSULTAS
-- =====================================================

