package com.mycalculator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Retrive extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    List<Model>getList;
    DocumentReference documentReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrive);
        recyclerView = recyclerView.findViewById(R.id.recycler);
        firebaseFirestore = FirebaseFirestore.getInstance();
        getList = new ArrayList<>();
        adapter Adapter = new adapter(getList);
        documentReference=FirebaseFirestore.collection("Datastore").document();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Retrive.this));
        recyclerView.setAdapter(Adapter);
        receiveData();
    }

    private void receiveData() {
        FirebaseFirestore.collection("Datastore").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(DocumentChange dc : value.getDocumentChanges()){
                    if (dc.getType() == DocumentChange.Type.ADDED){
                        Model model = dc.getDocument().toObject(Model.class);
                        getList.add(model);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}