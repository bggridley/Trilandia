package com.loafy.game.input;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class InputManager {

    private static boolean keys[];
    private static int pressedKey = -1;
    private static char pressedChar = Character.MIN_VALUE;

    public static boolean mouse1, mouse2, mouse3;
    public static boolean mouse1p, mouse2p, mouse3p;
    public static int mouseWd;

    public static float mouseX, mouseY;

    public static void init() {
        keys = new boolean[Keyboard.KEYBOARD_SIZE];
    }

    public static boolean keyDown(int key) {
        return Keyboard.isKeyDown(key);
    }

    public static boolean keyPressed(int key) {
        return keys[key];
    }

    public static int keyPressed() {
        return pressedKey;
    }

    public static char keyPressedChar() {
        return pressedChar;
    }

    public static void update() {
        mouseX = Mouse.getX();
        mouseY = Display.getHeight() - Mouse.getY();

        mouse1 = Mouse.isButtonDown(0);
        mouse2 = Mouse.isButtonDown(1);
        mouse3 = Mouse.isButtonDown(2);

        mouse1p = false;
        mouse2p = false;
        mouse3p = false;

        mouseWd = Mouse.getDWheel();


        while (Mouse.next()) {
            if (Mouse.getEventButtonState()) {
                switch (Mouse.getEventButton()) {
                    case 0:
                        mouse1p = true;
                        break;
                    case 1:
                        mouse2p = true;
                        break;
                    case 2:
                        mouse3p = true;
                        break;
                }
            }
        }

        pressedKey = -1;
        pressedChar = Character.MIN_VALUE;
        for (int i = 0; i < keys.length; i++) {
            keys[i] = false;
        }

        if (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                int key = Keyboard.getEventKey();
                pressedKey = key;
                pressedChar = Keyboard.getEventCharacter();

                if (key != -1) {
                    keys[key] = true;
                }
            }
        }
    }

    public static boolean mouseInBounds(float x, float y, float width, float height) {
        return (InputManager.mouseX > x && InputManager.mouseX < x + width && InputManager.mouseY > y && InputManager.mouseY < y + height);
    }
}
