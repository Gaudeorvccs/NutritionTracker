class RootVegetables extends Vegetable {
  public String desc = "Root Vegetables";
  private double vitaminC = 0.0;
  public boolean setVitaminC(double vitaminCInput) {
    boolean ret = true;
    vitaminC = vitaminCInput;
    return ret;
  }
  public double getVitaminC() {
    return vitaminC;
  }
  public void getInfoInput(String thisDesc) {
    super.getInfoInput(thisDesc);
    // vitamin C input
    double vitaminCContent = validatePositiveDouble("\nvitamin C" + getContentString("(mg)"));
    
    setVitaminC(vitaminCContent);
  }
  public void getInfoInput() {
    getInfoInput(desc);
  }
  public void getConfirmationReport(int numServings) {
    super.getConfirmationReport(numServings);
    System.out.println("-- Vitamin C: "+(getVitaminC()*numServings)+" mg");
  }
}