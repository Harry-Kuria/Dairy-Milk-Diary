package ultramodern.activity.milkdiary_collector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

public class Logout extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth auth;

    private Button logoutbutton;
    private IntentFilter intentFilter;

    public void onClick(View paramView) {
        if (paramView == this.logoutbutton) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), PhoneNumber.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_logout);
        Button button = (Button)findViewById(R.id.button4);
        this.logoutbutton = button;
        button.setOnClickListener(this);

        intentFilter = new IntentFilter();
        intentFilter.addAction("Check Internet");
        Intent serviceIntent = new Intent(this,MyService.class);
        startService(serviceIntent);

        //noInternet.setVisibility(View.GONE);
        if (isOnline(getApplicationContext())){
            set_Visibility_ON();
        }
        else {
            set_Visibility_OFF();
        }
    }
    protected void onStart() {
        super.onStart();
        registerReceiver(myReceiver,intentFilter);
//        this.adapter3.startListening();
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
        TextView noInternet = findViewById(R.id.noInternet1);
        Button logoutbtn = findViewById(R.id.button4);
        //RelativeLayout relativeLayout1 = findViewById(R.id.Main2ActivityLayout);
        //RecyclerView recyclerView1 = (RecyclerView)findViewById(R.id.recycler);
        //recyclerView1.setVisibility(View.VISIBLE);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar2);
        toolbar.setVisibility(View.VISIBLE);
        logoutbtn.setVisibility(View.VISIBLE);
        noInternet.setVisibility(View.GONE);
        //relativeLayout1.setVisibility(View.VISIBLE);
    }

    public void set_Visibility_OFF(){
        TextView noInternet = findViewById(R.id.noInternet1);
        Button logoutbtn = findViewById(R.id.button4);
        noInternet.setVisibility(View.VISIBLE);
        //recyclerView1.setVisibility(View.GONE);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar2);
        toolbar.setVisibility(View.GONE);
        logoutbtn.setVisibility(View.GONE);
    }


}
