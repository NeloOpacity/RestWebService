package com.example.boxwebservice.model;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;
import java.util.Set;

public class Box {
    private Integer id;
    private Integer containedIn;

    public Box() {
    }

    public Box(Integer id, Integer containedIn) {
        this.id = id;
        this.containedIn = containedIn;
    }

    public Integer getId() {
        return id;
    }

    public Integer getContainedIn() {
        return containedIn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Box)) return false;
        Box box = (Box) o;
        return id.equals(box.id) &&
                Objects.equals(containedIn, box.containedIn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
