package com.example.newprojectsetup.Common;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.newprojectsetup.Activity.Session_management;
import com.example.newprojectsetup.Activity.Splash_screen;
import com.example.newprojectsetup.R;
import com.example.newprojectsetup.Utli.AppController;
import com.example.newprojectsetup.networkconnectivity.NetworkConnection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;


public class Common {
    private static final int REQUEST_TIMEOUT = 1000000;
    Context context;
    ImageView mCheckConnection;
    TextView tryagain;
    ViewGroup viewGroup;
    LayoutInflater inflater;
    Session_management session_management;
    LoadingBar loadingBar;
    final Calendar myCalendar= Calendar.getInstance();

    static Toast toast;
    String regex = "[a-zA-Z0-9.\\-_]{2,256}@[a-zA-Z]{2,64}";
    String regexStr = "^[0-9]*$";
    Pattern p = Pattern.compile(regex);
    Pattern pattern_ifsc = Pattern.compile("[A-Z]{4}[0-9]{7}");
    Pattern pattern_PTIN = Pattern.compile("[A-Z]{1}[0-9]{8}");
    Pattern vechicle = Pattern.compile("[A-Z|a-z]{2}[0-9]{1,2}[A-Z|a-z]{0,3}[0-9]{4}");
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Pattern PanCrad = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
    Pattern Voterid = Pattern.compile("[A-Z|a-z]{3}[0-9]{7}");
    String dirregex = "^(([A-Z]{2}[0-9]{2})"
            + "( )|([A-Z]{2}-[0-9]"
            + "{2}))((19|20)[0-9]"
            + "[0-9])[0-9]{7}$";

    // Compile the ReGex
    Pattern Drivinglic = Pattern.compile(dirregex);
    Pattern Passport = Pattern.compile("[A-Z|a-z]{2}[0-9]{7}");
    Pattern NREGA = Pattern.compile("[A-Z|a-z]{2}[0-9]{1,2}[A-Z|a-z]{0,3}[0-9]{4}");



    public Common(Context context) {

        this.context = context;
        loadingBar = new LoadingBar(context);
        session_management = new Session_management(context);

    }

    public void postRequest(String url, HashMap<String, String> params, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        Log.e("postmethod", "postRequest: " + url + "\n" + params);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() {
                Log.e("params", "check" + params);
                return params;
                // return super.getParams ( );
            }
        };
        RetryPolicy retryPolicy = new DefaultRetryPolicy(REQUEST_TIMEOUT,

                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        AppController.getInstance().addToRequestQueue(stringRequest, "tag");
    }

    public void showVolleyError(VolleyError error, Context contexts) {
        String msg = VolleyErrorMessage(error);
        if (!msg.isEmpty()) {
            customtoastError("" + msg, contexts);
        }
    }

    public String VolleyErrorMessage(VolleyError error) {
        String str_error = "";
        if (error instanceof TimeoutError) {
            str_error = "Connection Timeout";
        } else if (error instanceof AuthFailureError) {
            str_error = "Session Timeout";
            //TODO
        } else if (error instanceof ServerError) {
            str_error = "Server not responding please try again later";
            //TODO
//        } else if (error instanceof NetworkError) {
//            str_error = "No Internet Connection";
//            //TODO
        } else if (error instanceof ParseError) {
            //TODO
            str_error = "An Unknown error occur";
        } else if (error instanceof NoConnectionError) {
            str_error = "No Internet Connection";
        } else {
            str_error = "Something Went Wrong";
        }

        return str_error;
    }

    public void No_internetdailoge() {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.actvity_no_internet_connection);
        dialog.getWindow();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // dialog.getWindow().getAttributes().windowAnimations = R.style.DailoAnimation;
        dialog.getWindow().setGravity(Gravity.CENTER);
//        dialog.show ( );

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.show();
        mCheckConnection = (ImageView) dialog.findViewById(R.id.no_internet_connection);
        tryagain = (TextView) dialog.findViewById(R.id.tryagain);
        tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (NetworkConnection.connectionChecking(context.getApplicationContext())) {
                    Intent intent = new Intent(context, Splash_screen.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                } else {
                    Toast.makeText(context.getApplicationContext(), "Check your connection.Try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void customtoastSuccess(String name, Context contexts) {

        String str = name.substring(0, 1).toUpperCase(Locale.ROOT);
        String str1 = name.substring(1, name.length()).toLowerCase(Locale.ROOT);
        String msg = str + str1;
        Log.e("vgbhnjmk,", msg);

        toast = new Toast(contexts);
        View view = LayoutInflater.from(contexts)
                .inflate(R.layout.custom_toast, null);

        TextView tvMessage = view.findViewById(R.id.tvMessage);
        TextView tv_title = view.findViewById(R.id.tv_title);

        tv_title.setText(R.string.congratulation);
        tvMessage.setText(msg);
        toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();

        // view.setBackgroundResource(R.color.colorPrimary);
        toast.show();

    }

    public void customtoastError(String name, Context contexts) {

        String str = name.substring(0, 1).toUpperCase(Locale.ROOT);
        String str1 = name.substring(1, name.length()).toLowerCase(Locale.ROOT);
        String msg = str + str1;
        Log.e("vgbhnjmk,", msg);
        toast = new Toast(contexts);

        View view = LayoutInflater.from(contexts)
                .inflate(R.layout.custom_toast, null);

        TextView tvMessage = view.findViewById(R.id.tvMessage);
        TextView tv_title = view.findViewById(R.id.tv_title);

        tv_title.setText("Error");
        tvMessage.setText(msg);

        toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();

        // view.setBackgroundResource(R.color.colorPrimary);
        // toast.show();

    }

    public void SwitchFragment(Fragment fragment) {
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.framelayout, fragment)
                .addToBackStack(null).commit();

    }
    public void showDate(TextView tv_date) {
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel(tv_date);}
        };
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(context, R.style.AlertDialog,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }
    private void updateLabel(TextView tv_date){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        tv_date.setText(dateFormat.format(myCalendar.getTime()));

    }


    public void check(EditText number, String msg, String error, Context contexts) {
        if (number.getText().toString().isEmpty()) {
            customtoastError(error, contexts);

        } else {
            customtoastSuccess(msg, contexts);
        }
    }


    public String checkNullValue(String value) {
        String str = "";
        if (value.isEmpty() || value.equals("") || value.equalsIgnoreCase("null")) {
            str = "";
        } else {
            str = value;
        }
        return str;
    }

    public String changeTime24to12HrsFormate(String time) {
        String tm = "";
        SimpleDateFormat time24hrs = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat time12hrs = new SimpleDateFormat("hh:mm a");
        try {

            Date date = time24hrs.parse(time);
            tm = time12hrs.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return tm;
    }

    public String changeTime12to24HrsFormate(String time) {
        String tm = "";
        SimpleDateFormat time12hrs = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat time24hrs = new SimpleDateFormat("hh:mm a");
        try {
            Date date = time12hrs.parse(time);
            tm = time24hrs.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return tm;


    }
    public void spinerList(Spinner spiner, ArrayList<String> spiner_arryList) {

        spiner_arryList = new ArrayList<>();
        spiner_arryList.add( "choose" );
        spiner_arryList.add( "dfghjk " );
        spiner_arryList.add( "dfhgjk" );
        spiner_arryList.add( " sfgh" );

        ArrayAdapter<String> adapter_price = new ArrayAdapter<>( context, R.layout.support_simple_spinner_dropdown_item, spiner_arryList );
        adapter_price.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner.setAdapter( adapter_price );

        spiner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText( context, "You Selected" + position, Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

    }

    public  void popupEdit_Delete(ImageView iv_edit){
        Context vv = new ContextThemeWrapper(context, R.style.PopupMenu);

        PopupMenu popupMenu = new PopupMenu(vv,iv_edit);
        popupMenu.getMenuInflater().inflate(R.menu.popup_hotel,popupMenu.getMenu());
        insertMenuItemIcons(context, popupMenu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.popup_edit:

                        break;
                    case R.id.popup_remove:
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle(R.string.Delete);
                        builder.setMessage(R.string.delete_dailoge_msg);
                        builder.setCancelable(false);
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();

                            }
                        });
                        final AlertDialog dialog=builder.create();
                        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void onShow(DialogInterface dialogInterface) {
                                dialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getColor(R.color.colorPrimary));
                                dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(context.getColor(R.color.colorPrimary));

                            }
                        });
                        dialog.show();


                        break;
                }
                return true;
            }
        });

        popupMenu.show();
//                MenuPopupHelper helper = new MenuPopupHelper(context, (MenuBuilder) popupMenu.getMenu());
//                helper.setForceShowIcon(true);
//                helper.setGravity( Gravity.END);
//                helper.show();

    }
    private  boolean hasIcon(Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            if (menu.getItem(i).getIcon() != null)
                return true;
        }
        return false;
    }
    private void insertMenuItemIcons(Context context, PopupMenu popupMenu) {
        Menu menu = popupMenu.getMenu();
        if (hasIcon(menu)) {
            for (int i = 0; i < menu.size(); i++) {
                insertMenuItemIcon(context, menu.getItem(i));
            }
        }
    }

    private void insertMenuItemIcon(Context context, MenuItem item) {
        Drawable icon = item.getIcon();

        // If there's no icon, we insert a transparent one to keep the title aligned with the items
        // which do have icons.
        if (icon == null) icon = new ColorDrawable( Color.TRANSPARENT);

        int iconSize = context.getResources().getDimensionPixelSize(R.dimen.tv_bold_12pt);
        icon.setBounds(0, 0, iconSize, iconSize);
        ImageSpan imageSpan = new ImageSpan(icon);

        // Add a space placeholder for the icon, before the title.
        SpannableStringBuilder ssb = new SpannableStringBuilder("       " + item.getTitle());

        // Replace the space placeholder with the icon.
        ssb.setSpan(imageSpan, 1, 2, 0);
        item.setTitle(ssb);
        // Set the icon to null just in case, on some weird devices, they've customized Android to display
        // the icon in the menu... we don't want two icons to appear.
        item.setIcon(null);
    }






    public void showToast(String name) {
        String str = name.substring(0, 1).toUpperCase(Locale.ROOT);
        String str1 = name.substring(1, name.length()).toLowerCase(Locale.ROOT);
        String msg = str + str1;
        Log.e("toast,", "" + msg);
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showProgressDailoge(ProgressDialog mProgressDialog, String msg) {
        if (TextUtils.isEmpty(msg)) {
            msg = "Please wait...";
        }
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    public static void dismissProgress(ProgressDialog mProgressDialog) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
            }
        }, 500);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void errorToast(Context context, String msg) {
        try {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.success_toast, null);

            TextView tv_msg = view.findViewById(R.id.tv_msg);
            String str = msg.substring(0, 1).toUpperCase(Locale.ROOT);
            String str1 = msg.substring(1, msg.length()).toLowerCase(Locale.ROOT);
            String msg_s = str + str1;
            Log.e("toast,", "" + msg_s);
            LinearLayout lin_main = view.findViewById(R.id.lin_main);
            lin_main.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.red)));

            tv_msg.setText(msg_s);
            toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(view);
            toast.show();

            // view.setBackgroundResource(R.color.colorPrimary);
            toast.show();

        } catch (Exception e) {
            Log.e("Exception", "errorToast: " + e);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void successToast(Context context, String msg) {
        try {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.success_toast, null);

            TextView tv_msg = view.findViewById(R.id.tv_msg);
            String str = msg.substring(0, 1).toUpperCase(Locale.ROOT);
            String str1 = msg.substring(1, msg.length()).toLowerCase(Locale.ROOT);
            String msg_s = str + str1;
            Log.e("successToast,", "" + msg_s);
            tv_msg.setText(msg_s);
            LinearLayout lin_main = view.findViewById(R.id.lin_main);
            lin_main.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.green)));
            toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(view);
            toast.show();

            // view.setBackgroundResource(R.color.colorPrimary);
            toast.show();


        } catch (Exception ex) {
            Log.e("Exception", "successToast: " + ex);

        }


    }

    Handler handler = new Handler();


    @RequiresApi(api = Build.VERSION_CODES.M)
    public String allValidation(String type, EditText et) {

        String val = "";
        String result = "y";
        switch (type) {
            case "mobile":
                if (et.getText().toString().isEmpty())
                {
                    val = et.getText().toString();
                    errorToast(context, "Mobile Number required");
                    et.setError("Mobile Number required");
                    et.requestFocus();
                    result = "Mobile Number required";
                } else if (et.getText().toString().length() != 10) {

                    errorToast(context, "Please fill valid mobile number");
                    et.setError("Please fill valid mobile number");
                    et.requestFocus();
                    result = "Please fill valid mobile number";
                } else if (Integer.parseInt(String.valueOf(et.getText().toString().charAt(0))) < 6) {
                    errorToast(context, "Mobile number should be start 6 or greater 0");
                    et.setError("Mobile number should be start 6 or greater 0");
                    et.requestFocus();
                    result = "Mobile number should be start 6 or greater 0";

                } else {
                    result = "correct";
                }

                break;
            case "pincode":
                if (et.getText().toString().isEmpty())
                {
                    errorToast(context, "Pincode  required");
                    et.setError("Pincode required");
                    et.requestFocus();
                    result = "Pincode required";
                }
                else if (Integer.parseInt(String.valueOf(et.getText().toString().charAt(0))) < 6) {

                    errorToast(context, "Please fill valid pincode");
                    et.setError("Please fill valid pincode");
                    et.requestFocus();
                    result = "Please fill valid pincode";
                }  else {
                    result = "correct";
                }
                break;
            case "email_ID":
                if(et.getText().toString().isEmpty()){
                    errorToast(context, "Enter email-ID");
                    et.setError("Enter email-ID");
                    et.requestFocus();}
                else if (!Patterns.EMAIL_ADDRESS.matcher(emailPattern).matches()){
                    et.setError("Invalid email-ID");
                    et.requestFocus();
                    errorToast(context, "Invalid email-ID");
                    et.setError("Invalid email-ID");
                    et.requestFocus();
                    result = "Invalid email-ID";

                } else {
                    result = "correct";
                }
                break;
            case "otp":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "Otp required");
                    et.setError("Otp required");
                    et.requestFocus();
                    result = "Otp required";
                } else if (et.getText().toString().length() != 4) {
                    errorToast(context, "Please enter 4 digit value");
                    et.setError("Please enter 4 digit value");
                    et.requestFocus();
                    result = "Please enter 4 digit value";
                } else {
                    result = "correct";

                }
                break;
            case "upi":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "Please fill UPI ID");
                    et.setError("Please fill UPI ID");
                    et.requestFocus();
                    result = "Please fill UPI ID";
                } else if (!p.matcher(et.getText().toString()).matches()) {
                    errorToast(context, "Please fill valid UPI ID");
                    et.setError("Please fill valid UPI ID");
                    et.requestFocus();
                    result = "Please fill valid UPI ID";
                } else {
                    result = "correct";

                }
                break;

            case "account_number":

                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "Account number required");
                    et.setError("Account number required");
                    et.requestFocus();
                    result = "Account number required";
                } else if (!et.getText().toString().matches("[a-zA-Z ]+")) {

                    errorToast(context, "Please fill valid account number");
                    et.setError("Please fill valid account number");
                    et.requestFocus();
                    result = "Please fill valid account number";
                } else if (et.getText().toString().length() < 9) {

                    errorToast(context, "Please fill Valid account number");
                    et.setError("Please fill Valid account number");
                    et.requestFocus();
                    result = "Please fill valid account number";
                } else {
                    result = "correct";

                }
                break;
            case "string":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "Please fill value");
                    et.setError("Please fill value");
                    et.requestFocus();
                    result = "Please fill value";
                } else if (et.getText().toString().matches(regexStr)) {
                    errorToast(context, "Please fill character value ");
                    et.setError("Please fill character value");
                    et.requestFocus();
                    result = "Please fill character value";
                } else {
                    result = "correct";

                }
                break;

            case "ifsc_code":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "IFSC Code required");
                    et.setError("IFSC Code required");
                    et.requestFocus();
                    result = "IFSC Code required";
                } else if (!pattern_ifsc.matcher(et.getText().toString()).matches()) {
                    errorToast(context, "Please fill valid IFSC Code");
                    et.setError("Please fill valid IFSC Code");
                    et.requestFocus();
                    result = "Please fill valid IFSC Code";
                } else {
                    result = "correct";

                }
                break;
            case "integer":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    et.setError("Value required");
                    et.requestFocus();
                    errorToast(context, "Value required");
                    result = "Value required";
                } else if (Integer.parseInt(et.getText().toString()) < 0) {
                    errorToast(context, "Please fill valid value");
                    et.setError("Please fill valid value");
                    et.requestFocus();
                    errorToast(context, "Please fill valid value")
                    ;
                    result = "Please fill valid value";
                } else {
                    result = "correct";

                }

                break;

            case "subscriber_id":
                if (et.getText().toString().length() != 10) {
                    errorToast(context, "Please fill valid ID");
                    et.setError("Please fill valid ID");
                    et.requestFocus();

                    result = "Please fill valid ID";
                } else {
                    result = "correct";

                }
                break;
            case "consumer_number":
            case "subscriber_number":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "Please fill subscriber number");
                    et.setError("Please fill subscriber number");
                    et.requestFocus();
                    result = "Please fill subscriber number";
                } else if (et.getText().toString().length() != 10) {
                    errorToast(context, "Please fill valid subscriber number");
                    et.setError("Please fill valid subscriber number");
                    et.requestFocus();
                    result = "Please fill valid subscriber number";
                } else {
                    result = "correct";

                }
                break;
            case "customer_id":
            case "vc_number":
            case "credit_card_number":
                if (et.getText().toString().length() < 3) {
                    errorToast(context, "Please fill valid number");
                    et.setError("Please fill valid number");
                    et.requestFocus();

                    result = "Please fill valid number";
                } else {
                    result = "correct";

                }
                break;
            case "vechicle_registered_number":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "vechicle number required");
                    et.setError("vechicle number required");
                    et.requestFocus();

                    result = "vechicle number required";
                } else if (!vechicle.matcher(et.getText().toString()).matches()) {
                    errorToast(context, "Please fill valid vechicle number");
                    et.setError("Please fill valid vechicle number");
                    et.requestFocus();
                    result = "Please fill valid vechicle number";
                } else {
                    result = "correct";

                }
                break;
            case "wallet_id":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "Wallet id required");
                    et.setError("Wallet id required");
                    et.requestFocus();
                    result = "Wallet id required";
                } else if (et.getText().toString().length() != 14) {
                    errorToast(context, "Please fill valid wallet id");
                    et.setError("Please fill valid wallet id");
                    et.requestFocus();

                    result = "Please fill valid wallet id";
                } else {
                    result = "correct";

                }
                break;
            case "crn_number":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "CRN number required");
                    et.setError("CRN number required");
                    et.requestFocus();

                    result = "CRN number required";
                } else if (et.getText().toString().length() != 11) {
                    errorToast(context, "Please fill valid CRN number ");
                    et.setError("Please fill valid CRN number");
                    et.requestFocus();

                    result = "Please fill valid CRN number ";
                } else {
                    result = "correct";

                }
                break;
            case "customer_id_10":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "Customer id required");
                    et.setError("Customer id required");
                    et.requestFocus();
                    result = "Customer id required";
                } else if (et.getText().toString().length() != 10) {
                    errorToast(context, "Please fill valid customer id");
                    et.setError("Please fill valid customer id");
                    et.requestFocus();

                    result = "Please fill valid customer id";
                } else {
                    result = "correct";

                }
                break;
            case "bp_no":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "BP No. required");
                    et.setError("BP No. required");
                    et.requestFocus();
                    result = "CBP No. required";
                } else if (et.getText().toString().length() < 1) {
                    errorToast(context, "Please fill valid BP No.");
                    et.setError("Please fill valid BP No.");
                    et.requestFocus();

                    result = "Please fill valid BP No.";
                } else {
                    result = "correct";

                }
                break;
            case "computer_code":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "Computer code required");
                    et.setError("Computer code required");
                    et.requestFocus();
                    result = "Computer code required";
                } else if (et.getText().toString().length() < 1) {
                    errorToast(context, "Please fill valid computer code required");
                    et.setError("Please fill valid computer code required");
                    et.requestFocus();

                    result = "Please fill valid computer code .";
                } else {
                    result = "correct";

                }
                break;
            case "k_no":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "K no. required");
                    et.setError("K no. required");
                    et.requestFocus();
                    result = "K no. required";
                } else if (et.getText().toString().length() < 12) {
                    errorToast(context, "Please fill valid K no.");
                    et.setError("Please fill valid K no.");
                    et.requestFocus();

                    result = "Please fill valid K no.";
                } else {
                    result = "correct";

                }
                break;
            case "service_number":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "Service number required");
                    et.setError("Service number required");
                    et.requestFocus();

                    result = "Service number required";
                } else if (et.getText().toString().length() < 8) {
                    errorToast(context, "Please fill valid service number");
                    et.setError("Please fill valid service number");
                    et.requestFocus();

                    result = "Please fill valid service number";
                } else {
                    result = "correct";

                }
                break;
            case "ca_number":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "CA number required");
                    et.setError("CA number required");
                    et.requestFocus();
                    result = "CA number required";
                } else if (et.getText().toString().length() < 9) {
                    errorToast(context, "Please fill valid CA number");
                    et.setError("Please fill valid CA number");
                    et.requestFocus();

                    result = "Please fill valid CA number";
                } else {
                    result = "correct";

                }
                break;
            case "Telephone_no":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "Telephone  number required");
                    result = "Telephone number required";
                } else if (et.getText().toString().length() < 11) {
                    errorToast(context, "Please fill valid Telephone number");

                    result = "Please fill valid Telephone number";
                } else {
                    result = "correct";

                }
                break;
            case "sub_code":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "Sub code required");
                    result = "Sub code required";
                } else if (et.getText().toString().length() < 13) {
                    errorToast(context, "Please fill valid Sub code");

                    result = "Please fill valid Sub code";
                } else {
                    result = "correct";

                }
                break;
            case "directory_number":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "Directory number required");
                    result = "Directory number required";
                } else if (et.getText().toString().length() < 10) {
                    errorToast(context, "Please fill valid Directory number");

                    result = "Please fill valid Directory number";
                } else {
                    result = "correct";

                }
                break;
            case "rc_number":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "RC number required");
                    result = "RC number required";
                } else if (et.getText().toString().length() < 2) {
                    errorToast(context, "Please fill valid RC number");

                    result = "Please fill valid RC number";
                } else {
                    result = "correct";

                }
                break;
            case "pti_number":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "PTI number required");
                    result = "PTI number required";
                } else if (!pattern_PTIN.matcher(et.getText().toString()).matches()) {
                    errorToast(context, "Please fill valid PTI number");

                    result = "Please fill valid PTI number";
                } else {
                    result = "correct";

                }
            case "Pan Card":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "Pan card number required");
                    result = "Pan card number required";
                } else if (!PanCrad.matcher(et.getText().toString()).matches()) {
                    errorToast(context, "Please fill valid Pan card number");
                    et.setError("Please fill valid Pan card number");
                    et.requestFocus();

                    result = "Please fill valid Pan card number";
                } else {
                    result = "correct";

                }

                break;
            case "Voter ID Card":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "Voter ID card number required");
                    et.setError("Voter ID card number required");
                    et.requestFocus();
                    result = "Voter ID card number required";
                } else if (!Voterid.matcher(et.getText().toString()).matches()) {
                    errorToast(context, "Please fill valid Voter ID card number");
                    et.setError("Please fill valid Voter ID card number");
                    et.requestFocus();

                    result = "Please fill valid Voter ID card number";
                } else {
                    result = "correct";

                }

                break;
            case "Driving Licence":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "Driving Licence number required");
                    et.setError("Driving Licence number required");
                    et.requestFocus();
                    result = "Driving Licence number required";
                } else if (!Drivinglic.matcher(et.getText().toString()).matches()) {
                    errorToast(context, "Please fill valid Driving Licence number");
                    et.setError("Please fill valid Driving Licence number");
                    et.requestFocus();

                    result = "Please fill valid Driving Licence number";
                } else {
                    result = "correct";

                }

                break;
            case "Passport":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "Passport number required");
                    et.setError("Passport number required");
                    et.requestFocus();

                    result = "Passport number required";
                } else if (!Passport.matcher(et.getText().toString()).matches()) {
                    errorToast(context, "Please fill valid Passport number");
                    et.setError("Please fill valid Passport number");
                    et.requestFocus();

                    result = "Please fill valid Passport number";
                } else {
                    result = "correct";

                }
                break;
            case "NREGA Job Card Number":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "NREGA Job Card Number required");
                    et.setError("NREGA Job Card Number required");
                    et.requestFocus();
                    result = "NREGA Job Card Number required";
                }
//                else if (!NREGA.matcher (et.getText ( ).toString ( )).matches ( )) {
//                    errorToast (context, "Please fill valid NREGA Job Card Number");
//
//                    result = "Please fill valid NREGA Job Card Number";
//                }
                else {
                    result = "correct";

                }


                break;


        }


//        result="correct";
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public String allValidatiopnTextview(String tv_type, TextView et) {
        String val = "";
        String result = "y";
        switch (tv_type) {

            case "mobile":
                if (et.getText().toString().isEmpty())
                {
                    val = et.getText().toString();
                    errorToast(context, "Mobile Number required");
                    et.setError("Mobile Number required");
                    et.requestFocus();
                    result = "Mobile Number required";
                } else if (et.getText().toString().length() != 10) {

                    errorToast(context, "Please fill valid mobile number");
                    et.setError("Please fill valid mobile number");
                    et.requestFocus();
                    result = "Please fill valid mobile number";
                } else if (Integer.parseInt(String.valueOf(et.getText().toString().charAt(0))) < 6) {
                    errorToast(context, "Mobile number should be start 6 or greater 0");
                    et.setError("Mobile number should be start 6 or greater 0");
                    et.requestFocus();
                    result = "Mobile number should be start 6 or greater 0";

                } else {
                    result = "correct";
                }

                break;
            case "pincode":
                if (et.getText().toString().isEmpty())
                {
                    errorToast(context, "Pincode  required");
                    et.setError("Pincode required");
                    et.requestFocus();
                    result = "Pincode required";
                }
                else if (Integer.parseInt(String.valueOf(et.getText().toString().charAt(0))) < 6) {

                    errorToast(context, "Please fill valid pincode");
                    et.setError("Please fill valid pincode");
                    et.requestFocus();
                    result = "Please fill valid pincode";
                }  else {
                    result = "correct";
                }
                break;
            case "email_ID":
                if(et.getText().toString().isEmpty()){
                    errorToast(context, "Enter email-ID");
                    et.setError("Enter email-ID");
                    et.requestFocus();}
                else if (!Patterns.EMAIL_ADDRESS.matcher(emailPattern).matches()){
                    et.setError("Invalid email-ID");
                    et.requestFocus();
                    errorToast(context, "Invalid email-ID");
                    et.setError("Invalid email-ID");
                    et.requestFocus();
                    result = "Invalid email-ID";

                } else {
                    result = "correct";
                }
                break;
            case "otp":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "Otp required");
                    et.setError("Otp required");
                    et.requestFocus();
                    result = "Otp required";
                } else if (et.getText().toString().length() != 4) {
                    errorToast(context, "Please enter 4 digit value");
                    et.setError("Please enter 4 digit value");
                    et.requestFocus();
                    result = "Please enter 4 digit value";
                } else {
                    result = "correct";

                }
                break;
            case "upi":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "Please fill UPI ID");
                    et.setError("Please fill UPI ID");
                    et.requestFocus();
                    result = "Please fill UPI ID";
                } else if (!p.matcher(et.getText().toString()).matches()) {
                    errorToast(context, "Please fill valid UPI ID");
                    et.setError("Please fill valid UPI ID");
                    et.requestFocus();
                    result = "Please fill valid UPI ID";
                } else {
                    result = "correct";

                }
                break;

            case "account_number":

                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "Account number required");
                    et.setError("Account number required");
                    et.requestFocus();
                    result = "Account number required";
                } else if (!et.getText().toString().matches("[a-zA-Z ]+")) {

                    errorToast(context, "Please fill valid account number");
                    et.setError("Please fill valid account number");
                    et.requestFocus();
                    result = "Please fill valid account number";
                } else if (et.getText().toString().length() < 9) {

                    errorToast(context, "Please fill Valid account number");
                    et.setError("Please fill Valid account number");
                    et.requestFocus();
                    result = "Please fill valid account number";
                } else {
                    result = "correct";

                }
                break;
            case "string":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "Please fill value");
                    et.setError("Please fill value");
                    et.requestFocus();
                    result = "Please fill value";
                } else if (et.getText().toString().matches(regexStr)) {
                    errorToast(context, "Please fill character value ");
                    et.setError("Please fill character value");
                    et.requestFocus();
                    result = "Please fill character value";
                } else {
                    result = "correct";

                }
                break;

            case "ifsc_code":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "IFSC Code required");
                    et.setError("IFSC Code required");
                    et.requestFocus();
                    result = "IFSC Code required";
                } else if (!pattern_ifsc.matcher(et.getText().toString()).matches()) {
                    errorToast(context, "Please fill valid IFSC Code");
                    et.setError("Please fill valid IFSC Code");
                    et.requestFocus();
                    result = "Please fill valid IFSC Code";
                } else {
                    result = "correct";

                }
                break;
            case "integer":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    et.setError("Value required");
                    et.requestFocus();
                    errorToast(context, "Value required");
                    result = "Value required";
                } else if (Integer.parseInt(et.getText().toString()) < 0) {
                    errorToast(context, "Please fill valid value");
                    et.setError("Please fill valid value");
                    et.requestFocus();
                    errorToast(context, "Please fill valid value")
                    ;
                    result = "Please fill valid value";
                } else {
                    result = "correct";

                }

                break;

            case "subscriber_id":
                if (et.getText().toString().length() != 10) {
                    errorToast(context, "Please fill valid ID");
                    et.setError("Please fill valid ID");
                    et.requestFocus();

                    result = "Please fill valid ID";
                } else {
                    result = "correct";

                }
                break;
            case "consumer_number":
            case "subscriber_number":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "Please fill subscriber number");
                    et.setError("Please fill subscriber number");
                    et.requestFocus();
                    result = "Please fill subscriber number";
                } else if (et.getText().toString().length() != 10) {
                    errorToast(context, "Please fill valid subscriber number");
                    et.setError("Please fill valid subscriber number");
                    et.requestFocus();
                    result = "Please fill valid subscriber number";
                } else {
                    result = "correct";

                }
                break;
            case "customer_id":
            case "vc_number":
            case "credit_card_number":
                if (et.getText().toString().length() < 3) {
                    errorToast(context, "Please fill valid number");
                    et.setError("Please fill valid number");
                    et.requestFocus();

                    result = "Please fill valid number";
                } else {
                    result = "correct";

                }
                break;
            case "vechicle_registered_number":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "vechicle number required");
                    et.setError("vechicle number required");
                    et.requestFocus();

                    result = "vechicle number required";
                } else if (!vechicle.matcher(et.getText().toString()).matches()) {
                    errorToast(context, "Please fill valid vechicle number");
                    et.setError("Please fill valid vechicle number");
                    et.requestFocus();
                    result = "Please fill valid vechicle number";
                } else {
                    result = "correct";

                }
                break;
            case "wallet_id":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "Wallet id required");
                    et.setError("Wallet id required");
                    et.requestFocus();
                    result = "Wallet id required";
                } else if (et.getText().toString().length() != 14) {
                    errorToast(context, "Please fill valid wallet id");
                    et.setError("Please fill valid wallet id");
                    et.requestFocus();

                    result = "Please fill valid wallet id";
                } else {
                    result = "correct";

                }
                break;
            case "crn_number":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "CRN number required");
                    et.setError("CRN number required");
                    et.requestFocus();

                    result = "CRN number required";
                } else if (et.getText().toString().length() != 11) {
                    errorToast(context, "Please fill valid CRN number ");
                    et.setError("Please fill valid CRN number");
                    et.requestFocus();

                    result = "Please fill valid CRN number ";
                } else {
                    result = "correct";

                }
                break;
            case "customer_id_10":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "Customer id required");
                    et.setError("Customer id required");
                    et.requestFocus();
                    result = "Customer id required";
                } else if (et.getText().toString().length() != 10) {
                    errorToast(context, "Please fill valid customer id");
                    et.setError("Please fill valid customer id");
                    et.requestFocus();

                    result = "Please fill valid customer id";
                } else {
                    result = "correct";

                }
                break;
            case "bp_no":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "BP No. required");
                    et.setError("BP No. required");
                    et.requestFocus();
                    result = "CBP No. required";
                } else if (et.getText().toString().length() < 1) {
                    errorToast(context, "Please fill valid BP No.");
                    et.setError("Please fill valid BP No.");
                    et.requestFocus();

                    result = "Please fill valid BP No.";
                } else {
                    result = "correct";

                }
                break;
            case "computer_code":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "Computer code required");
                    et.setError("Computer code required");
                    et.requestFocus();
                    result = "Computer code required";
                } else if (et.getText().toString().length() < 1) {
                    errorToast(context, "Please fill valid computer code required");
                    et.setError("Please fill valid computer code required");
                    et.requestFocus();

                    result = "Please fill valid computer code .";
                } else {
                    result = "correct";

                }
                break;
            case "k_no":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "K no. required");
                    et.setError("K no. required");
                    et.requestFocus();
                    result = "K no. required";
                } else if (et.getText().toString().length() < 12) {
                    errorToast(context, "Please fill valid K no.");
                    et.setError("Please fill valid K no.");
                    et.requestFocus();

                    result = "Please fill valid K no.";
                } else {
                    result = "correct";

                }
                break;
            case "service_number":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "Service number required");
                    et.setError("Service number required");
                    et.requestFocus();

                    result = "Service number required";
                } else if (et.getText().toString().length() < 8) {
                    errorToast(context, "Please fill valid service number");
                    et.setError("Please fill valid service number");
                    et.requestFocus();

                    result = "Please fill valid service number";
                } else {
                    result = "correct";

                }
                break;
            case "ca_number":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "CA number required");
                    et.setError("CA number required");
                    et.requestFocus();
                    result = "CA number required";
                } else if (et.getText().toString().length() < 9) {
                    errorToast(context, "Please fill valid CA number");
                    et.setError("Please fill valid CA number");
                    et.requestFocus();

                    result = "Please fill valid CA number";
                } else {
                    result = "correct";

                }
                break;
            case "Telephone_no":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "Telephone  number required");
                    result = "Telephone number required";
                } else if (et.getText().toString().length() < 11) {
                    errorToast(context, "Please fill valid Telephone number");

                    result = "Please fill valid Telephone number";
                } else {
                    result = "correct";

                }
                break;
            case "sub_code":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "Sub code required");
                    result = "Sub code required";
                } else if (et.getText().toString().length() < 13) {
                    errorToast(context, "Please fill valid Sub code");

                    result = "Please fill valid Sub code";
                } else {
                    result = "correct";

                }
                break;
            case "directory_number":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "Directory number required");
                    result = "Directory number required";
                } else if (et.getText().toString().length() < 10) {
                    errorToast(context, "Please fill valid Directory number");

                    result = "Please fill valid Directory number";
                } else {
                    result = "correct";

                }
                break;
            case "rc_number":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "RC number required");
                    result = "RC number required";
                } else if (et.getText().toString().length() < 2) {
                    errorToast(context, "Please fill valid RC number");

                    result = "Please fill valid RC number";
                } else {
                    result = "correct";

                }
                break;
            case "pti_number":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "PTI number required");
                    result = "PTI number required";
                } else if (!pattern_PTIN.matcher(et.getText().toString()).matches()) {
                    errorToast(context, "Please fill valid PTI number");

                    result = "Please fill valid PTI number";
                } else {
                    result = "correct";

                }
            case "Pan Card":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "Pan card number required");
                    result = "Pan card number required";
                } else if (!PanCrad.matcher(et.getText().toString()).matches()) {
                    errorToast(context, "Please fill valid Pan card number");
                    et.setError("Please fill valid Pan card number");
                    et.requestFocus();

                    result = "Please fill valid Pan card number";
                } else {
                    result = "correct";

                }

                break;
            case "Voter ID Card":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "Voter ID card number required");
                    et.setError("Voter ID card number required");
                    et.requestFocus();
                    result = "Voter ID card number required";
                } else if (!Voterid.matcher(et.getText().toString()).matches()) {
                    errorToast(context, "Please fill valid Voter ID card number");
                    et.setError("Please fill valid Voter ID card number");
                    et.requestFocus();

                    result = "Please fill valid Voter ID card number";
                } else {
                    result = "correct";

                }

                break;
            case "Driving Licence":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "Driving Licence number required");
                    et.setError("Driving Licence number required");
                    et.requestFocus();
                    result = "Driving Licence number required";
                } else if (!Drivinglic.matcher(et.getText().toString()).matches()) {
                    errorToast(context, "Please fill valid Driving Licence number");
                    et.setError("Please fill valid Driving Licence number");
                    et.requestFocus();

                    result = "Please fill valid Driving Licence number";
                } else {
                    result = "correct";

                }

                break;
            case "Passport":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "Passport number required");
                    et.setError("Passport number required");
                    et.requestFocus();

                    result = "Passport number required";
                } else if (!Passport.matcher(et.getText().toString()).matches()) {
                    errorToast(context, "Please fill valid Passport number");
                    et.setError("Please fill valid Passport number");
                    et.requestFocus();

                    result = "Please fill valid Passport number";
                } else {
                    result = "correct";

                }
                break;
            case "NREGA Job Card Number":
                if (et.getText().toString().isEmpty()) {
                    val = et.getText().toString();
                    errorToast(context, "NREGA Job Card Number required");
                    et.setError("NREGA Job Card Number required");
                    et.requestFocus();
                    result = "NREGA Job Card Number required";
                }
//                else if (!NREGA.matcher (et.getText ( ).toString ( )).matches ( )) {
//                    errorToast (context, "Please fill valid NREGA Job Card Number");
//
//                    result = "Please fill valid NREGA Job Card Number";
//                }
                else {
                    result = "correct";

                }


                break;


        }


//        result="correct";
        return result;
}


}