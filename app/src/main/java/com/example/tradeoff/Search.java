package com.example.tradeoff;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Search extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner city_spinner;
    private Spinner work_spinner;

    DatabaseReference ref;
    String work_search;
    String city_search;

    String[] city = {"שומרון", "מרכז",
            "ירושלים", "צפון",
            "שרון", "דרום"};

    String[] active = {"House repair", "House keeping",
            "Private lesson", "Plumbing",
            "Gardening", "Private lesson", "Computer assistance", "Phone assistance",
            "Administrative assistance", "Help Transport", "Other"};


    LinearLayout linearLayout;
    ArrayList<Post> posts=new ArrayList<Post>();
    Post p;
    User u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ref = FirebaseDatabase.getInstance().getReference();

//**********************spinner*************************
        city_spinner = (Spinner) findViewById(R.id.city_spinner);
        city_spinner.setOnItemSelectedListener(this);

        work_spinner = (Spinner) findViewById(R.id.work_spinner);
        work_spinner.setOnItemSelectedListener(this);

        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                city);

        city_spinner.setAdapter(ad);

        ArrayAdapter ad1
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                active);

        work_spinner.setAdapter(ad1);

        linearLayout = (LinearLayout) findViewById(R.id.listofMyPosts);



        linearLayout.removeAllViews();

        //getDat();
    }





    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spin = (Spinner)parent;
        Spinner spin2 = (Spinner)parent;
        if(spin.getId() == R.id.work_spinner)
        {
            work_search =active[position];
        }

        if(spin2.getId() == R.id.city_spinner)
        {
            city_search=city[position];
        }
        //Toast.makeText(Search.this, city_search ,Toast.LENGTH_LONG).show();

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
                                if(city_search.equals(u.getAdress())&& work_search.equals(i.getGive())) {
                                    LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                                    //You can use this view to findViewById, setOnClickListener, setText etc;
                                    View view = layoutInflater.inflate(R.layout.coulmn_row, null, false);

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

                                    linearLayout.addView(view);

                                    //  System.out.println("text free: " + i.getfreeText() + " Give: " + i.getGive() + " Take: " + i.getTake());
                                    //  System.out.println("Adress: " + u.getAdress() + " Email " + u.getEmail() + " Fname " + u.getFname() + " Lname " + u.getLname() + " Phone " + u.getPhone());

                                }

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

//    public void getDat(){
//        ref.child("Posts").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot statis : dataSnapshot.getChildren())
//                {
//                    posts.clear();
//                    p= statis.getValue(Post.class);
//
//                    posts.add(p);
//                    for(final Post i: posts){
//                        ref.child("User").child(i.getMail()).addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                               // System.out.println("email " + i.getMail());
//                                u = dataSnapshot.getValue(User.class);
//                               // System.out.println(u.getLname());
//
//                            //if(p.getMail().equals(u.getEmail())) {
//                                System.out.println("text free: " + i.getfreeText() + " Give: " + i.getGive() + " Take: " + i.getTake());
//                                System.out.println("Adress: " + u.getAdress() + " Email " + u.getEmail() + " Fname " + u.getFname() + " Lname " + u.getLname() + " Phone " + u.getPhone());
//                           // }
//
//
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//                                System.out.println("The read failed: " + databaseError.getCode());
//                            }
//                        });
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getCode());
//            }
//        });
//    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
        }

