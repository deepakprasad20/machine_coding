package com.snakeladderGame.com.snakeladdergame.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Player {
    String name;
    int pos;

    public boolean won(int winPosition) {
        return this.pos == winPosition;
    }

    public Player(){}
}
