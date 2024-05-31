package com.kehuldroid.flashcard;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DeckActivity extends AppCompatActivity {
    RecyclerView flashRv;
    ImageView fb;

    List<Flashcard> flashcardList = new ArrayList<>();

    FlashcardAdapter fa;
    TextView title,txt;
    int deckId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck);

        flashRv = findViewById(R.id.flashcardRecyclerView);
        fb = findViewById(R.id.add);
        title = findViewById(R.id.title);
        txt = findViewById(R.id.txt);

        String Name = getIntent().getStringExtra("name");
        title.setText(Name);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        flashRv.setLayoutManager(layoutManager);

        deckId = getIntent().getIntExtra("deckId",0);

         DatabaseHelper db = new DatabaseHelper(this);

         flashcardList = db.getFlashcardsForDeck(deckId);

         fa = new FlashcardAdapter(flashcardList);
         flashRv.setAdapter(fa);
         fa.notifyDataSetChanged();

        ImageView btnPrev = findViewById(R.id.backword);
        ImageView btnNext = findViewById(R.id.forword);

        btnPrev.setOnClickListener(v -> {
            int currentPosition = layoutManager.findFirstVisibleItemPosition();
            if (currentPosition > 0) {
                flashRv.smoothScrollToPosition(currentPosition - 1);
            }
        });

        btnNext.setOnClickListener(v -> {
            int currentPosition = layoutManager.findFirstVisibleItemPosition();
            if (currentPosition < flashRv.getAdapter().getItemCount() - 1) {
                flashRv.smoothScrollToPosition(currentPosition + 1);
            }
        });

        if(flashcardList.size()==0){
            btnNext.setVisibility(View.GONE);
            btnPrev.setVisibility(View.GONE);
            txt.setVisibility(View.VISIBLE);
            flashRv.setVisibility(View.INVISIBLE);
        }else{
            btnNext.setVisibility(View.VISIBLE);
            btnPrev.setVisibility(View.VISIBLE);
            txt.setVisibility(View.GONE);
            flashRv.setVisibility(View.VISIBLE);
        }

         fb.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent i = new Intent(DeckActivity.this , CreateFlashCardActivity.class);
                 i.putExtra("deckId",deckId);
                 startActivityForResult(i,1);
             }
         });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Update the deckList and notify the adapter of the change
            DatabaseHelper db = new DatabaseHelper(this);
            flashcardList.clear();
            flashcardList.addAll(db.getFlashcardsForDeck(deckId));
            fa.notifyDataSetChanged();
            if(flashcardList.size()!=0){
                flashRv.smoothScrollToPosition(flashcardList.size()-1);
            }
        }
    }

}