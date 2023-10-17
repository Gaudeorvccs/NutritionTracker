import java.io.*;
import java.util.Scanner;

import java.io.Serializable;
// import java.util.Arrays;
// import java.util.Collections;

class FoodItem implements Serializable {

  private double fat = 0;
  private double carbs = 0;
  private double protein = 0;
  private String name = "";
  public String desc = "Generic Food Item";

  // returns the class description for UI
  public String getDesc() {
    return desc;
  }

  public boolean setFat(double input) {
    boolean success = false;
    if (input > 0) {
      fat = input;
      success = true;
    }
    return success;
  }

  public boolean setCarbs(double input) {
    boolean success = false;
    if (input > 0) {
      carbs = input;
      success = true;
    }
    return success;
  }

  public boolean setProtein(double input) {
    boolean success = false;
    if (input > 0) {
      protein = input;
      success = true;
    }
    return success;
  }

  public boolean setName(String input) {
    name = input;
    return true;
  }

  public double getFat() {
    return fat;
  }

  public double getCarbs() {
    return carbs;
  }

  public double getProtein() {
    return protein;
  }

  public String getName() {
    return name;
  }

  public double getFatCalories() {
    return fat * 9;
  }

  public double getCarbsCalories() {
    return carbs * 4;
  }

  public double getProteinCalories() {
    return protein * 4;
  }

  public double getCalories() {
    return getFatCalories() + getCarbsCalories() + getProteinCalories();
  }

  public String DominantNutrient() {
    double fatcal = getFatCalories();
    double carbcal = getCarbsCalories();
    double procal = getProteinCalories();
    if (fatcal >= carbcal && fatcal >= procal) {
      return "fat";
    }
    if (carbcal >= procal && carbcal >= fatcal) {
      return "carbs";
    }
    if (procal >= carbcal && procal >= fatcal) {
      return "protein";
    }
    return "none";
  }

  // returns the content string " content of [food name] [measurement uint]". 
  // Example: " content of apple (g)"
  public String getContentString(String MeasurementString) {
    String ret = String.format(" content of %1$s %2$s: ", name, MeasurementString);
    return ret;
  }


  
  public double validatePositiveDouble(String SysMsg) {
    Scanner userInput = new Scanner(System.in);
    double ret = 0;
    System.out.print(SysMsg);
    boolean valid = false;
    do {
      try {
        ret = userInput.nextDouble();
        if (ret>=0) {
          valid = true;
        } else {
          System.out.println("[!] Invalid: Enter a Positive Number.");
          return validatePositiveDouble(SysMsg);
        }
      }
      catch (java.util.InputMismatchException e) {
        System.out.println("[!] Invalid: Enter a number.");
        return validatePositiveDouble(SysMsg);
      }
    }
    while (!valid);
    return ret;
  }


  

  public void getInfoInput(String thisDesc) {
    
    Scanner userInput = new Scanner(System.in);
    // name input
    System.out.print("\nName of "+thisDesc+": ");
    String nameOfFood = userInput.next();
    

    setName(nameOfFood);
    
    // fat input
    double fatContent = validatePositiveDouble("\nFat" + getContentString("(g)"));
    // carb input
    double carbContent = validatePositiveDouble("\nCarb" + getContentString("(g)"));
    // protein input
    double proteinContent = validatePositiveDouble("\nProtein"+ getContentString("(g)"));

    setFat(fatContent);
    setCarbs(carbContent);
    setProtein(proteinContent);
  }

  public void getInfoInput() {
    getInfoInput(desc);
  }

  
  public void getConfirmationReport(int numServings) {
    System.out.println("-- Fat: "+(getFat()*numServings)+" g");
    System.out.println("-- Carbs: "+(getCarbs()*numServings)+" g");
    System.out.println("-- Protein: "+(getProtein()*numServings)+" g");
  }

  
}