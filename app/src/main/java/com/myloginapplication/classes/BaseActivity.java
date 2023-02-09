package com.myloginapplication.classes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.myloginapplication.classes.interfaces.LogoutListener;
import com.myloginapplication.controller.LoginActivity;
import com.myloginapplication.util.UserUtil;

public class BaseActivity extends AppCompatActivity implements LogoutListener {

    public void startMyActivity(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getMyApp().registerSessionListener(this);
        getMyApp().startUserSession();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        getMyApp().onUserInteraction();
    }

    @Override
    public void onSessionLogout() {
        finish();
        UserUtil.CURRENT_USER = null;
        getMyApp().isSessionExpired = true;
        startMyActivity(LoginActivity.class);
    }

    public MyApp getMyApp() {
        return (MyApp) getApplication();
    }


}
