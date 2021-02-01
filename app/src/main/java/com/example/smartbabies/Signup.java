package com.example.smartbabies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Signup extends AppCompatActivity {
    @BindView(R.id.button) Button Register;
    @BindView(R.id.name) EditText texname;
    @BindView(R.id.Email) EditText  textEmail;
    @BindView(R.id.Phone) EditText textPhone;
    @BindView(R.id.Password) EditText    textpass;
    @BindView(R.id.textView4) TextView loginhere;
  @BindView(R.id.progressBar) ProgressBar progressBar;
    FirebaseAuth fAuth;
private Button login1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

   ButterKnife.bind(this);
        fAuth = FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),Signup.class));
            finish();
        }


        login1 =findViewById(R.id.login1);
        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), facebooklogin.class));

            }
        });

        Register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email = textEmail.getText().toString().trim();
                String password = textpass.getText().toString().trim();
                String phone = textPhone.getText().toString().trim();
                String name = texname.getText().toString().trim();
                if(TextUtils.isEmpty(name)){
                    texname.setError("Name required");
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                    textPhone.setError("Phone Required");
                    return;
                }
                if(phone.length()!=10){
                    textPhone.setError("Phone number must be ten");
                    return;
                }


                if (TextUtils.isEmpty(email)) {
                    textEmail.setError("email required");
                    return;

                }
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(textEmail.getText().toString()).matches()){
                    textEmail.setError("Enter Valid email");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    textpass.setError("password is empty");
                    return;

                }
                if (password.length() < 6) {
                    textpass.setError("password must be 6 characters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Signup.this, "user created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Login.class));
                            progressBar.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(Signup.this, "error occured" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });
            }
        });
        loginhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));

            }
        });

    }

}