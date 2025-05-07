package com.snakeladderGame.com.snakeladdergame.service;

import com.snakeladderGame.com.snakeladdergame.model.Dice;
import com.snakeladderGame.com.snakeladdergame.model.Ladder;
import com.snakeladderGame.com.snakeladdergame.model.Player;
import com.snakeladderGame.com.snakeladdergame.model.Snake;
import lombok.AllArgsConstructor;

import java.util.*;


public class SnakeAndLadder {
    int winPosition;
    int boardSize;
    Map<Integer, Integer> snakeMap;
    Map<Integer, Integer> ladderMap;
    Queue<Player> players;
    Dice dice;

    public SnakeAndLadder(int boardSize, List<Snake> snakes, List<Ladder> ladders, Dice dice){
        this.boardSize = boardSize;
        this.dice = dice;
        winPosition = boardSize;
        snakeMap = new HashMap<>();
        ladderMap = new HashMap<>();
        players = new LinkedList<>();

        for(int i = 0 ; i < snakes.size(); i++){
            snakeMap.put(snakes.get(i).getHead(), snakes.get(i).getTail());
        }
        for(int i = 0 ; i < ladders.size(); i++){
            ladderMap.put(ladders.get(i).getBottom(), ladders.get(i).getTop());
        }
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void start() {
        while(true){
            Player player = players.poll();
            System.out.println(player.getName() + " current position = " + player.getPos());
            if(player.won(winPosition)){
                // player won and return
                System.out.println(player.getName() + " won the game");
                break;
            } else{
                move(dice, player);
                if(player.won(winPosition)){
                    System.out.println(player.getName() + " won the game");
                    break;
                }
                players.add(player);
            }
        }
        return;
    }

    private void move(Dice dice, Player player) {
        int steps = dice.getMoves();
        int currPos = player.getPos();
        int nextStep = currPos + steps;
        if(nextStep > winPosition){
            System.out.println("We cannot move");
        } else{
            if(snakeMap.get(nextStep)==null && ladderMap.get(nextStep)==null){
                player.setPos(nextStep);
            }
            else if(snakeMap.get(nextStep)!=null){
                System.out.println("Snake bite to " + player.getName() + " and moved to new position " + snakeMap.get(nextStep));
                player.setPos(snakeMap.get(nextStep));
            }
            else if(ladderMap.get(nextStep)!=null){
                System.out.println( player.getName() + " climbed to ladder and moved to new position " + ladderMap.get(nextStep));
                player.setPos(ladderMap.get(nextStep));
            }
            System.out.println(player.getName() + " move from " + currPos +" to " + player.getPos());
        }
    }
}
