package transaction;

public class Transaction {
    protected String name;
    private String account_type;
    protected String type;
    protected String number;
    protected String time;
    public Transaction(){}
    public Transaction(String name,String account_type,String type,String number,String time){
        this.type = type;
        this.number = number;
        this.time = time;
        this.name = name;
        this.account_type = account_type;
    }
    public String getName(){
        return name;
    }
    public String getAccount_type(){
        return account_type;
    }
    public String getType(){
        return type;
    }
    public String getNumber(){
        return number;
    }
    public String getTime(){
        return time;
    }
    @Override
    public String toString(){
        return name+","+account_type+","+type+","+number+","+time;
    }
}
