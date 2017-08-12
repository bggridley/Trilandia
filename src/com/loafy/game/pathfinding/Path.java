package com.loafy.game.pathfinding;

import com.loafy.game.world.World;
import com.loafy.game.world.block.Material;
import com.loafy.game.world.block.Materials;

public class Path {

    private int width;
    private int height;
    private World world;
    private Node[][] nodes;
    
    private int topX;
    private int topY;

    public Path(World world, int startX, int startY, int targetX, int targetY) {
        this.world = world;
        this.width = Math.abs(targetX - startX);
        this.height = Math.abs(targetY - startY);
        this.nodes = new Node[width][height];

        this.topX = targetX;
        this.topY = targetY;

        if (startX < targetX) topX = startX;
        if (startY < targetY) topY = startY;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if(Materials.getID(world.getBlock(topX + x, topY + y)).isPassable()) {
                    if(world.getBlock(topX + x, topY + y + 1) != -1 && !Materials.getID(world.getBlock(topX + x, topY + y + 1)).isPassable()) {
                        // node x and y is relative to topX, topY
                        nodes[x][y] = new Node(x, y);
                    }
                }
            }
        }
    }


    public Node[][] getNodes() {
        return nodes;
    }

    public int getTopX() {
        return topX;
    }

    public int getTopY() {
        return topY;
    }
}
