package com.loafy.game.entity;

import com.loafy.game.gfx.Animation;
import com.loafy.game.resources.Resources;
import com.loafy.game.world.World;
import org.lwjgl.input.Keyboard;

public class EntityGoat extends EntityLiving {

    public EntityGoat(World world, float x, float y) {
        super(world, x, y);

        this.animation = Resources.goatAnimation;
        this.animation.setType(Animation.STILL);
        this.animation.setFrame(0);

        this.width = animation.getFrame().getWidth();
        this.height = animation.getFrame().getHeight();
        this.speed = 3F;
        this.maxHealth = 50F;
        this.health = maxHealth;

        this.PADDING_LEFT = 7;
        this.PADDING_RIGHT = 4;
        this.JUMP_START = -9F;

        world.addEntity(this);
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
