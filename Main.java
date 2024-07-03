import java.io.FileWriter;
import java.time.LocalDate;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static CheeseShop cheeseShop = new CheeseShop();
    public static CheeseService cheeseService = new CheeseService();
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Welcome to Cheese Shop!");

        // Load cheeses from a JSON file
        cheeseShop.loadCheesesFromJson("src/cheeses.json");

        // Create an action menu
        int accessRole;
        Customer client = null;  // Initialize to null initially

        while (true) {
            System.out.print("Press '1' for customer access, '2' for admin access, and '3' to exit: ");
            accessRole = scanner.nextInt();

            if (accessRole == 1) {
                client = identifyCustomer();
                if (client != null) {
                    handleCustomerActions(client);
                }
                // Dispose client instance after customer interaction
                client = null;

            } else if (accessRole == 2) {
                handleAdminActions();

            } else if (accessRole == 3) {
                System.out.println("You've chosen to exit the shop. See you next time!");
                break;  // Exit the main loop and terminate the program

            } else {
                System.out.println("Invalid access role. Please choose again.");
            }
        }
    }

    // Method to identify and return a Customer instance based on user input
    private static Customer identifyCustomer() {
        System.out.println("Dear customer, please identify yourself!");
        System.out.println("Enter customer ID: ");

        String FILE_PATH = "src/customerDatabase.json";
        int customerIdToFind = scanner.nextInt();
        scanner.nextLine();  // Consume newline character

        try {
            // Reading the JSON file
            StringBuilder jsonContent = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
            String line;
            while ((line = br.readLine()) != null) {
                jsonContent.append(line);
            }
            br.close();

            // Parsing the JSON content
            JSONObject jsonObject = new JSONObject(jsonContent.toString());
            JSONArray customers = jsonObject.getJSONArray("customers");

            // Searching for the customer by ID
            for (int i = 0; i < customers.length(); i++) {
                JSONObject customer = customers.getJSONObject(i);
                if (customer.getInt("customerId") == customerIdToFind) {
                    return new Customer(customer.getString("customerName"), customer.getInt("customerId"), customer.getDouble("balance"));
                }
            }

            System.out.println("Customer with ID " + customerIdToFind + " does not exist in the database.");
            System.out.println("Would you like to sign up as a customer? Press '1' for yes, '2' for no.");
            int action = scanner.nextInt();
            scanner.nextLine();  // Consume newline character

            switch (action) {
                case 1:
                    System.out.println("Enter your full name:");
                    String fullName = scanner.nextLine();
                    System.out.println("Enter your balance:");
                    double customerBalance = scanner.nextDouble();
                    // Create customerId automatically
                    int customerId = customers.length() + 1;
                    Customer customer = new Customer(fullName, customerId, customerBalance);

                    JSONObject newCustomer = new JSONObject();
                    newCustomer.put("customerName", customer.getName());
                    newCustomer.put("customerId", customer.getId());
                    newCustomer.put("balance", customer.getBalance());

                    customers.put(newCustomer);

                    FileWriter fileWriter = new FileWriter(FILE_PATH);
                    fileWriter.write(jsonObject.toString(4));
                    fileWriter.flush();
                    fileWriter.close();

                    System.out.println("New customer added successfully to the JSON file!");

                case 2:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;  // Return null if customer not found
    }

    // Method to handle actions for the identified customer
    private static void handleCustomerActions(Customer client) {
        boolean exitRole = false;

        while (!exitRole) {
            System.out.println("Dear customer, what would you like to do today?");
            System.out.println("Press '1' to view product range and prices.");
            System.out.println("Press '2' to add to cart.");
            System.out.println("Press '3' to remove from cart.");
            System.out.println("Press '4' to view your current cart.");
            System.out.println("Press '5' to go to checkout and pay for your cart.");
            System.out.println("Press '6' to exit the customer access role.");
            System.out.print("Choose an option: ");
            int action = scanner.nextInt();
            scanner.nextLine();  // Consume newline character

            switch (action) {
                case 1:
                    cheeseShop.viewProductRange();
                    break;
                case 2:
                    System.out.println("Enter the type of the product you want to add to your cart: ");
                    String type = scanner.nextLine();
                    Cheese cheese = cheeseShop.findCheeseByType(type);
                    if (cheese == null) {
                        System.out.println("Product not found.");
                    } else {
                        System.out.println("Enter amount in grams: ");
                        double amountInGrams = scanner.nextDouble();
                        scanner.nextLine();  // Consume newline character
                        cheeseShop.addToCart(client, cheese, amountInGrams);
                    }
                    break;
                case 3:
                    System.out.println("Enter the type of the product you want to remove from your cart: ");
                    type = scanner.nextLine();

                    // Find the correct CheeseService object in the client's cart
                    CheeseService removableItem = null;
                    for (CheeseService item : client.getCart()){
                        if (item.getCheese().getType().equalsIgnoreCase(type)){}
                            removableItem = item;
                            break;
                    }
                    if (removableItem == null) {
                        System.out.println("Product not found in your cart.");
                    } else {
                        cheeseShop.removeFromCart(client, removableItem);
                        System.out.println("Product removed from cart successfully.");
                    }
                    break;
                case 4:
                    cheeseShop.viewMyCart(client);
                    break;
                case 5:
                    cheeseShop.checkOut(client);
                    break;
                case 6:
                    System.out.println("You've chosen to exit the customer access role.");
                    exitRole = true;
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
                    break;
            }
        }
    }

    // Method to handle admin actions
    private static void handleAdminActions() {
        boolean exitRole = false;

        while (!exitRole) {
            System.out.println("Dear admin, what would you like to do today?");
            System.out.println("Press '1' to add new product to product range.");
            System.out.println("Press '2' to remove new product from product range.");
            System.out.println("Press '3' to exit the admin access role.");
            System.out.print("Choose an option: ");
            int action = scanner.nextInt();
            scanner.nextLine(); // Consume newline character after nextInt()

            // Implement admin actions based on the chosen option
            switch (action) {
                case 1:
                    // Add new product logic
                    System.out.println("Enter the type of the cheese: ");
                    String type = scanner.nextLine();
                    System.out.println("Enter the producer of the cheese: ");
                    String producer = scanner.nextLine();
                    System.out.println("Enter the origin country of the cheese: ");
                    String originCountry = scanner.nextLine();
                    System.out.println("Enter the age (in months) of the cheese: ");
                    int ageInMonths = scanner.nextInt();
                    System.out.println("Enter the price per kilogram of the cheese: ");
                    double pricePerKg = scanner.nextDouble();
                    System.out.println("Enter the expiry YEAR of the cheese: ");
                    int year = scanner.nextInt();
                    System.out.println("Enter the expiry MONTH of the cheese: ");
                    int month = scanner.nextInt();
                    System.out.println("Enter the expiry DAY of the cheese: ");
                    int day = scanner.nextInt();
                    LocalDate expiryDate = LocalDate.of(year, month, day);
                    System.out.println("Enter 'true' if the cheese is vegan, 'false' if it's not: ");
                    boolean isVegan = scanner.nextBoolean();

                    Cheese productToAdd = new Cheese(type, producer, originCountry, ageInMonths, pricePerKg, expiryDate, isVegan);
                    cheeseService.addProduct(cheeseShop, productToAdd);
                    break;
                case 2:
                    // Remove product logic
                    System.out.print("Enter the type of the cheese: ");
                    String cheeseType = scanner.nextLine();

                    Cheese productToRemove = cheeseShop.findCheeseByType(cheeseType);
                    cheeseService.removeProduct(cheeseShop, productToRemove);
                    break;
                case 3:
                    System.out.println("You've chosen to exit the admin access role.");
                    exitRole = true;
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
                    break;
            }
        }
    }
}
