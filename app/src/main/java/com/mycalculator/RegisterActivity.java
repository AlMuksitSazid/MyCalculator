package com.mycalculator;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText fullname, email, password;
    Button register;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    Spinner spinner;
    String [] gender = {"Male", "Female", "Others"};
    String value_gender;
    ImageView profileImage;
    private int CHOOSE_IMAGE = 1;
    Uri imageUri;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        fullname = (EditText) findViewById(R.id.fullname);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        register = (Button) findViewById(R.id.register);
        profileImage = (ImageView) findViewById(R.id.profileImage);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("Image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,"Select an Image"),1);
            }
        });
        spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setOnItemClickListener((AdapterView.OnItemClickListener) getApplicationContext());
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.genderitem,gender);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userfullname = fullname.getText().toString();
                String useremail = email.getText().toString();
                String userpassword = password.getText().toString();

                if(TextUtils.isEmpty(userfullname) || TextUtils.isEmpty(useremail) || !TextUtils.isEmpty(userpassword)){
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                    builder.setTitle("Confirmation")
                            .setMessage("Do you want register?")
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    progressDialog = new ProgressDialog(getApplicationContext());
                                    progressDialog.setTitle("Loading");
                                    progressDialog.show();
                                    firebaseAuth.createUserWithEmailAndPassword(useremail,userpassword)
                                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()){
                                                        UserModel userModel = new UserModel(userfullname, useremail, userpassword, value_gender);
                                                        firebaseFirestore.collection("Users")
                                                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                .set(userModel)
                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                progressDialog.dismiss();
                                                                                                Toasty.success(getApplicationContext(),"Registered Successfully",Toasty.LENGTH_SHORT,true).show();
                                                                                                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                                                                                            }
                                                                                        });
                                                    }
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    progressDialog.dismiss();
                                                    Toasty.error(getApplicationContext(),""+e.getMessage(),Toasty.LENGTH_LONG,true).show();
                                                }
                                            });
                                }
                            }).create().show();
                }
            }
        });
    }
/*
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
*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK || data.getData()!=null){
            imageUri=data.getData();
            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                profileImage.setImageBitmap(imageBitmap);
                upload(imageUri);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void upload(Uri imageUri) {
        if(imageUri !=null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            StorageReference ref = storageReference.child("Image/"+ UUID.randomUUID().toString());
            ref.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Task<Uri>uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                    while (!uriTask.isSuccessful());
                                    final Uri downloadUri = uriTask.getResult();

                                    if (uriTask.isSuccessful()){
                                        String downloadImageUri = downloadUri.toString();
                                        // Image uploaded successfully
                                        // Dismiss dialog
                                        progressDialog.dismiss();
                                        Toasty.success(RegisterActivity.this, "Image Uploaded!!", Toast.LENGTH_SHORT,true).show();
                                    }
                                }
                            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toasty.error(RegisterActivity.this, "Failed " + e.getMessage(), Toasty.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred()/ taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage("Uploaded " + (int)progress + "%");
                                }
                            });
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        value_gender = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    public void login(View view) {
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }
}