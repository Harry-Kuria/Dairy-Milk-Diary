package ultramodern.activity.milkdiary_collector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;

@SuppressWarnings("ALL")
public class UpdateRecord extends AppCompatActivity implements View.OnClickListener {
    private EditText amount;

    private Task<Void> auth;

    DatabaseReference databaseReference;

    private FirebaseDatabase firebaseDatabase;

    private Button updaterecordsbutton;
    private IntentFilter intentFilter;

    String value;
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_update_record);
        this.value = PreferenceManager.getDefaultSharedPreferences(this).getString("Name", "get");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TEXT RECEIVED SUCCESSFULLY AS ");
        stringBuilder.append(this.value);
        Log.d("TRACK", stringBuilder.toString());
        this.amount = (EditText)findViewById(R.id.editText4);
        Button button = (Button)findViewById(R.id.button5);
        this.updaterecordsbutton = button;
        FirebaseDatabase firebaseDatabase2 = FirebaseDatabase.getInstance("https://milkdiary-farmer.firebaseio.com/");
        DatabaseReference databaseReference3 = firebaseDatabase2.getReference().child("Text on Update Records");
        databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()){
                    TextView text = findViewById(R.id.textView12);
                    text.setText(childSnapshot.getValue().toString());
                    //Toast.makeText(UpdateRecord.this, dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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
    }
    public void firebaseActivity() {
        Calendar calendar = Calendar.getInstance();
        int i = calendar.get(1);
        int j = calendar.get(2);
        int k = calendar.get(7);
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(k);
        stringBuilder1.append("-");
        stringBuilder1.append(j);
        stringBuilder1.append("-");
        stringBuilder1.append(i);
        String str1 = stringBuilder1.toString();
        EditText editText = (EditText)findViewById(R.id.editText4);
        this.amount = editText;
        String str2 = editText.getText().toString();
        String str3 = PreferenceManager.getDefaultSharedPreferences(this).getString("Name", "get");
        FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance("https://milkdiary-farmer.firebaseio.com/");
        FirebaseDatabase firebaseDatabase2 = FirebaseDatabase.getInstance("https://milkdiary-farmer.firebaseio.com/");
        this.firebaseDatabase = firebaseDatabase1;
        //this.databaseReference = firebaseDatabase1.getReference("Users").child("Users").child("Your Records");
        DatabaseReference databaseReference2 = firebaseDatabase2.getReference("Users").child("Users").child(str3).child("Your Records");
        String name = getIntent().getStringExtra("username");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Records",str2);
        databaseReference2.child(name).setValue(hashMap);

//        String key = databaseReference.getKey();
//        Toast.makeText(this, key, Toast.LENGTH_SHORT).show();
        //DatabaseReference databaseReference = firebaseDatabase2.getReference("Farmer")
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("UPDATE RECORD GOT FIREBASE FULL REFERENCE SUCCESSFULLY AS ");
        stringBuilder2.append(this.databaseReference);
        Log.d("TRACK", stringBuilder2.toString());
        //DatabaseReference databaseReference1 = this.databaseReference.push();
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(str1);
        stringBuilder2.append(":          ");
        stringBuilder2.append(str2);
        //this.auth = databaseReference1.setValue(stringBuilder2.toString());
        //this.auth = databaseReference1.setValue(str2);
        Log.d("TRACK", "RECORDS UPDATED SUCCESSFULLY ");
    }

    public void onClick(View paramView) {
        if (paramView == this.updaterecordsbutton) {
            firebaseActivity();
            Toast.makeText(this, "RECORDS UPDATED SUCCESSFULLY", 0).show();
        }
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
        TextView textView10 = findViewById(R.id.textView10);
        TextView textView12 = findViewById(R.id.textView12);
        EditText editText4 = findViewById(R.id.editText4);
        Button button5 = findViewById(R.id.button5);
        //recyclerView1.setVisibility(View.VISIBLE);
        //Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar3);
        editText4.setVisibility(View.VISIBLE);
        button5.setVisibility(View.VISIBLE);
        textView10.setVisibility(View.VISIBLE);
        textView12.setVisibility(View.VISIBLE);
        noInternet.setVisibility(View.GONE);
        //relativeLayout1.setVisibility(View.VISIBLE);
    }

    public void set_Visibility_OFF(){
        TextView noInternet = findViewById(R.id.noInternet1);
        TextView textView10 = findViewById(R.id.textView10);
        TextView textView12 = findViewById(R.id.textView12);
        EditText editText4 = findViewById(R.id.editText4);
        Button button5 = findViewById(R.id.button5);
        editText4.setVisibility(View.GONE);
        button5.setVisibility(View.GONE);
        textView10.setVisibility(View.GONE);
        textView12.setVisibility(View.GONE);
        noInternet.setVisibility(View.VISIBLE);
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



}
