package com.myloginapplication.classes;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BaseButton extends androidx.appcompat.widget.AppCompatButton {

    public BaseButton(@NonNull Context context) {
        super(context);
    }

    public BaseButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setEnabled_Ext(boolean enabled) {
        this.setEnabled(enabled);
        if (enabled) {
            this.setAlpha(1f);
        } else {
            this.setAlpha(0.5f);
        }
    }


}
