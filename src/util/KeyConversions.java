package util;

import org.lwjgl.input.Keyboard;

import java.lang.reflect.Field;
import java.util.HashMap;

public class KeyConversions {

    public static HashMap<Integer, String> keyCodes = new HashMap<Integer, String>();
    public static HashMap<String, Integer> keyNames = new HashMap<String, Integer>();

    public static void init() {
        try {
            Field[] fields = Keyboard.class.getFields();
            for (Field field : fields) {

                String fieldName = field.getName();

                if (fieldName.startsWith("KEY")) {
                    int keyCode = field.getInt(null);
                    String keyChar = fieldName.substring(4);

                    keyCodes.put(keyCode, keyChar);
                    keyNames.put(keyChar, keyCode);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getKeyName(int keycode) {
        return keyCodes.get(keycode);
    }

    public static int getKeyCode(String keyName) {
        return keyNames.get(keyName.toUpperCase());
    }

}
