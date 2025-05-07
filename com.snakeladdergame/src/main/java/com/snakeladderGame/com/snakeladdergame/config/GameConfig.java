package com.snakeladderGame.com.snakeladdergame.config;

import com.snakeladderGame.com.snakeladdergame.model.Ladder;
import com.snakeladderGame.com.snakeladdergame.model.Player;
import com.snakeladderGame.com.snakeladdergame.model.Snake;

import java.util.List;

public class GameConfig {
    public int boardSize;
    public List<Snake> snakes;
    public List<Ladder> ladders;
    public List<Player> players;

    public GameConfig() {}
}
