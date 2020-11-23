package com.example.tradeoff;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfile extends AppCompatActivity {

    private Button saveButton;
    private EditText newLastName,newFirstName,newphone;
    private DatabaseReference UsersRef;
    private FirebaseUser current_user;
    String empty="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        newLastName = findViewById(R.id.edit_last_name);
        newFirstName = findViewById(R.id.edit_first_name);
        newphone = findViewById(R.id.change_phone);


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

        saveButton = (Button)findViewById(R.id.btn_back);


        final String finalEmpty = empty;
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String changeLastName = newLastName.getText().toString();
                final String changeFirstName = newFirstName.getText().toString();
                final String changedphone = newphone.getText().toString();


                if (changeLastName.isEmpty()==false){
                    UsersRef.child("lname").setValue(changeLastName);
                }
                if (changeFirstName.isEmpty()==false){
                    UsersRef.child("fname").setValue(changeFirstName);
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


                finish();
            }
        });
    }

}