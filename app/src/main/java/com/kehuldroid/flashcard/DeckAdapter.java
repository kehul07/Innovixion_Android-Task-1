package com.kehuldroid.flashcard;

import static com.kehuldroid.flashcard.DatabaseHelper.COLUMN_DECK_NAME;
import static com.kehuldroid.flashcard.DatabaseHelper.TABLE_DECKS;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DeckAdapter extends RecyclerView.Adapter<DeckAdapter.DeckViewHolder> {
    List<Deck> deckList = new ArrayList<>();

    public DeckAdapter(List<Deck> deckList) {
        this.deckList = deckList;
    }

    @NonNull
    @Override
    public DeckAdapter.DeckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deck_item,parent,false);
        return new DeckViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeckAdapter.DeckViewHolder holder, int position) {
        Deck d = deckList.get(position);
        holder.deckName.setText(d.getName());

        int columnIndex = position % 6;
        switch (columnIndex) {
            case 0:
                holder.itemView.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorColumn1)));
                break;
            case 1:
                holder.itemView.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorColumn2)));
                break;
            case 2:
                holder.itemView.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorColumn3)));
                break;
            case 3:
                holder.itemView.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorColumn4)));
                break;
            case 4:
                holder.itemView.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorColumn5)));
                break;
            case 5:
                holder.itemView.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorColumn6)));
                break;

        }
    }

    @Override
    public int getItemCount() {
        return deckList.size();
    }
    public class DeckViewHolder extends RecyclerView.ViewHolder {
        TextView deckName;
        public DeckViewHolder(@NonNull View itemView) {
            super(itemView);
            deckName = itemView.findViewById(R.id.deckName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view.getContext(),DeckActivity.class);
                    Deck d = deckList.get(getAdapterPosition());
                    i.putExtra("deckId",d.getId());
                    i.putExtra("name",d.getName());
                    view.getContext().startActivity(i);
                }
            });
        }

    }
}
