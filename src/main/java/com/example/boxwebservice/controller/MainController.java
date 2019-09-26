package com.example.boxwebservice.controller;

import com.example.boxwebservice.mapper.ItemMapper;
import com.example.boxwebservice.model.Item;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        ItemMapper itemMapper = new ItemMapper();

        List<Item> items = jdbcTemplate.query(
                "with recursive ta1 as" +
                        "(select ID from BOX where id=? " +
                        "union all " +
                        "select box.id from box inner join ta1 on box.CONTAINED_IN=ta1.id) " +
                        "select * from item as i where i.CONTAINED_IN in (select ta1.ID from ta1) AND COLOR=?",
                new Object[]{id, color}, itemMapper);

        return items.toString();
    }
}

