package com.kehuldroid.flashcard;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FlashcardAdapter extends RecyclerView.Adapter<FlashcardAdapter.FlashcardViewHolder> {

    List<Flashcard> flashcardList = new ArrayList<>();

    public FlashcardAdapter(List<Flashcard> flashcardList) {
        this.flashcardList = flashcardList;
    }

    @NonNull
    @Override
    public FlashcardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flashcard_item,parent,false);
        return new FlashcardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlashcardViewHolder holder, int position) {
        Flashcard fc = flashcardList.get(position);
        holder.question.setText("Q. "+fc.getQuestion());
        holder.answer.setText(fc.getAnswer());

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
        return flashcardList.size();
    }

    public class FlashcardViewHolder extends RecyclerView.ViewHolder {
        TextView question , answer;
        public FlashcardViewHolder(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.flashcardQuestion);
            answer = itemView.findViewById(R.id.flashcardAnswer);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Flashcard f = flashcardList.get(getAdapterPosition());
//                    Intent i = new Intent(view.getContext(), FlashCardActivity.class);
//                    view.getContext().startActivity(i);
//                }
//            });
        }
    }
}
