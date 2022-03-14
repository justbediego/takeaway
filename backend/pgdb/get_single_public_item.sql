-- FUNCTION: public.get_single_public_item(uuid, integer)

-- DROP FUNCTION IF EXISTS public.get_single_public_item(uuid, integer);

CREATE OR REPLACE FUNCTION public.get_single_public_item(
	item_id uuid,
	language_id integer)
    RETURNS TABLE(item_id character varying, title character varying, description character varying, first_name character varying, last_name character varying, public_phone_number character varying, public_email character varying, publish_start timestamp without time zone, item_category_id character varying, country_id character varying, country_name character varying, state_id character varying, state_name character varying, city_id character varying, city_name character varying, image_urls_json character varying)
    LANGUAGE 'sql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 1000

AS $BODY$
SELECT
    CAST(i.id AS varchar),
    i.title,
    i.description,
    u.first_name,
    u.last_name,
    CASE WHEN u.phone_number_is_public = TRUE THEN u.phone_number ELSE '' END,
    CASE WHEN u.email_is_public = TRUE THEN u.email ELSE '' END,
    i.publish_start ,
    CAST(i.item_category_id AS varchar),
    CAST(l.country_id AS varchar),
    CASE WHEN country_trs.id is null THEN country.english_name ELSE country_trs.value END,
    CAST(l.state_id AS varchar),
    CASE WHEN state_trs.id is null THEN state.english_name ELSE state_trs.value END,
    CAST(l.city_id AS varchar),
    CASE WHEN city_trs.id is null THEN city.english_name ELSE city_trs.value END,
    (SELECT json_agg(p.media_link) FROM attachments p WHERE p.picture_item_id = i.id)
FROM items i
         INNER JOIN users u on u.id = i.owner_user_id
         INNER JOIN locations l on i.location_id = l.id
         INNER JOIN countries country on country.id = l.country_id
         INNER JOIN states state on state.id = l.state_id
         INNER JOIN cities city on city.id = l.city_id
         LEFT JOIN data_translations country_trs ON (l.country_id = country_trs.country_id AND country_trs.language = language_id)
         LEFT JOIN data_translations state_trs ON (l.state_id = state_trs.state_id AND country_trs.language = language_id)
         LEFT JOIN data_translations city_trs ON (l.city_id = city_trs.city_id AND country_trs.language = language_id)
WHERE i.id = item_id

    $BODY$;

ALTER FUNCTION public.get_single_public_item(uuid, integer)
    OWNER TO takeaway;
