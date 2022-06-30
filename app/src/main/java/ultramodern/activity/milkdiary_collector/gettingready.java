package ultramodern.activity.milkdiary_collector;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

@SuppressWarnings("ALL")
public class gettingready extends AppCompatActivity {
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_gettingready);
        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(gettingready.this.getApplicationContext(), UsernameRequest.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gettingready.this.startActivity(intent);
                gettingready.this.finish();
            }
        },2000);
    }
}
