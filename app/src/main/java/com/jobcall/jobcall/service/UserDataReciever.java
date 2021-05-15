package com.jobcall.jobcall.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.jobcall.jobcall.activity.HomeActivity;

public class UserDataReciever extends BroadcastReceiver {

    HomeActivity homeActivity;

    public UserDataReciever(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            switch (intent.getAction()) {
                case "USER_DATA":
                    if (intent.getStringExtra("github") == null || intent.getStringExtra("github").isEmpty() || intent.getStringExtra("github").trim().isEmpty()) {
                        homeActivity.activateGithubLink();

                    } else {
                        homeActivity.removeGithubLink();
                    }
                    if (intent.getStringExtra("stack") == null || intent.getStringExtra("stack").isEmpty() || intent.getStringExtra("stack").trim().isEmpty()) {
                        homeActivity.activateStackLink();

                    } else {
                        homeActivity.removeStackLink();
                    }
                    homeActivity.hideProgressBar();
                    break;
            }
        }
    }
}
