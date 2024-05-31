package com.kehuldroid.flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateFlashCardActivity extends AppCompatActivity {
    EditText question , answer;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flash_card);

        question = findViewById(R.id.etFlashcardQuestion);
        answer = findViewById(R.id.etFlashcardAnswer);
        save=findViewById(R.id.btnSaveFlashcard);

        int deckId = getIntent().getIntExtra("deckId",0);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Question = question.getText().toString();
                String Answer = answer.getText().toString();

                if(!TextUtils.isEmpty(Question) && !TextUtils.isEmpty(Answer)){
                    DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                    db.insertFlashcard(Question,Answer,deckId);
                    Intent resultIntent = new Intent();
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }else{
                    Toast.makeText(CreateFlashCardActivity.this, "Enter Information", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}