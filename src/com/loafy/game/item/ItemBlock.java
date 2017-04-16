package com.loafy.game.item;

import com.loafy.game.entity.player.EntityPlayer;
import com.loafy.game.gfx.SpriteSheet;
import com.loafy.game.gfx.Texture;
import com.loafy.game.resources.Resources;
import com.loafy.game.world.block.Material;

public class ItemBlock extends Item {

    private SpriteSheet blocks;
    private Material material;
    private Texture texture;

    public ItemBlock(Material material) {
        this.id = material.getID() + 128;
        this.blocks = Resources.blocksSprite;
        this.material = material;
        this.texture = Resources.itemsSprite.getTexture(15);
    }

    public Texture getTexture() {
        if (id != -1)
            return blocks.getTexture(id - 128);

        return new Texture(0, 0, 0);
    }

    public String getName() {
        return material.getName();
    }

    public void useLeft(EntityPlayer player) {
        player.placeBlock(this, material);
    }

    public void render(float x, float y) {
        getTexture().render(x, y);
    }

    public void render(float x, float y, float scale, boolean flip) {
        getTexture().render(x, y, scale, flip);
        texture.render(x, y, scale, false);
    }
}