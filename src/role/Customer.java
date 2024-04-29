package role;
import account.Account;
import account.CheckingAccount;
import account.SavingAccount;
import account.StockAccount;
import utility.Read;
import utility.Write;

import java.util.List;
import java.util.UUID;

public class Customer extends User {
    private String password;
    private boolean check_account=false;
    private boolean saving_account=false;
    private boolean stocking_account=false;
    private boolean has_loan = false;
    private double loan_num = 0;
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
    public double getLoan_num(){
        return loan_num;
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
        return Read.getSavingAccount(name);
    }
    public CheckingAccount getCheckingAccount(){
        return  Read.getCheckingAccount(name);
    }
    public boolean createSavingAccount(){
        if(saving_account){
            return false;
        }
        saving_account = true;
        SavingAccount savingAccount = new SavingAccount(name,"0","saving","false");
        Write.rewriteSavingAccount(savingAccount);
        Write.rewriteUserInfo(this);
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
        return true;
    }
    public boolean request_loan(int loan_num){
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
    public boolean pay_loan(int pay){
        if(!check_account){
            return false;
        }
        if(!has_loan){
            return false;
        }
        boolean result = getCheckingAccount().payLoan((1.0+Account.loan_rate)*Math.min(pay,loan_num));
        if (result){
            loan_num-=Math.min(pay,loan_num);
        }
        if(loan_num==0){
            has_loan = false;
        }
        Write.rewriteUserInfo(this);
        return result;
    }
    public boolean createStockingAccount(){
        if(has_stock_account()){
            return false;
        }
        if(!this.saving_account){
            return false;
        }
        if(this.getSavingAccount().getBalance()<5000){
            return false;
        }
        this.getSavingAccount().transferOut(1000);
        StockAccount stockAccount = new StockAccount(name,"0","stocking");
        stockAccount.transferIn(1000);
        this.stocking_account = true;
        Write.rewriteUserInfo(this);
        return true;
    }
    public StockAccount getStockAccount(){
        return Read.getStockAccount(name);
    }
}
