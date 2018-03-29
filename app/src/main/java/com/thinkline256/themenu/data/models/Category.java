package com.thinkline256.themenu.data.models;

/**
 * Created by cato on 3/28/18.
 */

public class Category {

    private String id;
    private String name;
    private String description;

    public Category() {

    }

    public Category(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


}

