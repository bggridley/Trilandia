package com.loafy.game.world;

import com.google.gson.Gson;
import com.loafy.game.resources.Resources;

import java.io.*;

public class WorldLoader {

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }

    public static void delete(int worldSlot) {
        String path = WorldLoader.getWorldPath("world" + (worldSlot + 1) + "/");
        File file = new File(path);
        deleteDir(file);
        System.out.println(path);
    }

    public static void save(Object data, String worldFileName, String fileName) {
        try {
            String wldPath = WorldLoader.getWorldPath(worldFileName);


            new File(wldPath).mkdirs();
            System.out.println((new File(wldPath + "/" + fileName).createNewFile()));

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(wldPath + "/" + fileName));
            oos.writeObject(data);

            oos.flush();
            oos.close();
            /*
            Gson gson = new Gson();
            FileWriter writer = new FileWriter(wldPath + "/" + fileName);
            gson.toJson(data, writer);
            writer.flush();

            writer.close();*/

           // com.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object load(Class clazz, String worldFileName, String fileName) {
        try {
            /*Gson gson = new Gson();
            FileReader reader = new FileReader(WorldLoader.getWorldPath(worldFileName) + "/" + fileName);
            Object data = gson.fromJson(reader, clazz);
            reader.close();*/

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(WorldLoader.getWorldPath(worldFileName) + "/" + fileName));
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*public static void saveInfo(World world) {
        try {
            Gson gson = new Gson();
            WorldData data = world.getData();
            FileWriter writer = new FileWriter(WorldLoader.getWorldPath(data.name) + "\\world.dat");
            gson.toJson(data, writer);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WorldData loadInfo(String name) {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(WorldLoader.getWorldPath(name) + "\\world.dat\\");
            WorldData data = gson.fromJson(reader, WorldData.class);
            reader.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void savePlayerData(World world) {
        try {
            Gson gson = new Gson();
            WorldData data = world.getData();
            FileWriter writer = new FileWriter(WorldLoader.getWorldPath(data.name) + "\\world.dat");
            gson.toJson(data, writer);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static PlayerData loadPlayerData(String name) {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(WorldLoader.getWorldPath(name) + "\\player.dat\\");
            PlayerData data = gson.fromJson(reader, PlayerData.class);
            reader.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/

   /* public static Chunk fetchChunk(String fileName, int chunkX, int chunkY) {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(WorldLoader.getWorldPath(fileName) + "/chunkData/chunk" + chunkX + "," + chunkY);
            ChunkData data = gson.fromJson(reader, ChunkData.class);
            reader.close();

            Block[][] blocks = new Block[Chunk.SIZE][Chunk.SIZE];
            Block[][] walls = new Block[Chunk.SIZE][Chunk.SIZE];

            for (int x = 0; x < data.blocks.length; x++) {
                for (int y = 0; y < data.blocks[0].length; y++) {
                    blocks[x][y] = new Block(Material.fromID(data.blocks[x][y]), ((chunkX * Chunk.SIZE) + x) * Material.SIZE, ((chunkY * Chunk.SIZE) + y) * Material.SIZE);
                }
            }

            for (int x = 0; x < data.walls.length; x++) {
                for (int y = 0; y < data.walls[0].length; y++) {
                    walls[x][y] = new Block(Material.fromID(data.walls[x][y]), ((chunkX * Chunk.SIZE) + x) * Material.SIZE, ((chunkY * Chunk.SIZE) + y) * Material.SIZE);
                }
            }

            return new Chunk(blocks, walls, chunkX, chunkY);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveChunk(String fileName, Chunk chunk, Gson gson) {
        try {
            String path = WorldLoader.getWorldPath(fileName) + "/chunkData/chunk" + chunk.getChunkX() + "," + chunk.getChunkY();
            File file = new File(path);

            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            ChunkData data = chunk.getData();
            FileWriter writer = new FileWriter(path);
            gson.toJson(data, writer);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

    protected static String getWorldPath(String worldName) {
        return Resources.gameLocation + "/saves/" + worldName;
    }
}
