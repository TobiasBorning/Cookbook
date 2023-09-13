package core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Cookbook {
  private Collection<Recipe> recipies = new ArrayList<>();

  //adding and removing recipies

  public void addRecipie(Recipe recipie) {
    if (recipie == null) {
      throw new IllegalArgumentException("Invalid recipie");
    }
    recipies.add(recipie);
  }

  public void removeRecipie(Recipe recipie) {
    if (!recipies.contains(recipie)) {
      throw new IllegalArgumentException("Recipie not in cookbook");
    }
    recipies.remove(recipie);
  }

  //filter function

  public Collection<Recipe> filterRecipies(Predicate<Recipe> p) {
    return recipies.stream().filter(p).collect(Collectors.toList());
  }


  public static void main(String[] args) {
  }

}
