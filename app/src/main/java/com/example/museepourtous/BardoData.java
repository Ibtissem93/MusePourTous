package com.example.museepourtous;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.bumptech.glide.Glide;


public class BardoData extends AppCompatActivity {
TextView title,description,textCurrentTime, textTotalDuration;
ImageView img, imagePlayPause;
private SeekBar playerSeekBar;
private MediaPlayer mediaPlayer;
private Handler handler = new Handler();
    String Soundurl;


DatabaseReference reff;
String data1;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bardo_data);
      //  Button btn = findViewById(R.id.btn_show);
        title = (TextView) findViewById(R.id.titlee);
        description = (TextView) findViewById(R.id.desc);
        img = (ImageView) findViewById(R.id.Img) ;
        imagePlayPause=findViewById(R.id.imagePlayPause);
        textCurrentTime=findViewById(R.id.textCurrentTime);
        textTotalDuration=findViewById(R.id.textTotalDuration);
        playerSeekBar=findViewById(R.id.playerSeekBar);
        mediaPlayer=new MediaPlayer();
        playerSeekBar.setMax(100);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            data1 = extras.getString("namebutton");
        }




   /* btn.setOnClickListener(new View.OnClickListener() {
           @Override
         public void onClick(View v) {
                String buttonName = ((Button) v).getText().toString();*/
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String  language = prefs.getString("My_Lang","");

          reff = FirebaseDatabase.getInstance().getReference();
           reff.keepSynced(true);
           if(language.equals("en")) {
                reff = FirebaseDatabase.getInstance().getReference().child("0").child("Musees").child("Bardo").child("Beacons").child(data1);

                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String titl = snapshot.child("title").getValue().toString();
                        String des = snapshot.child("description").getValue().toString();
                        String urrl = snapshot.child("imageURL").getValue().toString();
                        Soundurl = snapshot.child("soundURL").getValue().toString();
                        Glide.with(getApplicationContext()).load(urrl).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img);
                        title.setText(titl);
                        description.setText(des);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
          else  if (language.equals("fr")) {

                reff = FirebaseDatabase.getInstance().getReference().child("1").child("Musees").child("Bardo").child("Beacons").child(data1);

                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String titl = snapshot.child("title").getValue().toString();
                        String des = snapshot.child("description").getValue().toString();
                        String urrl = snapshot.child("imageURL").getValue().toString();
                        // Soundurl = snapshot.child("soundURL").getValue().toString();
                        Glide.with(getApplicationContext()).load(urrl).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img);
                        title.setText(titl);
                        description.setText(des);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

           else if (language.equals("ar")) {

                reff = FirebaseDatabase.getInstance().getReference().child("2").child("Musees").child("Bardo").child("Beacons").child(data1);

                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String titl = snapshot.child("title").getValue().toString();
                        String des = snapshot.child("description").getValue().toString();
                        String urrl = snapshot.child("imageURL").getValue().toString();
                        // Soundurl = snapshot.child("soundURL").getValue().toString();
                        Glide.with(getApplicationContext()).load(urrl).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img);
                        title.setText(titl);
                        description.setText(des);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
         // }
   // });

      imagePlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    handler.removeCallbacks(updater);
                    mediaPlayer.pause();
                    imagePlayPause.setImageResource(R.drawable.ic_play);
                }
                else {
                    mediaPlayer.start();
                    imagePlayPause.setImageResource(R.drawable.ic_pause);
                    updateSeekBar();
                }
            }
        });

        prepareMediaPlayer();

        playerSeekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                SeekBar seekBar = (SeekBar) v;
                int playPosition = (mediaPlayer.getDuration()/100)* seekBar.getProgress();
                mediaPlayer.seekTo(playPosition);
                textCurrentTime.setText(milliSecondsToTimer(mediaPlayer.getCurrentPosition()));
                return false;
            }
        });

        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                playerSeekBar.setSecondaryProgress(percent);
            }
        });

       mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playerSeekBar.setProgress(0);
                imagePlayPause.setImageResource(R.drawable.ic_play);
                textCurrentTime.setText(R.string.zero);
                textTotalDuration.setText(R.string.zero);
                mediaPlayer.reset();
                prepareMediaPlayer();
            }
        });

    }

    private void prepareMediaPlayer(){
        try {
            mediaPlayer.setDataSource(Soundurl);
            mediaPlayer.prepare();
            textTotalDuration.setText(milliSecondsToTimer((mediaPlayer.getDuration())));
        } catch (Exception exception){
            Toast.makeText(this,exception.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private Runnable updater = new Runnable() {
        @Override
        public void run() {
            updateSeekBar();
            long currentDuration = mediaPlayer.getCurrentPosition();
            textCurrentTime.setText(milliSecondsToTimer(currentDuration));
        }
    };

    private void updateSeekBar(){
        if(mediaPlayer.isPlaying()){
            playerSeekBar.setProgress((int)(((float) mediaPlayer.getCurrentPosition()/mediaPlayer.getDuration())*100));
            handler.postDelayed(updater,1000);
        }
    }

    private String milliSecondsToTimer(long milliSeconds){
        String timerString ="" ;
        String secondsString;

         int hours = (int)(milliSeconds/(1000*60*60));
        int minutes = (int)(milliSeconds%(1000*60*60))/(1000*60);
        int seconds = (int)((milliSeconds%(1000*60*60))%(1000*60)/1000);

        if(hours>0){
           timerString = hours + ":";
        }

        if(seconds<10){
            secondsString = "0" + seconds;
        }
        else {
            secondsString=""+seconds;
        }

        timerString=timerString+minutes+":"+secondsString;
        return timerString;
    }


}
