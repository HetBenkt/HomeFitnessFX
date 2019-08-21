package nl.bos;

import java.util.HashMap;
import java.util.Map;

public class Controllers {
    private static Map<String, Object> controllerMap = new HashMap();

    private Controllers() {
    }

    public static void put(String key, Object value) {
        controllerMap.put(key, value);
    }

    public static Object get(String key) {
        return controllerMap.get(key);
    }
}
