package com.games.ebocc.thehero.util;

import com.games.ebocc.thehero.enemyballoons.Enemy;

import java.util.ArrayList;
import java.util.List;

public class HeroPos{

    private List<Enemy> observers = new ArrayList<>();

    public void addObserver(Enemy enemy){
        if(!observers.contains(enemy))
            observers.add(enemy);
    }

    public void notifyEnemies(int x, int y){
        for(Enemy enemy : observers){
            enemy.updateHeroPosition(x, y);
        }
    }
}
