package frontend;

import javax.swing.*;

import account.SavingAccount;
import utility.*;
import role.Customer;

import java.awt.event.*;
import java.util.ArrayList;
import java.awt.*;

import java.util.List;

public class TimeModel {
  private List<TimeModelListener> listeners = new ArrayList<TimeModelListener>();
  public void addTimeModelListener(TimeModelListener listener) {
    // System.out.println("listener added: " + listener);
    listeners.add(listener);
  }
  public void removeTimeModelListener(TimeModelListener listener) {
      listeners.remove(listener);
  }
  private void notifyTimeUpdate() {
    List<TimeModelListener> localListeners = new ArrayList<>(listeners);
    // System.out.println("Current total: " + listeners.size());
    for (TimeModelListener listener : localListeners) {
      listener.timeUpdate();
    }
  }

  private static TimeModel instance = new TimeModel();

  private TimeModel() {}

  public static TimeModel getInstance() {
    if (instance == null) {
      instance = new TimeModel();
    }
    return instance;
  }

  public void incrementTime() {
    int currentTime = Read.readTime();  // Read the current time
    currentTime++;  // Increment the time
    Write.writeTime(currentTime);  // Write the new time back to the file
    addInterests(); // given the increment of a day, we add interests to
    // all saving accounts with high enough belance
    notifyTimeUpdate();
  }

  private void addInterests() {
    List<Customer> customers = Read.readUsers();
    for (Customer customer : customers) {
      SavingAccount savingAccount = customer.getSavingAccount();
      if (savingAccount != null) {
        if (savingAccount.getBalance() > 2000) {
          savingAccount.addInterest();
        }
      }
    }
  }

  public JPanel createTimePanel() {
    String time = "Day " + Read.readTime(); // format: day xx
    JLabel jlbTime = new JLabel(time);

    JButton jbtTime = new JButton("End Day");
    jbtTime.addActionListener(e -> {
      incrementTime();
    });

    JPanel timePanel = new JPanel();
    timePanel.add(jlbTime);
    timePanel.add(jbtTime);

    return timePanel;
  }

  public int getTime() {
    int currentTime = Read.readTime();
    return currentTime;
  }
}
