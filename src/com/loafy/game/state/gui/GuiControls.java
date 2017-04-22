package com.loafy.game.state.gui;

import com.loafy.game.input.Controls;
import com.loafy.game.input.InputManager;
import com.loafy.game.state.GameState;
import com.loafy.game.state.gui.objects.GuiButton;
import util.KeyConversions;

import java.util.ArrayList;
import java.util.HashMap;

public class GuiControls extends Gui {

    private ArrayList<ControlButton> cButtons;

    public GuiControls(GameState state, Gui parent) {
        super(state, parent, "Controls");
        cButtons = new ArrayList<>();

        HashMap<String, Integer> controls = Controls.getControls();

        float index = 0;
        float yOffset = 294;
        float space = 56;

        for (String string : controls.keySet()) {

            ControlButton button = new ControlButton(string + ":" + KeyConversions.getKeyName(Controls.getControls().get(string)), yOffset + index * space) {

                public void action() {
                    super.action();
                    for (ControlButton button : cButtons) {
                        button.setChangeKey(false);
                        button.setDefault();
                    }

                    if (!changeKey) {
                        changeKey = true;
                        text = ">" + key + ":" + value + "<";

                    }
                }
            };

            button.setKey(string);
            button.setValue(KeyConversions.getKeyName(Controls.getControls().get(string)));

            index++;
            cButtons.add(button);
        }
    }

    public void exit() {
        for (ControlButton curButton : cButtons) {
            curButton.setChangeKey(false);
            curButton.setDefault();
        }

        Controls.save();
    }

    public void render() {
        super.render();

        for (ControlButton button : cButtons) {
            button.render();
        }
    }

    public void update() {
        super.update();

        for (ControlButton button : cButtons) {
            button.update();
        }

        ControlButton button = null;

        for (ControlButton buttons : cButtons) {
            if (buttons.changeKey) button = buttons;
        }


        if (button != null) {
            if (button.changeKey) {
                int value = InputManager.keyPressed();
                if (value != -1) {
                    Controls.getControls().put(button.getKey(), value);
                    button.setValue(KeyConversions.getKeyName(value));
                    button.setDefault();
                    button.changeKey = false;
                }
            }
        }

    }

    public class ControlButton extends GuiButton {

        public String key;
        public String value;
        public boolean changeKey = false;

        public ControlButton(String text, float x, float y) {
            super(text, x, y);
        }

        public ControlButton(String text, float y) {
            super(text, y);
        }

        public void setDefault() {
            setText(getKey() + ":" + getValue());
        }

        public void setText(String text) {
            this.text = text;
        }


        public void setChangeKey(boolean c) {
            changeKey = c;
        }

        public void setKey(String string) {
            this.key = string;
        }

        public String getKey() {
            return this.key;
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String string) {
            this.value = string;
        }
    }
}
