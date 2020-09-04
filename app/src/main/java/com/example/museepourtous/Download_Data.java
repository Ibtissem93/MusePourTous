package com.example.museepourtous;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
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

public class Download_Data extends AppCompatActivity {
    DatabaseReference reff;
    StorageReference storeRef,storeSound ;
    private ProgressBar mProgressBar;
    private TextView mLoadingText;
    private int mProgressStatus =0;
    private Handler mHandler = new Handler();
    OutputStream outputStream;
    Bitmap bitmapp;
    Button start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download__data);
        reff = FirebaseDatabase.getInstance().getReference();
        reff.keepSynced(true);
        storeRef = FirebaseStorage.getInstance().getReference("Images");
        storeSound = FirebaseStorage.getInstance().getReference("Sounds");
        mProgressBar = (ProgressBar) findViewById(R.id.progressbarr);
        mLoadingText=(TextView) findViewById(R.id.LoadingCompleteTextVieww);
        start = (Button) findViewById(R.id.startBtn) ;
        /*boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
            Toast.makeText(Download_Data.this,"connecté",Toast.LENGTH_SHORT).show();
        }
        else
            connected = false;
        Toast.makeText(Download_Data.this," n'est pas connecté",Toast.LENGTH_SHORT).show();*/
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        int visit = prefs.getInt("visit",-1);

        if(visit==1)
        {
            Intent intent = new Intent(Download_Data.this,Language.class);
            startActivity(intent);
        }
        else {
            GetDataFirebase();
          GetSounds();
        }


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Download_Data.this,Language.class);
                startActivity(intent);

            }
        });
    }


    public void GetDataFirebase(){
        storeRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {

                for (StorageReference item : listResult.getItems()) {
                    try {

                        final String name= item.getName();

                        // All the items under listRef.
                        final File file = File.createTempFile("Images","jpg");
                        item.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {


                                bitmapp= BitmapFactory.decodeFile(file.getAbsolutePath());
                                Bitmap bitmap= bitmapp;

                                File filepath = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                                File dir = new File(filepath.getAbsolutePath() + "/TestFolder/");
                                dir.mkdir();
                                File file = new File(dir, name +".jpg");
                                outputStream = null;

                                try {
                                    outputStream = new FileOutputStream(file);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

                                try {
                                    outputStream.flush();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    outputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        while (mProgressStatus<100){
                                            mProgressStatus++;
                                            android.os.SystemClock.sleep(50);
                                            mHandler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    mProgressBar.setProgress(mProgressStatus);

                                                }
                                            });

                                        }
                                        mHandler.post(new Runnable() {
                                            @Override
                                            public void run() {

                                        mLoadingText.setVisibility(View.VISIBLE);
                                        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
                                        editor.putInt("visit",1);
                                        editor.apply();
                                        start.setVisibility(View.VISIBLE);

                                        }
                                        });
                                    }
                                }).start();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Download_Data.this,"image failed to load",Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch(IOException e){
                        e.printStackTrace();
                    }


                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Uh-oh, an error occurred!
            }
        });


    }

    private void GetSounds() {
        storeSound.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {

                for (final StorageReference item : listResult.getItems()) {

                    final String names = item.getName();
                    item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            downloadFiles(Download_Data.this, names , ".mp3" ,"Music/Sounds" , url  );

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });


                }



            }
        });
    }
    private void downloadFiles(Context context , String fileName , String fileExtention , String destinationDirectory , String url) {
        DownloadManager downloadmanager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context , destinationDirectory , fileName + fileExtention);
        downloadmanager.enqueue(request);

    }

}
