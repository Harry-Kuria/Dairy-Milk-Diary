package ultramodern.activity.milkdiary_collector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

@SuppressWarnings("ALL")
public class WelcomeSplashFarmer extends AppCompatActivity {

    private TextView textView9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_splash_farmer);

        String str = getIntent().getStringExtra("username");
        TextView textView = (TextView)findViewById(R.id.textView9);
        this.textView9 = textView;
        textView.setText(str);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                String str = WelcomeSplashFarmer.this.getIntent().getStringExtra("username");
                Intent intent = new Intent(WelcomeSplashFarmer.this.getApplicationContext(), MainActivity.class);
                intent.putExtra("username", str);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("TEXT FROM WELCOME SPLASH SENT AS ");
                stringBuilder.append(str);
                Log.d("TRACK", stringBuilder.toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                WelcomeSplashFarmer.this.startActivity(intent);
                WelcomeSplashFarmer.this.finish();
            }
        },2000);
    }
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance() != null)
            (new Handler()).postDelayed(new Runnable() {
                public void run() {
                    Intent intent = new Intent(WelcomeSplashFarmer.this.getApplicationContext(), MainActivityFarmer.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    WelcomeSplashFarmer.this.startActivity(intent);
                    WelcomeSplashFarmer.this.finish();
                }
            },2000);
    }
}