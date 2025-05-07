package com.snakeladderGame.com.snakeladdergame.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;


@Getter
@Setter
public class Dice {
    int size;
    Random random;

    public Dice(int size){
        this.size = size;
        random = new Random();
    }

    public int getMoves(){
        int steps = 0;
        while (steps <= 0){
            steps = random.nextInt()%size;
        }
        System.out.println("Dice rolled: " + steps);
        return steps;
    }
}
