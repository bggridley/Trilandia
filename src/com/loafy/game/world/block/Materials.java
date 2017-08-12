package com.loafy.game.world.block;

import java.util.ArrayList;

public class Materials {

    private static ArrayList<Material> materials;

    public static int AIR = 0;
    public static int STONE = 1;
    public static int DIRT = 2;
    public static int GRASS = 3;
    public static int LOG = 4;
    public static int LEAF = 5;
    public static int CHEST = 7;
    public static int TORCH = 8;
    public static int COPPER_ORE = 14;
    public static int SILVER_ORE = 15;
    public static int STONE_WALL = 17;
    public static int DIRT_WALL = 18;
    public static int WOOD_WALL = 21;

    public static int CERISE_STONE = 22;

    public static void init() {
        materials = new ArrayList<>();
        materials.add(Material.DIRT_WALL);
        materials.add(Material.STONE_WALL);
        materials.add(Material.LOG);
        materials.add(Material.LEAF);
        materials.add(Material.AIR);
        materials.add(Material.STONE);
        materials.add(Material.DIRT);
        materials.add(Material.GRASS);
        materials.add(Material.WOOD);
        materials.add(Material.WOOD_WALL);
        materials.add(Material.COPPER_ORE);
        materials.add(Material.SILVER_ORE);
        materials.add(Material.CHEST);
        materials.add(Material.TORCH);
        materials.add(Material.CERISE_STONE);
    }

    public static Material getID(int id) {
        for (Material material : materials) {
            if (id == material.getID()) return material;
        }

        return Material.AIR;
    }

}
