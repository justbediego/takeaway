package com.takeaway.takeaway.dataaccess.repository;

import com.takeaway.takeaway.business.dto.ItemCategoryDto;
import com.takeaway.takeaway.dataaccess.model.ItemCategory;
import com.takeaway.takeaway.dataaccess.model.enums.UserLanguages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemCategoryRepository extends JpaRepository<ItemCategory, UUID> {

    @Query("""
            SELECT new com.takeaway.takeaway.business.dto.ItemCategoryDto(
                ic.id,
                CASE WHEN trs.id is null THEN ic.englishName else trs.value END
            ) FROM ItemCategory ic
            LEFT JOIN ic.translations trs ON trs.language = ?1""")
    List<ItemCategoryDto> getItemCategories(UserLanguages language);
}