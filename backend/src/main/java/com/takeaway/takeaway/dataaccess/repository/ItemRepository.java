package com.takeaway.takeaway.dataaccess.repository;

import com.takeaway.takeaway.business.dto.GetSingleItemDto;
import com.takeaway.takeaway.dataaccess.model.Item;
import com.takeaway.takeaway.dataaccess.model.enums.UserLanguages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {

    @Query("SELECT x FROM Item x WHERE x.user.id = ?1 AND x.id = ?2")
    Optional<Item> findByIdAndUserId(UUID userId, UUID itemId);

    @Query("""
            SELECT new com.takeaway.takeaway.business.dto.GetSingleItemDto(
                i.title,
                i.description,
                u.firstName,
                u.lastName,
                CASE WHEN u.phoneNumberIsPublic = TRUE THEN u.phoneNumber ELSE '' END,
                CASE WHEN u.emailIsPublic = TRUE THEN u.email ELSE '' END,
                i.publishStart,
                i.category.id,
                loc.country.id,
                CASE WHEN country_trs.id is null THEN loc.country.englishName ELSE country_trs.value END,
                loc.state.id,
                CASE WHEN state_trs.id is null THEN loc.state.englishName ELSE state_trs.value END,
                loc.city.id,
                CASE WHEN city_trs.id is null THEN loc.city.englishName ELSE city_trs.value END,
                (SELECT json_agg(p.mediaLink) FROM i.pictures p)
            ) FROM Item i
            JOIN i.user u
            LEFT JOIN i.location loc
            LEFT JOIN loc.country country
            LEFT JOIN loc.state state
            LEFT JOIN loc.city city
            LEFT JOIN country.translations country_trs ON country_trs.language = ?2
            LEFT JOIN state.translations state_trs ON state_trs.language = ?2
            LEFT JOIN city.translations city_trs ON city_trs.language = ?2            
            WHERE i.id = ?1""")
    Optional<GetSingleItemDto> getPublicItem(UUID item, UserLanguages language);






}


