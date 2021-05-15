package com.jobcall.jobcall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;

import com.jobcall.jobcall.R;
import com.jobcall.jobcall.utils.API;

public class LoginActivity extends AppCompatActivity {
    Button login_github;
    Button login_stackoverflow;
    ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_github = findViewById(R.id.buttonGithub);
        login_github.setOnClickListener(view -> {
            API.loginWithGithub(this.getBaseContext());
        });

        login_stackoverflow = findViewById(R.id.buttonStack);
        login_stackoverflow.setOnClickListener(view -> {
            API.loginWithStack(this.getBaseContext());
        });

        layout = (ConstraintLayout)findViewById(R.id.layout);
        AnimationDrawable animationDrawable =  (AnimationDrawable)layout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(3000);
        new Thread(animationDrawable::start).start();    }

    /*private void login() {
        UserDataManager userDataUtils = new UserDataManager(this);
        FirebaseUtils.getUser(FirebaseAuth.getInstance().getCurrentUser().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                userDataUtils.ReadUserData(documentSnapshot.toObject(UserModel.class));
            }
        });
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }*/
}