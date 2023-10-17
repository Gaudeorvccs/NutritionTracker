import java.io.Serializable;
import java.util.Scanner;

class Serving implements Serializable {
  private FoodItem foodItem = new FoodItem();
  private int servings = -1;
  
  public boolean setFood(FoodItem input) {
    boolean success = true; // IDK what to use success for atm, but its here if I need it later
    foodItem = input;
    return success;
  }

  public boolean setNumServings(int input) {
    boolean success = false; // IDK what to use success for atm, but its here if I need it later
    if (input > 0) {
      servings = input;
      success = true;
    }
    return success;
  }

  public FoodItem getFoodItem() {
    return foodItem;
  }

  public int getServings() {
    return servings;
  }

  public double getFatCalories() {
    return foodItem.getFatCalories()*servings;
  }
  public double getCarbsCalories() {
    return foodItem.getCarbsCalories()*servings;
  }
  public double getProteinCalories() {
    return foodItem.getProteinCalories()*servings;
  }
  public double getCalories() {
    return getFatCalories()+getCarbsCalories()+getProteinCalories();
  }
  public String DominantNutrient() {
    return foodItem.DominantNutrient();
  }

  // validates that input is an int, asks again if it isn't
  private int validatePositiveInt(String SysMsg) {
    Scanner userInput = new Scanner(System.in);
    int ret = 0;
    System.out.print(SysMsg);
    boolean valid = false;
    do {
      try {
        ret = userInput.nextInt();
        if (ret>0) {
          valid = true;
        } else {
          System.out.println("[!] Invalid: Enter a Positive Integer.");
          return validatePositiveInt(SysMsg);
        }
      }
      catch (java.util.InputMismatchException e) {
        System.out.println("[!] Invalid: Enter an Integer.");
        return validatePositiveInt(SysMsg);
      }
    }
    while (!valid);
    return ret;
  }



  
  public void getConfirmationReport() {
    // Proper English, please
    String serstr = "serving";
    if (servings > 1) {
      serstr = "servings";
    }
    System.out.println("-- For "+servings+" "+serstr+" of "+foodItem.getName()+":");
    foodItem.getConfirmationReport(servings);
    System.out.println("-- ----------------------------");
    System.out.println("-- Total Calories for "+servings+" "+serstr+" of "+foodItem.getName()+": "+getCalories()+ " cal");
    System.out.println("-- Dominant Macronutrient: "+DominantNutrient());
  }


  
  // call this only after setting the FoodItem Subclass type if desired. 
  // Gets the input for the food item and the servings by the user
  public void getInfoInput() {
    // first, get the information for the type of food
    foodItem.getInfoInput();
    // Then get # servings input
    int servings = validatePositiveInt("\nEnter Number of servings of "+ foodItem.getName() +": " );
    setNumServings(servings);
  }
  
}