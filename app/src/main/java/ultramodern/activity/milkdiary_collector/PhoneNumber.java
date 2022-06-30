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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

@SuppressWarnings("ALL")
public class PhoneNumber extends AppCompatActivity implements View.OnClickListener {
    Button button;

    EditText editText;
    private IntentFilter intentFilter;


    public void onClick(View paramView) {
        if (paramView == this.button) {
            EditText editText1 = (EditText)findViewById(R.id.editText);
            if (editText1.length()==0){
                editText1.setError("Input is needed!");
            }
            else if (editText1.length()<12){
                editText1.setError("That contact number is incomplete!");
            }
            else if (!editText1.getText().toString().startsWith("+")){
                editText1.setError("Use this format, +country code contact!");
            }
            else {
                Intent intent = new Intent(getApplicationContext(), PhoneNumberAuthentication.class);
                this.editText = editText1;
                intent.putExtra("phonenumber", editText1.getText().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

        }
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_phone_number);
        Button button1 = (Button)findViewById(R.id.button);
        this.button = button1;
        button1.setOnClickListener(this);
        EditText editText = findViewById(R.id.editText);


        intentFilter = new IntentFilter();
        intentFilter.addAction("Check Internet");
        Intent serviceIntent = new Intent(this,MyService.class);
        startService(serviceIntent);

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
        TextView textView4 = findViewById(R.id.textView4);
        EditText editText = findViewById(R.id.editText);
        Button button = findViewById(R.id.button);
        //RelativeLayout relativeLayout1 = findViewById(R.id.Main2ActivityLayout);
        //RecyclerView recyclerView1 = (RecyclerView)findViewById(R.id.recycler);
        //recyclerView1.setVisibility(View.VISIBLE);
        ImageView imageView2 = findViewById(R.id.imageView2);
        imageView2.setVisibility(View.VISIBLE);
        textView4.setVisibility(View.VISIBLE);
        editText.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);
        noInternet.setVisibility(View.GONE);
        //relativeLayout1.setVisibility(View.VISIBLE);
    }

    public void set_Visibility_OFF(){
        TextView noInternet = findViewById(R.id.noInternet1);
        TextView textView4 = findViewById(R.id.textView4);
        EditText editText = findViewById(R.id.editText);
        Button button = findViewById(R.id.button);
        //RelativeLayout relativeLayout1 = findViewById(R.id.Main2ActivityLayout);
        //RecyclerView recyclerView1 = (RecyclerView)findViewById(R.id.recycler);
        //recyclerView1.setVisibility(View.VISIBLE);
        ImageView imageView2 = findViewById(R.id.imageView2);
        imageView2.setVisibility(View.GONE);
        textView4.setVisibility(View.GONE);
        editText.setVisibility(View.GONE);
        button.setVisibility(View.GONE);
        noInternet.setVisibility(View.VISIBLE);
    }


}
