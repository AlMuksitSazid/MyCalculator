package com.mycalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import es.dmoral.toasty.Toasty;

public class ForgetPassword extends AppCompatActivity {
    EditText email;
    Button submit;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        firebaseAuth = FirebaseAuth.getInstance();
        email = (EditText) findViewById(R.id.email);
        submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail = email.getText().toString();
                if(TextUtils.isEmpty(useremail)){
                    Toasty.info(getApplicationContext(),"Enter value first", Toasty.LENGTH_SHORT, true).show();
                    return;
                }
                else if (!useremail.endsWith(".com") || !useremail.contains("@")) {
                    Toasty.warning(getApplicationContext(), "Invalid Email type", Toasty.LENGTH_SHORT, true).show();
                    return;
                }
                else {
                    firebaseAuth.confirmPasswordReset(useremail,"12345678")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toasty.success(getApplicationContext(),"Done",Toasty.LENGTH_SHORT,true).show();
                                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toasty.error(getApplicationContext(), "Failed: "+e.getMessage(), Toasty.LENGTH_LONG, true).show();
                                    return;
                                }
                            });
                }
            }
        });
        email.addTextChangedListener(textWatcher);
    }
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String Email = email.getText().toString();
            submit.setEnabled(!Email.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}