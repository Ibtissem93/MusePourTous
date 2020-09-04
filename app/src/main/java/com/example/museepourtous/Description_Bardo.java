package com.example.museepourtous;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Description_Bardo extends AppCompatActivity {

    Button btn_bardo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description__bardo);
        setTitle(R.string.titeldesBardo);

        btn_bardo = (Button) findViewById(R.id.btn_bardo_des);

        btn_bardo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Description_Bardo.this,Bardo.class);
                startActivity(i);
            }
        });
    }
}
