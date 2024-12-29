package com.example.myapplicationnote.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationnote.Interface.NotesClickListener;
import com.example.myapplicationnote.Model.Notes;
import com.example.myapplicationnote.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NoteListAdapter extends RecyclerView.Adapter<NotesViewHolder> {
    Context context;
    List<Notes> notesList;
    NotesClickListener listener;

    public NoteListAdapter(Context context, List<Notes> notesList, NotesClickListener listener) {
        this.context = context;
        this.notesList = notesList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.note_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
     holder.titleTxt.setText(notesList.get(position).getTitle());
     holder.notesTxt.setText(notesList.get(position).getNotes());
     holder.dateTxt.setText(notesList.get(position).getDate());
     holder.dateTxt.setSelected(true);

     if (notesList.get(position).getPinned() == true){
         holder.imageView.setImageResource(R.drawable.pin);
     }else {
         holder.imageView.setImageResource(0);
      }

     int color_code = getRandomColor();
     holder.cardView.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code));

     holder.cardView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
           listener.onClick(notesList.get(holder.getAdapterPosition()));
         }
     });

     holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
         @Override
         public boolean onLongClick(View view) {
             listener.onLongPress(notesList.get(holder.getAdapterPosition()),holder.cardView);
             return true;
         }
     });
    }

    private int getRandomColor(){
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.color10);
        colorCode.add(R.color.color13);
        colorCode.add(R.color.color4);
        colorCode.add(R.color.color2);
        colorCode.add(R.color.color3);
        colorCode.add(R.color.blue_normal_deep);
        colorCode.add(R.color.blue_white_normal);

        Random random = new Random();
        int random_color = random.nextInt(colorCode.size());
        return colorCode.get(random_color);

    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public void filterList(List<Notes> filterList){
        notesList=filterList;
        notifyDataSetChanged();
    }
}


class NotesViewHolder extends RecyclerView.ViewHolder{
    CardView cardView;
    TextView notesTxt,titleTxt,dateTxt;
    ImageView imageView;
    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.note_container);
        notesTxt = itemView.findViewById(R.id.notesTxt);
        titleTxt = itemView.findViewById(R.id.titleTxt);
        dateTxt = itemView.findViewById(R.id.dateTxt);
        imageView = itemView.findViewById(R.id.pinned);
    }
}
