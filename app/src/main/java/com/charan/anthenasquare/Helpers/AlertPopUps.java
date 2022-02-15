package com.charan.anthenasquare.Helpers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class AlertPopUps {

    public static AlertDialog alertDialog;


    public static View showLayoutPopup(int layout , Context context, Activity activity) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        View view = activity.getLayoutInflater().inflate(layout , null, false);
        alert.setView(view);
        alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        return view;
    }





    public static void dismissPopup(){
        alertDialog.dismiss();
    }
}