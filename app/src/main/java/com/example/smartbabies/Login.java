package com.example.smartbabies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Login extends AppCompatActivity {

   @BindView(R.id.email1) EditText textEmail;
 @BindView(R.id.textView7) TextView  create;
  @BindView(R.id.Pass) EditText  textpass;
 @BindView(R.id.button2) Button login;
    @BindView(R.id.progressBar2) ProgressBar progressBar;
    private EditText Name;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Name = (EditText) findViewById(R.id.name);

       ButterKnife.bind(this);
   fAuth = FirebaseAuth.getInstance();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = textEmail.getText().toString().trim();
                String password = textpass.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    textEmail.setError("email required");
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

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "you have succesfully logged", Toast.LENGTH_SHORT).show();

                            String name = Name.getText().toString();
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            intent.putExtra("name", name);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Login.this, "error occured" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });



            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Signup.class));

            }
        });

    }
}