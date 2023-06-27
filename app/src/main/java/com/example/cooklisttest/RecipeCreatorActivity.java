package com.example.cooklisttest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import java.util.ArrayList;

public class RecipeCreatorActivity extends AppCompatActivity {
    private ArrayList<String> products; // Lista do przechowywania produktów
    private ArrayList<String> steps; // Lista do przechowywania kroków
    private EditText titleEditText; // Pole tekstowe do wprowadzania tytułu przepisu
    private EditText productEditText; // Pole tekstowe do wprowadzania produktu
    private EditText stepEditText; // Pole tekstowe do wprowadzania kroku
    private Button addProductButton; // Przycisk do dodawania produktu
    private Button addStepButton; // Przycisk do dodawania kroku
    private Button saveRecipeButton; // Przycisk do zapisywania przepisu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_creator);

        // Inicjalizacja pól tekstowych i przycisków
        titleEditText = findViewById(R.id.title_edit_text);
        productEditText = findViewById(R.id.product_edit_text);
        stepEditText = findViewById(R.id.step_edit_text);
        addProductButton = findViewById(R.id.add_product_button);
        addStepButton = findViewById(R.id.add_step_button);
        saveRecipeButton = findViewById(R.id.save_recipe_button);

        // Inicjalizacja list
        products = new ArrayList<>();
        steps = new ArrayList<>();

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String product = productEditText.getText().toString(); // Pobranie tekstu z pola tekstowego produktu
                if (!product.isEmpty()) { // Sprawdzenie, czy produkt nie jest pusty
                    products.add(product); // Dodanie produktu do listy produktów
                    productEditText.setText(""); // Wyczyszczenie pola tekstowego produktu
                }
            }
        });

        addStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String step = stepEditText.getText().toString(); // Pobranie tekstu z pola tekstowego kroku
                if (!step.isEmpty()) { // Sprawdzenie, czy krok nie jest pusty
                    steps.add(step); // Dodanie kroku do listy kroków
                    stepEditText.setText(""); // Wyczyszczenie pola tekstowego kroku
                }
            }
        });

        saveRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString(); // Pobranie tekstu z pola tekstowego tytułu
                if (!title.isEmpty() && !products.isEmpty() && !steps.isEmpty()) { // Sprawdzenie, czy tytuł, produkty i kroki nie są puste
                    Recipe recipe = new Recipe(title, products, steps); // Utworzenie nowego obiektu Recipe z podanym tytułem, produktami i krokami
                    saveRecipe(recipe); // Zapisanie przepisu
                    finish(); // Zakończenie aktywności
                }
            }
        });
    }

    // Metoda do zapisywania przepisu w SharedPreferences
    private void saveRecipe(Recipe recipe) {
        SharedPreferences sharedPrefs = getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE); // Pobranie obiektu SharedPreferences
        SharedPreferences.Editor editor = sharedPrefs.edit(); // Pobranie edytora SharedPreferences

        ArrayList<Recipe> savedRecipes = MainActivity.loadRecipesFromPreferences(this); // Wczytanie wcześniej zapisanych przepisów z SharedPreferences
        savedRecipes.add(recipe); // Dodanie nowego przepisu do listy zapisanych przepisów

        Gson gson = new Gson(); // Utworzenie nowego obiektu Gson
        String jsonRecipes = gson.toJson(savedRecipes); // Konwersja listy zapisanych przepisów do formatu JSON
        editor.putString("recipes", jsonRecipes); // Zapisanie ciągu JSON w SharedPreferences pod kluczem "recipes"
        editor.apply(); // Zastosowanie wprowadzonych zmian
    }
}
