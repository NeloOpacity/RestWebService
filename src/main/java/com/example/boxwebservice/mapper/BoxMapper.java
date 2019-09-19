package com.example.boxwebservice.mapper;

import com.example.boxwebservice.model.Box;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BoxMapper implements RowMapper<Box> {
    @Override
    public Box mapRow(ResultSet resultSet, int i) throws SQLException {
        Integer id = resultSet.getInt("id");
        Integer containedIn = resultSet.getInt("contained_in");
        if (containedIn == 0)
            containedIn = null;
        return new Box(id, containedIn);
    }
}
