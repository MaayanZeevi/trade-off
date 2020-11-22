package com.example.tradeoff;
//levana sciari

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class Home extends AppCompatActivity {

    ArrayList<Post> posts=new ArrayList<Post>();
    Post p;
    User u;
    DatabaseReference ref;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ref = FirebaseDatabase.getInstance().getReference();
        linearLayout = (LinearLayout) findViewById(R.id.listofMyPosts_home);
        getDat();
    }

            public void CreatePost(View view) {
        Intent forgot = new Intent(Home.this, CreatePost.class);
        Bundle extras = getIntent().getExtras();
        forgot.putExtra("email", extras.getString("email"));
        startActivity(forgot);
        finish();
    }

    public void Exit(View view) {
        Intent Exit = new Intent(Home.this, MainActivity.class);
        startActivity(Exit);
        finish();
    }

    public void profile(View view) {
        Intent tent = new Intent(this, Profil.class);
        Bundle extras = getIntent().getExtras();
        tent.putExtra("email", extras.getString("email"));
        startActivity(tent);
    }

    public void Search(View view) {
        startActivity(new Intent(this, Search.class));
    }



    public void getDat(){
        linearLayout.removeAllViews();
        ref.child("Posts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot statis : dataSnapshot.getChildren())
                {
                    posts.clear();
                    p= statis.getValue(Post.class);

                    posts.add(p);
                    for(final Post i: posts){
                        ref.child("User").child(i.getMail()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                u = dataSnapshot.getValue(User.class);

                                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                                //You can use this view to findViewById, setOnClickListener, setText etc;
                                View view = layoutInflater.inflate(R.layout.coulmn_row, null,false);

                                //FindView using inflated view
                                TextView fname = view.findViewById(R.id.Fname_card);
                                TextView lname = view.findViewById(R.id.Lname_card);
                                TextView mail = view.findViewById(R.id.mail_card);
                                TextView phone = view.findViewById(R.id.phone_card);
                                TextView adress = view.findViewById(R.id.adress_card);
                                TextView give = view.findViewById(R.id.get_card);
                                TextView take = view.findViewById(R.id.take_card);
                                TextView freetext = view.findViewById(R.id.freetext_card);
                                fname.setText(u.getFname());
                                lname.setText(u.getLname());
                                mail.setText(u.getEmail());
                                phone.setText(u.getPhone());
                                adress.setText(u.getAdress());
                                give.setText(i.getGive());
                                take.setText(i.getTake());
                                freetext.setText(i.getfreeText());

//                                final android.widget.ImageView image=(ImageView) findViewById(R.id.image_card);
//
//                                StorageReference storageReference = FirebaseStorage.getInstance().getReference();
//                                StorageReference photoReference= storageReference.child("images/").child("User/"+u.getEmail());
//
//                                    final long ONE_MEGABYTE = 1024 * 1024;
//                                    photoReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//                                        @Override
//                                        public void onSuccess(byte[] bytes) {
//                                            //android.widget.ImageView image=(ImageView) findViewById(R.id.image_card);
//                                            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                                            if (bmp != null) {
//                                                image.setImageBitmap(bmp);
//                                            }
//
//                                        }
//                                    }).addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception exception) {
//                                            Toast.makeText(getApplicationContext(), "No Such file or Path found!!", Toast.LENGTH_LONG).show();
//                                        }
//                                    });

                                linearLayout.addView(view);

                              //  System.out.println("text free: " + i.getfreeText() + " Give: " + i.getGive() + " Take: " + i.getTake());
                              //  System.out.println("Adress: " + u.getAdress() + " Email " + u.getEmail() + " Fname " + u.getFname() + " Lname " + u.getLname() + " Phone " + u.getPhone());



                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                System.out.println("The read failed: " + databaseError.getCode());
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
    }

