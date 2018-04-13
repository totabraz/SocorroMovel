package totabraz.com.socorromovel.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import totabraz.com.socorromovel.R;
import totabraz.com.socorromovel.activities.setups.SetupAndroidPermissionsActivity;

public class SplashScreenActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(getApplicationContext(), SetupAndroidPermissionsActivity.class));
                finish();
            }
        }, 500);

    }
}

