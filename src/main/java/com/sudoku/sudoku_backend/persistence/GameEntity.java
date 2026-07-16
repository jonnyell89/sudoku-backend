package com.sudoku.sudoku_backend.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "game")
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 81)
    private String complete;

    @Column(nullable = false, length = 81)
    private String carved;

    @Column(nullable = false, length = 81)
    private String current;

    protected  GameEntity() {}

    public GameEntity(String complete, String carved, String current) {
        this.complete = complete;
        this.carved = carved;
        this.current = current;
    }

    public Long getId() {
        return id;
    }

    public String getComplete() {
        return complete;
    }

    public String getCarved() {
        return carved;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }
}
