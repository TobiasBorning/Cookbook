package core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Cookbook {
  private Collection<Recipie> recipies = new ArrayList<>();

  //adding and removing recipies

  public void addRecipie(Recipie recipie) {
    if (recipie == null) {
      throw new IllegalArgumentException("Invalid recipie");
    }
    recipies.add(recipie);
  }

  public void removeRecipie(Recipie recipie) {
    if (!recipies.contains(recipie)) {
      throw new IllegalArgumentException("Recipie not in cookbook");
    }
    recipies.remove(recipie);
  }

  //filter function

  public Collection<Recipie> filterRecipies(Predicate<Recipie> p) {
    return recipies.stream().filter(p).collect(Collectors.toList());
  }

}
