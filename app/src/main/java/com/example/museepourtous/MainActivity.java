package com.example.museepourtous;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);



        //   loadLocale();

        setContentView(R.layout.activity_main);
        Intent intent = new Intent(MainActivity.this,Download_Data.class);
        startActivity(intent);


        //   ActionBar actionBar = getSupportActionBar();
        // actionBar.setTitle(getResources().getString(R.string.app_name));

        /* Button changeLang = findViewById(R.id.changeLang);*/
      //  Button arabe = findViewById(R.id.ar);
      //  Button francais = findViewById(R.id.fr);
      //  Button anglais = findViewById(R.id.en);

       /* changeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLanguageDialog();
            }
        });*/

      /*  arabe.setOnClickListener(new View.OnClickListener() {
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
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
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

        Intent intent = new Intent(this,VNV.class);

        startActivity(intent);
    }

    public void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale=locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();

    }

    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang","");
        setLocale(language);

    }*/
    }
}
