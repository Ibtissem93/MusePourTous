package com.example.museepourtous;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

public class Language extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_language);
        setTitle(R.string.app_name);
        loadLocale();

        /*ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));*/


        /* Button changeLang = findViewById(R.id.changeLang);*/
        Button arabe = findViewById(R.id.ar);
        Button francais = findViewById(R.id.fr);
        Button anglais = findViewById(R.id.en);


       /* changeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLanguageDialog();
            }
        });*/

        arabe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("ar");

                recreate();
                ShowVNV();
            }
        });

        francais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("fr");
                recreate();
                ShowVNV();
            }
        });

        anglais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("en");
                recreate();
                ShowVNV();
            }
        });
    }

    private  void  showChangeLanguageDialog(){

        final String[] listItems = {"العربية","Français","English"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Language.this);
        mBuilder.setTitle("Choose Language...");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    setLocale("ar");
                    recreate();
                }

                else if(which==1){
                    setLocale("fr");
                    recreate();
                }

                else if(which==2){
                    setLocale("en");
                    recreate();
                }

                dialog.dismiss();
                ShowVNV();

            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();

    }

    private void ShowVNV() {

        Intent intent = new Intent(this, VNV.class);

        startActivity(intent);
    }

 public void setLocale(String lang) {
       Locale locale = new Locale(lang);

     Locale.setDefault(locale);
     Resources resources = getResources();
     Configuration configuration = resources.getConfiguration();
     DisplayMetrics displayMetrics = resources.getDisplayMetrics();
     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
         configuration.setLocale(locale);
     } else{
         configuration.locale=locale;
     }
     if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N){
         getApplicationContext().createConfigurationContext(configuration);
     } else {
         resources.updateConfiguration(configuration,displayMetrics);
     }

      SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
      editor.putString("My_Lang",lang);
      editor.apply();

    }


    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang","");
        setLocale(language);

    }




}
