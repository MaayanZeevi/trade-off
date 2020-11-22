package com.example.tradeoff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static com.example.tradeoff.Profil.RESULT_LOAD_IMG;

public class RegisterUser extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {

    EditText Fname;
    EditText Lname;
    EditText email;
    EditText password;
    EditText phone;
    Uri imageUri;
    String _act;
    String _CITY;
    String b;
    String empty;
    FirebaseStorage storage;
    StorageReference storageReference;
    String[] city = { "שומרון", "מרכז",
            "ירושלים", "צפון",
            "שרון", "דרום" };

    String[] active = { "House repair", "House keeping",
            "rivate lesson", "Plumbing",
            "Gardening","Private lesson","Computer assistance","Phone assistance",
            "Administrative assistance","Help Transport", "Other" };

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        Fname= (EditText)findViewById(R.id.FirstName);
        Lname=(EditText)findViewById(R.id.LastName);
        email= (EditText)findViewById(R.id.mail);
        password=(EditText)findViewById(R.id.Password);
        phone= (EditText)findViewById(R.id.Phone);

//אתחול
        Spinner spinoadress = findViewById(R.id.spineradress);
        spinoadress.setOnItemSelectedListener(this);

//   // for spinner
        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                city);

        spinoadress.setAdapter(ad);

    }

void addtoautu (String email,String pass){
       //הוספה לautu לא לdatabase
    FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, pass).addOnCompleteListener(RegisterUser.this, new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful()){
            Log.e("TAG", "successful");
            }else{
            Log.e("TAG", "unsuccessful");
        }
        }
    });
    }

//register user
    public void create(View view) {
        addtoautu( email.getText().toString().trim(), password.getText().toString().trim());//register to authentification
        String s = email.getText().toString().trim();
        empty="";
        for(int i=0 ; i<s.length(); i ++){
            if(s.charAt(i)!='.'){
                empty+=s.charAt(i);
            }else{
                empty+='_';
            }
        }
        uploadImage();
        s.replaceAll(".","_");
        System.out.println(s);
        System.out.println(empty);

        if (Fname.getText().toString().isEmpty()) {
            Toast.makeText(RegisterUser.this, "First Name is empty", Toast.LENGTH_LONG).show();
            return;
        }
        if (Lname.getText().toString().isEmpty()) {
            Toast.makeText(RegisterUser.this, "Last Name is empty", Toast.LENGTH_LONG).show();
            return;
        }

        if (email.getText().toString().isEmpty()) {
            Toast.makeText(RegisterUser.this, "Email is empty", Toast.LENGTH_LONG).show();
            return;
        }
        if (password.getText().toString().isEmpty()) {
            Toast.makeText(RegisterUser.this, "Pssseword is empty", Toast.LENGTH_LONG).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(RegisterUser.this, "Pssseword is shorter", Toast.LENGTH_LONG).show();
            return;
        }

        if (phone.getText().toString().isEmpty()) {
            Toast.makeText(RegisterUser.this, "Phone is empty", Toast.LENGTH_LONG).show();
            return;
        }

        if (phone.length() != 10) {
            Toast.makeText(RegisterUser.this, "Phone is not valid", Toast.LENGTH_LONG).show();
            return;
        }
        if (phone.getText().toString().charAt(0) != '0') {
            Toast.makeText(RegisterUser.this, "Phone is not valid", Toast.LENGTH_LONG).show();
            return;
        }
        if (phone.getText().toString().charAt(1) != '5') {
            Toast.makeText(RegisterUser.this, "Phone is not valid", Toast.LENGTH_LONG).show();
            return;
        }
            User a = new User(
                    Fname.getText().toString().trim(),
                    Lname.getText().toString().trim(),
                    empty,
                    password.getText().toString().trim(),
                    phone.getText().toString().trim(),
                    _act,
                    _CITY);

             b = a.getEmail();
            mDatabase.child("User").child(b).setValue(a)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(RegisterUser.this, "Welcome To TradeOff", Toast.LENGTH_LONG).show();
                            Intent act= new Intent(RegisterUser.this, Home.class);
                            act.putExtra("email",b);
                            startActivity(act);// Write was successful!
                            finish();
                            // ...
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterUser.this, "Register is not success", Toast.LENGTH_LONG).show();
                            // Write failed
                            // ...
                        }
                    });
        }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        Spinner spin = (Spinner)parent;
        Spinner spin2 = (Spinner)parent;
        if(spin.getId() == R.id.spineradress)
        {
            _CITY=city[position];
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void signin(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }

    public void Upload(View view) {

        //פותח את הגלריה בפלאפון ומנסה לעלות תמונה
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);

    }
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
                // מה אני עושה עם התמונה הזאת
            imageUri= data.getData();
        }
    }


    // UploadImage method
    private void uploadImage()
    {
        if (imageUri != null) {
            // Defining the child of storageReference
            StorageReference ref = storageReference.child("images/");

            // adding listeners on upload
            // or failure of image
            ref.child("User").child(empty).putFile(imageUri)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    // Image uploaded successfully
                                    Toast.makeText(RegisterUser.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            // Error, Image not uploaded
                            Toast.makeText(RegisterUser.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                }
                            });
        }
    }
}

