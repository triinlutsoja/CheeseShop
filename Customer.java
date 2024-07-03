// Customer - contains money the customer has, contains the items the customer owns.
// Whenever the customer buys something, money is reduced.
// If customer doesn't have any money left, then notify the user about it.

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private String name;
    private int id;
    private double balance;
    private List<CheeseService> cart;

    public Customer(){
    }

    public Customer(String name, int id, double balance) {
        this.name = name;
        this.id = id;
        this.balance = balance;
        this.cart = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public List<CheeseService> getCart() {
        return cart;
    }

    public void updateBalance(double checkOutTotal) {
        if (checkOutTotal <= balance) {
            balance -= checkOutTotal;
            System.out.println("Purchase successfully completed.");
            // customer.getCart().clear();
        } else {
            System.out.println("Purchase failed. Not enough money.");
        }
    }


}
