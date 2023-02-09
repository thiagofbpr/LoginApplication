package com.myloginapplication.util;

import android.app.Activity;
import android.content.DialogInterface;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.myloginapplication.R;

public class AlertDialogUtil {

    public static void showAlertDialog(Activity activity, String title, String message, Runnable blockPositiveButton ) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(activity);
                dialog.setTitle(title);
                dialog.setMessage(message);
                dialog.setPositiveButton(activity.getApplicationContext().getString(R.string.btnOK), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (blockPositiveButton != null) {
                            blockPositiveButton.run();
                        }
                        dialog.dismiss();
                    }
                });
                dialog.setCancelable(false);
                dialog.show();
            }
        });
    }


}
