package com.flip.service.services.impl;

import com.flip.data.entity.Item;

import java.util.List;

/**
 * @author Charles on 10/01/2023
 */
public interface ItemService {

    List<Item> fetchItems(int page, int pageSize);

    List<Item> searchItems(int page, int pageSize, String search);
}
