package com.example.myapplicationnote.Interface;

import androidx.cardview.widget.CardView;

import com.example.myapplicationnote.Model.Notes;

public interface NotesClickListener {
    void onClick(Notes notes);
    void onLongPress(Notes notes, CardView cardView);
}
