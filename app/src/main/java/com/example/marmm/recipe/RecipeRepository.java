package com.example.marmm.recipe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by marmm on 15/03/2018.
 */

public class RecipeRepository {

    private SQLiteDatabase database;
    private final DBHelper dbHelper;
    private final String[] RECIPES_ALL_COLUMNS = {
            RecipeContract.RecipeEntry._ID,
            RecipeContract.RecipeEntry.COLUMN_NAME_NAME,
            RecipeContract.RecipeEntry.COLUMN_NAME_DESCRIPTION};

    public RecipeRepository(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void save(Recipe recipe) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RecipeContract.RecipeEntry.COLUMN_NAME_NAME, recipe.getName());
        values.put(RecipeContract.RecipeEntry.COLUMN_NAME_DESCRIPTION, recipe.getDescription());
        // Inserting Row
        db.insert(RecipeContract.RecipeEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void update(int id, Recipe recipe) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RecipeContract.RecipeEntry.COLUMN_NAME_NAME, recipe.getName());
        values.put(RecipeContract.RecipeEntry.COLUMN_NAME_DESCRIPTION, recipe.getDescription());

        db.update(RecipeContract.RecipeEntry.TABLE_NAME, values, RecipeContract.RecipeEntry._ID + "= ?", new String[]{String.valueOf(id)});
        db.close(); // Closing database connection
    }

    public void delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(RecipeContract.RecipeEntry.TABLE_NAME, RecipeContract.RecipeEntry._ID + " =?",
                new String[]{Integer.toString(id)});
        db.close();

    }

    public Cursor findAll() {
        // If we have not yet opened the database, open it
        if (database == null) {
            database = dbHelper.getReadableDatabase();
        }

        return database.query(RecipeContract.RecipeEntry.TABLE_NAME, RECIPES_ALL_COLUMNS, null, null, null, null, null);
    }
}
