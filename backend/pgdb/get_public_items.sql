SELECT
    CAST(i.id AS varchar),
    i.title,
    u.first_name,
    u.last_name,
    i.publish_start ,
    CAST(i.item_category_id AS varchar),
    CAST(l.country_id AS varchar),
    CASE WHEN country_trs.id is null THEN country.english_name ELSE country_trs.value END,
    CAST(l.state_id AS varchar),
    CASE WHEN state_trs.id is null THEN state.english_name ELSE state_trs.value END,
    CAST(l.city_id AS varchar),
    CASE WHEN city_trs.id is null THEN city.english_name ELSE city_trs.value END,
    (SELECT p.media_link FROM attachments p WHERE p.picture_item_id = i.id ORDER BY p.order_index ASC LIMIT 1)
FROM items i
    INNER JOIN users u on u.id = i.owner_user_id
    INNER JOIN item_categories category on category.id = i.item_category_id
    INNER JOIN locations l on i.location_id = l.id
    INNER JOIN countries country on country.id = l.country_id
    INNER JOIN states state on state.id = l.state_id
    INNER JOIN cities city on city.id = l.city_id
    LEFT JOIN data_translations country_trs ON (l.country_id = country_trs.country_id AND country_trs.language = 0)
    LEFT JOIN data_translations state_trs ON (l.state_id = state_trs.state_id AND country_trs.language = 0)
    LEFT JOIN data_translations city_trs ON (l.city_id = city_trs.city_id AND country_trs.language = 0)
WHERE
    ('Bmw' IS NULL
   OR lower(i.title) LIKE lower('%Bmw%')
    )
  AND
    (101000000 IS NULL
   OR (101000000 % 100 != 0 AND category.category_code = 101000000)
   OR (101000000 % 100 = 0 AND 101000000 % 10000 != 0 AND category.category_code / 100 = 101000000 / 100)
   OR (101000000 % 100 = 0 AND 101000000 % 10000 = 0 AND 101000000 % 1000000 != 0 AND category.category_code / 10000 = 101000000 / 10000)
   OR (101000000 % 100 = 0 AND 101000000 % 10000 = 0 AND 101000000 % 1000000 = 0 AND 101000000 % 100000000 != 0 AND category.category_code / 1000000 = 101000000 / 1000000)
   OR (101000000 % 100 = 0 AND 101000000 % 10000 = 0 AND 101000000 % 1000000 = 0 AND 101000000 % 100000000 = 0 AND category.category_code / 100000000 = 101000000 / 100000000)
    )


