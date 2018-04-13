package totabraz.com.socorromovel.activities.setups;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import totabraz.com.socorromovel.R;
import totabraz.com.socorromovel.activities.MainActivity;

public class SetupAndroidPermissionsActivity extends AppCompatActivity {
    private static final int REQUEST_PHONE_CALL = 1;


    private void getCallPermission() {
        Intent intent = new Intent(this, MainActivity.class);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(SetupAndroidPermissionsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SetupAndroidPermissionsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
            } else {
                startActivity(intent);
                finish();
            }
        } else {
            startActivity(intent);
            finish();


        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_android_permissions);
        this.getCallPermission();

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted!
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                } else {
                    // permission denied, boo! Disable the
                    getCallPermission();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
