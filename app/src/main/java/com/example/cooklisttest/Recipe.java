package com.example.cooklisttest;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class Recipe implements Parcelable {
    private String title;
    private ArrayList<String> products;
    private ArrayList<String> steps;

    public Recipe(String title, ArrayList<String> products, ArrayList<String> steps) {
        this.title = title;
        this.products = products;
        this.steps = steps;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getProducts() {
        return products;
    }

    public ArrayList<String> getSteps() {
        return steps;
    }

    // Implementacja Parcelable
    // ...

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    protected Recipe(Parcel in) {
        title = in.readString();
        products = in.createStringArrayList();
        steps = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeStringList(products);
        dest.writeStringList(steps);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
