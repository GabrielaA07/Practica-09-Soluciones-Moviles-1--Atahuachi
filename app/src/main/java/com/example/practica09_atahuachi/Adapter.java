package com.example.practica09_atahuachi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.LibroViewHolder> implements View.OnClickListener {
    private View.OnClickListener listener;

    List<Book> libros;
    Context context;

    Adapter(List<Book> personas, Context context){
        this.libros = personas;
        this.context = context;
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public Adapter.LibroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book,parent,false);
        LibroViewHolder lvh = new LibroViewHolder(view);

        view.setOnClickListener(this);

        return lvh;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.LibroViewHolder holder, int position) {
        holder.title.setText(libros.get(position).Title);
        holder.author.setText(libros.get(position).Author);
        holder.year.setText(libros.get(position).Year);
        holder.description.setText(libros.get(position).Description);
    }

    @Override
    public int getItemCount() {
        return libros.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }
    public static class LibroViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView title;
        TextView author;
        TextView year;
        TextView description;
        ImageView photoBook;

        LibroViewHolder(View itemView){
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cvBook);
            title = (TextView)itemView.findViewById(R.id.Title);
            author = (TextView)itemView.findViewById(R.id.Author);
            year = (TextView)itemView.findViewById(R.id.Year);
            description = (TextView)itemView.findViewById(R.id.Description);
        }
    }
}
