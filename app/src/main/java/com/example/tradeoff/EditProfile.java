package com.example.tradeoff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static com.example.tradeoff.Profil.RESULT_LOAD_IMG;

public class EditProfile extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button save;
    private EditText newFirstName,newLastName, newPhone;
    private DatabaseReference UsersRef;
    private FirebaseUser current_user;
    String[] regions;
    String selectedRegion;
    String empty="";
    Uri imageUri;
    FirebaseStorage storage;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        regions = getResources().getStringArray(R.array.regions_array);
        Spinner spinnerRegions = findViewById(R.id.spinner_regions);
        spinnerRegions.setOnItemSelectedListener(this);
        ArrayAdapter adapter
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                regions);

        spinnerRegions.setAdapter(adapter);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        newFirstName = findViewById(R.id.edit_first_name);
        newPhone = findViewById(R.id.edit_phone);
        newLastName = findViewById(R.id.edit_last_name);


        current_user = FirebaseAuth.getInstance().getCurrentUser();
        String s = current_user.getEmail().toString().trim();

        for(int i=0 ; i<s.length(); i ++){
            if(s.charAt(i)!='.'){
                empty+=s.charAt(i);
            }else{
                empty+='_';
            }
        }
        UsersRef =  FirebaseDatabase.getInstance().getReference().child("User").child(empty);

        save = (Button)findViewById(R.id.btn_save_profile_changes);


        final String finalEmpty = empty;
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String changeFirstname = newFirstName.getText().toString();
                final String changeLastname = newLastName.getText().toString();

                final String changedphone = newPhone.getText().toString();


                if (changeFirstname.isEmpty() == false) {
                    UsersRef.child("fname").setValue(changeFirstname);
                }
                if (changeLastname.isEmpty() == false) {
                    UsersRef.child("lname").setValue(changeLastname);
                }

                if (changedphone.isEmpty()==false) {
                    if (changedphone.length() != 10) {
                        Toast.makeText(EditProfile.this, "NotValidPhone", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (changedphone.charAt(0) != '0') {
                        Toast.makeText(EditProfile.this, "NotValidPhone", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (changedphone.charAt(1) != '5') {
                        Toast.makeText(EditProfile.this, "NotValidPhone", Toast.LENGTH_LONG).show();
                        return;
                    }
                    UsersRef.child("phone").setValue(changedphone);
                }
                UsersRef.child("adress").setValue(selectedRegion);
                Intent act= new Intent(new Intent(EditProfile.this,Profil.class));
                act.putExtra("email",empty);
                startActivity(act);// Write was successful!
            }
        });
    }

    public void change_image(View view) {

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
            uploadImage();

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
                                    Toast.makeText(EditProfile.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            // Error, Image not uploaded
                            Toast.makeText(EditProfile.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner)parent;
        if(spinner.getId() == R.id.spinner_regions)
        {
            selectedRegion=regions[position];
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}