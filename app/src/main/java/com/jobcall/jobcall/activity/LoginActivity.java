package com.jobcall.jobcall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;

import com.jobcall.jobcall.R;
import com.jobcall.jobcall.service.API;

public class LoginActivity extends AppCompatActivity {
    Button login;
    ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.buttonGithub);
        login.setOnClickListener(view -> {
            new API(this.getBaseContext()).loginWithGithub();
        });
        layout = (ConstraintLayout)findViewById(R.id.layout);
        AnimationDrawable animationDrawable =  (AnimationDrawable)layout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();
    }

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