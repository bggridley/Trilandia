package com.loafy.game.pathfinding;

public class Node {

    private int x;
    private int y;

    private int platformIndex;
    private PlatformType type;

    public Node (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    private enum PlatformType {

        NONE, PLATFORM, LEFT_EDGE, RIGHT_EDGE, SOLO;

    }
}
