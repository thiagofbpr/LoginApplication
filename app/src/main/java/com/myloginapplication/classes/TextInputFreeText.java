package com.myloginapplication.classes;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.myloginapplication.R;
import com.myloginapplication.classes.abstracts.AbstractTextInputEditText;

public class TextInputFreeText extends AbstractTextInputEditText {
    public TextInputFreeText(@NonNull Context context) {
        super(context);
    }

    public TextInputFreeText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TextInputFreeText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected boolean isValidData() {
        return true;
    }

    @Override
    protected int getErrorMessage() {
        return R.string.errorInvalidFormat;
    }


}
