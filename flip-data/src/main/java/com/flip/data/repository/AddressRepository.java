package com.flip.data.repository;

import com.flip.data.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Charles on 21/06/2021
 */
public interface AddressRepository extends JpaRepository<Address, Long> {

    Address findAddressById(Long id);
}
