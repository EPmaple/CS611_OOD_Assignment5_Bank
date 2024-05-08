package utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.lang.System.exit;

public class InputHandler {
    private static InputHandler instance; // Singleton instance
    final private Scanner scanner;

    // Private constructor prevents instantiation from outside the class
    private InputHandler() {
        this.scanner = new Scanner(System.in);
    }

    // Public static method to get the instance
    public static InputHandler getInstance() {
        if (instance == null) {
            instance = new InputHandler();
        }
        return instance;
    }

    public String getValidStringInput(List<String> validOptions) {
        while (true) {
            // System.out.print("Enter your input: ");
            String input = scanner.nextLine();
            if(input.equals("q")){
                System.out.println("Good bye~");
                exit(0);
            } if (validOptions.contains(input)) {
                return input;
            } else {
                System.out.print("Invalid input. Please try again: ");
            }
        }
    }

    public int getValidIntInput(int min, int max) {
        while (true) {
            System.out.print("Enter your input: ");
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.printf("Invalid input. Please enter a number between %d and %d.\n", min, max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    public void showInputFormat(List<String> inputOptions, List<String> descriptions) {
        if (inputOptions.size() != descriptions.size()) {
            throw new IllegalArgumentException("The lists must be of the same length.");
        }

        System.out.print("Input options: ");
        for (int i = 0; i < inputOptions.size(); i++) {
            System.out.printf("<%s> - %s, ", inputOptions.get(i), descriptions.get(i));
        }
        System.out.println("<q> - Exit");
    }

    public void showInputFormat(String prompt){
        System.out.print(prompt);
        System.out.print(":? ");
    }

    public List<List<String>> loadFileContents(String filePath) {
        List<List<String>> dataLists = new ArrayList<>();
        try {
            File inputFile = new File(filePath);
            Scanner fileReader = new Scanner(inputFile);
            while (fileReader.hasNextLine()) {
                String dataLine = fileReader.nextLine();
                if (dataLine.isEmpty()) continue;
                List<String> lineItems = new ArrayList<>();
                if(dataLine.contains("Name")){
                    String[] elements = dataLine.split("/");
                    lineItems.addAll(Arrays.asList(elements));
                    dataLists.add(lineItems);
                } else{
                    if(dataLine.contains("All")) dataLine = dataLine.replace("All ", "All#");
                    String[] elements = dataLine.split("\\s+");
                    for(int index = 0; index < elements.length; index++){
                        if(elements[index].contains("All")) elements[index] = elements[index].replace("All#", "All ");
                    }
                    lineItems.addAll(Arrays.asList(elements));
                    dataLists.add(lineItems);
                }
            }
            return dataLists;
        } catch (FileNotFoundException exception) {
            System.err.println(exception.toString());
        }
        return dataLists;
    }

    public String toPath(String filename){
        String basePath = "src/data/"; // TODO: change this when submitting the hw
        return basePath + filename + ".txt";
    }

    // Function to prompt the user to select an option from the list
    public <T> T selectFromList(List<T> options) {
        if(options.isEmpty()){
            return null;
        }
        // Display all options with indices
        System.out.println("Please select an option:");
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i).toString());
        }
        System.out.println("<1~9> - <Select this item>, <0> - <Cancel and return>");
        int selectedIndex = -1;
        do {
            System.out.print("Enter the number of your choice: ");
            if (scanner.hasNextInt()) {
                selectedIndex = scanner.nextInt();
                scanner.nextLine();
                if (selectedIndex < 1 || selectedIndex > options.size()) {
                    System.out.println("Invalid selection. Please select a number between 1 and " + options.size() + ".");
                    selectedIndex = -1; // Reset selection to loop again
                }
            } else {
                // Not an integer, consume this input to avoid infinite loop
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume the invalid input
            }
        } while (selectedIndex == -1);

        return options.get(selectedIndex - 1); // Adjust for 0-based indexing
    }

    public <T> T selectFromListOptional(List<T> options) {
        if(options.isEmpty()){
            return null;
        }
        // Display all options with indices
        System.out.println("Please select an option:");
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i).toString());
        }
        System.out.println("<1~9> - <Select this item>, <0> - <Cancel and return>");

        int selectedIndex = -1;
        do {
            System.out.print("Enter the number of your choice: ");
            if (scanner.hasNextInt()) {
                selectedIndex = scanner.nextInt();
                scanner.nextLine(); // consume the '\n'
                if(selectedIndex == 0){
                    System.out.println("You decided not to make a selection.");
                    return null;
                } else if (selectedIndex < 0 || selectedIndex > options.size()) {
                    System.out.println("Invalid selection. Please select a number between 1 and " + options.size() + ".");
                    selectedIndex = -1; // Reset selection to loop again
                }
            } else {
                // Not an integer, consume this input to avoid infinite loop
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume the invalid input
            }
        } while (selectedIndex == -1);

        return options.get(selectedIndex - 1); // Adjust for 0-based indexing
    }

    public static void main(String[] args) {
        System.out.println(InputHandler.getInstance().loadFileContents("src/data/Armory.txt"));
        System.out.println(InputHandler.getInstance().loadFileContents("src/data/FireSpells.txt"));
        System.out.println(InputHandler.getInstance().loadFileContents("src/data/IceSpells.txt"));
        System.out.println(InputHandler.getInstance().loadFileContents("src/data/LightningSpells.txt"));
        System.out.println(InputHandler.getInstance().loadFileContents("src/data/Weaponry.txt"));
        System.out.println(InputHandler.getInstance().loadFileContents("src/data/Potions.txt"));
    }

}
