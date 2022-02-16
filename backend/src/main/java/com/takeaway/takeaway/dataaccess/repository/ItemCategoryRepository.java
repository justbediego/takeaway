package com.takeaway.takeaway.dataaccess.repository;

import com.takeaway.takeaway.dataaccess.model.ItemCategory;
import com.takeaway.takeaway.dataaccess.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItemCategoryRepository extends JpaRepository<ItemCategory, UUID> {

    @Query("SELECT x FROM ItemCategory x WHERE x.parentCategory.id = ?1")
    List<ItemCategory> findByParentId(UUID parentId);

    @Query("SELECT x FROM ItemCategory x WHERE x.parentCategory IS NULL")
    List<ItemCategory> findRootCategories();
}