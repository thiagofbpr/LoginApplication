package com.myloginapplication.classes.abstracts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.myloginapplication.R;
import java.util.Objects;

public abstract class AbstractTextInputEditText extends TextInputEditText {

    protected boolean required;

    public AbstractTextInputEditText(@NonNull Context context) {
        super(context);
        init(null);
    }

    public AbstractTextInputEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public AbstractTextInputEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {
        if (set != null) {
            @SuppressLint("CustomViewStyleable") TypedArray ta = getContext().obtainStyledAttributes(set, R.styleable.TextInputEditText);
            required = ta.getBoolean(R.styleable.TextInputEditText_isRequired_Ext, false);
            ta.recycle();
        }
    }

    private boolean isRequired() {
        return required;
    }

    private boolean isEmpty() {
        return Objects.requireNonNull(this.getText()).toString().trim().isEmpty();
    }

    protected abstract boolean isValidData();

    protected abstract int getErrorMessage();

    public boolean hasValidData() {
        TextInputLayout parent = (TextInputLayout)this.getParent().getParent();

        if (isRequired()) {
            if (isEmpty()) {
                parent.setError(getContext().getString(R.string.errorRequiredField));
                return false;
            }
        }

        if (!isValidData()) {
            parent.setError(getContext().getString(getErrorMessage()));
            return false;
        }
        return true;
    }


}
