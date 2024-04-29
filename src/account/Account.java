package account;

public abstract class Account {
    public static final double interest_rate = 0.01;
    public static final double transfer_rate = 0.001;
    public static final double loan_rate = 0.02;
    public static double minimun_balance = 2500;
    private int balance;
    public void add_Interest(){}
    public void transferIn(double amount){}
    public boolean transferOut(double amount){return true;}
}
