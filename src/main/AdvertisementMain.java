package main;

import model.Category;
import model.Gender;
import model.Item;
import model.User;
import storage.DataStorage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class AdvertisementMain implements Commands{

    private static Scanner scanner = new Scanner(System.in);
    private static DataStorage dataStorage = new DataStorage();
    private static User currentUser = null;
//    private static List<Item> items = null;
//    private static Map<String, User> users = null;

    public static void main(String[] args) throws IOException, ClassNotFoundException{
        dataStorage.initData();

        boolean isRun = true;
        while (isRun) {
            Commands.printMainCommands();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case EXIT:
                    isRun = false;
                    break;
                case LOGIN:
                    loginUser();
                    break;
                case REGISTER:
                    registerUser();
                    break;
                default:
                    System.out.println("Wrong command!");
            }
        }
    }

    private static void registerUser() throws IOException{
        System.out.println("Please input user data(name,surname,gender(MALE,FEMALE),age,phoneNumber,password)");
        try{
            String userDataStr = scanner.nextLine();
            String[] userDataArr = userDataStr.split(",");
            User userFromStorage = dataStorage.getUser(userDataArr[4]);
            if (userFromStorage == null) {
                User user = new User();
                user.setName(userDataArr[0]);
                user.setSurname(userDataArr[1]);
                user.setGender(Gender.valueOf(userDataArr[2].toUpperCase()));
                user.setAge(Integer.parseInt(userDataArr[3]));
                user.setPhoneNumber(userDataArr[4]);
                user.setPassword(userDataArr[5]);
                dataStorage.add(user);
                System.out.println("User successfully added");
            } else {
                System.out.println("User already exists");
            }
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Wrong Data!");
        }

    }

    private static void loginUser() throws IOException {
        System.out.println("Please input phoneNumber and password");
        String loginStr = scanner.nextLine();
        String[] loginArr = loginStr.split(",");
        User user = dataStorage.getUser(loginArr[0]);
        if (user != null && user.getPassword().equals(loginArr[1])) {
            currentUser = user;
            loginSuccess();
        } else {
            System.out.println("Wrong phoneNumber or password");
        }
        System.out.println("Wrong data!");
    }


    private static void loginSuccess() throws IOException {
        System.out.println("Welcome " + currentUser.getName() + " !");
        boolean isRun = true;
        while (isRun) {
            Commands.printUserCommands();
            int command;
            command = Integer.parseInt(scanner.nextLine());
            switch (command) {
                case LOGOUT:
                    isRun = false;
                    break;
                case ADD_NEW_AD:
                    addNewItem();
                    break;
                case PRINT_MY_ADS:
                    dataStorage.printItemsByUser(currentUser);
                    break;
                case PRINT_ALL_ADS:
                    dataStorage.printItems();
                    break;
                case PRINT_ADS_BY_CATEGORY:
                    printByCategory();
                    break;
                case PRINT_ALL_ADS_SORT_BY_TITLE:
                    dataStorage.printItemsOrderByTitle();
                    break;
                case PRINT_ALL_ADS_SORT_BY_DATE:
                    dataStorage.printItemsOrderByDate();
                    break;
                case DELETE_MY_ALL_ADS:
                    dataStorage.deleteItemsByUser(currentUser);
                    break;
                case DELETE_AD_BY_ID:
                    deleteById();
            }
        }
    }

    private static void deleteById() throws IOException {
        System.out.println("Please choose id from list ");
        dataStorage.deleteItemsByUser(currentUser);
        long id = Long.parseLong(scanner.nextLine());
        Item itemById = dataStorage.getItemById(id);
        if (itemById != null && itemById.getId() == id) {
            dataStorage.deleteItemsById(id);
        } else {
            System.out.println("Wrong id");
        }
    }

    private static void printByCategory() {
        System.out.println("Please choose category fro list " + Arrays.toString(Category.values()));
        try {
            String categoryStr = scanner.nextLine();
            Category category = Category.valueOf(categoryStr);
            dataStorage.printItemsByCategory(category);
        } catch (Exception e) {
            System.out.println("Wrong Category!");
        }
    }

    private static void addNewItem() {
        System.out.println("Please input item data(title,text,price,category)");
        System.out.println("Please choose category name from list: " + Arrays.toString(Category.values()));
        try {
            String itemDataStr = scanner.nextLine();
            String[] itemDataArr = itemDataStr.split(",");
            Item item = new Item(itemDataArr[0], itemDataArr[1], Double.parseDouble(itemDataArr[2]),
                    currentUser, Category.valueOf(itemDataArr[3]), new Date());
            dataStorage.add(item);
            System.out.println("Ite was successfully added");
        } catch (Exception e) {
            System.out.println("Wrong Data!");
        }
    }


}