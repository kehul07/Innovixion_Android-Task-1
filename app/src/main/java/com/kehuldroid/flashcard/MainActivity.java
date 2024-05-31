package com.kehuldroid.flashcard;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView deckRv;
    DeckAdapter da;
    List<Deck> deckList ;
    ImageView fb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deckRv = findViewById(R.id.deckRecyclerView);
        fb = findViewById(R.id.fabAddDeck);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this , CreateDeckActivity.class);
                startActivityForResult(i,1);
            }
        });

        DatabaseHelper db = new DatabaseHelper(this);
        deckList = new ArrayList<>();
        deckList = db.getAllDecks();

        for(int i=0 ; i<deckList.size() ; i++){
            Log.d("item",deckList.get(i).getName());
        }


        deckRv.setLayoutManager(new GridLayoutManager(this,2));
        da = new DeckAdapter(deckList);
        deckRv.setAdapter(da);
        da.notifyDataSetChanged();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Update the deckList and notify the adapter of the change
            DatabaseHelper db = new DatabaseHelper(this);
            deckList.clear();
            deckList.addAll(db.getAllDecks());
            da.notifyDataSetChanged();
            deckRv.smoothScrollToPosition(deckList.size()-1);
        }
    }

}