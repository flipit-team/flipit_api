package com.flip.service.services.impl;

import com.flip.data.entity.Item;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Charles on 10/01/2023
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Override
    public List<Item> fetchItems(int page, int pageSize) {
        return null;
    }

    @Override
    public List<Item> searchItems(int page, int pageSize, String search) {
        return null;
    }

}
