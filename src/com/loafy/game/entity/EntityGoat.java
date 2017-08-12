package com.loafy.game.entity;

import com.loafy.game.gfx.Animation;
import com.loafy.game.resources.Resources;
import com.loafy.game.world.World;
import com.loafy.game.world.block.Material;
import org.lwjgl.input.Keyboard;

public class EntityGoat extends EntityLiving {

    public EntityGoat() {

    }


    public EntityGoat(World world, float x, float y) {
        super(world, x, y, 3 * Material.SIZE + 4);

        this.animation = Resources.goatAnimation;
        this.animation.setType(Animation.STILL);
        this.animation.setFrame(0);

        this.width = animation.getFrame().getWidth();
        this.height = animation.getFrame().getHeight();
        this.speed = 3F;
        this.maxHealth = 50F;
        this.health = maxHealth;

        this.PADDING_LEFT = 32;
        this.PADDING_RIGHT = 0;

        world.addEntity(this);
    }

    public boolean spawnConditions(World world, int x, int y) {
        if(!super.spawnConditions(world, x, y)) return false;

        if(world.getBlock(world.getBlocks(), x, y + 3) != Material.GRASS.getID()) return false;

        return true;
    }

    public boolean despawnConditions(World world, int x, int y) {
        return super.despawnConditions(world, x, y);
    }

    public EntityGoat newInstance(World world, float x, float y) {
        return new EntityGoat(world, x, y);
    }

    public int getSpawnRate() {
        return 150;
    }

    public void render(float xOffset, float yOffset, float lightLevel) {
        super.render(xOffset, yOffset, lightLevel);
    }

    public void update(float delta) {

        // a i b o i s


        left = Keyboard.isKeyDown(Keyboard.KEY_LEFT);
        right = Keyboard.isKeyDown(Keyboard.KEY_RIGHT);

        if (Keyboard.isKeyDown(Keyboard.KEY_UP))
            jump();

        super.update(delta);
    }
}
