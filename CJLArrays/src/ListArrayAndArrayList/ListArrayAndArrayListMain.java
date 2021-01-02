package ListArrayAndArrayList;

import java.util.ArrayList;
import java.util.Scanner;

public class ListArrayAndArrayListMain {
    private static Scanner scanner = new Scanner(System.in);
    private static GroceryList groceryList = new GroceryList();

    public static void main(String[] args) {
        boolean quit = false;
        int choice = 0;
        printInstructions();
        while(!quit) {
            System.out.println("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch(choice) {
                case 0:
                    printInstructions();
                    break;
                case 1:
                    groceryList.printList();
                    break;
                case 2:
                    addItem();
                    break;
                case 3:
                    modifyItem();
                    break;
                case 4:
                    removeItem();
                    break;
                case 5:
                    searchItem();
                    break;
                case 6:
                    processArrayList();
                    break;
                case 7:
                    quit = true;
                    break;
            }
        }
    }

    public static void printInstructions() {
        System.out.println("\n Press \n" +
                "\t 0 - To print choice options.\n" +
                "\t 1 - To print the list of grocery items\n" +
                "\t 2 - To add an item to the list\n" +
                "\t 3 - To modify an item in the list\n" +
                "\t 4 - To remove an item from the list\n" +
                "\t 5 - To search for an item in the list\n" +
                "\t 6 - To quit the application\n");
    }

    public static void addItem() {
        System.out.println("Please enter the grocery item: ");
        groceryList.addItem(scanner.nextLine());
    }

    public static void modifyItem() {
        System.out.println("Enter item name: ");
        String itemNo = scanner.nextLine();
        scanner.nextLine();
        System.out.println("Enter new item: ");
        String newItem = scanner.nextLine();
        groceryList.modifyItem(itemNo, newItem);
    }

    public static void removeItem() {
        System.out.println("Enter item name: ");
        String itemNo = scanner.nextLine();
        scanner.nextLine();
        groceryList.removeItem(itemNo);
    }

    public static void searchItem() {
        System.out.println("Item to search for: ");
        String searchItem = scanner.nextLine();
        if(groceryList.onFile(searchItem)) {
            System.out.println("Found " + searchItem + " in our grocery list");
        } else {
            System.out.println(searchItem + " is not in the shopping list");
        }
    }

   public static void processArrayList() {
        // 3 ways of copying arrays:
        ArrayList<String> newArray = new ArrayList<String>();
       newArray.addAll(groceryList.getGroceryList());

       ArrayList<String> nextArray = new ArrayList<String>(groceryList.getGroceryList());

       String[] myArray = new String[groceryList.getGroceryList().size()];
       myArray = groceryList.getGroceryList().toArray(myArray);
   }
    /*private static Scanner s = new Scanner(System.in);
    private static int[] baseData = new int[3];

    public static void main(String[] args) {
        System.out.println("Enter integers:");
        getInput();
        printArray(baseData);
        resizeArray();
        System.out.println("Enter integers:");
        getInput();
        printArray(baseData);
    }

    private static void getInput() {
        for(int i=0; i<baseData.length; i++) {
            baseData[i] = s.nextInt();
        }
    }
    private static void printArray(int[] arr) {
        for(int i=0; i < arr.length; i++)
            System.out.print(arr[i] + " ");
        System.out.println();
    }
    private static void resizeArray() {
        int[] original = baseData;
        baseData = new int[original.length * 2];
        for(int i=0; i<original.length; i++)
            baseData[i] = original[i];
    }*/
}
