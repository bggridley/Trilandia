package com.loafy.game.entity;

import com.loafy.game.Main;
import com.loafy.game.entity.Entity;
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

    public EntityLiving(World world, float x, float y) {
        super(world, x, y);
    }

    public void jump() {
        if (!falling && !jumping) jumping = true;
    }

    public void calculateMovement(float delta) {
        if (left) dx = -speed;
        if (right) dx = speed;

        if (left && right) dx = 0;

        if (falling && !jumping) {
            float add = GRAVITY * delta;
            dy += add;
            if (dy > MAX_FALLING_SPEED) {  dy = MAX_FALLING_SPEED; }

        }

        if (jumping && !falling) {
            dy = JUMP_START;
            jumping = false;
            falling = true;
        }
    }

    public void land() {
        fallHeight = startY - y;


        if(fallHeight <= Material.SIZE * -8) {
            float damage =((Material.SIZE * -4) - fallHeight) / 3;
            damage(damage);
        }

        fallHeight = 0;
    }

    public void damage(float damage) {
        if(time > immunity) {
            this.health -= damage;
            damaged = true;
        }
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

}
