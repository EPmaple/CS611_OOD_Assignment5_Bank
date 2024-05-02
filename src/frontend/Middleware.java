package frontend;

import java.util.*;

import account.BalanceListener;
import role.AccountListener;
import stock.StockListener;

import java.io.*;

public class Middleware {
  // singleton
  private static Middleware instance = new Middleware();
  private Middleware() {}
  public static Middleware getInstance() {
    if (instance == null) {
      instance = new Middleware();
    }
    return instance;
  }

  // ****************************************************

  private List<AccountListener> listeners = new ArrayList<AccountListener>();

  public void addAccountListener(AccountListener listener) {
      // System.out.println("listener added: " + listener);
      listeners.add(listener);
  }

  public void removeAccountListener(AccountListener listener) {
      listeners.remove(listener);
  }

  public void notifyAccountUpdated(String accountType) {
    List<AccountListener> localListeners = new ArrayList<AccountListener>(listeners);
    // System.out.println("Is there any listeners?");
    for (AccountListener listener : localListeners) {
        // System.out.println(listener.toString());
        listener.accountUpdated(accountType);
    }
  }

  // ****************************************************

  private List<BalanceListener> accountListeners = new ArrayList<BalanceListener>();

  public void addBalanceListener(BalanceListener listener) {
      // System.out.println("listener added: " + listener);
      accountListeners.add(listener);
  }

  public void removeBalanceListener(BalanceListener listener) {
    accountListeners.remove(listener);
  }

  public void notifyBalanceUpdated(String customerName) {
    List<BalanceListener> localListeners = new ArrayList<BalanceListener>(accountListeners);
    System.out.println("Is there any listeners? " + accountListeners.size());
    for (BalanceListener listener : localListeners) {
        // System.out.println(listener.toString());
        listener.balanceUpdated(customerName);
    }
  }

  // ****************************************************

  private List<StockListener> stockListeners = new ArrayList<StockListener>();

  public void addStockListener(StockListener listener) {
      // System.out.println("listener added: " + listener);
      stockListeners.add(listener);
  }

  public void removeStockListener(StockListener listener) {
    stockListeners.remove(listener);
  }

  public void notifyStockUpdated() {
    List<StockListener> localListeners = new ArrayList<StockListener>(stockListeners);
    System.out.println("Is there any listeners? " + stockListeners.size());
    for (StockListener listener : localListeners) {
        // System.out.println(listener.toString());
        listener.stockUpdated();
    }
  }
}
