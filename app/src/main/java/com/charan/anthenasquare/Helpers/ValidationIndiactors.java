package com.charan.anthenasquare.Helpers;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;

import com.charan.anthenasquare.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ValidationIndiactors {

    @SuppressLint("ResourceAsColor")
    public static void showErrorIndicatorAfterBtnPressed(TextInputEditText textInputEditText , String message, TextInputLayout inputField){
        int color = R.color.cancelColor;
        textInputEditText.setError(message);
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inputField.setBoxStrokeColor(Color.parseColor("#FFFFFFFF"));
                inputField.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF")));
            }
        });
        inputField.setBoxStrokeColor(Color.parseColor("#FD0707"));
        inputField.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#FD0707")));
    }

    @SuppressLint("ResourceAsColor")
    public static void showErrorIndicator(TextInputEditText textInputEditText , String message, TextInputLayout inputField){

        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 0)
                {
                    int color = R.color.cancelColor;
                    textInputEditText.setError(message);
                    inputField.setBoxStrokeColor(Color.parseColor("#FD0707"));
                    inputField.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#FD0707")));
                }
                else {
                    inputField.setBoxStrokeColor(Color.parseColor("#FFFFFFFF"));
                    inputField.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF")));
                }
            }
        });


    }
}
