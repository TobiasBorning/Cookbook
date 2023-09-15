package cookbook.core;

//Data class capable of storing more information
public class Ingredient { 
  String name;

  public Ingredient(String name) {
    this.name = name;
  }

  public Ingredient() {
    
  }

  public String getName() {
    return this.name;
  }

  public String toString() {
    return this.name;
  }
}
