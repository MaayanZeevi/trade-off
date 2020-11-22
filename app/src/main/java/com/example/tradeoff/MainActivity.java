package com.example.tradeoff;
//levana dayan
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;


public class MainActivity extends AppCompatActivity {

    EditText email;
    EditText pass;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.Email);
        pass = (EditText) findViewById(R.id.Pass);


    }

    public void connect(View view) {
        final ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this, "Please wait...", "Processing...", true);
        String Email=email.getText().toString().trim();
        String Password=pass.getText().toString().trim();
if(Email.isEmpty()||Password.isEmpty()) {progressDialog.dismiss();
    Toast.makeText(MainActivity.this, "Email or Passeword empty", Toast.LENGTH_LONG).show();
return;}

        auth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(MainActivity.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {

                    String currentUserID = "EYYkLnqcZ2VXVeTRwlLOdblkOXQ2";
                    if (auth.getCurrentUser().getUid().equals(currentUserID)) {
                        startActivity(new Intent(MainActivity.this, Administrator.class));
                        Toast.makeText(MainActivity.this, "Administrator Connector", Toast.LENGTH_LONG).show();
                        finish();


                    } else {
                        Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(MainActivity.this, Home.class);
                        i.putExtra("email",email.getText().toString().trim());
                        startActivity(i);
                        finish();
                    }
                }
                else {
                    try {
                        throw task.getException();
                    }

                    catch (FirebaseAuthException e){
                        Toast.makeText(getApplicationContext(), "Invalid Email or Passeword", Toast.LENGTH_LONG).show();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }


    public void Forgot(View view) {
        startActivity(new Intent(this, Forgot_Password.class));
    }

    public void NotAccount(View view) {
        startActivity(new Intent(this, RegisterUser.class));
    }
}
