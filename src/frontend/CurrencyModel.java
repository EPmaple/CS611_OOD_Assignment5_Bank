package frontend;

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;

import java.text.DecimalFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


public class CurrencyModel {
  public static final String DOLLAR = "$";
  public static final String YUAN = "¥";
  public static final String EURO = "€";
  private String currentCurrency;

  private static CurrencyModel instance = new CurrencyModel();

  private  List<CurrencyModelListener> listeners = new ArrayList<CurrencyModelListener>();

  public void addCurrencyModelListener(CurrencyModelListener listener) {
      // System.out.println("listener added: " + listener);
      listeners.add(listener);
  }

  public void removeCurrencyModelListener(CurrencyModelListener listener) {
      listeners.remove(listener);
  }

  /*
   * a currency update can be
   * 1.) an update to the conversion rates
   * 2.) an update to the current currency
   */
  private void notifyCurrencyUpdate() {
      List<CurrencyModelListener> localListeners = new ArrayList<>(listeners);
      // System.out.println("Current total: " + listeners.size());
      for (CurrencyModelListener listener : localListeners) {
        listener.currencyUpdate();
      }
  }

  // ******************************************

  private  List<SetRatesListener> secondListeners = new ArrayList<SetRatesListener>();

  public void addSetRatesListener(SetRatesListener listener) {
    // System.out.println("listener added: " + listener);
    secondListeners.add(listener);
  }

  public void removeSetRatesListener(SetRatesListener listener) {
    secondListeners.remove(listener);
  }

  /*
  * a currency update can be
  * 1.) an update to the conversion rates
  * 2.) an update to the current currency
  */
  private void notifyRatesUpdate() {
      List<SetRatesListener> localListeners = new ArrayList<>(secondListeners);
      // System.out.println("Current total: " + listeners.size());
      for (SetRatesListener listener : localListeners) {
        listener.ratesUpdate();
      }
  }

  /*
   * and we are always converting from dollar to another currency
   */
  // look up how to init nested hashmap
  private Map<String, Map<String, Double>> rates = new HashMap<>();

  private CurrencyModel() {
    setRate(DOLLAR, DOLLAR, 1);
    setRate(DOLLAR, YUAN, 7.24);
    setRate(DOLLAR, EURO, 0.94);

    setRate(EURO, EURO, 1);
    setRate(EURO, DOLLAR, 1.07);
    setRate(EURO, YUAN, 7.73);

    setRate(YUAN, YUAN, 1);
    setRate(YUAN, DOLLAR, 0.14);
    setRate(YUAN, EURO, 0.13);

    setCurrentCurrency(DOLLAR);
  }

  public static CurrencyModel getInstance() {
    if (instance == null) {
      instance = new CurrencyModel();
    }
    return instance;
  }

  public Map<String, Map<String, Double>> getRates() {
    return this.rates;
  }

  public void setRate(String currencyConvertingFrom, String currencyConvertingTo, double rate) {
    // error check
    if (rate < 0) {
      throw new IllegalArgumentException("Cannot input negative rate");
    }

    // Check if there already exists a mapping for the currency we're
    // converting from and to
    Map<String, Double> innerMap = rates.getOrDefault(currencyConvertingFrom, new HashMap<String, Double>());

    innerMap.put(currencyConvertingTo, rate);

    rates.put(currencyConvertingFrom, innerMap);

    // meaning the rate for the current currency has changed
    if (currentCurrency != null) {
      notifyRatesUpdate();
      if (currencyConvertingFrom.equals(DOLLAR) && currentCurrency.equals(currencyConvertingTo)) {
        notifyCurrencyUpdate();
      }
    }

  }
  public double getRate(String currencyConvertingFrom, String currencyConvertingTo) {
    Map<String, Double> innerMap = rates.get(currencyConvertingFrom);
    return innerMap.get(currencyConvertingTo);
  }

  public void setCurrentCurrency(String currency) {
    // error check
    if (rates.containsKey(currency)) {
      currentCurrency = currency;
      notifyCurrencyUpdate();
    }
  }
  public String getCurrentCurrency() {
    return this.currentCurrency;
  }

  public double convertToCurrencyForStorage(double number) {
    double rate = getRate(currentCurrency, DOLLAR);
    double result = number * rate;
    return result;
  }

  // currencyFrom is by default $ as from the db
  public String convertToCurrentCurrency(double number) {
    double rate = getRate(DOLLAR, currentCurrency);
    double result = number * rate;
    DecimalFormat df = new DecimalFormat("#0.00");
    String formatResult = df.format(result);
    // double roundedResult = Math.round(result * 100.0) / 100.0;
    return currentCurrency + formatResult;
  }

  public double convertToSpecifiedCurrency(String currencyTo, double number) {
    if (!rates.containsKey(currencyTo)) {
      throw new IllegalArgumentException("The currency we are "+
      "converting to is currently not supported: " + currencyTo);
    }
    double rate = getRate(DOLLAR, currencyTo);
    double result = number * rate;
    DecimalFormat df = new DecimalFormat("#0.00");
    String formatResult = df.format(result);
    // double roundedResult = Math.round(result * 100.0) / 100.0;
    double convertedNumber = Double.parseDouble(formatResult);
    return convertedNumber;
  }
  

  public JComboBox<String> createCurrencyComboBox() {
    // Ensure the currentCurrency is the first element in the array
    List<String> currencyOptions = new ArrayList<>();
    currencyOptions.add(currentCurrency);  // Add current currency first

    // Add other currencies ensuring no duplicates
    if (!currentCurrency.equals(DOLLAR)) {
      currencyOptions.add(DOLLAR);
    }
    if (!currentCurrency.equals(YUAN)) {
        currencyOptions.add(YUAN);
    }
    if (!currentCurrency.equals(EURO)) {
        currencyOptions.add(EURO);
    }

    // Convert the List to an array for JComboBox constructor
    String[] currencyArray = currencyOptions.toArray(new String[0]);
    JComboBox<String> jcbCurrencyOptions = new JComboBox<>(currencyArray);
    jcbCurrencyOptions.addActionListener(e -> {
      String selectedCurrency = jcbCurrencyOptions.getItemAt(jcbCurrencyOptions.getSelectedIndex());

      if (!selectedCurrency.equals(this.getCurrentCurrency())) {
        this.setCurrentCurrency(selectedCurrency);
      }
    });
    return jcbCurrencyOptions;
  }
}

