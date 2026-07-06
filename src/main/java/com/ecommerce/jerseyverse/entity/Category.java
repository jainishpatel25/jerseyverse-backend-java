package com.ecommerce.jerseyverse.entity;

import com.ecommerce.jerseyverse.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false, unique = true, length = 100)
    private String name;

}
