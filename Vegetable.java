class Vegetable extends FoodItem {

  public String desc = "Vegetable";
  private double fiber = 0.0;
  public boolean setFiber(double fiberinput) {
    boolean ret = true;
    fiber = fiberinput;
    return ret;
  }
  public double getFiber() {
    return fiber;
  }
  public void getInfoInput(String thisDesc) {
    super.getInfoInput(thisDesc);
    // fat input
    double fiberContent = validatePositiveDouble("\nFiber" + getContentString("(g)"));
    
    setFiber(fiberContent);
  }
  public void getInfoInput() {
    getInfoInput(desc);
  }
  public void getConfirmationReport(int numServings) {
    super.getConfirmationReport(numServings);
    System.out.println("-- Fiber: "+(getFiber()*numServings)+" g");
  }
}