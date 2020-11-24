package com.example.tradeoff;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfile extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button save;
    private TextInputEditText newFirstName, newLastName;
    private EditText newPhone;
    private DatabaseReference usersReference;
    private FirebaseUser current_user;
//        String[] regions = getResources().getStringArray(R.array.regions_array);
    String[] regions= {
                            "North", "Haifa", "Tel-Aviv", "Center", "Jerusalem",
                                    "South", "Shomron", "Binyamin"
                        };
    String selectedRegion;
    String identifier = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        newFirstName = findViewById(R.id.edit_first_name);
        newLastName = findViewById(R.id.edit_last_name);
        newPhone = findViewById(R.id.edit_phone);
        Spinner spinnerRegions = findViewById(R.id.spinner_regions);
        spinnerRegions.setOnItemSelectedListener(this);
        ArrayAdapter adapter
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                regions);

        spinnerRegions.setAdapter(adapter);

        final String finalEmpty = identifier;

        current_user = FirebaseAuth.getInstance().getCurrentUser();
        String email = current_user.getEmail().toString().trim();
        identifier = email.replaceAll("\\.", "_");
//        for(int i=0 ; i<email.length(); i ++){
//            if(email.charAt(i)!='.'){
//                empty+=email.charAt(i);
//            }else{
//                empty+='_';
//            }
//        }
        usersReference = FirebaseDatabase.getInstance().getReference().child("User").child(identifier);

        save = (Button) findViewById(R.id.btn_back);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String changeFirstname = newFirstName.getText().toString();
                final String changeLastname = newLastName.getText().toString();

                final String changedphone = newPhone.getText().toString();


                if (changeFirstname.isEmpty() == false) {
                    usersReference.child("fname").setValue(changeFirstname);
                }
                if (changeLastname.isEmpty() == false) {
                    usersReference.child("lname").setValue(changeLastname);
                }

                if (changedphone.isEmpty() == false) {
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
                    usersReference.child("phone").setValue(changedphone);

                }
                usersReference.child("adress").setValue(selectedRegion);

                finish();
            }
        });
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