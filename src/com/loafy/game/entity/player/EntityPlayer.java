package com.loafy.game.entity.player;

import com.loafy.game.entity.Entity;
import com.loafy.game.entity.EntityItem;
import com.loafy.game.entity.EntityLiving;
import com.loafy.game.gfx.Animation;
import com.loafy.game.input.InputManager;
import com.loafy.game.item.Item;
import com.loafy.game.item.ItemBlock;
import com.loafy.game.item.ItemStack;
import com.loafy.game.item.Tool;
import com.loafy.game.item.container.Container;
import com.loafy.game.item.container.ContainerSlot;
import com.loafy.game.resources.Resources;
import com.loafy.game.state.IngameState;
import com.loafy.game.world.World;
import com.loafy.game.world.block.Block;
import com.loafy.game.world.block.Material;
import com.loafy.game.world.block.MaterialType;
import com.loafy.game.world.data.PlayerData;
import com.loafy.game.world.lighting.Light;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.geom.Rectangle;

import java.awt.*;


public class EntityPlayer extends EntityLiving {

    /**
     * Player inventory.
     */
    private PlayerInventory inventory;
    private Container activeContainer;
    private ItemStack selectedItem;

    private PlayerController controller;

    /**
     * The last direction that the player faced.
     */

    private boolean lastDirection;

    public int spawnX;
    public int spawnY;

    public static float spawnRate = 69f;

    public EntityPlayer(World world, float x, float y) {
        super(world, x, y, 4 * Material.SIZE + 4);

        this.spawnX = (int) x / Material.SIZE;
        this.spawnY = (int) y / Material.SIZE;

        this.inventory = new PlayerInventory(this, 40);
        this.animation = Resources.playerAnimation;

        this.width = animation.getFrame().getWidth();
        this.height = animation.getFrame().getHeight();
        this.speed = 5F;

        this.maxHealth = 100F;
        this.health = maxHealth;

        this.maxStamina = 100F;
        this.stamina = maxStamina;

        this.PADDING_LEFT = 12;
        this.PADDING_RIGHT = 12;

        //JUMP HEIGHT IN PIXELS = JumpSpeed2/(2*Gravity)

        selectedItem = new ItemStack(new Item(), 0);
        controller = new PlayerController(this);

        world.addEntity(this);
        closeInventory(); // hotbar slots
    }

    public void damage(float delta) {
        super.damage(delta);

        if (damaged) {
            IngameState.flashHearts();
        }
    }

    public void move(float delta) {
        super.move(delta);

        if (dx > 0)
            world.bxOffset += 0.75 * delta; //(0.75 * delta) / 1000 * Main.UPS;
        else if (dx < 0)
            world.bxOffset += -0.75 * delta; //(-0.75 * delta) / 1000 * Main.UPS;
    }

    public void update(float delta) {
        controller.update(delta);

        inventory.update(this);
        if (activeContainer != null) {
            activeContainer.update(this);
        }


        /**
         * Find nearby item drops and add the to the player inventory.
         */
        for (Entity entity : world.getEntities()) {
            if (entity instanceof EntityItem) {
                EntityItem entityItem = (EntityItem) entity;

                float entityX = entityItem.getX();
                float entityY = entityItem.getY();

                if (Math.abs(x + 32 - entityX) <= Material.SIZE * 1.5) {
                    if (Math.abs(y + 32 - entityY) <= Material.SIZE * 1.5) {
                        if (entityItem.getTime() >= entityItem.getPickupTime()) {
                            if (inventory.addItem(entityItem.getItem(), 1)) //TODO
                                world.removeEntityLoop(entityItem);
                        }
                    }
                }
            }
        }

        super.update(delta);
        if (right && dx > 0)
            lastDirection = false;

        if(left && dx < 0)
            lastDirection = true;

            handleAnimations(delta);

    }

    public boolean isAnySelected() {
        return !(inventory.getMouseSlot() == -1 && inventory.getCraftingList().getMouseSlot() == -1) || (activeContainer != null && activeContainer.getMouseSlot() != -1);
    }

    public void renderContainer() {
        if (activeContainer != null && inventory.isOpen()) {
            activeContainer.render(this);
        }

        inventory.render(this);
        if (selectedItem != null) {
            inventory.renderItemStack(selectedItem, InputManager.mouseX - ContainerSlot.SIZE / 2 - 16f, InputManager.mouseY - ContainerSlot.SIZE / 2);
        }
    }

    public void render(float xOffset, float yOffset, float lightLevel) {

        ItemStack itemstack = inventory.getSlots().get(inventory.getHotbarSlot()).getItemStack();

        if (itemstack == null) return;
        Item item = itemstack.getItem();

        if (item instanceof ItemBlock) {

            if (!inventory.isOpen()) {
                int mx = (int) (Mouse.getX() + world.xOffset);
                int my = (int) (Display.getHeight() - Mouse.getY() + world.yOffset);

                int blockX = world.getBlockX(mx);
                int blockY = world.getBlockY(my);

                if (canPlace(((ItemBlock) item).getMaterial(), mx, my))
                    Resources.breakingSprite.getTexture(0).render(blockX * Material.SIZE - xOffset, blockY * Material.SIZE - yOffset);
                else
                    Resources.breakingSprite.getTexture(1).render(blockX * Material.SIZE - xOffset, blockY * Material.SIZE - yOffset);
            }
        } else if (item instanceof Tool) {
            Color light = new Color(lightLevel, lightLevel, lightLevel);
            if (!lastDirection) // right
                item.getTexture().render(x - xOffset + 44, y - yOffset + 14, 1f, false, light);
            else // left
                item.getTexture().render(x - xOffset - 12, y - yOffset + 14, 1f, true, light);
        }

        super.render(xOffset, yOffset, lightLevel);
    }

    public void placeBlock(Item item, Material material) {
        int mx = (int) (Mouse.getX() + world.xOffset);
        int my = (int) (Display.getHeight() - Mouse.getY() + world.yOffset); //todo make all these block things the same too for mouse

        if (!canPlace(material, mx, my))
            return;

        Block block = world.createBlock(material.getID(), world.getBlockX(mx) * Material.SIZE, world.getBlockY(my) * Material.SIZE);

        float x = block.getX();
        float y = block.getY();

        inventory.subtractItem(item, inventory.getHotbarSlot(), 1); //TODO
        //block.setMaterial(material);
        // block.setHardness(material.getHardness());

        // just set to a new block

        float light = material.getLight();
        if (light != -1) {
            world.getLightMap().addLight(new Light(world.getLightMap(), light, (int) x / Material.SIZE, (int) y / Material.SIZE, null));
        }

        switch (material.getType()) {
            case BLOCK:
                world.setBlockFromChunks(block, (int) x, (int) y);
                break;
            case WALL:
                world.setWallFromChunks(block, (int) x, (int) y);
                break;
        }
    }

    public boolean canPlace(Material m, int mx, int my) {
        int blockX = world.getBlockX(mx) * Material.SIZE; //todo this isn't blockX this is the block position relative to the world
        int blockY = world.getBlockY(my) * Material.SIZE;

        Block block = world.getBlockFromChunks(blockX, blockY);
        Block wall = world.getWallFromChunks(blockX, blockY);

        boolean conditions = m.getPlaceConditions(world, blockX, blockY);

        if (m.getType() == MaterialType.BLOCK) {
            if (block.getMaterial() != Material.AIR) return false;
        } else if (m.getType() == MaterialType.WALL) {
            if (wall.getMaterial() != Material.AIR) return false;
        }

        if (!conditions) {
            return false;
        }

        if (!m.isPassable()) {
            for (Entity entity : getWorld().getEntities()) {
                if (entity instanceof EntityLiving) {
                    if (new Rectangle(block.getX(), block.getY(), Material.SIZE, Material.SIZE).intersects(entity.getBox())) {
                        return false;
                    }
                }
            }
        }

        if (Math.abs(x + Material.SIZE - blockX) / Material.SIZE <= 4) {
            if (Math.abs(y + Material.SIZE - blockY) / Material.SIZE <= 4) {
                return true;
            }
        }

        return false;
    }

    public void toggleInventory() {
        if (inventory.isOpen()) {
            closeInventory();
            dropPicked();

        } else openInventory();
    }

    public void openInventory() {
        inventory.setOpen(true);
        inventory.openSlots();
    }

    public void closeInventory() {
        inventory.setOpen(false);
        activeContainer = null;

        inventory.closeSlots();

        for (int i = 0; i < Container.GRID_WIDTH; i++) { //make hotbar active
            inventory.getSlots().get(i).setActive(true);
        }
    }

    public void dropPicked() {
        int dx;

        if (lastDirection)
            dx = -1;
        else
            dx = 1;

        if (selectedItem != null) {
            selectedItem.drop(getWorld(), getX() + 32 - 16, getY() + 16, 5f * dx, -1f, false);
            selectedItem = new ItemStack(new Item(), 0);
        }
    }

    public void handleAnimations(float delta) {
        this.animation.setType(Animation.LOOP);
        this.animation.setInterval(5f);
        this.animation.setStart(1);
        this.animation.setEnd(4);

        animation.setFlipped(lastDirection);
        if ((left && right) || (!left && !right)) {
            animation.setType(Animation.STILL);
            animation.setFrame(0);
        }

        animation.update(delta);
    }

    public Container getActiveContainer() {
        return activeContainer;
    }

    public void setActiveContainer(Container activeContainer) {
        this.activeContainer = activeContainer;
        for (ContainerSlot slot : activeContainer.getSlots()) {
            slot.setActive(true);
        }
    }

    public PlayerInventory getInventory() {
        return inventory;
    }

    public ItemStack getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(ItemStack selectedItem) {
        this.selectedItem = selectedItem;
    }

    public PlayerController getController() {
        return controller;
    }

    public PlayerData getData() {
        return new PlayerData(this);
    }

    public abstract class PlaceCondition {

        private boolean condition;

        public abstract void update(EntityPlayer player);

        public boolean getCondition() {
            return condition;
        }

    }
}
