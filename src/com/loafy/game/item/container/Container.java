package com.loafy.game.item.container;

import com.loafy.game.Main;
import com.loafy.game.entity.player.EntityPlayer;
import com.loafy.game.gfx.Font;
import com.loafy.game.gfx.Texture;
import com.loafy.game.input.InputManager;
import com.loafy.game.item.Item;
import com.loafy.game.item.ItemStack;
import com.loafy.game.resources.Resources;
import com.loafy.game.state.gui.Gui;
import org.newdawn.slick.Color;

public class Container {

    protected ContainerSlot[] slots;
    public static final int GRID_WIDTH = 10;
    public static final float GRID_SPACING = 3f;

    private Texture slotTexture;

    private float startX;
    private float startY;

    private int mouseSelected;

    public Container(int slots, float startX, float startY) {
        this.slots = new ContainerSlot[slots];
        this.startX = startX;
        this.startY = startY;
    }

    public Container(int slots, int gridSlots, float startX, float startY) {
        this(slots, startX, startY);
        slotTexture = Resources.inventoryTexture;

        if (gridSlots % GRID_WIDTH != 0) try {
            throw new Exception("Grid must be divisible by " + GRID_WIDTH);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int rows = (gridSlots / GRID_WIDTH);

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < GRID_WIDTH; x++) {
                this.slots[x + GRID_WIDTH * y] = new ContainerSlot(x * (ContainerSlot.SIZE + GRID_SPACING), y * (ContainerSlot.SIZE + GRID_SPACING));
            }
        }
    }

    public Container(int slots, int gridSlots) {
        this(slots, gridSlots, getDefaultX(), getDefaultY(gridSlots));
    }

    public void update(EntityPlayer player) {
        updateInput(player);
        updateSelected();
    }

    public void render() {
        for (ContainerSlot slot : slots) {
            if (!slot.isActive()) continue;
            float x = startX + slot.getX();
            float y = startY + slot.getY();

            if (!slot.isSelected())
                slotTexture.render(x, y);
            else
                slotTexture.render(x, y, 1f, false, Color.pink);

            if (slot.getItemStack() != null)
                renderItemStack(slot.getItemStack(), x, y);
        }
    }

    public int getMouseSlot() {
        return mouseSelected;
    }

    public void renderItemStack(ItemStack stack, float x, float y) {
        stack.renderInContainer(x, y);
        if (stack.getItem().getMaxStackSize() == 1 || stack.getItem().getID() != -1)
            Font.renderString(String.valueOf(stack.getAmount()), x + ContainerSlot.SIZE - 36f, y + 16f, 4, Color.white);
    }

    public void updateInput(EntityPlayer player) {

        ItemStack picked = player.getSelectedItem(); // current picked
        if (getMouseSlot() != -1) {
            ItemStack itemstack = slots[getMouseSlot()].getItemStack();
            if (!itemstack.useLeft()) {
                if (InputManager.mouse1p) {
                    if (itemstack.getItem().getID() == picked.getItem().getID()) {
                        int amount = itemstack.getAmount() + picked.getAmount();

                        if (itemstack.getAmount() < itemstack.getMaxStackSize() && picked.getAmount() < picked.getMaxStackSize()) {
                            if (amount > itemstack.getMaxStackSize()) {
                                picked.setAmount(picked.getMaxStackSize());
                                itemstack.setAmount(amount - picked.getMaxStackSize());
                            } else {
                                picked.setAmount(amount);
                                itemstack = new ItemStack(new Item(), 0); // TODO
                            }
                        }
                    }

                    slots[getMouseSlot()].setItemStack(picked);
                    picked = itemstack;
                }

                if (InputManager.mouse2p) {
                    if (picked.getItem().getID() == -1) {
                        int oldamount = itemstack.getAmount();
                        if (oldamount > 1) {
                            int amount = oldamount / 2;

                            itemstack.setAmount(amount);
                            picked.setItem(itemstack.getItem());
                            picked.setAmount(oldamount - amount);
                        }

                        slots[getMouseSlot()].setItemStack(picked);
                        picked = itemstack;
                    } else {
                        if (itemstack.getMaxStackSize() != 1 && itemstack.getAmount() != itemstack.getMaxStackSize()) {

                            itemstack.setItem(picked.getItem());
                            picked.setAmount(picked.getAmount() - 1);
                            itemstack.setAmount(itemstack.getAmount() + 1);

                            if (picked.getAmount() <= 0) {
                                picked.setItem(new Item()); // TODO
                                picked.setAmount(0);
                            }


                        } else {
                            slots[getMouseSlot()].setItemStack(picked);
                            picked = itemstack;
                        }
                    }
                }
            }

            player.setSelectedItem(picked);

        } else {
            if (InputManager.mouse1p) {
                // dropPicked();
            }
        }
    }

    public void updateSelected() {
        mouseSelected = -1;

        for (int i = 0; i < slots.length; i++) {
            ContainerSlot slot = slots[i];

            slot.setSelected(false);
            if (!slot.isActive()) continue;
            if (InputManager.mouseX > startX + slot.getX() && InputManager.mouseX < startX + slot.getX() + ContainerSlot.SIZE && InputManager.mouseY > startY + slot.getY() && InputManager.mouseY < startY + slot.getY() + ContainerSlot.SIZE) {
                slot.setSelected(true);

                mouseSelected = i;
            }
        }
    }

    public void openSlots() {
        for (ContainerSlot slot : slots) {
            slot.setActive(true);
        }
    }

    public void closeSlots() {
        for (ContainerSlot slot : slots) {
            slot.setActive(false);
        }
    }

    public static float getDefaultX() {
        return Gui.getCenteredX(((ContainerSlot.SIZE + GRID_SPACING) * GRID_WIDTH));
    }

    public static float getDefaultY(int gridSlots) {
        return 4;
    }

    public ContainerSlot[] getSlots() {
        return slots;
    }
}