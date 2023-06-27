package com.example.cooklisttest;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class Recipe implements Parcelable {
    private String title; // Tytuł przepisu
    private ArrayList<String> products; // Lista produktów
    private ArrayList<String> steps; // Lista kroków

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

    // Implementacja interfejsu Parcelable

    // Metoda tworząca obiekt Recipe na podstawie obiektu Parcel
    protected Recipe(Parcel in) {
        title = in.readString();
        products = in.createStringArrayList();
        steps = in.createStringArrayList();
    }

    // Metoda do zapisu danych obiektu do obiektu Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeStringList(products);
        dest.writeStringList(steps);
    }

    // Opisuje specjalne obiekty zawierające FileDescriptor, które nie są obsługiwane
    @Override
    public int describeContents() {
        return 0;
    }

    // Tworzy obiekt Recipe z obiektu Parcel
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
}
