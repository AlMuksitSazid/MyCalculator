package com.mycalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

import android.app.ProgressDialog;
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

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button signIn;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        signIn = (Button) findViewById(R.id.signIn);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail = email.getText().toString();
                String userpassword = password.getText().toString();

                if(TextUtils.isEmpty(useremail) || !TextUtils.isEmpty(userpassword)){
                    Toasty.info(getApplicationContext(),"Enter value first", Toasty.LENGTH_SHORT, true).show();
                    return;
                }
                else if (!useremail.endsWith(".com") || !useremail.contains("@")){
                    Toasty.warning(getApplicationContext(),"Invalid Email type", Toasty.LENGTH_SHORT,true).show();
                    return;
                }
                else if (userpassword.length()<7) {
                    Toasty.warning(getApplicationContext(),"Password must be at least 8 characters", Toasty.LENGTH_LONG,true).show();
                    return;
                }
                else {
                    ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setTitle("Loading...");
                    progressDialog.setMessage("Please wait");
                    progressDialog.show();
                    firebaseFirestore.collection("Users")
                            .document(useremail)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()){
                                        if (task.getResult().exists()){
                                            String user_password = task.getResult().getString("password");
                                            if (userpassword.equals(user_password)){
                                                firebaseAuth.signInWithEmailAndPassword(useremail, user_password)
                                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                                if (task.isSuccessful()){
                                                                    progressDialog.dismiss();
                                                                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                                                }
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toasty.error(LoginActivity.this,""+e.getMessage(),Toasty.LENGTH_LONG,true).show();
                                                            }
                                                        });
                                            }
                                        }
                                        else{
                                            progressDialog.dismiss();
                                            Toasty.error(getApplicationContext(),"User not found",Toasty.LENGTH_SHORT,true).show();
                                        }
                                    }
                                    else{
                                        progressDialog.dismiss();
                                        Toasty.error(getApplicationContext(),"User not found",Toasty.LENGTH_SHORT,true).show();
                                    }
                                }
                            });
                }
            }
        });

    }

    public void register(View view) {
        startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
    }
}