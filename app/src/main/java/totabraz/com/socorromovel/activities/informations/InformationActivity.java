package totabraz.com.socorromovel.activities.informations;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import totabraz.com.socorromovel.R;
import totabraz.com.socorromovel.utils.SysUtils;

public class InformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        getSupportActionBar().setTitle("Denúncia Realizada");
        String msg = "";
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            msg = bundle.getString(SysUtils.KEY_MSG_SEND_ANONY_REPORT);
        }
        TextView tvMsgm = findViewById(R.id.tvMsgm);
        tvMsgm.setText(msg);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 5000);
        Button btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
