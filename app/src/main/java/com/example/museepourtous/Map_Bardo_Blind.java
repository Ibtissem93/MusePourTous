package com.example.museepourtous;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Map_Bardo_Blind extends AppCompatActivity {

    public String NameButton;
    int i = 0;
    Button button1;
    LinearLayout relativeLayout;
    Intent intent;
    ViewGroup rootLayout;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map__bardo__blind);
        setTitle(R.string.mapB);
        scrollView = findViewById(R.id.scrollBlind);
        relativeLayout = findViewById(R.id.linearScroll);

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Positions");

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    for (DataSnapshot snapshot1 : snapshot.getChildren())
                    {
                        i++;
                        String uid = snapshot1.getKey();
                        String name =  snapshot1.child("buttonTitle").getValue().toString();

                        button1 = new Button(Map_Bardo_Blind.this);
                        button1.setId(i);
                        button1.setText(name);
                        button1.setTag(uid);

                        button1.setLayoutParams(new
                                RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));

                        if (relativeLayout != null)
                        {
                            relativeLayout.addView(button1);
                            button1 = ((Button) findViewById(i));
                            button1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    intent = new Intent(Map_Bardo_Blind.this, Bardo_Data_Blind.class);
                                    NameButton = ((Button) v).getTag().toString();
                                    intent.putExtra("namebuttoon", NameButton);
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
