package com.example.tradeoff;


import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
public class Forgot_Password extends AppCompatActivity {

      private EditText emailboxLogin;
      private FirebaseAuth auth;
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_forgot_code);
    auth = FirebaseAuth.getInstance();
    emailboxLogin=(EditText)findViewById(R.id.Email);
}

    public void Return(View view) {
    startActivity(new Intent(this,MainActivity.class));
    }

    public void reset(View view) {
    //מה קורה שלוחצים על reset password
        String email = emailboxLogin.getText().toString().trim();

        if (email.isEmpty()) {

           Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_LONG).show();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getApplicationContext(), "Email not good", Toast.LENGTH_LONG).show();
                }
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Forgot_Password.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Forgot_Password.this,MainActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(Forgot_Password.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                }
                            }
                       });
}
}
