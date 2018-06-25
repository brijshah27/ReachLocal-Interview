package com.reachlocal.todo;

import javax.persistence.*;

@Table
@Entity
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String description;

    public Todo() {
    }

    public Todo(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
