package com.example.boxwebservice.mapper;

import com.example.boxwebservice.model.Item;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemMapper implements RowMapper<Item> {
    @Override
    public Item mapRow(ResultSet resultSet, int i) throws SQLException {
        Integer id=resultSet.getInt("id");
        Integer containedIn=resultSet.getInt("contained_in");
        String color=resultSet.getNString("color");
        return new Item(id,color,containedIn);
    }
}
