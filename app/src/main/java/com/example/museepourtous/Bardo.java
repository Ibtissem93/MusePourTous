package com.example.museepourtous;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Bardo extends AppCompatActivity {


 public String NameButton;
 Intent intent;
 int i = 0;
 Button button1;
    RelativeLayout relativeLayout;
    ViewGroup rootLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bardo);
        setTitle(R.string.mapB);

        rootLayout = findViewById(R.id.Rl);
        relativeLayout = findViewById(R.id.Rl);


        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Positions");


        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        i++;
                       String uid = snapshot1.getKey();

                      String name = snapshot1.child("buttonTitle").getValue().toString();

                        button1 = new Button(Bardo.this);
                        button1.setId(i);
                        button1.setText(name);

                        button1.setTag(uid);

                        String x = snapshot1.child("xposition").getValue().toString();
                        String y = snapshot1.child("yposition").getValue().toString();

                        button1.setX(Float.parseFloat(x));
                        button1.setY(Float.parseFloat(y));

                        button1.setLayoutParams(new
                                RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));


                        if (relativeLayout != null) {
                            relativeLayout.addView(button1);


                            button1 = ((Button) findViewById(i));
                            button1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    intent = new Intent(Bardo.this, BardoData.class);
                                   // NameButton = ((Button) v).getText().toString();
                                    String NameButton = ((Button) v).getTag().toString();
                                    intent.putExtra("namebutton", NameButton);
                                    startActivity(intent);
                                }
                            });

                        }


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
