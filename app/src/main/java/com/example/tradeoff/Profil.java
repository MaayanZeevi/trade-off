package com.example.tradeoff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Profil extends AppCompatActivity {
    public static final int RESULT_LOAD_IMG = 1;
    android.widget.ImageView p;
    String email;

    TextView number;

    TextView mail;

    TextView name;

    TextView lastname;

    TextView act;

    TextView adress ;
    String empty;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        p = (ImageView) findViewById(R.id.imageView);

        number = (TextView)findViewById(R.id.phonenumber);
        mail = (TextView)findViewById(R.id.mail);
        name = (TextView)findViewById(R.id.fname);
        lastname = (TextView)findViewById(R.id.lname);
        act = (TextView)findViewById(R.id.act);
        adress = (TextView)findViewById(R.id.adress);
        Bundle extras = getIntent().getExtras();
        email=extras.getString("email");
        ref = FirebaseDatabase.getInstance().getReference();
        getfromdatabase(email);
        loadpicture();
    }


    void getfromdatabase(String email){
        empty="";
        for(int i=0 ; i<email.length(); i ++){
            if(email.charAt(i)!='.'){
                empty+=email.charAt(i);
            }else{
                empty+='_';
            }
        }
        ref.child("User").child(empty).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User User = dataSnapshot.getValue(User.class);
                mail.setText(User.getEmail());
                act.setText(User.getActiv());
                name.setText(User.getFname());
                lastname.setText(User.getLname());
                number.setText(User.getPhone());
                adress.setText(User.getAdress());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


    void loadpicture(){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference photoReference= storageReference.child("images/").child("User/"+empty);

        final long ONE_MEGABYTE = 1024 * 1024;
        photoReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                p.setImageBitmap(bmp);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), "No Such file or Path found!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void EditProfil(View view) {
        startActivity(new Intent(Profil.this, EditProfile.class));
    }




    public void Remove_post(View view) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query Query = ref.child("Posts");

        Query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    Post p=snap.getValue(Post.class);
                    System.out.println(p.getMail() + " "+empty);
                    if(p.getMail().equals(empty))
                    snap.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("no posts");
            }
        });
    }
}