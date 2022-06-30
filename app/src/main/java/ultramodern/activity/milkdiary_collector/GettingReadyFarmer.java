package ultramodern.activity.milkdiary_collector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

@SuppressWarnings("ALL")
public class GettingReadyFarmer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_ready_farmer);

        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(GettingReadyFarmer.this.getApplicationContext(), UsernameRequestFarmer.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                GettingReadyFarmer.this.startActivity(intent);
                GettingReadyFarmer.this.finish();
            }
        },2000);

    }
}