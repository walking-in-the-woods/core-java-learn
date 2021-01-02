package ListArrayAndArrayList;

import java.util.ArrayList;

public class GroceryList {
    private ArrayList<String> groceryList = new ArrayList<String>();
    public void addItem(String item) {
        groceryList.add(item);
    }

    public ArrayList<String> getGroceryList() {
        return groceryList;
    }

    public void printList() {
        System.out.println("You have " + groceryList.size() + " items in your grocery list");
        for(int i=0; i<groceryList.size(); i++) {
            System.out.println((i+1) + ". " + groceryList.get(i));
        }
    }

    public void modifyItem(String currentItem, String newItem) {
        int position = findItem(currentItem);
        if(position >= 0) {
            modifyItem(position, newItem);
        }
    }

    private void modifyItem(int position, String newItem) {
        groceryList.set(position, newItem);
        System.out.println("Grocery item " + (position+1) + "has been modified.");
    }

    public void removeItem(String item) {
        int position = findItem(item);
        if(position >= 0) {
            removeItem(position);
        }
    }

    private void removeItem(int position) {
        //String theItem = groceryList.get(position);
        groceryList.remove(position);
    }

    private int findItem(String item) {
        return groceryList.indexOf(item);
        //boolean exists = groceryList.contains(item);
        /*int position = groceryList.indexOf(item);
        if(position >=0) {
            return groceryList.get(position);
        }
        return null;*/
    }

    public boolean onFile(String searchItem) {
       int position = findItem(searchItem);
       if(position >= 0) {
           return true;
       }
       return false;
    }
}
