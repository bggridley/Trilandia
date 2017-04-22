package com.loafy.game.item;

import com.loafy.game.Main;
import com.loafy.game.entity.player.EntityPlayer;
import com.loafy.game.input.InputManager;
import com.loafy.game.item.Item;
import com.loafy.game.world.World;
import com.loafy.game.world.block.Block;
import com.loafy.game.world.block.Material;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import java.util.HashMap;

public abstract class Tool extends Item {

    private HashMap<Integer, Float> materials;

    public Tool() {
        materials = new HashMap<>();
        initMaterials();

        this.maxStackSize = 1;
    }

    public void useRight(EntityPlayer player, float delta) {
        super.useRight(player, delta);
    }

    public void useLeft(EntityPlayer player, float delta) {
        super.useLeft(player, delta);
        World world = player.getWorld();

        float x = player.getX();
        float y = player.getY();

        int mx = (int) (InputManager.mouseX + world.xOffset);
        int my = (int) (InputManager.mouseY + world.yOffset);

        Block block = world.getBlock(mx, my);

        if (block.getMaterial() == Material.AIR)
            block = world.getWall(mx, my);

        float digSpeed = getSpeed(block.getMaterial()) * delta; //* (float) Main.UPS / (float) Main.FPS;


        if (Math.abs(x - mx) / Material.SIZE <= 4) {
            if (Math.abs(y - my) / Material.SIZE <= 4) {
                player.getController().setDugBlock(block);

                if (block.getMaterial().isSolid()) {
                    block.setHardness(block.getHardness() - digSpeed);
                    if (block.getHardness() <= 0) {
                        block.destroy(world);
                    }
                }
            }
        }
    }

    public abstract void initMaterials();

    public void addMaterial(int id, float speed) {
        materials.put(id, speed);
    }

    public float getSpeed(Material material) {
        for (int id : materials.keySet()) {
            if (material.getID() == id) {
                return materials.get(id);
            }
        }

        return 0f;
    }
}
