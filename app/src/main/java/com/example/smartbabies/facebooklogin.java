package com.example.smartbabies;
import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
        import android.widget.TextView;

        import com.facebook.CallbackManager;
        import com.facebook.FacebookCallback;
        import com.facebook.FacebookException;
        import com.facebook.login.LoginResult;
        import com.facebook.login.widget.LoginButton;
        import com.squareup.picasso.Picasso;

public class facebooklogin extends AppCompatActivity {
    private TextView info;
    private ImageView profile;
    private LoginButton login;
    private Button exit;
    CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebooklogin);
        info=findViewById(R.id.info);
        profile=findViewById(R.id.profile);
        login= findViewById(R.id.login);
         exit= findViewById(R.id.btn);
        callbackManager= CallbackManager.Factory.create();
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);

            }
        });
        login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                info.setText("User id:"+ loginResult.getAccessToken().getUserId());
                String imageURL = "https://graph.facebook.com/"+ loginResult.getAccessToken().getUserId()+"/picture?return_ssl_resources=1";
                Picasso.get().load(imageURL).into(profile);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

}