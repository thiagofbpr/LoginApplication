package com.myloginapplication.classes;

import android.app.Application;
import com.myloginapplication.classes.interfaces.LogoutListener;
import com.myloginapplication.util.UserUtil;
import java.util.Timer;
import java.util.TimerTask;

public class MyApp extends Application {

    public static final long DELAY = 60000; // 1 minute
    public boolean isSessionExpired = false;
    private LogoutListener listener;
    private Timer timer;


    public void startUserSession() {
        cancelTimer();
        startTimer();
    }

    private void startTimer() {
        if (UserUtil.CURRENT_USER != null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    listener.onSessionLogout();
                }
            }, DELAY);
        }
    }

    private void cancelTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    public void registerSessionListener(LogoutListener listener) {
        this.listener = listener;
    }

    public void onUserInteraction() {
        startUserSession();
    }


}
