package com.loafy.game.item;

import com.loafy.game.entity.player.EntityPlayer;
import com.loafy.game.gfx.SpriteSheet;
import com.loafy.game.gfx.Texture;
import com.loafy.game.resources.Resources;
import com.loafy.game.world.block.Material;

public class ItemBlock extends Item {

    private Material material;

    public ItemBlock(Material material) {
        this.id = material.getID() + 128;
        this.material = material;
      //  this.texture = Resources.itemsSprite.getTexture(15);
    }

    public Texture getTexture() {
        if (id != -1)
            return Material.fromID(id - 128).getTexture();

        return new Texture(0, 0, 0);
    }

    public String getName() {
        return material.getName();
    }

    public void useLeft(EntityPlayer player, float delta) {
        super.useLeft(player, delta);
        player.placeBlock(this, material);
    }

    public float getLight() {
        return material.getLight();
    }

    public void render(float x, float y) {
        getTexture().render(x, y, 1f, false);
    }

    public void render(float x, float y, float scale, boolean flip) {
        getTexture().render(x, y, scale, flip);
        //texture.render(x, y, scale, false);
    }

    public Material getMaterial() {
        return material;
    }

}
