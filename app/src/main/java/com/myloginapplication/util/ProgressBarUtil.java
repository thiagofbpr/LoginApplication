package com.myloginapplication.util;

import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProgressBarUtil extends AppCompatActivity {

    private static ProgressBarUtil Instance;

    private ProgressBarUtil() {}

    public static ProgressBarUtil getInstance() {
        if (Instance == null) {
            Instance = new ProgressBarUtil();
        }
        return Instance;
    }

    public void runWithProgressBar(ViewGroup progressBarLayout, Runnable block) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        progressBarLayout.setVisibility(View.VISIBLE);
                    }
                });
                block.run();
            }
        });
    }


}
