package com.gueei.applocker;

import android.app.AlertDialog;
import android.content.Context;
import android.preference.DialogPreference;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.LinearLayout;


public class PasswordPreference extends DialogPreference {
    private String password;
    private EditText passwordEditText;
    private EditText confirmEditText;

    public PasswordPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init(context, attrs);
    }

    public PasswordPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setDialogLayoutResource(R.layout.confirm_password);

        passwordEditText = new EditText(context, attrs);
        confirmEditText = new EditText(context, attrs);

    }

    private void detachView(View view, View newParent) {
        ViewParent oldParent = view.getParent();
        if (oldParent != newParent && oldParent != null)
            ((ViewGroup)oldParent).removeView(view);
    }

    @Override
    public void onBindDialogView(View view) {
        detachView(passwordEditText, view);
        detachView(confirmEditText, view);

        LinearLayout layout = (LinearLayout)view.findViewById(R.id.linearLayout);
        layout.addView(passwordEditText);
        layout.addView(confirmEditText);

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                verifyInput();
            }
        });

        confirmEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                verifyInput();
            }
        });

        String oldPassword = getSharedPreferences().getString("password", "");
        passwordEditText.setText(oldPassword);
        confirmEditText.setText(oldPassword);
    }

    private void verifyInput() {
        String newPassword = passwordEditText.getText().toString();
        String confirmedPassword = confirmEditText.getText().toString();

        boolean passwordOk = false;
        if (newPassword.equals(confirmedPassword)) {
            passwordOk = true;
            password = newPassword;
        }

        AlertDialog dialog = (AlertDialog)getDialog();
        if (dialog != null)
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(passwordOk);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (!positiveResult)
            return;
        persistString(password);
    }
}
