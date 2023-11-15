package com.example.android.barbera;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
/*import android.view.Window;
import android.view.WindowManager;*/
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class splash_logo extends AppCompatActivity {

    public ProgressBar progressBar;
    public int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_logo);

        progressBar = findViewById(R.id.progress_bar);

        Thread thread = new Thread(() ->
        {
            doWork();
            startApp();
        });
        thread.start();
    }
    private void doWork()
    {
        for ( i = 0; i<=100; i = i+30)
        {
            try {
                Thread.sleep(1000);
                progressBar.setProgress(i);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
    private void startApp()
    {
        Intent intent = new Intent(splash_logo.this,welcom.class);
        startActivity(intent);
        finish();
    }
}
