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
    private ArrayList<String> products;
    private ArrayList<String> steps;
    private EditText titleEditText;
    private EditText productEditText;
    private EditText stepEditText;
    private Button addProductButton;
    private Button addStepButton;
    private Button saveRecipeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_creator);

        titleEditText = findViewById(R.id.title_edit_text);
        productEditText = findViewById(R.id.product_edit_text);
        stepEditText = findViewById(R.id.step_edit_text);
        addProductButton = findViewById(R.id.add_product_button);
        addStepButton = findViewById(R.id.add_step_button);
        saveRecipeButton = findViewById(R.id.save_recipe_button);

        products = new ArrayList<>();
        steps = new ArrayList<>();

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String product = productEditText.getText().toString();
                if (!product.isEmpty()) {
                    products.add(product);
                    productEditText.setText("");
                }
            }
        });

        addStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String step = stepEditText.getText().toString();
                if (!step.isEmpty()) {
                    steps.add(step);
                    stepEditText.setText("");
                }
            }
        });

        saveRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString();
                if (!title.isEmpty() && !products.isEmpty() && !steps.isEmpty()) {
                    Recipe recipe = new Recipe(title, products, steps);
                    saveRecipe(recipe);
                    finish();
                }
            }
        });
    }

    private void saveRecipe(Recipe recipe) {
        SharedPreferences sharedPrefs = getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();

        ArrayList<Recipe> savedRecipes = MainActivity.loadRecipesFromPreferences(this);
        savedRecipes.add(recipe);

        Gson gson = new Gson();
        String jsonRecipes = gson.toJson(savedRecipes);
        editor.putString("recipes", jsonRecipes);
        editor.apply();
    }
}
