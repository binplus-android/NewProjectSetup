package com.example.newprojectsetup.Common;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.example.newprojectsetup.R;


public class LoadingBar {

    Context context;
    Dialog dialog;

    public LoadingBar(Context context) {
        this.context = context;

        dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView( R.layout.progressbar);
        dialog.setCanceledOnTouchOutside(false);



    }
    public void show()

    {
        if (dialog.isShowing())
        {
            dialog.dismiss();
        }

        dialog.show();
    }

    public void dismiss()
    {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

    }




}