package com.example.thetngonehtoo.dialogtest;

import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ArrayList mSelectedItems;
    AlertDialog dialog;

    TextView tvMessage;

    String msg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMessage = (TextView) findViewById(R.id.tvMessage);

        Button btnlist = (Button) findViewById(R.id.btnlist);
        Button btnmultiple = (Button) findViewById(R.id.btnmultiple);
        Button btncustomlayout = (Button) findViewById(R.id.btncustomlayout);

        btnlist.setOnClickListener( this);
        btnmultiple.setOnClickListener( this);
        btncustomlayout.setOnClickListener( this);
    }

    public void onClick(android.view.View view){
        Button btnObj =(Button) view;
        switch (btnObj.getId()){

            case R.id.btnlist:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Pink Color")
                        .setItems(R.array.colors_array,new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                                msg = "";
                                String[] strarr = getResources().getStringArray(R.array.colors_array);
                                int[] ints = {getResources().getColor(R.color.colorRed),getResources().getColor(R.color.colorGreen),getResources().getColor(R.color.colorBlue),getResources().getColor(R.color.colorYellow)};
                                msg = strarr[which];

                              tvMessage.setBackgroundColor(ints[which]);
                                tvMessage.setText(Html.fromHtml(msg));
                            }
                        });
                builder.show();

                break;

            case R.id.btnmultiple:
                mSelectedItems = new ArrayList();
                AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
                builder2.setTitle("Pick your toppings")
                        .setMultiChoiceItems(R.array.toppings, null, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {
                                if (isChecked) {
                                    // If the user checked the item, add it to the selected items
                                    mSelectedItems.add(which);
                                } else if (mSelectedItems.contains(which)) {
                                    // Else, if the item is already in the array, remove it
                                    mSelectedItems.remove(Integer.valueOf(which));
                                }


                            }
                        })
                             .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK, so save the mSelectedItems results somewhere
                                // or return them to the component that opened the dialog
                                msg = "";
                                String[] strarr = getResources().getStringArray(R.array.toppings);
                                for(int i=0; i<mSelectedItems.size(); i++){
                                    int index = (Integer) mSelectedItems.get(i);
                                    msg += strarr[index]+"<br/>";
                                }
                                tvMessage.setText(Html.fromHtml(msg));
                              }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });




                dialog = builder2.show();

                break;

            case R.id.btncustomlayout:

                AlertDialog.Builder builder3 = new AlertDialog.Builder(MainActivity.this);
                // Get the layout inflater
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                final View dialogview = inflater.inflate(R.layout.dialogcustomlayout, null);

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder3.setView(dialogview)
                        // Add action buttons
                        .setPositiveButton("Signin", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                EditText etUserName = (EditText) dialogview.findViewById(R.id.username);
                                EditText etPassword = (EditText) dialogview.findViewById(R.id.password);

                                msg = "UserName : "+etUserName.getText().toString()+"<br/>";
                                msg += "Password : "+etPassword.getText().toString();

                                tvMessage.setText(Html.fromHtml(msg));
                            }
                        })
                        .setNegativeButton("Calcel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                dialog = builder3.show();

                break;
        }
    };
}
