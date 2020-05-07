package storage;

import main.ItemDateComparator;
import model.Category;
import model.Item;
import model.User;
import util.FileUtilItem;
import util.FileUtilUser;

import java.io.IOException;
import java.util.*;

public class DataStorage {

    private static long itemId = 1;

    private  Map<String, User> userMap = new HashMap<>();
    private  List<Item> items = new ArrayList<>();
    private  Map<String, User> userMap2;
    private  List<Item> items2;


    public void add(User user) throws IOException {
        FileUtilUser.serializeUserMap(userMap);
        userMap.put(user.getPhoneNumber(), user);
    }

    public void add(Item item) throws IOException{
        FileUtilItem.serializeItem(items);
        item.setId(itemId++);
        items.add(item);
    }

    public User getUser(String phoneNumber){
        return userMap.get(phoneNumber);
    }

    public Item getItemById(long id){
        for (Item item : items) {
            if(item.getId() == id){
                return item;
            }
        }return null;
    }

    public void printItems(){
        for (Item item : items) {
            System.out.println(item);
        }
    }

    public void printItemsByUser(User user){
        for (Item item : items) {
            if (item.getUser().equals(user)){
                System.out.println(item);
            }
        }
    }

    public void printItemsByCategory(Category category){
        for (Item item : items) {
            if (item.getCategory() == category) {
                System.out.println(item);
            }
        }
    }

    public void printItemsOrderByTitle(){
        List<Item> orderedList = new ArrayList<>(items);
        Collections.sort(orderedList);
        //orderedList.sort(Item :: compareTo);
        for (Item item : orderedList) {
            System.out.println(item);
        }
    }

    public void printItemsOrderByDate(){
        List<Item> orderedList = new ArrayList<>(items);
        orderedList.sort(new ItemDateComparator());
         for (Item item : orderedList) {
            System.out.println(item);
        }
    }

    public void deleteItemsByUser(User user) throws IOException{
        FileUtilItem.serializeItem(items);
        Iterator<Item> iterator = items.iterator();
        while(iterator.hasNext()){
            Item next = iterator.next();
            if(next.getUser().equals(user)){
                iterator.remove();
            }
        }
//        items.removeIf(item -> item.getUser().equals(user));
    }

    public void deleteItemsById(long id) throws IOException{
        FileUtilItem.serializeItem(items);
        items.remove(getItemById(id));
//        Item itemById = getItemById(id);
//        items.remove(itemById);
    }

    public void initData() throws IOException, ClassNotFoundException{
        userMap = FileUtilUser.deserializeUserMap();
        items = FileUtilItem.deserializeItem();
    }


}
