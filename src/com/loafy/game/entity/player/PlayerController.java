package com.loafy.game.entity.player;

import com.loafy.game.input.Controls;
import com.loafy.game.input.InputManager;
import com.loafy.game.item.ItemStack;
import com.loafy.game.world.Chunk;
import com.loafy.game.world.World;
import com.loafy.game.world.block.Block;
import org.lwjgl.input.Keyboard;

public class PlayerController {

    private EntityPlayer player;
    private World world;

    /**
     * The current block that the player is destroying.
     */
    private Block dugBlock;

    public PlayerController(EntityPlayer player) {
        this.player = player;
        this.world = player.getWorld();
    }

    public void update(float delta) {
        int leftk = Controls.getControls().get("left");
        int rightk = Controls.getControls().get("right");
        player.left = Keyboard.isKeyDown(leftk);
        player.right = Keyboard.isKeyDown(rightk);

        if (Keyboard.isKeyDown(Controls.getControls().get("jump")))
            player.jump();

        if (InputManager.keyPressed(Controls.getControls().get("inventory"))) {
            player.toggleInventory();
        }

        handleItemActions(delta);
    }

    /**
     * Handle mouse actions on the selected item.
     */
    public void handleItemActions(float delta) {
        PlayerInventory inventory = player.getInventory();
        ItemStack itemstack = inventory.getSlots().get(inventory.getHotbarSlot()).getItemStack();
        if (InputManager.mouse1) {
            itemstack.getItem().useLeft(player, delta);
        } else if (InputManager.mouse2p) {
            itemstack.getItem().useRight(player, delta);
        }

        clearBlockDamage();
    }

    /**
     * Iterate through visible chunks and remove all block damage player inflicted.
     */
    private void clearBlockDamage() {  //TODO this may be inneficient because its updating like everything lol, also add to entityplayer class lol
        for (Chunk chunk : world.getActiveChunks()) {
            for (int x = 0; x < chunk.getBlocks().length; x++) {
                for (int y = 0; y < chunk.getBlocks()[x].length; y++) {
                    Block blocks = chunk.getBlocks()[x][y];
                    Block walls = chunk.getWalls()[x][y];
                    if (blocks != dugBlock) {
                        blocks.setHardness(blocks.getMaxHardness());

                    }

                    if (walls != dugBlock) {
                        walls.setHardness(walls.getMaxHardness());
                    }
                }
            }
        }

        dugBlock = null;
    }

    public Block getDugBlock() {
        return dugBlock;
    }

    public void setDugBlock(Block block) {
        this.dugBlock = block;
    }
}
