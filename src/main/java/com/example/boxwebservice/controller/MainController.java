package com.example.boxwebservice.controller;

import com.example.boxwebservice.mapper.BoxMapper;
import com.example.boxwebservice.mapper.ItemMapper;
import com.example.boxwebservice.model.Box;
import com.example.boxwebservice.model.Item;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class MainController {
    private JdbcTemplate jdbcTemplate;

    public MainController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/test")
    public String test(
            @RequestBody Map<String, String> request
    ) {
        String id = request.get("id");
        String color = request.get("color");
        List<Box> boxesToCheck = new ArrayList<>();
        Box mainBox = jdbcTemplate.queryForObject("select * from BOX where id=" + id, new BoxMapper());
        boxesToCheck.add(mainBox);

        List<Item> items = new ArrayList<>();
        for(int i=0;i<boxesToCheck.size();i++) {
            Box b=boxesToCheck.get(i);
            List<Box> boxesToAdd = jdbcTemplate.query("select * from BOX where CONTAINED_IN=" + b.getId(),
                    new BoxMapper());
            List<Item> itemsToAdd = jdbcTemplate.query("select * from ITEM where CONTAINED_IN=" + b.getId() +
                            " AND " +
                            "COLOR " + (color == null ? "IS null" : "='" + color + "'"),
                    new ItemMapper());
            boxesToCheck.addAll(boxesToAdd);
            items.addAll(itemsToAdd);
        }

        return items.toString();
    }
}

