package com.flip.data.repository;

import com.flip.data.entity.UserIdentification;
import com.flip.data.enums.IdentificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Charles on 14/12/2022
 */
public interface UserIdentificationRepository extends JpaRepository<UserIdentification, Long> {

    List<UserIdentification> findUserIdentificationsByAppUser_Id(Long id);

    UserIdentification findUserIdentificationsByAppUser_IdAndIdType(Long id, IdentificationType idType);

}
