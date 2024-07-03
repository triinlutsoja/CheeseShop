// This class gives access for the shop owner to add/remove different cheeses,

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CheeseService {

    private Cheese cheese;
    private double amountInGrams;
    private static final String CHEESE_FILE_PATH = "src/cheeses.json";

    public CheeseService() {
        // Default constructor
    }

    public CheeseService(Cheese cheese, double amountInGrams) {
        this.cheese = cheese;
        this.amountInGrams = amountInGrams;
    }

    // Shop owner's abilities
    public void addProduct(CheeseShop shop, Cheese product){
        shop.productRange.add(product);
        saveCheeseToFile(product);
    }

    public void saveCheeseToFile(Cheese product){
        try {
            StringBuilder jsonContent = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(CHEESE_FILE_PATH));
            String line;
            while ((line = br.readLine()) != null){
                jsonContent.append(line);
            }
            br.close();

            JSONObject jsonObject = new JSONObject(jsonContent.toString());
            JSONArray cheeses = jsonObject.getJSONArray("cheeses");

            JSONObject newCheese = new JSONObject();
            newCheese.put("type", product.getType());
            newCheese.put("producer", product.getProducer());
            newCheese.put("originCountry", product.getOriginCountry());
            newCheese.put("ageInMonths", product.getAgeInMonths());
            newCheese.put("pricePerKg", product.getPricePerKg());
            newCheese.put("expiryDate", product.getExpiryDate().toString());
            newCheese.put("isVegan", product.getIsVegan());

            cheeses.put(newCheese);

            FileWriter fileWriter = new FileWriter(CHEESE_FILE_PATH);
            fileWriter.write(jsonObject.toString(4));
            fileWriter.flush();
            fileWriter.close();

            System.out.println("New cheese added successfully to the JSON file!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeProduct(CheeseShop shop, Cheese product){
        shop.productRange.remove(product);
        removeCheeseFromFile(product);
    }

    public void removeCheeseFromFile(Cheese cheese){
        try{
            // Read JSON file
            StringBuilder jsonContent = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(CHEESE_FILE_PATH));
            String line;
            while ((line = br.readLine()) != null){
                jsonContent.append(line);
            }
            br.close();

            // Parse the JSON content
            JSONObject jsonObject = new JSONObject(jsonContent.toString());
            JSONArray cheeses = jsonObject.getJSONArray("cheeses");

            // Find and remove
            for (int i = 0; i < cheeses.length(); i++){
                JSONObject cheeseObject = cheeses.getJSONObject(i);
                if (cheeseObject.getString("type").equals(cheese.getType()) && cheeseObject.getString("producer").equals(cheese.getProducer())){
                    cheeses.remove(i);
                    break;
                }
            }

            // Write the updated JSON back to the file
            FileWriter fileWriter = new FileWriter(CHEESE_FILE_PATH);
            fileWriter.write(jsonObject.toString(4));
            fileWriter.flush();
            fileWriter.close();

            System.out.println("Cheese removed successfully from the JSON file!");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // Customer's purchasing functionalities
    public Cheese getCheese() {
        return cheese;
    }

    public double getAmountInGrams() {
        return amountInGrams;
    }

    public double getTotalPrice() {
        double totalPrice = (cheese.getPricePerKg() / 1000) * amountInGrams;
        String formattedPrice = String.format("%.2f", totalPrice);
        return Double.parseDouble(formattedPrice);
    }

    @Override
    public String toString() {
        return cheese.getType() + " - " + amountInGrams + " grams at " + cheese.getPricePerKg() + " €/kg. Total: " + getTotalPrice() + " €";
    }

}
