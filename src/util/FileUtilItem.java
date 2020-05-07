package util;

import model.Item;
import model.User;

import java.io.*;
import java.util.List;
import java.util.Map;

public class FileUtilItem {


    private static final String FILE_PATH2 = "C:\\Users\\user\\IdeaProjects\\Advertisement\\src\\model\\utilFileUser.txt";


    public static void serializeItem(List<Item> items) throws IOException {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FILE_PATH2));
            objectOutputStream.writeObject(items);
            objectOutputStream.close();
        } catch (IOException e) {
        }
    }

    public static List<Item> deserializeItem() throws IOException, ClassNotFoundException {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(FILE_PATH2));
            Object deserialization = objectInputStream.readObject();
            List<Item> items = (List<Item>) deserialization;
            objectInputStream.close();
            return items;
        } catch (Exception e) {
        }
        return null;
    }
}



