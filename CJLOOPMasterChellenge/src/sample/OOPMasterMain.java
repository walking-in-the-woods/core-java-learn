package sample;

public class OOPMasterMain {
    public static void main(String[] args) {
        Hamburger hamburger = new Hamburger("Basic", "Sausage", 3.56, "White");
        double price = hamburger.itemizeHamburger();
        hamburger.addHamburgerAddition1("Tomato", 0.27);
        hamburger.addHamburgerAddition2("Lettuce", 0.75);
        hamburger.addHamburgerAddition3("Cheese", 1.13);
        System.out.println("Total price is " + hamburger.itemizeHamburger());

        Healthyburger healthyburger = new Healthyburger("Bacon", 5.67);
        healthyburger.addHamburgerAddition1("Egg", 5.43);
        healthyburger.addHealthAddition1("Lentils", 3.41);
        System.out.println("Total Healthy Burger price is " + healthyburger.itemizeHamburger());

        DeluxeBurger deluxeBurger = new DeluxeBurger();
        deluxeBurger.addHamburgerAddition1("Should not do this", 50.53);
        deluxeBurger.itemizeHamburger();
    }
}
