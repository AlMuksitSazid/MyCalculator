package com.mycalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    TextView myCalculator;
    EditText firstNumber, secondNumber;
    Button reset, sum, sub, mul, div;
    FirebaseFirestore firebaseFirestore;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MultiDex.install(this);
        firebaseFirestore = FirebaseFirestore.getInstance();
        startActivity(new Intent(getApplicationContext(), Retrive.class));

        myCalculator = (TextView) findViewById(R.id.mycalculator);
        firstNumber = (EditText) findViewById(R.id.firstnumber);
        secondNumber = (EditText) findViewById(R.id.secondnumber);
        reset = (Button) findViewById(R.id.Reset);
        sum = (Button) findViewById(R.id.Sum);
        sub = (Button) findViewById(R.id.Sub);
        mul = (Button) findViewById(R.id.Mul);
        div = (Button) findViewById(R.id.Div);

        reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myCalculator.setText("MY CALCULATOR");
                firstNumber.setText("");
                secondNumber.setText("");
                Toasty.info(MainActivity.this, "Reset done", Toast.LENGTH_SHORT).show();
            }
        });

        sum.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String first = firstNumber.getText().toString();
                String second = secondNumber.getText().toString();

                if(!TextUtils.isEmpty(first) || !TextUtils.isEmpty(second)){
                   // String uuid = UUID.randomUUID().toString();
 /*                   Map<String, Object> history = new HashMap<>();
                    history.put("first", first);
                    history.put("second", second);  */
                   // history.put("UUID", uuid);
                    double n1 = Double.parseDouble(first);
                    double n2 = Double.parseDouble(second);
                    double sum = n1+n2;
                    myCalculator.setText(String.valueOf(sum));
                    Model model = new Model(first, second, String.valueOf(sum));
         //           history.put("sum", sum);
                    firebaseFirestore.collection("Datastore").add(model)
                                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            if(task.isSuccessful()){
                                                Toasty.success(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toasty.error(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                    }
                else
                    Toasty.error(MainActivity.this, "Error! Enter Value first", Toast.LENGTH_LONG).show();
            }
        });

        sub.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String first = firstNumber.getText().toString();
                String second = secondNumber.getText().toString();

                if(!TextUtils.isEmpty(first) || !TextUtils.isEmpty(second)) {
                    // String uuid = UUID.randomUUID().toString();
                    Map<String, Object> history = new HashMap<>();
                    history.put("first", first);
                    history.put("second", second);
                    // history.put("UUID", uuid);
                    double n1 = Double.parseDouble(first);
                    double n2 = Double.parseDouble(second);
                    double sub = n1 - n2;
                    myCalculator.setText(String.valueOf(sub));
                    history.put("sub", sub);
                    firebaseFirestore.collection("Datastore").add(history)
                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if (task.isSuccessful()) {
                                        Toasty.success(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toasty.error(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                    }
                else
                    Toasty.error(MainActivity.this, "Error! Enter Value first", Toast.LENGTH_LONG).show();
            }
        });

        mul.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String first = firstNumber.getText().toString();
                String second = secondNumber.getText().toString();

                if(!TextUtils.isEmpty(first) || !TextUtils.isEmpty(second)){
                    // String uuid = UUID.randomUUID().toString();
                    Map<String, Object> history = new HashMap<>();
                    history.put("first", first);
                    history.put("second", second);
                    // history.put("UUID", uuid);
                    double n1 = Double.parseDouble(first);
                    double n2 = Double.parseDouble(second);
                    double mul = n1*n2;
                    myCalculator.setText(String.valueOf(mul));
                    history.put("mul", mul);
                    firebaseFirestore.collection("Datastore").add(history)
                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if(task.isSuccessful()){
                                        Toasty.success(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toasty.error(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                    }
                else
                    Toasty.error(MainActivity.this, "Error! Enter Value first", Toast.LENGTH_LONG).show();
            }
        });

        div.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String first = firstNumber.getText().toString();
                String second = secondNumber.getText().toString();

                if(!TextUtils.isEmpty(first) || !TextUtils.isEmpty(second)){
                    // String uuid = UUID.randomUUID().toString();
                    Map<String, Object> history = new HashMap<>();
                    history.put("first", first);
                    history.put("second", second);
                    // history.put("UUID", uuid);
                    double n1 = Double.parseDouble(first);
                    double n2 = Double.parseDouble(second);
                        double div = n1/n2;
                        myCalculator.setText(String.valueOf(div));
                        history.put("div", div);
                        firebaseFirestore.collection("Datastore").add(history)
                                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                        if(task.isSuccessful()){
                                            Toasty.success(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toasty.error(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });

                }
                else
                    Toasty.error(MainActivity.this, "Error! Enter Value first", Toast.LENGTH_LONG).show();
            }
        });

        firstNumber.addTextChangedListener(resetText);
        secondNumber.addTextChangedListener(resetText);
    }
    TextWatcher resetText = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String s1 = firstNumber.getText().toString().trim();
            String s2 = secondNumber.getText().toString().trim();
            reset.setEnabled(!s1.isEmpty() || !s2.isEmpty());
        }
        public void afterTextChanged(Editable s) {

        }
    };
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}