public class Boat {
    public enum boatType {UNKNOWN, POWER, SAILING}
    private boatType type;
    private String name;
    private short year;
    private String model;
    private byte length;
    private double price;
    private double expenses;

   public Boat(){

       type = boatType.UNKNOWN;
       name = null;
       year = 0;
       model = null;
       length = 0;
       price = 0.0;
       expenses = 0.0;
   }
//constructor for CSV
    public Boat(boatType myType, String myName, short myYear, String myModel, byte myLength, double myPrice, double myExpenses){
       type = myType;
       name = myName;
       year = myYear;
       model = myModel;
       length = myLength;
       price = myPrice;
       expenses = myExpenses;
    }
   public String toString(){
//DO THIS
    }

    public String getName(){
       return name;

    }

    public double getPrice(){
       return price;
    }

    public double getExpenses(){
       return expenses;
    }

    //spend money, not over 1 million, check first, then use loop to add to expenses for that boat
    public boolean spend (double amountSpent){
       if (amountSpent + expenses <= price){
           expenses = amountSpent + expenses;
           return true;
       }
       else{
           return false;
       }
    }
}
