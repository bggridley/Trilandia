package com.loafy.game.world.block.blocks;

import com.loafy.game.entity.player.EntityPlayer;
import com.loafy.game.item.container.Container;
import com.loafy.game.item.container.ContainerSlot;
import com.loafy.game.item.container.Inventory;
import com.loafy.game.world.block.Block;
import com.loafy.game.world.block.Material;

public class BlockChest extends Block {

    private Container container;

    public BlockChest(float x, float y) {
        super(Material.CHEST, x, y);

        float inventoryHeight = (ContainerSlot.SIZE + Container.GRID_SPACING) * 5;
        container = new Container(60, 60, Container.getDefaultX(), inventoryHeight);
    }

    public void clickRight(EntityPlayer player) {
        player.openInventory();
        player.setActiveContainer(getContainer());
    }

    public Container getContainer() {
        return container;
    }
}
