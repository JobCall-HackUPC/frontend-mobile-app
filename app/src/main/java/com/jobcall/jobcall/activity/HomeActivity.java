package com.jobcall.jobcall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.jobcall.jobcall.R;
import com.jobcall.jobcall.service.CallManagerService;
import com.jobcall.jobcall.service.UserDataReciever;
import com.jobcall.jobcall.utils.API;

public class HomeActivity extends AppCompatActivity {
    Button login_github;
    Button login_stackoverflow;
    ConstraintLayout layout;
    Button disconectGoogleText;
    UserDataReciever reciever;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        reciever = new UserDataReciever(this);
        registerReceiver(reciever, new IntentFilter("USER_DATA"));
        showProgressBar();
        login_github = findViewById(R.id.buttonGithub);

        login_stackoverflow = findViewById(R.id.buttonStack);


        layout = (ConstraintLayout)findViewById(R.id.layout);


        Intent notifications = new Intent(getApplicationContext(), CallManagerService.class);
        notifications.putExtra("email", getIntent().getStringExtra("mail"));
        startService(notifications);


        API.retrieveUserData(getApplicationContext(), getIntent().getStringExtra("mail")
                , getIntent().getStringExtra("uid"));
        disconectGoogleText = findViewById(R.id.buttonLogout);
        disconectGoogleText.setOnClickListener((v -> {
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(this, SignUpActivity.class);
            startActivity(i);
        }));
    }
    public void hideProgressBar() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void showProgressBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void activateGithubLink(){
        login_github.setOnClickListener(view -> {
            API.loginWithGithub(this.getBaseContext(), getIntent().getStringExtra("mail")
                    , getIntent().getStringExtra("uid"));
        });
        login_github.setText(R.string.login_with_github);
    }
    public void activateStackLink(){
        login_stackoverflow.setOnClickListener(view -> {
            API.loginWithStack(this.getBaseContext(), getIntent().getStringExtra("mail")
                    , getIntent().getStringExtra("uid"));
        });
        login_stackoverflow.setText(R.string.login_with_stack);

    }
    public void removeGithubLink(){
        login_github.setOnClickListener(view -> {
            API.unlinkGit(this.getBaseContext(), getIntent().getStringExtra("mail")
                    , getIntent().getStringExtra("uid"));
        });
        login_github.setText(R.string.disable_with_github);

    }
    public void removeStackLink(){
        login_stackoverflow.setOnClickListener(view -> {
            API.unlinkStack(this.getBaseContext(), getIntent().getStringExtra("mail")
                    , getIntent().getStringExtra("uid"));
        });
        login_stackoverflow.setText(R.string.disable_with_stack);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        API.retrieveUserData(getApplicationContext(), getIntent().getStringExtra("mail")
                , getIntent().getStringExtra("uid"));
    }
}