//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import bank.Bank;

public class Main {
    public static void main(String[] args) {
        Bank bank = Bank.get_bank();
        bank.start();
    }
}