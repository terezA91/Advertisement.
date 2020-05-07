package util;

import model.Item;
import model.User;
import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.util.List;
import java.util.Map;

public class FileUtilUser {

    private static final String FILE_PATH = "C:\\Users\\user\\IdeaProjects\\Advertisement\\src\\util\\utilFileItem.txt";


    public static void serializeUserMap(Map<String, User> userMap)throws IOException {
        try{
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FILE_PATH));
            objectOutputStream.writeObject(userMap);
            objectOutputStream.close();
        }catch(IOException e){
        }
    }

    public static Map<String, User> deserializeUserMap()throws IOException, ClassNotFoundException{
        try{
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(FILE_PATH));
            Object deserialization = objectInputStream.readObject();
            Map<String, User> userMap = (Map<String, User>) deserialization;
            objectInputStream.close();
            return userMap;
        }catch(Exception e){
        }return null;
    }


}
