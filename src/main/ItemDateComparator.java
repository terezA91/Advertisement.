package main;

import model.Item;

import java.util.Comparator;

public class ItemDateComparator implements Comparator<Item> {

    @Override
    public int compare(Item item, Item t1) {
        return item.getCreatedDate().compareTo(t1.getCreatedDate());
    }
}
