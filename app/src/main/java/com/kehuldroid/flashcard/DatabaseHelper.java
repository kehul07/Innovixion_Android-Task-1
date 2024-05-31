package com.kehuldroid.flashcard;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "flashcards.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_DECKS = "decks";
    public static final String COLUMN_DECK_ID = "id";
    public static final String COLUMN_DECK_NAME = "name";

    public static final String TABLE_FLASHCARDS = "flashcards";
    public static final String COLUMN_FLASHCARD_ID = "id";
    public static final String COLUMN_FLASHCARD_QUESTION = "question";
    public static final String COLUMN_FLASHCARD_ANSWER = "answer";
    public static final String COLUMN_FLASHCARD_DECK_ID = "deck_id";

    private static final String CREATE_TABLE_DECKS = "CREATE TABLE " + TABLE_DECKS + " (" +
            COLUMN_DECK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_DECK_NAME + " TEXT)";

    private static final String CREATE_TABLE_FLASHCARDS = "CREATE TABLE " + TABLE_FLASHCARDS + " (" +
            COLUMN_FLASHCARD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_FLASHCARD_QUESTION + " TEXT, " +
            COLUMN_FLASHCARD_ANSWER + " TEXT, " +
            COLUMN_FLASHCARD_DECK_ID + " INTEGER, " +
            "FOREIGN KEY(" + COLUMN_FLASHCARD_DECK_ID + ") REFERENCES " + TABLE_DECKS + "(" + COLUMN_DECK_ID + "))";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_DECKS);
        db.execSQL(CREATE_TABLE_FLASHCARDS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DECKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLASHCARDS);
        onCreate(db);
    }

    public long insertDeck(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DECK_NAME, name);
        return db.insert(TABLE_DECKS, null, values);
    }

    public List<Deck> getAllDecks() {
        List<Deck> decks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_DECKS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Deck deck = new Deck(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DECK_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DECK_NAME))
                );
                decks.add(deck);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return decks;
    }

    public long insertFlashcard(String question, String answer, int deckId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FLASHCARD_QUESTION, question);
        values.put(COLUMN_FLASHCARD_ANSWER, answer);
        values.put(COLUMN_FLASHCARD_DECK_ID, deckId);
        return db.insert(TABLE_FLASHCARDS, null, values);
    }

    public List<Flashcard> getFlashcardsForDeck(int deckId) {
        List<Flashcard> flashcards = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FLASHCARDS, null, COLUMN_FLASHCARD_DECK_ID + " = ?", new String[]{String.valueOf(deckId)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Flashcard flashcard = new Flashcard(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FLASHCARD_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FLASHCARD_QUESTION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FLASHCARD_ANSWER)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FLASHCARD_DECK_ID))
                );
                flashcards.add(flashcard);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return flashcards;
    }

}