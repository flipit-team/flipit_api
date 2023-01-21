package com.flip.api.controllers;

import com.flip.data.entity.Item;
import com.flip.service.services.impl.ItemService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Charles on 21/06/2021
 */
@Log4j2
@RestController
@RequestMapping("/api/v1/items")
public class ItemsController {

    @Autowired
    private ItemService itemService;

    @Value("${page.size}")
    private int pageSize;

    @GetMapping("/{page}/{size}")
    public List<Item> getItems(@PathVariable("page") int page, @PathVariable("size") int size) {
        size = size == 0 ? pageSize : size;
        return itemService.fetchItems(page, size);
    }

    @GetMapping("/search/{page}/{size}")
    public List<Item> searchItems(@PathVariable("page") int page, @PathVariable("size") int size,
                                  @RequestParam String search) {
        size = size == 0 ? pageSize : size;
        return itemService.searchItems(page, size, search);
    }
}
