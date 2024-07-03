// This class gives access for the customer to buy different cheeses,
// remove cheese from cart, should be possible to get all the cheeses from the cart
// or available cheeses in the store

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CheeseShop {

    public ArrayList<Cheese> productRange;


    public CheeseShop(){
        this.productRange = new ArrayList<>();// default constructor
    }

    // Customer's abilities
    public void addToCart(Customer customer, Cheese cheese, double amountInGrams) {
        CheeseService order = new CheeseService(cheese, amountInGrams);
        customer.getCart().add(order);
    }

    public void removeFromCart(Customer customer, CheeseService order) {
        customer.getCart().remove(order);
    }

    public void viewMyCart(Customer customer) {
        System.out.println("This is what " + customer.getName() + " currently has in the shopping cart:");
        if (customer.getCart().size() == 0) {
            System.out.println("Your cart is currently empty");
        } else {
            for (CheeseService order : customer.getCart()) {
                System.out.println(order.getCheese().getType() + " " + order.getTotalPrice() + " €.");
            }
        }
    }

    public Cheese findCheeseByType(String type){
        for (Cheese cheese : productRange){
            if (cheese.getType().equalsIgnoreCase(type)) {
                return cheese; // Found the cheese by type
            }
        }
        return null;
    }

    public void viewProductRange() {
        System.out.println("This is the product range available in the cheese shop:");
        for (Cheese product : productRange) {
            System.out.println(product.getType() + " " + product.getPricePerKg() + " €/kg." + "(Vegan: " + product.getIsVegan() + ")");
        }
    }

    public void checkOut(Customer customer) {
        double total = 0.0;
        for (CheeseService order : customer.getCart()) {
            System.out.println(order.getCheese().getType() + " " + order.getAmountInGrams() + " grams " + order.getTotalPrice() + " €.");
            total += order.getTotalPrice();
        }
        System.out.println("Checkout total is: " + total + " €.");
        customer.updateBalance(total);
        //addPurchaseHistory();
        customer.getCart().clear();
    }

    /*public void addPurchaseHistory(String customerId, List<CheeseService> purchasedItems) {
        // Save purchase history to JSON file
        String FILE_PATH = "src/customerDatabase.json";

        StringBuilder jsonContent = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
        String line;
        while ((line = br.readLine()) != null){
            jsonContent.append(line);
        }
        br.close();

        JSONObject jsonObject = new JSONObject(jsonContent.toString());
        JSONArray jsonArray = jsonObject.getJSONArray("customers");

        // Find customer


        for (CheeseService order : customer.getCart()) {

        }
    }*/

    public void loadCheesesFromJson(String filePath) {
        try {
            StringBuilder jsonContent = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                jsonContent.append(line);
            }
            br.close();

            JSONObject jsonObject = new JSONObject(jsonContent.toString());
            JSONArray cheeses = jsonObject.getJSONArray("cheeses");

            for (int i = 0; i < cheeses.length(); i++) {
                JSONObject cheeseJson = cheeses.getJSONObject(i);
                Cheese cheese = new Cheese(
                        cheeseJson.getString("type"),
                        cheeseJson.getString("producer"),
                        cheeseJson.getString("originCountry"),
                        cheeseJson.getInt("ageInMonths"),
                        cheeseJson.getDouble("pricePerKg"),
                        LocalDate.parse(cheeseJson.getString("expiryDate")),
                        cheeseJson.getBoolean("isVegan")
                );
                productRange.add(cheese);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
