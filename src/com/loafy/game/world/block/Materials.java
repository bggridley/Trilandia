package com.loafy.game.world.block;

import java.util.ArrayList;

public class Materials {

    private static ArrayList<Material> materials;

    public static void init() {
        materials = new ArrayList<>();
        materials.add(Material.DIRT_WALL);
        materials.add(Material.STONE_WALL);
        materials.add(Material.WOOD);
        materials.add(Material.LEAF);
        materials.add(Material.AIR);
        materials.add(Material.STONE);
        materials.add(Material.DIRT);
        materials.add(Material.GRASS);
    }

    public static Material getID(int id) {
        for(Material material : materials) {
            if (id == material.getID()) return material;
        }

        return Material.AIR;
    }

}
