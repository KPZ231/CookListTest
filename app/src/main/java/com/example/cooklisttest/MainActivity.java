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
    public static final String PREFS_NAME = "CookListPrefs"; // Nazwa SharedPreferences
    private ArrayList<Recipe> savedRecipes; // Lista przechowująca zapisane przepisy
    private Button addRecipeButton; // Przycisk do dodawania przepisu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicjalizacja przycisku do dodawania przepisu
        addRecipeButton = findViewById(R.id.add_recipe_button);
        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Przekierowanie do aktywności RecipeCreatorActivity po kliknięciu przycisku
                Intent intent = new Intent(MainActivity.this, RecipeCreatorActivity.class);
                startActivity(intent);
            }
        });

        // Wczytanie przepisów z pamięci podręcznej
        savedRecipes = loadRecipesFromPreferences(this);

        // Wyświetlenie wczytanych przepisów
        displaySavedRecipes();
    }

    // Metoda statyczna do wczytywania przepisów z pamięci podręcznej
    public static ArrayList<Recipe> loadRecipesFromPreferences(Context context) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); // Pobranie SharedPreferences
        String jsonRecipes = sharedPrefs.getString("recipes", ""); // Pobranie zapisanych przepisów w formacie JSON

        Gson gson = new Gson(); // Inicjalizacja obiektu Gson
        Recipe[] recipeArray = gson.fromJson(jsonRecipes, Recipe[].class); // Konwersja JSON na tablicę obiektów typu Recipe
        ArrayList<Recipe> recipes = new ArrayList<>(); // Lista przechowująca przepisy

        if (recipeArray != null) {
            for (Recipe recipe : recipeArray) {
                recipes.add(recipe); // Dodanie przepisów do listy
            }
        }

        return recipes; // Zwrócenie listy przepisów
    }

    // Wyświetlanie zapisanych przepisów
    private void displaySavedRecipes() {
        LinearLayout recipeContainer = findViewById(R.id.recipe_container); // Kontener, w którym będą wyświetlane przepisy

        for (final Recipe recipe : savedRecipes) {
            // Tworzenie przycisku dla każdego zapisanego przepisu
            Button recipeButton = new Button(this);
            recipeButton.setText(recipe.getTitle());

            // Obsługa kliknięcia przycisku
            recipeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Otwieranie aktywności RecipeEditorActivity w celu edycji wybranego przepisu
                    openRecipeEditor(recipe);
                }
            });

            // Dodawanie przycisku do kontenera
            recipeContainer.addView(recipeButton);
        }
    }

    // Otwieranie aktywności RecipeEditorActivity w celu edycji przepisu
    private void openRecipeEditor(Recipe recipe) {
        Intent intent = new Intent(MainActivity.this, RecipeEditorActivity.class);
        intent.putExtra("recipe", recipe); // Przekazanie wybranego przepisu do aktywności RecipeEditorActivity
        startActivity(intent);
    }
}
