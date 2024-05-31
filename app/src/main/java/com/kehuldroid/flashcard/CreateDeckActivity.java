package com.kehuldroid.flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateDeckActivity extends AppCompatActivity {

    EditText deckName ;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_deck);
        deckName= findViewById(R.id.etDeckName);
        save = findViewById(R.id.btnSaveDeck);

        DatabaseHelper db = new DatabaseHelper(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = deckName.getText().toString();
                if(!TextUtils.isEmpty(Name)){
                    db.insertDeck(Name);
                    Intent resultIntent = new Intent();
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
                else{
                    Toast.makeText(CreateDeckActivity.this, "Enter Name", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}