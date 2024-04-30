package frontend;

import java.text.DecimalFormat;
import java.util.*;

public class CurrencyModel {
  private static final String DOLLAR = "$";
  private static final String YUAN = "¥";
  private static final String EURO = "€";
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
      // System.out.println("Current total: " + listeners.size());
      for (CurrencyModelListener listener : listeners) {
        listener.currencyUpdate();
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
    }
  }
  public String getCurrentCurrency() {
    return this.currentCurrency;
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
}

