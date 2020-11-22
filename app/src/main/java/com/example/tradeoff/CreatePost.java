package com.example.tradeoff;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class CreatePost extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button createPost;
    private EditText freeText;
    private Button cancelBtn;

    private String couurentGive;

private String courrentTake;

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private String currentUserID;
    private DatabaseReference RootRef;
    private String MoreInfoText;
    private Spinner mySpinner_take;
    private Spinner mySpinner_give;
    String[] city = { "שומרון", "מרכז",
            "ירושלים", "צפון",
            "שרון", "דרום" };

    String[] active = { "House repair", "House keeping",
            "Private lesson", "Plumbing",
            "Gardening","Private lesson","Computer assistance","Phone assistance",
            "Administrative assistance","Help Transport", "Other" };
String email;
String empty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        createPost = findViewById(R.id.createPostbtn);

        freeText = findViewById(R.id.freeText);
        cancelBtn = findViewById(R.id.cancelBtn);
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        RootRef = firebaseDatabase.getInstance().getReference();
        mySpinner_take = (Spinner) findViewById(R.id.spinner_take);
        mySpinner_give = (Spinner) findViewById(R.id.spinner_give);
        Bundle extras = getIntent().getExtras();
        email=extras.getString("email");

        Spinner take = findViewById(R.id.spinner_take);
        take.setOnItemSelectedListener(this);

        Spinner give = findViewById(R.id.spinner_give);
        give.setOnItemSelectedListener(this);
        // for spinner
        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                active);

        take.setAdapter(ad);

        ArrayAdapter ad1
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                active);

        give.setAdapter(ad1);



        createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerPostToDataBase();
                Intent i = new Intent(CreatePost.this, Home.class);
                Bundle extras = getIntent().getExtras();
                i.putExtra("email",extras.getString("email"));
                startActivity(i);
                finish();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreatePost.this, Home.class);
                Bundle extras = getIntent().getExtras();

                i.putExtra("email",extras.getString("email"));
                startActivity(i);
                finish();
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        Spinner spin = (Spinner)parent;
        Spinner spin2 = (Spinner)parent;
        if(spin.getId() == R.id.spinner_take)
        {
            courrentTake=active[position];
        }

        if(spin2.getId() == R.id.spinner_give)
        {
            couurentGive=active[position];
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void registerPostToDataBase(){
        MoreInfoText = freeText.getText().toString().trim();
        empty="";
        for(int i=0 ; i<email.length(); i ++){
            if(email.charAt(i)!='.'){
                empty+=email.charAt(i);
            }else{
                empty+='_';
            }
        }

        if (couurentGive.isEmpty()) {
            Toast.makeText(CreatePost.this, "Please select Give Option", Toast.LENGTH_LONG).show();
            return;
        }
        if (courrentTake.isEmpty()) {
            Toast.makeText(CreatePost.this, "Please select Take Option", Toast.LENGTH_LONG).show();
            return;
        }else {
            String postId = RootRef.push().getKey();
            Post p = new Post(couurentGive, courrentTake, MoreInfoText,empty);
            FirebaseDatabase.getInstance().getReference("Posts").child(postId).setValue(p);
        }
    }

}
