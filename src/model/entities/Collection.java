package model.entities;

import java.io.Serializable;

public class Collection implements Serializable {
    private String name;

    public Collection() {
    }

    public Collection(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
