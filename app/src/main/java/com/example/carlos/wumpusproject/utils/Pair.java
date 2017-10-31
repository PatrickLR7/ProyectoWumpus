package com.example.carlos.wumpusproject.utils;

/**
 * Created by carlos on 30/10/17.
 */

public class Pair {
    private int x;
    private int y;

    public Pair(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Pair restarPares(Pair pair){
        int x = this.x - pair.x;
        int y = this.y - pair.y;
        return new Pair(x, y);
    }
}
