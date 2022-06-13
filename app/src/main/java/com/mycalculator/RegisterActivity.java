package com.mycalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class RegisterActivity extends AppCompatActivity {

    TextView registerPage;
    EditText email, password;
    Button register;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        MultiDex.install(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        registerPage = (TextView) findViewById(R.id.registerPage);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        register = (Button) findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString();
                String Password = password.getText().toString();

                if (!Email.endsWith(".com") || !Email.contains("@"))
                    Toasty.warning(RegisterActivity.this, "Invalid Email type", Toast.LENGTH_LONG).show();

                else if (password.getText().length()<8)
                    Toasty.warning(RegisterActivity.this, "Password must be at least 8 characters", Toast.LENGTH_LONG).show();

                else if(!TextUtils.isEmpty(Email) || !TextUtils.isEmpty(Password)){
                    // String uuid = UUID.randomUUID().toString();
                    // info.put("UUID", uuid);
                   // Model model = new Model(Email, Password);
                    firebaseAuth.createUserWithEmailAndPassword(Email,Password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        updatepassword(firebaseAuth.getCurrentUser().getEmail(), Password);
                                        Toasty.success(RegisterActivity.this, "Done", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toasty.error(RegisterActivity.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                }
                else
                    Toasty.error(RegisterActivity.this, "Error! Enter value first", Toast.LENGTH_LONG).show();
            }

            private void updatepassword(String Email, String Password) {
                Map<String, Object> user = new HashMap<>();
                user.put("Email", Email);
                user.put("Password", Password);
                firebaseFirestore.collection("Password")
                        .document(Email)
                        .set(user)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
            }
        });
        email.addTextChangedListener(Register);
        password.addTextChangedListener(Register);
    }
    TextWatcher Register = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
        public void afterTextChanged(Editable s) {
            String s1 = email.getText().toString().trim();
            String s2 = password.getText().toString().trim();
            register.setEnabled(!s1.isEmpty() && !s2.isEmpty());
        }
    };
    public void sendRegistrationEmail(final String name, final String emails){
        BackgroundMail.newBuilder(this)
                .withUsername("username@gmail.com")
                .withPassword("password12345")
                .withMailto("to-email@gmail.com")
          //      .withMailCc("cc-email@gmail.com")
         //       .withMailBcc("bcc-email@gmail.com")
                .withType(BackgroundMail.TYPE_PLAIN)
                .withSubject("this is the subject")
                .withBody("this is the body")
                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                    @Override
                    public void onSuccess() {
                        //do some magic
                    }
                })
                .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                    @Override
                    public void onFail() {
                        //do some magic
                    }
                })
                .send();
    }
}