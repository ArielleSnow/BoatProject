import java.util.ArrayList;
import java.io.*;
import java.io.FileReader;
import java.util.Scanner;
import java.io.IOException;


public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static final BOAT_DB_FILENAME = "FleetData.db";
    public static void main(String[] args) {

        ArrayList<Boat> boatList;
      System.out.println("Welcome to the Fleet Management System");

        if(args.length > 0){
            boatList = initFromCSV();
        } else {
            boatList = initFromDB();
        }
        if (boatList != null){
            menu(boatList);
            writeToDB(boatList);
        }

        System.out.println("Exiting the Fleet Management System");
    } // end of main

    private static ArrayList<Boat> initFromCSV(File csvFile){

        String csvData;
        ArrayList<Boat> boatList;
        BufferedReader readingData = null;
        boatList = new ArrayList<>();
        try{
            readingData = new BufferedReader(new FileReader(csvFile));
            csvData = readingData.readLine();
            while(csvData != null){
                boatList.add(makeBoatData(csvData));
                csvData = readingData.readLine();
            }
        }
        catch (IOException e) {
            System.out.println("Error opening/reading " + csvFile);
            return null;
        }
        finally {
            try {
                readingData.close();
            }
            catch (IOException e) {
                System.out.println("Error closing " + csvFile);
                return null;
            }
        }
        return (boatList);
    }
    private static ArrayList<Boat> initFromDB(){
        ObjectInputStream streamData = null;
        ArrayList <Boat> boatList = null;

        try{
            streamData = new ObjectInputStream(new FileInputStream(BOAT_DB_FILENAME));
            boatList = (ArrayList<Boat>)streamData.readObject();
        } catch (IOException e){
            System.out.print("Error loading from " + BOAT_DB_FILENAME);
            return null;
        } finally {
                try {
                    streamData.close();
                } catch(IOException e){
                    System.out.println("Error closing " + e.getMessage());
                    return null;
                }
            }

        return boatList;
    }
    private static void writeToDB(ArrayList<Boat> boatList){
        ObjectOutputStream streamData = null;
        try {
            streamData = new ObjectOutputStream(new FileOutputStream(BOAT_DB_FILENAME));
            streamData.writeObject(boatList);
        } catch (IOException e){
            System.out.println("Error saving to " + BOAT_DB_FILENAME);
        } finally {
            try{
                streamData.close();
            } catch (IOException e){
                System.out.println("Error closing " + BOAT_DB_FILENAME);
            }
        }
    }
    private static void menu(ArrayList<Boat> boatList){
        char choice = 'Q';
        while (choice != 'X' && choice != 'x'){
            System.out.println("(P)rint, (A)dd, (R)emove, (E)xpense, e(X)it : ");
            choice = scanner.nextLine().charAt(0);
            if (choice == 'P' || choice == 'p'){
                printData(boatList);
            } else if (choice == 'A' || choice == 'a'){
                add(boatList);
            } else if (choice == 'R' || choice == 'r'){
                remove(boatList);
            } else if (choice == 'E' || choice == 'e'){
                expense(boatList);
            } else {
                System.out.println("Invalid menu option, try again");
            }
        }

    }
    private static void printData(ArrayList<Boat> boatList){
        double price = 0.0;
        double totalExpenses = 0.0;
        int index;
        System.out.println("Fleet report:");
        for (index = 0; index < boatList.size(); index++) {
            System.out.println(boatList.get(index));
            price += boatList.get(index).getPrice();
            totalExpenses += boatList.get(index).getExpenses();
        }
        System.out.println("Total:     " + " Paid: " + price + " Spent $: " + totalExpenses);
    }

    private static void add(ArrayList<Boat> boatList){
        String csvData;
        System.out.println("PLease enter the new boat CSV data: ");
        csvData = scanner.nextLine();
        boatList.add(makeBoatData(csvData));
    }
    private static void remove(ArrayList<Boat> boatList){
        String boatName;
        int index = 0;
        System.out.println("Which boat do you want to remove?");
        boatName = scanner.nextLine();

        while (index < boatList.size()){
            if (boatList.get(index).getName() == boatName){
                boatList.remove(index);
            }
            index ++;

        }

    }
    private static void expense(ArrayList<Boat> boatList){
        Boat myBoat = new Boat();
        double amountWanted;
        String boatName;
        int index = 0;

        System.out.println("Which boat do you want to spend on? ");
        boatName = scanner.nextLine();
        while (index < boatList.size()){
            if (boatList.get(index).getName() == boatName){
                myBoat = boatList.get(index);
            }
            index ++;
        }
        System.out.println("How mcuh do you want to spend? ");
        amountWanted = Double.parseDouble(scanner.nextLine());
        if (myBoat.spend(amountWanted)){
            System.out.println("Expense authroized " + amountWanted + " spent");
        } else{
            System.out.println("Expense not permitted, only " + (myBoat.getPrice() - myBoat.getExpenses()) + "left to spend");
        }
    }
    private static Boat.boatType makeBoatData(String csvData){
        String[] boatPieces;
        Boat.boatType type;
        boatPieces = csvData.split(",");
        if (boatPieces[0].toUpperCase() == "POWER"){
            type = Boat.boatType.POWER;
        } else if (boatPieces[0].toUpperCase()== "SAILING") {
            type = Boat.boatType.SAILING;
        } else {
            type = Boat.boatType.UNKNOWN;
        }
        return type;
    }
}