package com.quickserve.Restaurant_Service.Controller;

import com.quickserve.Restaurant_Service.Entity.MenuItem;
import com.quickserve.Restaurant_Service.Repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private MenuRepository menuRepository;

    // 1. Saare available menu items fetch karne ki API (Customer ke liye)
    @GetMapping
    public List<MenuItem> getAllAvailableMenu() {
        return menuRepository.findByIsAvailableTrue();
    }

    // 2. Naya Menu Item add karne ki API (Admin/Restaurant Owner ke liye)
    @PostMapping
    public MenuItem addMenuItem(@RequestBody MenuItem menuItem) {
        return menuRepository.save(menuItem);
    }
}