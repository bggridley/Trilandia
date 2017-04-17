package com.loafy.game.input;

import com.loafy.game.Main;
import com.loafy.game.resources.Resources;
import util.KeyConversions;

import java.io.*;
import java.util.HashMap;
import java.util.Properties;

public class Controls {

    private static String filePath;
    private static String fileName = "controls.txt";

    private static HashMap<String, Integer> controls;

    public static void init() {
        controls = new HashMap<>();
        initControls();

        filePath = Resources.gameLocation;
        Properties properties = new Properties();
        InputStream stream;

        try {
            File file = Resources.makeFile(filePath, fileName);
            stream = new FileInputStream(file);
            properties.load(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String string : controls.keySet()) {
            controls.put(string, new Integer(properties.getProperty(string, String.valueOf(controls.get(string)))));
        }

        save();
    }

    private static void initControls() {
        controls.put("jump", KeyConversions.getKeyCode("space"));
        controls.put("left", KeyConversions.getKeyCode("a"));
        controls.put("right", KeyConversions.getKeyCode("d"));
        controls.put("inventory", KeyConversions.getKeyCode("q"));
        controls.put("pause", KeyConversions.getKeyCode("escape"));
    }

    public static void save() {
        try {
            File file = new File(filePath, fileName);
            Properties properties = new Properties();
            //set properties

            for (String string : controls.keySet()) {
                properties.put(string, String.valueOf(controls.get(string)));
            }

            OutputStream out = new FileOutputStream(file);
            properties.store(out, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, Integer> getControls() {
        return controls;
    }

}
