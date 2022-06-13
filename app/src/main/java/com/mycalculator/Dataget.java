package com.mycalculator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;

public class Dataget extends RecyclerView.Adapter<Dataget.myview> {
    public List<Model>data;
    FirebaseFirestore firebaseFirestore;

    public Dataget(List<Model> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_text_view,parent,false);
        android.view.View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_text_view, parent,false);
        return new Dataget.myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Dataget.myview holder, int position) {
        holder.mytextview.setText(data.get(position).getFirst());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myview extends RecyclerView.ViewHolder {
        TextView mytextview;
        public myview(@NonNull View itemView){
            super(itemView);
            mytextview=itemView.findViewById(R.id.mytextview);
        }
    }
}
