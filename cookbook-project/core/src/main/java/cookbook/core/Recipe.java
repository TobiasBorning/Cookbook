    package cookbook.core;

    import java.util.HashMap;
    import java.util.Map;

    public class Recipe {

    private String name;
    private Map<String, String> ingredients = new HashMap<>();
    private String originCountry;
    private String description;
    private boolean favorite = false;
    private boolean isVegan = false;
    private boolean isGlutenFree = false;
    private boolean isLactoseFree = false;

    
    // Different constructors for creating a Recipe object
    /**
     * Constructor for creating a Recipe object with name and ingredients
     *
     * @param name        The name of the Recipe.
     * @param ingredients The ingredients and their amounts as a map.
     */
    public Recipe(String name, Map<String, String> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }
    /**
    * Constructor for creating a Recipe object with name, ingredients, origincountry and description
    *
    * @param name The name of the Recipe
    * @param ingredients The ingredients and their amounts as a map.
    * @param originCountry This indicates the country from which the ingredient originates.
    * @param description The cooking-methode, how to make the recipe. 
    * @param favorite The recipe is a favorite or not.
    * @param isVegan The recipe is vegan or not.
    * @param isGlutenFree The recipe is glutenfree or not.
    * @param isLactoseFree The recipe is lactosefree or not.
    */ 
    public Recipe(String name, Map<String, String> ingredients, String originCountry, String description, boolean favorite, boolean isVegan, boolean isGlutenFree, boolean isLactoseFree){
        this.name = name;
        this.ingredients = ingredients;
        this.originCountry = originCountry;
        this.description = description;
        this.favorite = favorite;
        this.isVegan = isVegan;
        this.isGlutenFree = isGlutenFree;
        this.isLactoseFree = isLactoseFree;
    }

        /**
     * Constructor for creating a Recipe object with name, ingredients, origincountry and description
     *
     * @param name The name of the Recipe
     * @param ingredients The ingredients and their amounts as a map.
     * @param originCountry This indicates the country from which the ingredient originates.
     * @param description The cooking-methode, how to make the recipe. 
     */
    public Recipe(String name, Map<String, String> ingredients, String originCountry, String description){
        this.name = name;
        this.ingredients = ingredients;
        this.originCountry = originCountry;
        this.description = description;
    }
        /**
     * Constructor for creating a Recipe object with name, ingredients and originCountry.
     *
     * @param name The name of the Recipe.
     * @param ingredients The ingredients and their amounts as a map.
     * @param originCountry This indicates the country from which the ingredient originates.
     */
    public Recipe(String name, Map<String, String> ingredients, String originCountry){
        this.name = name;
        this.ingredients = ingredients;
        this.originCountry = originCountry;
    }

    /**
     * Constructor for creating a Recipe object with name only.
     *
     * @param name The name of the Recipe.
     */
    public Recipe(String name) {
        this.name = name;
    }

    /**
     * Empty constructor for creating a Recipe object.
     */
    public Recipe() {
    }

    /**
     * Set the name of the Recipe.
     *
     * @param name The new name for the Recipe.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the ingredients of the Recipe.
     *
     * @param ingredients The ingredients and their amounts as a map.
     */
    public void setIngredients(Map<String, String> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Get the origin country of the Recipe.
     *
     * @return The origin country of the Recipe.
     */
    public String getOriginCountry() {
        return originCountry;
    }

    /**
     * Get the description of the Recipe.
     *
     * @return The description of the Recipe.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the name of the Recipe.
     *
     * @return The name of the Recipe.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get a copy of the ingredients and their amounts as a map.
     *
     * @return A copy of the ingredients map.
     */
    public Map<String, String> getIngredients() {
        return ingredients;
    }

    /**
     * Set the origin country of the Recipe.
     *
     * @param originCountry The new origin country for the Recipe.
     */
    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    /**
     * Set the description of the Recipe.
     *
     * @param description The new description for the Recipe.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Add an ingredient to the Recipe.
     *
     * @param ingredient The name of the ingredient.
     * @param amount     The amount of the ingredient.
     * @throws IllegalArgumentException If the ingredient is already in the map.
     */
    public void addIngredient(String ingredient, String amount) {
        if (ingredients.keySet().contains(ingredient)) {
            throw new IllegalArgumentException("Ingredient already in map");
        }
        ingredients.put(ingredient, amount);
    }

    /**
     * Remove an ingredient from the Recipe.
     *
     * @param ingredient The name of the ingredient to remove.
     * @throws IllegalArgumentException If the ingredient is not in the map.
     */
    public void removeIngredient(String ingredient) {
        if (!ingredients.keySet().contains(ingredient)) {
            throw new IllegalArgumentException("Ingredient not in map");
        }
        ingredients.remove(ingredient);
        System.out.println(ingredients + ": " + ingredients.size());
    }

    // TODO add javadoc
    public boolean isFavorite() {
        return favorite;
    }
    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
    public boolean isVegan() {
        return isVegan;
    }
    public void setVegan(boolean isVegan) {
        this.isVegan = isVegan;
    }
    public boolean isGlutenFree() {
        return isGlutenFree;
    }
    public void setGlutenFree(boolean isGlutenFree) {
        this.isGlutenFree = isGlutenFree;
    }
    public boolean isLactoseFree() {
        return isLactoseFree;
    }
    public void setLactoseFree(boolean isLactoseFree) {
        this.isLactoseFree = isLactoseFree;
    }
}
