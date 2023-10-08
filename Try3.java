import java.util.*;
import java.io.*;

//
class Vehicle{
    private String brand;  // Private member variable to store the brand of the car
    private String model;  // Private member variable to store the model of the car
    private int year;      // Private member variable to store the year of the car
    private double price;  // Private member variable to store the price of the car

    // Constructor to initialize a Car object
    public Vehicle(String brand, String model, int year, double price) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
    }

    // Getter methods to retrieve car attributes
    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }

    // Override toString() method to represent Car as a string
    public String toString() {
        return year + "\t" + brand + "\t" + model + "\t" + price;
    }
}

// Class to represent a Car
class Car extends Vehicle{
    
    public Car(String brand, String model, int year, double price) {
        super(brand, model, year, price);
    }

    // Static method to create a Car object from a string representation
    public static Car fromString(String carString) {
        String[] parts = carString.split("\t");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid format for car data in file.");
        }

        String brand = parts[1];
        String model = parts[2];
        int year = Integer.parseInt(parts[0]);
        double price = Double.parseDouble(parts[3]);

        return new Car(brand, model, year, price);
    }

    // Static method to get car details from the user using a scanner
    public static Car getCarDetailsFromUser(Scanner scanner) {
        try {
            // Getting car details from the user
            System.out.print("Enter car brand: ");
            String brand = scanner.nextLine();

            System.out.print("Enter car model: ");
            String model = scanner.nextLine();

            System.out.print("Enter car year: ");
            int year = Integer.parseInt(scanner.nextLine());

            if (year < 1886 || year > 2030) {  // Validate the year range
                throw new IllegalArgumentException("Invalid year. Please enter a valid year.");
            }

            System.out.print("Enter car price: $");
            double price = Double.parseDouble(scanner.nextLine());

            if (price < 0) {  // Validate the price
                throw new IllegalArgumentException("Invalid price. Price cannot be negative.");
            }

            return new Car(brand, model, year, price);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for year or price. Please enter valid numerical values.");
            return null;
        } catch (IllegalArgumentException e) {
            System.out.println("Input validation failed: " + e.getMessage());
            return null;
        }
    }
}

//
class VehicleType extends Car {
    private String type;
    private Scanner input;
    private String FILE_PATHf;  // Define FILE_PATHf here

    public VehicleType(String brand, String model, int year, double price, String type, Scanner input, String FILE_PATHf) {
        super(brand, model, year, price);
        this.type = type;
        this.FILE_PATHf = FILE_PATHf;
        this.input = input;
    }

    public void selectCarType() {
        System.out.println("What Type Of Car Do You Want to Add: ");
        System.out.print("1: Sport Car\n2: JDM Cars\n3: SUV's Cars\n4: Luxury Cars\n5: Daily Run Cars\n");

        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(input.nextLine());

        switch (choice) {
            case 1:
                addSportCar();
                break;
            // Add cases for other types of cars as needed
            default:
                System.out.println("Invalid choice. Please choose a valid option.");
                break;
        }
    }

    public String addSportCar() {
        FILE_PATHf = "Sports_Cars.txt";
        Car sportCar = super.getCarDetailsFromUser(input);
        Dealer d1 = new Dealer(FILE_PATHf);

        if (sportCar != null) {
            d1.addCar(sportCar);
            d1.saveInventoryToFile();
        }
        else{
            System.out.println("Empty Details Try Again...");
            addSportCar();
        }
        return FILE_PATHf;
    }
}

// Class to define the main interface
class Interface1 {

    // Method to prompt the user to go back to the main menu or exit
    public void MenuNavigatorMethod(Scanner scanner) {
        System.out.print("Press 'X' to go back to the main menu, or any other key to exit: ");
        String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase("x")) {
            start();  // Start the main menu if 'X' is pressed
        } else {
            System.out.println("Exiting the program.");  // Exit the program for any other input
            System.exit(0);
        }
    }
    
    // Method to start the main menu
    public void start() {
        Scanner scanner = new Scanner(System.in);

        try {
            // Displaying a loading message
            for (int j = 0; j <= 5; j++) {
                for (int i = 0; i <= 5; i++) {
                    String loadingMessage = "Updating The System" + ".".repeat(i);
                    System.out.print(loadingMessage + "   \r");  // Overwrite the previous line
                    Thread.sleep(100);
                }
            }
        } catch(Exception e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\u001B[H\u001B[2J");
        Dealer dealer = new Dealer(null);
        VehicleType vt1 = new VehicleType(null, null, 0, 0, null, scanner,null);
        dealer.loadInventoryFromFile();  // Load inventory from file
        
        try {
            Thread.sleep(1000);
            System.out.print("\u001B[H\u001B[2J" + "\u001B[1m" ); 
        } catch(Exception e) {}

        System.out.println("\t\tWelcome\n\n");
        System.out.println("Select Any one option \n1 : Add Car In DealerShip\n2 : Check Inventory\n3 : Update Car Details\n4 : Delete Car\n5 : Load Inventory from File\n0 : Exit");
    
        int input;
        try {
            System.out.print("Enter your choice: ");
            input = Integer.parseInt(scanner.nextLine());
    
            switch (input) {
                case 1:
                    // dealer.addCarToInventory(scanner);
                    vt1.selectCarType();
                    MenuNavigatorMethod(scanner);
                    break;
                case 2:
                    dealer.displayInventory();
                    MenuNavigatorMethod(scanner);
                    break;
                case 3:
                    dealer.updateCarDetails(scanner);
                    MenuNavigatorMethod(scanner);
                    break;
                case 4:
                    dealer.deleteCar(scanner);
                    MenuNavigatorMethod(scanner);
                    break;
                case 5:
                    dealer.loadInventoryFromFile();
                    MenuNavigatorMethod(scanner);
                    break;
                case 0:
                    System.out.println("Exiting the program.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
                    start();
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid option.");
            start();
        }
    }
}

// Class to manage the dealer and car inventory
class Dealer {

    private List<Car> inventory;  // Private member variable to store the car inventory
    // private final String FILE_PATH = "car_inventory.txt";  // Path to the inventory file
    
    private String FILE_PATH;  // Path to the inventory file
    
    // Constructor to initialize a Dealer object
    public Dealer(String FILE_PATHf) {
        inventory = new ArrayList<>();  // Initializing the inventory as an ArrayList
        this.FILE_PATH = FILE_PATHf;
    }

    // Method to add a car to the inventory
    public void addCar(Car car) {
        inventory.add(car);
        System.out.println("Car added to inventory.");
    }

    // Method to add a car to the inventory from user input
    public void addCarToInventory(Scanner scanner) {
        
        while (true) {
            Car car = Car.getCarDetailsFromUser(scanner);

            if (car != null) {
                System.out.println("Car details: " + car);
                System.out.print("Confirm car addition? (Y/N): ");
                String confirmation = scanner.nextLine().trim().toLowerCase();

                if (confirmation.equalsIgnoreCase("y")) {
                    addCar(car);
                    saveInventoryToFile(); // Save inventory after adding a car
                } else {
                    System.out.println("Car addition canceled.");
                }
            }

            System.out.print("Do you want to add another car? (Y/N): ");
            String addAnother = scanner.nextLine().trim().toLowerCase();

            if (!addAnother.equalsIgnoreCase("y")) {
                break;
            }
        }
        displayInventory();
    }

    // Method to get the inventory
    public List<Car> getInventory() {
        return inventory;
    }

    // Method to display the inventory
    public void displayInventory() {
        if (inventory.isEmpty()) {
            System.out.println("\t\t\tInventory is empty.");
        } else {
            System.out.println("\t\t\tCar Inventory:\n");
            System.out.println("Year\tBrand\tModel\tPrice\n");
            int sequenceNumber = 1;
            for (Car car : inventory) {
                System.out.println(car);
                sequenceNumber++;
            }
            
            System.out.println("\n\tSort The List By\n1 : Year  2 : Model  3 : Brand  4 : Price  0 : Back To Menu");
            Scanner scn = new Scanner(System.in);
            int input = scn.nextInt();

            if (input == 0) {
                return; // Go back to the main menu
            } else {
                switch (input) {
                    case 1:
                        sortInventoryByYear();
                        break;
                
                    case 2:
                        sortInventoryByModel();
                        break;
                
                    case 3:
                        sortInventoryByBrand();
                        break;
                
                    case 4:
                        sortInventoryByPrice();
                        break;
                
                    default:
                        System.out.println("Invalid sort choice.");
                        break;
                }
                displayInventory();
            }
        }
    }

    // Method to sort inventory by year
    public void sortInventoryByYear() {
        Collections.sort(inventory, new Comparator<Car>() {
            public int compare(Car car1, Car car2) {
                return Integer.compare(car1.getYear(), car2.getYear());
            }
        });
    }

    // Method to sort inventory by model
    public void sortInventoryByModel() {
        Collections.sort(inventory, new Comparator<Car>() {
            public int compare(Car car1, Car car2) {
                return car1.getModel().compareTo(car2.getModel());
            }
        });
    }

    // Method to sort inventory by brand
    public void sortInventoryByBrand() {
        Collections.sort(inventory, new Comparator<Car>() {
            public int compare(Car car1, Car car2) {
                return car1.getBrand().compareTo(car2.getBrand());
            }
        });
    }

    // Method to sort inventory by price
    public void sortInventoryByPrice() {
        Collections.sort(inventory, new Comparator<Car>() {
            public int compare(Car car1, Car car2) {
                return Double.compare(car1.getPrice(), car2.getPrice());
            }
        });
    }

    // Method to update car details
    public void updateCarDetails(Scanner scanner) {
        System.out.print("Enter the serial number of the car to update: ");
        int serialNumber = Integer.parseInt(scanner.nextLine()) - 1;  // Adjust for 0-based index

        if (serialNumber >= 0 && serialNumber < inventory.size()) {
            Car car = inventory.get(serialNumber);

            System.out.println("Current details for Car " + (serialNumber + 1) + ":");
            System.out.println(car);

            Car updatedCar = Car.getCarDetailsFromUser(scanner);
            if (updatedCar != null) {
                inventory.set(serialNumber, updatedCar);
                System.out.println("Car details updated.");
                saveInventoryToFile(); // Save inventory after updating a car
            }
        } else {
            System.out.println("Invalid serial number. No car found for the given serial number.");
        }
    }

    // Method to delete a car from the inventory
    public void deleteCar(Scanner scanner) {
        if (inventory.isEmpty()) {
            System.out.println("\t\t\tInventory is empty.");
        } else {
            System.out.println("\t\t\tCar Inventory:\n");
            System.out.println("S/No\tYear\tBrand\tModel\tPrice\n");
            int sequenceNumber = 1;
            for (Car car : inventory) {
                System.out.println(sequenceNumber + ":\t" + car);
                sequenceNumber++;
            }

            System.out.print("Enter the serial number of the car to delete: ");
            int serialNumber = Integer.parseInt(scanner.nextLine()) - 1;  // Adjust for 0-based index

            if (serialNumber >= 0 && serialNumber < inventory.size()) {
                Car car = inventory.get(serialNumber);
                inventory.remove(serialNumber);
                System.out.println("Car " + (serialNumber + 1) + " deleted:");
                System.out.println(car);
                saveInventoryToFile(); // Save inventory after deleting a car
            } else {
                System.out.println("Invalid serial number. No car found for the given serial number.");
            }
        }
    }

    // Method to save the inventory to a file
    public void saveInventoryToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Car car : inventory) {
                writer.write(car.toString());
                writer.newLine();
            }
            System.out.println("Inventory saved to file.");
        } catch (IOException e) {
            System.out.println("Failed to save inventory to file: " + e.getMessage());
        }
    }

    // Method to load the inventory from a file
    void loadInventoryFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Car car = Car.fromString(line);
                inventory.add(car);
            }
            System.out.println("Inventory loaded from file.");
        } catch (IOException e) {
            System.out.println("Failed to load inventory from file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error parsing car data from file: " + e.getMessage());
        }
    }
}

// this is the main class
public class Try3{
    public static void main(String[] args) {
        try{
        System.out.print("\u001B[H\u001B[2J" + "\u001B[1m" ); 
        String greenColor = "\u001B[32m"; // text color and text Style
        System.out.println(greenColor);

        Interface1 IF1 = new Interface1();
        IF1.start();
        }catch(Exception e){  System.err.println("An exception occurred: " + e.getMessage());}
    }
}

/*
   To Do:
   1. Add a method to allow the user to create a new file to store cars in different files.
   2. Add a method that allows the user to select which file to load from.
   3. Make changes in the UpdateCarDetail option so if the user wants to update any specific attribute.
*/

/*
   Foreground (Text) Colors:
   Black: \u001B[30m
   Red: \u001B[31m
   Green: \u001B[32m
   Yellow: \u001B[33m
   Blue: \u001B[34m
   Magenta: \u001B[35m
   Cyan: \u001B[36m
   White: \u001B[37m

   Background Colors:
   Black: \u001B[40m
   Red: \u001B[41m
   Green: \u001B[42m
   Yellow: \u001B[43m
   Blue: \u001B[44m
   Magenta: \u001B[45m
   Cyan: \u001B[46m
   White: \u001B[47m

   Reset:
   Reset all attributes (color, bold, etc.): \u001B[0m

   Other Attributes:
   Bold: \u001B[1m
   Underline: \u001B[4m
   Blink (slow): \u001B[5m
   Blink (rapid): \u001B[6m
   Reverse video: \u001B[7m
*/
