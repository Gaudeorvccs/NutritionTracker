import java.util.ArrayList;
import java.io.Serializable;
import java.util.Scanner;

class Diet implements Serializable {

  private ArrayList<Serving> foodServings = new ArrayList<Serving>();
  private String name = "";
  private boolean saved = false;

  public ArrayList<Serving> getServings() {
    return foodServings;
  }

  public boolean addServing(Serving input) {
    boolean success = true;
    this.foodServings.add(input);
    return success;
  }

  public boolean rmServing( int index ) {
    boolean success = false;
    if (index >= 0 && index < foodServings.size() ) {
      foodServings.remove(index);
      success = true;
    }
    return success;
  }

  public boolean setName(String input) {
    boolean success = false;
    if (name == "") {
      success = true;
      name = input;
    }
    return success;
  }

  public String getName() {
    return name;
  }

  public void markSaved() {
    saved = true;
  }

  public double getFatCalories() {
    double fatcal = 0;
    for (int i=0;i<foodServings.size();i++) {
      fatcal += foodServings.get(i).getFatCalories();
    }
    return fatcal;
  }
  public double getCarbsCalories() {
    double carbcal = 0;
    for (int i=0;i<foodServings.size();i++) {
      carbcal += foodServings.get(i).getCarbsCalories();
    }
    return carbcal;
  }
  public double getProteinCalories() {
    double proteincal = 0;
    for (int i=0;i<foodServings.size();i++) {
      proteincal += foodServings.get(i).getProteinCalories();
    }
    return proteincal;
  }
  
  public double getCalories() {
    return getFatCalories()+getCarbsCalories()+getProteinCalories();
  }


  
  // ui to add a serving to the class
  public void addServingUI() {
    Scanner userInput = new Scanner(System.in);

    Serving thisServing = new Serving();
    FoodItem thisFood;

    
    System.out.println("-- Serving Entry --");
    // food type input
    String SysMsg ="\nChoose Food Type:\nAvailable types:\n   1) Generic Food Item\n    2) Vegetable\n     3) Leafy Greens\n     4) Root Vegetables\n>> ";

    ArrayList<String> AccIn = new ArrayList<String>();
    AccIn.add("1"); AccIn.add("2"); AccIn.add("3"); AccIn.add("4");
    String Choice = NutritionTracker.getStringUntilAcceptable(AccIn, SysMsg);

    int intChoice = Integer.parseInt(Choice);
    
    switch (intChoice) {
      case 2:
        thisFood = new Vegetable();
        break;
      case 3:
        thisFood = new LeafyGreens();
        break;
      case 4:
        thisFood = new RootVegetables();
        break;
      default:
        thisFood = new FoodItem();
    }
    thisServing.setFood(thisFood);
    thisServing.getInfoInput();
    
    addServing(thisServing);
    thisServing.getConfirmationReport();
  }

  // ui list servings added in diet
  public void listServings() {
    // System.out.println("we got here");
    // System.out.println(foodServings.size());
    for (int i=0;i<foodServings.size();i++) {
      Serving Ser = foodServings.get(i);
      System.out.println("=================================");
      System.out.println("Serving "+(i+1)+": ");
      Ser.getConfirmationReport();
    }
  }
}