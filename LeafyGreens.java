class LeafyGreens extends Vegetable {
  public String desc = "Leafy Greens";
  private double iron = 0.0;
  public boolean setIronContent(double ironInput) {
    boolean ret = true;
    iron = ironInput;
    return ret;
  }
  public double getIronContent() {
    return iron;
  }
  public void getInfoInput(String thisDesc) {
    super.getInfoInput(thisDesc);
    // fat input
    double ironContent = validatePositiveDouble("\nIron Content" + getContentString("(mg)"));
    
    setIronContent(ironContent);
  }
  public void getInfoInput() {
    getInfoInput(desc);
  }
  public void getConfirmationReport(int numServings) {
    super.getConfirmationReport(numServings);
    System.out.println("-- Iron Content: "+(getIronContent()*numServings)+" mg");
  }
}