package ultramodern.activity.milkdiary_collector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

@SuppressWarnings("ALL")
public class UsernameRequestFarmer extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "TRACK";
    Task<Void> auth;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    SharedPreferences sp;
    SharedPreferences sp1;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username_request_farmer);

        EditText edittext3farmer = findViewById(R.id.editText3Farmer);
        Button button = (Button)findViewById(R.id.button3Farmer);

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
        button.setOnClickListener(this);
        String str = edittext3farmer.getText().toString();
        this.sp = getSharedPreferences("login", 0);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.sp1 = sharedPreferences;
        edittext3farmer.setText(sharedPreferences.getString("Name", str));
        edittext3farmer.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable param1Editable) { UsernameRequestFarmer.this.sp1.edit().putString("Name", param1Editable.toString()).commit(); }

            public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}

            public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
        });
        if (this.sp.getBoolean("logged", false)){
            goToNextActivity();
        }

    }
    public void onClick(View paramView) {
        EditText edittext3farmer = findViewById(R.id.editText3Farmer);
        String str = edittext3farmer.getText().toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TEXT RECIEVED SUCCESSFULLY AS ");
        stringBuilder.append(str);
        if (edittext3farmer.getText().length()<1){
            edittext3farmer.setError("A value is needed!");
        }
        Log.d("TRACK", stringBuilder.toString());
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://milkdiary-farmer.firebaseio.com/");
        this.database = firebaseDatabase;
        this.auth = firebaseDatabase.getReference("Farmer").child(str).setValue(str);
        this.auth = this.database.getReference("Farmer-list").child(str).setValue(str);
        DatabaseReference databaseReference1 = this.database.getReference("Farmer").child(str).child("Records").push();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Records","Welcome");
        this.auth = databaseReference1.setValue(hashMap);

        goToNextActivity();
        this.sp.edit().putBoolean("logged", true).apply();
    }
    private void goToNextActivity() {
        EditText edittext3farmer = findViewById(R.id.editText3Farmer);
        String str = edittext3farmer.getText().toString();
        Intent intent = new Intent(getApplicationContext(), WelcomeSplashFarmer.class);
        intent.putExtra("username", str);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
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
        ImageView imageView4 = findViewById(R.id.imageView4);
        TextView textView7 = findViewById(R.id.textView7);
        EditText editText3 = findViewById(R.id.editText3Farmer);
        Button button3 = findViewById(R.id.button3Farmer);
        textView7.setVisibility(View.VISIBLE);
        imageView4.setVisibility(View.VISIBLE);
        textView7.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        noInternet.setVisibility(View.GONE);
        //relativeLayout1.setVisibility(View.VISIBLE);
    }

    public void set_Visibility_OFF(){
        TextView noInternet = findViewById(R.id.noInternet1);
        ImageView imageView4 = findViewById(R.id.imageView4);
        TextView textView7 = findViewById(R.id.textView7);
        EditText editText3 = findViewById(R.id.editText3Farmer);
        Button button3 = findViewById(R.id.button3Farmer);
        textView7.setVisibility(View.GONE);
        imageView4.setVisibility(View.GONE);
        textView7.setVisibility(View.GONE);
        button3.setVisibility(View.GONE);
        noInternet.setVisibility(View.VISIBLE);
    }
}