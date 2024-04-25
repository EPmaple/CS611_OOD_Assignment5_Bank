package frontend;

import java.util.*;

public class CurrencyModel {
  private static final String DOLLAR = "$";
  private static final String YUAN = "¥";
  private static final String EURO = "€";

  private static CurrencyModel instance = new CurrencyModel();

  private final List<CurrencyChangeListener> listeners = new ArrayList<CurrencyChangeListener>();

  /*
   * and we are always converting from dollar to another currency
   */
  // look up how to init nested hashmap
  private Map<String, Map<String, Double>> rates = new HashMap<>();

  private CurrencyModel() {

  }

  public static CurrencyModel getInstance() {
    if (instance == null) {
      instance = new CurrencyModel();
    }
    return instance;
  }

  public void setRate(String currencyConvertingFrom, String currencyConvertingTo, double rate) {
    // Check if there already exists a mapping for the currency we're
    // converting from and to
    Map<String, Double> innerMap = rates.getOrDefault(currencyConvertingFrom, new HashMap<String, Double>());

    innerMap.put(currencyConvertingTo, rate);

    rates.put(currencyConvertingFrom, innerMap);
  }

  public interface CurrencyChangeListener {

  }
}
