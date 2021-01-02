package LinkedList;

/* Linked list formula:
Index | Address | Value
for an integer:
Address = index * 4 bites + zeroElementAddress
for a double:
Address = index * 8 bites + zeroElementAddress
for a string:
Value = StringAddress | --> Address | Value
Start -> val0/link1 -> val1/linkN -> valN/linkNull
 */

import java.util.ArrayList;

public class LinkedListMain {
    public static void main(String[] args) {
        Customer customer = new Customer("Tim", 54.96);
        Customer anotherCustomer;
        anotherCustomer = customer;
        anotherCustomer.setBalance(12.18);
        System.out.println("Balance for customer" + customer.getName() + " is " + customer.getBalance());

        ArrayList<Integer> intList = new ArrayList<Integer>();

        intList.add(1);
        intList.add(3);
        intList.add(4);

        for(int i=0; i<intList.size(); i++) {
            System.out.println(i + ": " + intList.get(i));
        }

        intList.add(1, 2);

        for(int i=0; i<intList.size(); i++) {
            System.out.println(i + ": " + intList.get(i));
        }
    }
}
