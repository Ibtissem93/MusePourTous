package com.example.museepourtous;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Bardo extends AppCompatActivity implements View.OnClickListener {


 public String NameButton;
 Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bardo);
        Button statut = findViewById(R.id.statut);
        Button statut2 = findViewById(R.id.statut2);
        Button statut3 = findViewById(R.id.statut3);

        statut.setOnClickListener(this);
        statut2.setOnClickListener(this);
        statut3.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.statut:
                 intent = new Intent(Bardo.this,BardoData.class);
                NameButton = ((Button) v).getText().toString();
                intent.putExtra("namebutton",NameButton);
                startActivity(intent);
                break;

            case R.id.statut2:
               intent = new Intent(Bardo.this,BardoData.class);
                NameButton = ((Button) v).getText().toString();
                intent.putExtra("namebutton",NameButton);
                startActivity(intent);
                break;

            case R.id.statut3:
                intent = new Intent(Bardo.this,BardoData.class);
                NameButton = ((Button) v).getText().toString();
                intent.putExtra("namebutton",NameButton);
                startActivity(intent);
                break;


        }

    }
}
