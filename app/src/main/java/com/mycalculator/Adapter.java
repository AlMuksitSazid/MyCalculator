package com.mycalculator;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.core.View;
import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.myview> {
    public List<Model>data;
    FirebaseFirestore firebaseFirestore;

    public adapter(List<Model> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public adapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_text_view,parent,false);
        View view = View.LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_text_view, parent,false);
        return new adapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter.myview holder, int position) {
    holder.mytextview.setText(data.get(position).getFirst());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

     class myview extends RecyclerView.ViewHolder {
        TextView mytextview;
        public myview(@NonNull View itemView){
            super(View itemView);
            mytextview=itemView.findViewById(R.id.mytextview);
        }
    }
}
