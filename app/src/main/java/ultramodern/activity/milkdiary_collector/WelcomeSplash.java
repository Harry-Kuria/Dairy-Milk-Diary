package ultramodern.activity.milkdiary_collector;

import android.content.Intent;
//import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class WelcomeSplash extends AppCompatActivity {
    //SharedPreferences sp1;

    TextView textView9;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_welcome_splash);
        String str = getIntent().getStringExtra("username");
        TextView textView = findViewById(R.id.textView9);
        this.textView9 = textView;
        textView.setText(str);
        (new Handler()).postDelayed(() -> {
            Intent intent = new Intent(WelcomeSplash.this.getApplicationContext(), Main2Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            WelcomeSplash.this.startActivity(intent);
            WelcomeSplash.this.finish();
        },2000);
    }

    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            (new Handler()).postDelayed(() -> {
                Intent intent = new Intent(WelcomeSplash.this.getApplicationContext(), Main2Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                WelcomeSplash.this.startActivity(intent);
                WelcomeSplash.this.finish();
            },2000);
    }
}
