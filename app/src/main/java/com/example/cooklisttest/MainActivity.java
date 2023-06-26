package com.example.cooklisttest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "CookListPrefs";
    private ArrayList<Recipe> savedRecipes;
    private Button addRecipeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addRecipeButton = findViewById(R.id.add_recipe_button);
        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecipeCreatorActivity.class);
                startActivity(intent);
            }
        });

        savedRecipes = loadRecipesFromPreferences(this);
        displaySavedRecipes();
    }

    public static ArrayList<Recipe> loadRecipesFromPreferences(Context context) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String jsonRecipes = sharedPrefs.getString("recipes", "");

        Gson gson = new Gson();
        Recipe[] recipeArray = gson.fromJson(jsonRecipes, Recipe[].class);
        ArrayList<Recipe> recipes = new ArrayList<>();

        if (recipeArray != null) {
            for (Recipe recipe : recipeArray) {
                recipes.add(recipe);
            }
        }

        return recipes;
    }

    private void displaySavedRecipes() {
        LinearLayout recipeContainer = findViewById(R.id.recipe_container);

        for (final Recipe recipe : savedRecipes) {
            Button recipeButton = new Button(this);
            recipeButton.setText(recipe.getTitle());

            recipeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openRecipeEditor(recipe);
                }
            });

            recipeContainer.addView(recipeButton);
        }
    }

    private void openRecipeEditor(Recipe recipe) {
        Intent intent = new Intent(MainActivity.this, RecipeEditorActivity.class);
        intent.putExtra("recipe", recipe);
        startActivity(intent);
    }
}


