package Man;

import java.io.*;
import java.util.HashMap;

class Cashes {
    private static String filePath = "src//cache.ser";
    private static HashMap<String, Object> cache = new HashMap<>();


    // Save cache to disk
    public static void saveCache() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(cache);
        }
    }

    // Load cache from disk
    public static void loadCache() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            cache = (HashMap<String, Object>) ois.readObject();
        }
    }

    public static void put(String key, Object value) throws IOException {
        cache.put(key, value);
        saveCache();
    }

    public static Object get(String key) throws IOException, ClassNotFoundException {
        loadCache();
        return cache.get(key);
    }
}