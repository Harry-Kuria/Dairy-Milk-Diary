package ultramodern.activity.milkdiary_collector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class ViewAllRecords extends AppCompatActivity {

    private ArrayList<Records> arrayList;
    private RecyclerView recyclerView;
    private RecordsAdapter adapter;
    private IntentFilter intentFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_records);

        String str = PreferenceManager.getDefaultSharedPreferences(this).getString("Name", "get");
        recyclerView = findViewById(R.id.records_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        arrayList = new ArrayList<>();

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

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://milkdiary-farmer.firebaseio.com/");
        DatabaseReference databaseReference =firebaseDatabase.getReference("Users").child("Users").child(str).child("Your Records");
        databaseReference.orderByChild("Records").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()){
                    Records records = new Records();
                    String key = childSnapshot.getKey();
                    Toast.makeText(ViewAllRecords.this, key, Toast.LENGTH_SHORT).show();
                    records.setFarmerName(key);
                    if (childSnapshot.child("Records").getValue().toString() == null){
                        records.setFarmerRecord(childSnapshot.getValue().toString());
                    }
                    else {
                        records.setFarmerRecord(childSnapshot.child("Records").getValue().toString());}

                    //Records records = childSnapshot.getValue(Records.class);
                    arrayList.add(records);
                }
                adapter = new RecordsAdapter(ViewAllRecords.this,arrayList,str);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //RecordsAdapter recordsAdapter = new RecordsAdapter(this, arrayList);
        //recyclerView.setAdapter(recordsAdapter);

    }
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
        TextView noInternet = findViewById(R.id.noInternet1);
        RecyclerView recyclerView1 = (RecyclerView)findViewById(R.id.records_recycler);
        recyclerView1.setVisibility(View.VISIBLE);
        Toolbar toolbar = (Toolbar)findViewById(R.id.all_records_toolbar);
        toolbar.setVisibility(View.VISIBLE);
        noInternet.setVisibility(View.GONE);
        //relativeLayout1.setVisibility(View.VISIBLE);
    }

    public void set_Visibility_OFF(){
        TextView noInternet = findViewById(R.id.noInternet1);
        RecyclerView recyclerView1 = (RecyclerView)findViewById(R.id.records_recycler);
        noInternet.setVisibility(View.VISIBLE);
        recyclerView1.setVisibility(View.GONE);
        Toolbar toolbar = (Toolbar)findViewById(R.id.all_records_toolbar);
        toolbar.setVisibility(View.GONE);
    }



}