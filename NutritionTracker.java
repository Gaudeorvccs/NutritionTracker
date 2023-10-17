import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;


class NutritionTracker {
  // helper functions at the top

  // saves the Diet to a file
  private static void saveDiet(Diet input) throws IOException, ClassNotFoundException {
    FileOutputStream fos = new FileOutputStream("./" + input.getName()+".ntr");
    ObjectOutputStream oos = new ObjectOutputStream(fos);
    oos.writeObject(input);
    oos.close();
  }

  // loads the Diet from the file filePath if present.
  private static Diet loadDiet(String filePath) throws IOException, ClassNotFoundException {
    FileInputStream fis = new FileInputStream(filePath);
    ObjectInputStream ois = new ObjectInputStream(fis);
    Diet ret = (Diet)ois.readObject();
    ois.close();
    return ret;
  }


  // Asks user for input until they enter a valid input, given as an ArrayList of strings. 
  // Shows an error and repeats SystemMsg if input is invalid.
  public static String getStringUntilAcceptable(ArrayList<String> acceptableInputs, String SystemMsg) {
    Scanner userInput = new Scanner(System.in);
    String UserInputString = "";
    do {
      System.out.print("\n"+SystemMsg);
      UserInputString = userInput.next();
      if (acceptableInputs.contains(UserInputString) == false) {
        System.out.println("Invalid Input. Please enter one of the choices.");
      }
    }
    while (acceptableInputs.contains(UserInputString) == false);
  
    return UserInputString;
  }

  // gets a diet filepath from the user. Asks again if it does not exist. Returns Diet object
  private static Diet getDietFromUser() {
    Scanner userInput = new Scanner(System.in);
    File file;
    String path;

    System.out.print("\nEnter the Diet filename to load (without the extention): ");
    path = "./" + userInput.next() + ".ntr";
    file = new File(path);
    if (file.exists() == false) {
      System.out.println("Error: File not found!");
    }
    try {
      return loadDiet(path);
    }
    catch (IOException e) {
      System.out.println("[!] Error: File not found!");
      return getDietFromUser();
    }
    // Insanity confirmation
    catch (ClassNotFoundException e) {
      System.out.println("[!] Error: Something's Very Wrong ... this should not be shown.");
      return getDietFromUser();
    }
  }
  

  // returns the user's valid input for the Diet menu
  private static String getNextDietAction(){
    String SysMsg = "Enter: \n\n (e) <- Exit Back to Main Menu without saving \n (s) Save Diet and Exit to Main Menu \n (a) Add Serving \n (l) List Servings \n (d) Delete Serving by ID \n (c) Calculate Diet Calories \n>> ";
    ArrayList<String> AccIn = new ArrayList<String>();
    AccIn.add("a"); AccIn.add("s"); AccIn.add("l"); AccIn.add("d"); AccIn.add("c"); AccIn.add("e");
    return getStringUntilAcceptable(AccIn, SysMsg);
  }
  
  // returns the user's valid input for the Main menu
  private static String getNextMainLoopAction(){
    String SysMsg = "Welcome to Gaudeor's Nutrition Tracker! Please enter:\n\n (l) load diet \n (n) new diet \n (e) X Exit App \n>> ";
    ArrayList<String> AccIn = new ArrayList<String>();
    AccIn.add("l"); AccIn.add("n"); AccIn.add("e");
    return getStringUntilAcceptable(AccIn, SysMsg);
  }

  // Main loop: returns true if ready to loop again, false if ready to quit.
  private static boolean mainLoop() {
    
    Scanner userInput = new Scanner(System.in);
    
    // e: exit // n: new diet // l: load diet
    String cmd = getNextMainLoopAction(); 

    // load the Diet
    Diet sessionDiet = new Diet();
    boolean leave = false;
    switch (cmd) {
      case "e":
        leave = true;
        break;
      case "l":
        sessionDiet = getDietFromUser();
        System.out.println("Diet Loaded.");  
        sessionDiet.markSaved();
        break;
      case "n":
        System.out.print("\nEnter Diet Name: ");
        // check the name doesn't already exist!
        // String dietName;
        // File file;
        // do {
        String dietName = userInput.next();
        //   file = new File("./" + userInput.next() + ".ntr");
        //   if (file.exists()) {
        //     System.out.print("\nThat Diet already exists! Enter a different name: ");
        //   }
        // }
        // while (file.exists());
        
        sessionDiet = new Diet();
        sessionDiet.setName(dietName);
        System.out.println("Diet Created.");
        break;
    }


    // endof Load the Diet code

    if (!leave) {
      
      // loop the Diet menu until the user quits
      boolean loop = true;
      do {
        loop = dietLoop(sessionDiet);
      }
      while (loop);
  
      // mark as ready to loop again
      return true;
    }
    return false;
  }

  // Diet Loop: can modify the diet. Returns true if ready to loop again or false to quit to main menu
  private static boolean dietLoop(Diet sessionDiet) {

    // e: exit to main menu without saving 
    // s: save diet to file and exit to main menu     
    // a: add serving 
    // l: list servings 
    // d: delete servings by id   
    // c: calculate diet
    String cmd = getNextDietAction();
    boolean leave = false;

    switch (cmd) {
      case "e":
        leave = true;
        break;
      case "s":
        try {
          saveDiet(sessionDiet);
          return false;
        }
        catch (IOException e) {
          System.out.println("[!] Error: Couldn't save!");
        }
        // Insanity confirmation
        catch (ClassNotFoundException e) {
          System.out.println("[!] Error: Something's Very Wrong (2) ... this should not be shown.");
        }
        leave = true;
        break;
      case "a":
        sessionDiet.addServingUI();
        break;
      case "l":
        sessionDiet.listServings();
        break;
      case "d":
        int i = -1;
        Scanner userInput = new Scanner(System.in);
  
        System.out.print("\nEnter the index to delete: ");
        try {
          i = userInput.nextInt();
        }
        catch (java.util.InputMismatchException e) {
          System.out.println("[!] Invalid: Please try again!");
        }
        if (i > 0) {
          // the user-shown index is 1 greater than the actual index
          System.out.println("You entered: "+i);
          boolean success = sessionDiet.rmServing(i-1);
          if (!success) {
            System.out.println("[!] Invalid: Please try again!");
          }
        }
        break;
      case "c":
        System.out.println("Diet Report:");
        System.out.println("Total combined calories for all Servings: "+sessionDiet.getCalories());
        System.out.println("Fat Calories make up "+(100*(sessionDiet.getFatCalories()/sessionDiet.getCalories()))+"% of your diet.");
        System.out.println("Carb Calories make up "+(100*(sessionDiet.getCarbsCalories()/sessionDiet.getCalories()))+"% of your diet.");
        System.out.println("Protien Calories make up "+(100*(sessionDiet.getProteinCalories()/sessionDiet.getCalories()))+"% of your diet.");
        break;
    }

    
    // mark as ready to loop again or time to quit
    if (leave) {
      return false;
    } else {
      return true;
    }
  }
  
  private static void start() {
    boolean loop = true;
    do {
      loop = mainLoop();
    }
    while (loop != false);
    System.out.println("Thank you for using Gaudeor's Diet Tracker! Goodbye!");
  }
  
  public static void main(String[] args) {
    System.out.println("");
    System.out.println("");
    System.out.println("");
    System.out.println("///          Gaudeor's Diet Tracker v. 0.0.0.1.1-alpha-prerelease          \\\\\\");
    System.out.println("");
    start();
  }
}