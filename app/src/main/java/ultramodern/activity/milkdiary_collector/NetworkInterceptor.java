package ultramodern.activity.milkdiary_collector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressWarnings("ALL")
public class NetworkInterceptor extends AppCompatActivity {

    private RelativeLayout relativeLayout1;
    private IntentFilter intentFilter;

    public NetworkInterceptor(RelativeLayout relativeLayout1) {
        this.relativeLayout1 = relativeLayout1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_interceptor);

        TextView noInternet = findViewById(R.id.noInternet);
        intentFilter = new IntentFilter();
        intentFilter.addAction("Check Internet");

        Intent serviceIntent = new Intent(this,MyService.class);
        startService(serviceIntent);

        noInternet.setVisibility(View.GONE);
        if (isOnline(getApplicationContext())){
            set_Visibility_ON();
        }
        else {
            set_Visibility_OFF();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(myReceiver,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myReceiver,intentFilter);
    }

    public BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("Check Internet")){
                if (intent.getStringExtra("online_status").equals("true")){
                    set_Visibility_ON();
                }
                else {
                    set_Visibility_OFF();
                }
            }
        }
    };
    public boolean isOnline(Context c){
        ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo !=null && networkInfo.isConnectedOrConnecting()){
            return true;
        }
        else {
            return false;
        }
    }

    public void set_Visibility_ON(){
        TextView noInternet = findViewById(R.id.noInternet);
        RelativeLayout relativeLayout = findViewById(R.id.NetworkInterceptorLayout);
        relativeLayout.setVisibility(View.GONE);
        noInternet.setVisibility(View.GONE);
        relativeLayout1.setVisibility(View.VISIBLE);
        //binding.submit.setVisibility(View.VISIBLE);
        //binding.parent.setBackgroundColor(Color.WHITE);

    }

    public void set_Visibility_OFF(){
        TextView noInternet = findViewById(R.id.noInternet);
        RelativeLayout relativeLayout = findViewById(R.id.NetworkInterceptorLayout);
        relativeLayout.setVisibility(View.VISIBLE);
        noInternet.setVisibility(View.VISIBLE);
        relativeLayout1.setVisibility(View.GONE);
        //binding.submit.setVisibility(View.GONE);
        //binding.parent.setBackgroundColor(Color.RED);

    }


}