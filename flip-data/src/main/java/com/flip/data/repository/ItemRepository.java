package com.flip.data.repository;

import com.flip.data.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Charles on 21/01/2023
 */
public interface ItemRepository extends JpaRepository<Item, Long> {
}
