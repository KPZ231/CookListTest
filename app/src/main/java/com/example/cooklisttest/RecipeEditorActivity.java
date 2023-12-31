package com.example.cooklisttest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import java.util.ArrayList;

public class RecipeEditorActivity extends AppCompatActivity {
    private Recipe recipe; // Aktualnie edytowany przepis
    private EditText titleEditText; // Pole edycji tytułu przepisu
    private EditText productEditText; // Pole edycji produktu
    private EditText stepEditText; // Pole edycji kroku
    private Button addProductButton; // Przycisk dodawania produktu
    private Button addStepButton; // Przycisk dodawania kroku
    private Button saveChangesButton; // Przycisk zapisywania zmian
    private LinearLayout productContainer; // Kontener na wyświetlanie produktów
    private LinearLayout stepContainer; // Kontener na wyświetlanie kroków

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_editor);

        // Inicjalizacja elementów interfejsu użytkownika
        titleEditText = findViewById(R.id.title_edit_text);
        productEditText = findViewById(R.id.product_edit_text);
        stepEditText = findViewById(R.id.step_edit_text);
        addProductButton = findViewById(R.id.add_product_button);
        addStepButton = findViewById(R.id.add_step_button);
        saveChangesButton = findViewById(R.id.save_changes_button);
        productContainer = findViewById(R.id.product_container);
        stepContainer = findViewById(R.id.step_container);

        // Pobranie przepisu do edycji z danych przekazanych z poprzedniej aktywności
        recipe = getIntent().getParcelableExtra("recipe");

        if (recipe != null) {
            titleEditText.setText(recipe.getTitle());
            displayProducts();
            displaySteps();
        }

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String product = productEditText.getText().toString();
                if (!product.isEmpty()) {
                    recipe.getProducts().add(product);
                    productEditText.setText("");
                    displayProducts();
                }
            }
        });

        addStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String step = stepEditText.getText().toString();
                if (!step.isEmpty()) {
                    recipe.getSteps().add(step);
                    stepEditText.setText("");
                    displaySteps();
                }
            }
        });

        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTitle = titleEditText.getText().toString();
                if (!newTitle.isEmpty()) {
                    recipe.setTitle(newTitle);
                    saveRecipeChanges(recipe);
                    finish();
                }
            }
        });
    }

    // Wyświetlanie produktów w interfejsie
    private void displayProducts() {
        productContainer.removeAllViews();

        for (final String product : recipe.getProducts()) {
            final View productItem = getLayoutInflater().inflate(R.layout.item_recipe_editor, null);
            EditText productEditText = productItem.findViewById(R.id.edit_text_item);
            productEditText.setText(product);

            Button deleteButton = productItem.findViewById(R.id.delete_item_button);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recipe.getProducts().remove(product);
                    displayProducts();
                }
            });

            productContainer.addView(productItem);
        }
    }

    // Wyświetlanie kroków w interfejsie
    private void displaySteps() {
        stepContainer.removeAllViews();

        for (final String step : recipe.getSteps()) {
            final View stepItem = getLayoutInflater().inflate(R.layout.item_recipe_editor, null);
            EditText stepEditText = stepItem.findViewById(R.id.edit_text_item);
            stepEditText.setText(step);

            Button deleteButton = stepItem.findViewById(R.id.delete_item_button);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recipe.getSteps().remove(step);
                    displaySteps();
                }
            });

            stepContainer.addView(stepItem);
        }
    }

    // Zapisywanie zmian przepisu
    private void saveRecipeChanges(Recipe recipe) {
        SharedPreferences sharedPrefs = getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();

        ArrayList<Recipe> savedRecipes = MainActivity.loadRecipesFromPreferences(this);
        for (int i = 0; i < savedRecipes.size(); i++) {
            if (savedRecipes.get(i).getTitle().equals(recipe.getTitle())) {
                savedRecipes.set(i, recipe);
                break;
            }
        }

        Gson gson = new Gson();
        String jsonRecipes = gson.toJson(savedRecipes);
        editor.putString("recipes", jsonRecipes);
        editor.apply();
    }
}
