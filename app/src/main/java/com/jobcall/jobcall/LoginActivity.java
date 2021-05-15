package com.jobcall.jobcall;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        login = findViewById(R.id.button);


        if (mAuth.getCurrentUser() != null) //login();
            ;

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