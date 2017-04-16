package com.loafy.game.resources;

import com.loafy.game.GameSettings;
import org.newdawn.slick.Sound;

import java.util.HashMap;

public class AudioPlayer {

    private static HashMap<String, Sound> soundMap = new HashMap<>();

    public static void addSound(String key, String path) {
        try {
            soundMap.put(key, new Sound(Resources.gameLocation + "/res/sounds/" + path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playSound(String name) {
        soundMap.get(name).play(1.0f, GameSettings.soundVolume);
    }
}
