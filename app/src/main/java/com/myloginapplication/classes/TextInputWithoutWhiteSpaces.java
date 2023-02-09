package com.myloginapplication.classes;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.myloginapplication.R;
import com.myloginapplication.classes.abstracts.AbstractTextInputEditText;
import java.util.Objects;

public class TextInputWithoutWhiteSpaces extends AbstractTextInputEditText {

    public TextInputWithoutWhiteSpaces(@NonNull Context context) {
        super(context);
    }

    public TextInputWithoutWhiteSpaces(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TextInputWithoutWhiteSpaces(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected boolean isValidData() {
        return !Objects.requireNonNull(this.getText()).toString().trim().contains(" ");
    }

    @Override
    protected int getErrorMessage() {
        return R.string.errorWhiteSpaces;
    }


}
