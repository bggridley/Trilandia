package com.loafy.game.entity;

import com.loafy.game.world.World;
import com.loafy.game.world.block.Material;

public class EntityLiving extends Entity {

    public float maxHealth;
    public float health;

    public float maxStamina;
    public float stamina;

    public boolean jumping;
    public float fallHeight;

    public boolean damaged = false;

    public EntityLiving() {

    }

    public EntityLiving(World world, float x, float y, float JUMP_HEIGHT) {
        super(world, x, y);

        this.JUMP_HEIGHT = JUMP_HEIGHT;
        this.JUMP_START = (float) Math.sqrt(2 * GRAVITY * JUMP_HEIGHT) * -1;

    }

    public void jump() {
        if (!falling && !jumping) jumping = true;
        adds = 0;
    }


    float adds = 0;

    public void calculateMovement(float delta) {
        if (left) {
            dx -= ACCELERATION;
            if (dx < -speed) dx = -speed;
        }
        if (right) {
            dx += ACCELERATION;
            if (dx > speed) dx = speed;
        }

        if (left && right) dx = 0;

        if (falling && !jumping) {
            float add = GRAVITY * delta;
            dy += add;

            if (dy > MAX_FALLING_SPEED) {
                dy = MAX_FALLING_SPEED;
            }
        }

        if (jumping && !falling) {
            dy = JUMP_START;
            jumping = false;
            falling = true;
        }

    }

    public void land() {
        fallHeight = startY - y;


        if (fallHeight <= Material.SIZE * -8) {
            float damage = ((Material.SIZE * -4) - fallHeight) / 3;
            damage(damage);
        }

        fallHeight = 0;
    }

    public void damage(float damage) {
        if (time > immunity) {
            this.health -= damage;
            damaged = true;
        }
    }

    public boolean spawnConditions(World world, int x, int y) {
        return true;
    }

    public boolean despawnConditions(World world, int x, int y) {
        return (Math.abs(world.getPlayer().getX() / Material.SIZE - x) > 80 || Math.abs(world.getPlayer().getY() / Material.SIZE - y) > 80);
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public float getHealth() {
        return health;
    }

    public float getMaxStamina() {
        return maxStamina;
    }

    public float getStamina() {
        return stamina;
    }


    public int getSpawnRate() {
        return 0;
    }

}
