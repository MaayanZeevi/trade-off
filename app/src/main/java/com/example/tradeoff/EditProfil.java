package com.example.tradeoff;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfil extends AppCompatActivity {

    private Button save;
    private EditText newname, newphone;
    private DatabaseReference UsersRef;
    private FirebaseUser current_user;
    String empty="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);

        newname = findViewById(R.id.changename);
        newphone = findViewById(R.id.changephone);


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

        save = (Button)findViewById(R.id.btn_back);


        final String finalEmpty = empty;
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String changedname = newname.getText().toString();

                final String changedphone = newphone.getText().toString();


                if (changedname.isEmpty()==false){
                    UsersRef.child("fname").setValue(changedname);
                }

                if (changedphone.isEmpty()==false) {
                    if (changedphone.length() != 10) {
                        Toast.makeText(EditProfil.this, "NotValidPhone", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (changedphone.charAt(0) != '0') {
                        Toast.makeText(EditProfil.this, "NotValidPhone", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (changedphone.charAt(1) != '5') {
                        Toast.makeText(EditProfil.this, "NotValidPhone", Toast.LENGTH_LONG).show();
                        return;
                    }
                    UsersRef.child("phone").setValue(changedphone);
                }


                finish();
            }
        });
    }

}