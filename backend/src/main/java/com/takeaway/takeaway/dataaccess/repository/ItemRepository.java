package com.takeaway.takeaway.dataaccess.repository;

import com.takeaway.takeaway.business.dto.GetItemDto;
import com.takeaway.takeaway.dataaccess.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {

    @Query("SELECT x FROM Item x WHERE x.user.id = ?1 AND x.id = ?2")
    Optional<Item> findByIdAndUserId(UUID userId, UUID itemId);

    @Query(value = "SELECT * FROM get_single_public_item(?1, ?2)", nativeQuery = true)
    Optional<GetPublicItemResponse> getPublicItem(UUID item, Integer languageOrdinal);

    interface GetPublicItemResponse {
        Date getPublish_start();
        String getTitle();
        String getDescription();
        String getFirst_name();
        String getLast_name();
        String getPublic_phone_number();
        String getPublic_email();
        String getItem_category_id();
        String getCountry_id();
        String getCountry_name();
        String getState_id();
        String getState_name();
        String getCity_id();
        String getCity_name();
        String getImage_Urls_Json();
    }

//    List<GetItemsDto> getPublicItems(UserLanguages language);
}


