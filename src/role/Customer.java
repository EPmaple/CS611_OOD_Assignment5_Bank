package role;
import account.Account;
import account.BalanceListener;
import account.CheckingAccount;
import account.SavingAccount;
import account.StockAccount;
import frontend.CurrencyModelListener;
import frontend.Middleware;
import utility.Read;
import utility.Write;
import utility.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Customer extends User {
    private Middleware mwInstance = Middleware.getInstance();
    // ******************************************

    private String password;
    private boolean check_account=false;
    private boolean saving_account=false;
    private boolean stocking_account=false;
    private boolean has_loan = false;
    private double loan_num = 0;
    private SavingAccount savingAccount;
    private CheckingAccount checkingAccount;
    private StockAccount stockAccount;
    public Customer(String name) {
        super(name, EnumRole.Customer);
    }
    public Customer(String uuid, String name, String password, String check_account,String saving_account,String stocking_account,String has_loan,String loan_num){
        super(name,EnumRole.Customer);
        super.set_id(UUID.fromString(uuid));
        this.password = password;
        this.check_account = check_account.equals("true");
        this.saving_account =saving_account.equals("true");
        this.stocking_account = stocking_account.equals("true");
        this.has_loan = has_loan.equals("true");
        this.loan_num = Double.parseDouble(loan_num);
        this.savingAccount = Read.getSavingAccount(name);
        this.checkingAccount = Read.getCheckingAccount(name);
        this.stockAccount = Read.getStockAccount(name);
    }
    public void setPassword(String new_password){
        password = new_password;
        Write.rewriteUserInfo(this);
    }
    public boolean log_in(String password){
        return password.equals(this.password);
    }
    public boolean has_check_account(){
        return check_account;
    }
    public boolean has_saving_account(){
        return saving_account;
    }
    public boolean has_stock_account(){
        return stocking_account;
    }
    public String get_password(){
        return password;
    }
    public boolean get_has_loan(){
        return has_loan;
    }
    public double get_loan_num(){
        return loan_num;
    }
    @Override
    public String toString(){
        return this.id.toString()+","+name+","+password+","+check_account+","+saving_account+","+stocking_account+","+has_loan+","+loan_num;
    }
    public SavingAccount getSavingAccount(){
        return this.savingAccount;
    }
    public CheckingAccount getCheckingAccount(){
        return this.checkingAccount;
    }
    public boolean createSavingAccount(){
        if(saving_account){
            return false;
        }
        saving_account = true;
        SavingAccount savingAccount = new SavingAccount(name,"0","saving","false");
        Write.rewriteSavingAccount(savingAccount);
        Write.rewriteUserInfo(this);
        this.savingAccount = savingAccount;
        mwInstance.notifyAccountUpdated(this.name);
        return true;
    }
    public boolean createCheckingAccount(){
        if(check_account){
            return false;
        }
        CheckingAccount checkingAccount = new CheckingAccount(name,"0","checking");
        Write.rewriteCheckingAccount(checkingAccount);
        check_account = true;
        Write.rewriteUserInfo(this);
        this.checkingAccount = checkingAccount;
        mwInstance.notifyAccountUpdated(this.name);
        return true;
    }
    public boolean request_loan(double loan_num){
        if(!check_account){
            return false;
        }
        if(has_loan){
            return false;
        }
        has_loan = true;
        getCheckingAccount().getLoan(loan_num);
        this.loan_num = loan_num;
        Write.rewriteUserInfo(this);
        return true;
    }

    // public boolean pay_loan(double pay) {
    //     if (!check_account || !has_loan) {
    //         return false;
    //     }

    //     BigDecimal payAmount = new BigDecimal(String.valueOf(pay));
    //     BigDecimal loanNum = new BigDecimal(String.valueOf(loan_num));
    //     BigDecimal loanRate = new BigDecimal(String.valueOf(Account.loan_rate));

    //     // Calculate the amount to pay considering the loan rate
    //     BigDecimal totalPayable = payAmount.min(loanNum).multiply(loanRate.add(BigDecimal.ONE));
        
    //     // Delegate the payment to the checking account's payLoan method
    //     boolean result = getCheckingAccount().payLoan(totalPayable.doubleValue());
    //     System.out.println("checking account pay loan result: " + result);
        
    //     if (result) {
    //         System.out.println("pay: " + pay);
    //         System.out.println("loan_num before: " + loan_num);

    //         // Subtract the actual pay amount from the loan number
    //         loanNum = loanNum.subtract(payAmount.min(loanNum));
    //         loan_num = loanNum.doubleValue(); // Updating the double representation
    //         System.out.println("loan_num after: " + loan_num);
    //     }

    //     System.out.println("Check if the loan is completely paid off: " + (loanNum.compareTo(BigDecimal.ZERO) == 0));
    //     // Check if the loan is completely paid off
    //     if (loanNum.compareTo(BigDecimal.ZERO) == 0) {
    //         has_loan = false;
    //     }

    //     // Persist any changes
    //     Write.rewriteUserInfo(this);
    //     return result;
    // }

    public boolean pay_loan(double pay){
        if(!check_account){
            return false;
        }
        if(!has_loan){
            return false;
        }
        boolean result = getCheckingAccount().payLoan((1.0+Account.loan_rate)*Math.min(pay,loan_num));
        System.out.println("checking account pay loan result: " + result);
        if (result){
            System.out.println("pay: " + pay);
            System.out.println("loan_num before: " + loan_num);
            loan_num-=Math.min(pay,loan_num);
            System.out.println("loan_num after: " + loan_num);
        }
        System.out.println("Check if the loan is completely paid off: " + (loan_num==0.0));
        if(loan_num==0.0){
            has_loan = false;
        }
        Write.rewriteUserInfo(this);
        return result;
    }
    public boolean createStockingAccount(double transferAmt){
        if(has_stock_account()){
            return false;
        }
        if(!this.saving_account){
            return false;
        }
        if(this.getSavingAccount().getBalance()<5000){
            return false;
        }
        this.getSavingAccount().transferOut(transferAmt);
        StockAccount stockAccount = new StockAccount(name,"0","stocking");
        stockAccount.transferIn(transferAmt);
        this.stockAccount = stockAccount;
        this.stocking_account = true;
        Write.rewriteUserInfo(this);
        mwInstance.notifyAccountUpdated(this.name);
        return true;
    }
    public StockAccount getStockAccount(){
        return this.stockAccount;
    }

    public boolean deleteCheckingAccount(){
        if(!check_account){
            return false;
        }
        if(has_loan){
            return false;
        }
        getCheckingAccount().deleteAccount();
        this.check_account = false;
        Write.rewriteUserInfo(this);
        return true;
    }
    public boolean deleteSavingAccount(){
        if(!saving_account){
            return false;
        }
        getSavingAccount().deleteAccount();
        this.saving_account = false;
        Write.rewriteUserInfo(this);
        return true;
    }
    public boolean deleteStockAccount(){
        if(!stocking_account){
            return false;
        }
        getStockAccount().deleteAccount();
        this.stocking_account = false;
        Write.rewriteUserInfo(this);
        return true;
    }
}
