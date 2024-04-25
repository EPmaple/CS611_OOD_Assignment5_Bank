package frontend;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.util.List;

public class Middleware {
  // static final vars
  private static final String CHECKING = "CHECKING";
  private static final String SAVINGS = "SAVINGS";
  private static final String SECURITIES = "SECURITIES";

  // .txt files
  private static final String CUSTOMER_FILE = "./src/Customer.txt";
  private static final String BANKING_ACCOUNTS_FILE = "./src/BankingAccounts.txt";

  // singleton
  private static Middleware instance = new Middleware();
  private Middleware() {
    loadCustomers();
  }
  public static Middleware getInstance() {
    if (instance == null) {
      instance = new Middleware();
    }
    return instance;
  }

  private HashMap<String, String> usernameToPassword = new HashMap<String, String>();
  private HashMap<String, Double> usernameToBalance = new HashMap<String, Double>();

  // accessor methods
  public HashMap<String, String> getUsernameToPassword() {
    return this.usernameToPassword;
  }

  public HashMap<String, Double> getUsernameToBalance() {
    return this.usernameToBalance;
  }

  // mutator methods
  /*
   * username cannot be empty, password cannot be empty
   * if a username exists, we cannot change it
   */
  public boolean setUsernameToPassword(String username, String password) {
    if (username.trim().isEmpty() || password.trim().isEmpty()) {
      // neither username nor password can be empty
      return false; 
    } else {
      usernameToPassword.put(username, password);
      return true;
    }
  }

  public boolean addUsernameToPassword(String username, String password) {
    if (usernameToPassword.get(username) != null) {
      // the username already exists and thus the user cannot register an account
      // with this username
      return false; // print "username or password already exists"
    } else {
      return setUsernameToPassword(username, password);
    }
  }

  /*
   * 
   */

  public boolean createCustomerAccount(String username, String password) {

    if (usernameToPassword.get(username) != null) {
      return false; // account already exists

    } else { // account does not yet exists
      // put into the map
      usernameToPassword.put(username, password);
      // also the need to write to the .txt file
      List<String> attributes = new ArrayList<String>();
      attributes.add(username);
      attributes.add(password);
      writeToFileAppend(CUSTOMER_FILE, attributes);
      return true; // successfully created an account
    }
  }

  public boolean login(String username, String password) {
    /*
     * the possible returns of usernameToPassword.get(username):
     * 1.) null if username does not exist
     * 2.) a different password
     * return false for the above
     * 3.) the same password
     * return true
     */
    if (usernameToPassword.get(username) == null) {
      return false;
    } else if (usernameToPassword.get(username).equals(password)) {
      return true;
    } else {
      return false;
    }
  }

  // ****************************************************

  private boolean writeToFileAppend(String file, List<String> attributes) {
    // write to the .txt file
    try (FileWriter writer = new FileWriter(file, true)) {
      // starting to write the line
      for (int i = 0; i < attributes.size(); i++) {
        String attribute = attributes.get(i);

        if (i == 0) {
          writer.append(attribute);
        } else {
          writer.append(" " + attribute);
        }
      }
      writer.append("\n");
      // finished writing the line
      return true;

    } catch (IOException e) {
      e.printStackTrace();
      return false; // Return false if there was an error writing to the file
    }
  }

  // private void loadBankingAccounts() {
  //   try (Scanner scanner = new Scanner(new File(BANKING_ACCOUNTS_FILE))) {
  //     if (scanner.hasNextLine()) {
  //       scanner.nextLine();
  //     }
  //     // Read and process the remaining lines
  //     while (scanner.hasNextLine()) {
  //       String line = scanner.nextLine();
  //       // Split each into parts based on whitespace
  //       String[] parts = line.split("\\s+");
  //       String username = parts[0];
  //       String accountType = parts[1];
  //       double balance = Double.parseDouble(parts[2]);
  //       usernameToPassword.put(username, password);
  //     }  

  //   } catch (FileNotFoundException err) {
  //     err.printStackTrace();
  //   }
  // }

  private void loadCustomers() {
    try (Scanner scanner = new Scanner(new File(CUSTOMER_FILE))) {
      if (scanner.hasNextLine()) {
        scanner.nextLine();
      }
      // Read and process the remaining lines
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        // Split each into parts based on whitespace
        String[] parts = line.split("\\s+");
        String username = parts[0];
        String password = parts[1];
        usernameToPassword.put(username, password);
      }  

    } catch (FileNotFoundException err) {
      err.printStackTrace();
    }
  }


}
