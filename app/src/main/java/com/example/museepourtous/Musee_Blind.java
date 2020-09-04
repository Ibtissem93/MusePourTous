package com.example.museepourtous;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Musee_Blind extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musee__blind);
        Button bardo = findViewById(R.id.bardoB);
        setTitle(R.string.musee);

        bardo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Musee_Blind.this,Map_Bardo_Blind.class);
                startActivity(intent);
            }
        });
    }
}
