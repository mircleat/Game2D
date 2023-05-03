
package com.example.game2d;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This is the splash activity that is run when the app is launched.
 * It displays our logo and a loading/progress bar before going to
 * <code>StartActivity</code>
 */
public class SplashActivity extends AppCompatActivity {
    /**
     * sets the content to the related XML file while removing the
     * status bar. Also creates the handler to
     * make sure the start activity is run after some time.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        new Handler().postDelayed(new Runnable() {
            /**
             * Creates/executes the intent to <code>StartActivity</code> after 3000 ms.
             */
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, StartActivity.class));
                finish();
            }
        }, 3000);

    }

}
