package com.loafy.game.entity;

import com.loafy.game.entity.player.EntityPlayer;
import com.loafy.game.gfx.Animation;
import com.loafy.game.world.World;
import com.loafy.game.world.block.Block;
import com.loafy.game.world.block.Material;
import org.newdawn.slick.geom.Rectangle;

public class Entity {

    public World world;
    public Animation animation;

    // CONSTANTS

    protected final float GRAVITY = 0.65F;

    protected float VELOCITY_DECREASE = 0.35F;
    protected float MAX_FALLING_SPEED = 12.5F;
    protected float JUMP_START;

    protected int PADDING_LEFT;
    protected int PADDING_RIGHT;

    // LOCATION

    protected float x, y;
    protected float dx, dy;

    // ATTRIBUTES

    protected float width;
    protected float height;
    protected float speed;

    // MOVEMENT

    public boolean left;
    public boolean right;
    protected boolean falling;

    // COLLISIONS

    private Rectangle box; //todo just use coordinates to detect collision
    private boolean topLeft, topRight, midLeft, midRight, bottomLeft, bottomRight;

    protected float blockFriction;
    protected float airFriction = 1.0f;

    protected float startY;

    protected int immunity = 240;
    protected float time;

    public Entity(World world, float x, float y) {
        this.world = world;
        this.x = x;
        this.y = y;

        this.box = new Rectangle(x + 4, y + 4, width - 8, height - 8);
    }

    public void render(float xOffset, float yOffset, float lightLevel) {
        animation.render(x - xOffset, y - yOffset, lightLevel);
    }

    public void update(float delta) {
        calculateMovement(delta);
        calculateCollisions(delta);
        move(delta);
        this.box = new Rectangle(x + 4, y + 4, width - 8, height - 8);

        time += delta;
    }

    public void calculateMovement(float delta) {
        if (falling) {
            dy += GRAVITY * delta;
            if (dy > MAX_FALLING_SPEED) dy = MAX_FALLING_SPEED;
        }
    }

    public void calculateCollisions(float delta) {
        float tox = x + (dx) * delta;
        float toy = y + (dy) * delta;

        calculateCorners(tox, y - 1);
        if (dx < 0) {
            if (topLeft || midLeft || bottomLeft) {
                dx = 0;
            }
        }

        if (dx > 0) {
            if (topRight || midRight || bottomRight) {
                dx = 0;
            }
        }

        calculateCorners(x, toy);

        if (topLeft || topRight) {
            dy = 0;
            falling = true;
            int pr = world.getBlockY((int) toy);
            float ya = (pr + 1) * Material.SIZE;

            if (this instanceof EntityPlayer)
                world.yOffset -= y - ya;

            y = ya;

        }

        if (bottomLeft || bottomRight && falling) {
            falling = false;
            dy = 0;

            int add = 0;
            if (this instanceof EntityItem)
                add = 16;

            int pr = world.getBlockY((int) toy);
            float ya = (pr * Material.SIZE) + add;

            if (this instanceof EntityPlayer)
                world.yOffset -= y - ya;

            y = ya;

            land();
        }

        if (!falling) {
            startY = y;
        }

        if (!bottomLeft && !bottomRight) {
            falling = true;
        }
    }

    private void calculateCorners(float x, float y) {
        int leftTile = (int) x + PADDING_LEFT - 4;
        int rightTile = (int) x + (int) width - PADDING_RIGHT;
        int topTile = (int) y + 2;
        int midTile = (int) (y + height / 2);
        int bottomTile = (int) y + (int) width;

        try {
            topLeft = !world.getBlockFromChunks(leftTile, topTile).getMaterial().isPassable();
            topRight = !world.getBlockFromChunks(rightTile, topTile).getMaterial().isPassable();
            midLeft = !world.getBlockFromChunks(leftTile, midTile).getMaterial().isPassable();
            midRight = !world.getBlockFromChunks(rightTile, midTile).getMaterial().isPassable();
            bottomLeft = !world.getBlockFromChunks(leftTile, bottomTile).getMaterial().isPassable();
            bottomRight = !world.getBlockFromChunks(rightTile, bottomTile).getMaterial().isPassable();

            Block bottomLeftBlock = world.getBlockFromChunks(leftTile, bottomTile);
            Block bottomRightBlock = world.getBlockFromChunks(rightTile, bottomTile);

            if (bottomLeftBlock.getMaterial().getID() == Material.AIR.getID() || bottomRightBlock.getMaterial().getID() == Material.AIR.getID()) {
                blockFriction = airFriction;
            } else {
                blockFriction = (bottomLeftBlock.getFriction() + bottomRightBlock.getFriction()) / 2;
            }

        } catch (Exception e) {

        }
    }

    public void move(float delta) {
        x += (dx) * delta; //* delta) / 1000 * Main.UPS;
        y += (dy) * delta; //* delta) / 1000 * Main.UPS;

        if (this instanceof EntityPlayer) {
            world.xOffset += (dx) * delta; //* delta) / 1000 * Main.UPS;
            world.yOffset += (dy) * delta; //* delta) / 1000 * Main.UPS;
        }


        if (dx > 0) {
            dx -= VELOCITY_DECREASE * blockFriction;
            if (dx < 0) dx = 0;
        }

        if (dx < 0) {
            dx += VELOCITY_DECREASE * blockFriction;
            if (dx > 0) dx = 0;
        }
    }

    public void land() {

    }

    public void setVelocity(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public World getWorld() {
        return world;
    }

    public Rectangle getBox() {
        return box;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}
