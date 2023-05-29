package domainObject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Set;

public class RecipeConverter {
    public ArrayList<Recipe> convertRecipes(JSONArray recipeJsons) {
        ArrayList<Recipe> recipes = new ArrayList<>();

        for (Object recipeJson : recipeJsons) {
            recipes.add(convertRecipe((JSONObject)recipeJson));
        }

        return recipes;
    }

    public Recipe convertRecipe(JSONObject recipeJson) {
        Recipe.RecipeBuilder rb = Recipe.builder();
        Nutrient.NutrientBuilder nb = Nutrient.builder();
        Manual[] manuals = new Manual[20];
        for (int i=0; i<manuals.length; i++) manuals[i] = new Manual();

        Set<String> keySet = recipeJson.keySet();
        for (String key : keySet) {
            Object data = recipeJson.get(key);
            if (key.contains("SEQ")) {
                data = Integer.parseInt(("0" + data).replaceAll("[^0-9]", ""));
            }
            else if (key.contains("INFO")) {
                data = Double.parseDouble(("0" + data).replaceAll("[^0-9.]", ""));
            }

            // set fields
            String setterName = convert2CamelCase(key);
            dataBinding(rb, setterName, data);
            dataBinding(nb, setterName, data);
            manualBinding(manuals, key, data);
        }

        return rb.infoNutri(nb.build())
                .manuals(manuals)
                .build();
    }

    public void dataBinding(Object builder, String setterName, Object data) {
        Method[] methods = builder.getClass().getMethods();

        for (Method method : methods) {
            if (setterName.equals(method.getName())) {
                try {
                    method.invoke(builder, data);
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void manualBinding(Manual[] manuals, String key, Object data) {
        if (key.contains("MANUAL")) {
            int idx = Integer.parseInt(key.replaceAll("[^0-9]", "")) - 1;
            if (key.contains("IMG")) {
                manuals[idx].setImg((String) data);
            }
            else {
                manuals[idx].setText((String) data);
            }
        }
    }

    public static String convert2CamelCase(String underScore) {
        if (underScore.indexOf('_') < 0 && Character.isLowerCase(underScore.charAt(0))) {
            return underScore;
        }
        StringBuilder result = new StringBuilder();
        boolean nextUpper = false;
        int len = underScore.length();

        for (int i = 0; i < len; i++) {
            char currentChar = underScore.charAt(i);
            if (currentChar == '_') {
                nextUpper = true;
            } else {
                if (nextUpper) {
                    result.append(Character.toUpperCase(currentChar));
                    nextUpper = false;
                } else {
                    result.append(Character.toLowerCase(currentChar));
                }
            }
        }
        return result.toString();
    }
}
