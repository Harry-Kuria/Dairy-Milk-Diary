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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
//import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("ALL")
public class FarmerList extends AppCompatActivity {
    private FarmerAdapter adapter;

    private FirebaseRecyclerAdapter adapteranother;

    private ArrayList<Model> arrayList;

    Task<Void> auth;

    private RecyclerView recyclerView;
    private IntentFilter intentFilter;

    public void fetch() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://milkdiary-farmer.firebaseio.com").getReference("Farmer-list");
        FirebaseRecyclerAdapter<Model, ViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Model, ViewHolder>((new FirebaseRecyclerOptions.Builder()).setQuery(databaseReference, new SnapshotParser<Model>() {
            public Model parseSnapshot(DataSnapshot param1DataSnapshot) { return new Model(param1DataSnapshot.getValue().toString()); }
        }).build()) {
            protected void onBindViewHolder(final FarmerList.ViewHolder viewHolder, int param1Int, Model param1Model) {
                viewHolder.setTextname(param1Model.getName());
                viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton param2CompoundButton, boolean param2Boolean) {
                        if (param2Boolean) {
                            String str2 = PreferenceManager.getDefaultSharedPreferences(FarmerList.this.getApplicationContext()).getString("Name", "get");
                            String str1 = viewHolder.textView.getText().toString();
                            StringBuilder stringBuilder2 = new StringBuilder();
                            stringBuilder2.append("FARMER NAMES RETRIEVED AS ");
                            stringBuilder2.append(viewHolder.textView.getText().toString());
                            Log.d("TRACK", stringBuilder2.toString());
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://milkdiary-farmer.firebaseio.com").getReference("Users");
                            StringBuilder stringBuilder1 = new StringBuilder();
                            stringBuilder1.append("DATABASE INITIALIZED SUCCESSFULLY AS ");
                            stringBuilder1.append(databaseReference);
                            String key = databaseReference.getKey();
                            Log.d("TRACK", stringBuilder1.toString());
                            FarmerList.this.auth = databaseReference.child(key).child(str2).child("Farmers-list").push().setValue(str1);
                            DatabaseReference databaseReference2 = FirebaseDatabase.getInstance("https://milkdiary-farmer.firebaseio.com").getReference("Users").child("Users").child("Your Records");
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put(str1,"");
                            databaseReference2.push().setValue(hashMap);
                            Toast.makeText(FarmerList.this, "SUCCESSFULLY ADDED",Toast.LENGTH_LONG).show();
                        } else {
                            final String value = PreferenceManager.getDefaultSharedPreferences(FarmerList.this.getApplicationContext()).getString("Name", "get");
                            FirebaseDatabase.getInstance("https://milkdiary-farmer.firebaseio.com").getReference("Collector").child(value).child("Farmer-list").addValueEventListener(new ValueEventListener() {
                                public void onCancelled(DatabaseError param3DatabaseError) {}

                                public void onDataChange(DataSnapshot param3DataSnapshot) { param3DataSnapshot.getRef().child(value).child("Farmer-list").removeValue(); }
                            });
                        }
                    }
                });
            }

            public FarmerList.ViewHolder onCreateViewHolder(ViewGroup param1ViewGroup, int param1Int) { return new FarmerList.ViewHolder(LayoutInflater.from(param1ViewGroup.getContext()).inflate(R.layout.farmerlistrecycler, param1ViewGroup, false)) {

            }; }
        };
        this.adapteranother = firebaseRecyclerAdapter;
        this.recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_farmer_list);
        RecyclerView recyclerView1 = (RecyclerView)findViewById(R.id.recyclerview2);
        this.recyclerView = recyclerView1;
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.addItemDecoration(new DividerItemDecoration(this, 1));
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar4);
        toolbar.setTitle("Add Farmers");
        toolbar.setTitleTextColor(-1);
        setSupportActionBar(toolbar);

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
        fetch();
    }

    protected void onStart() {
        super.onStart();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GUIDELINES");
        builder.setMessage("In order to add farmers to your list, kindly tap on the checkbox of your choice and the farmer will be automatically added");
        builder.show();
        this.adapteranother.startListening();
        registerReceiver(myReceiver,intentFilter);
    }

    protected void onStop() {
        super.onStop();
        this.adapteranother.stopListening();
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;

        private Button submitbutton;

        private TextView textView;

        public ViewHolder(View param1View) {
            super(param1View);
            this.textView = (TextView)param1View.findViewById(R.id.textView16);
            this.checkBox = (CheckBox)param1View.findViewById(R.id.checkBoxNew);
            //this.submitbutton = (Button)param1View.findViewById(R.id.b);
        }

        public void setTextname(String param1String) { this.textView.setText(param1String); }
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
//        RelativeLayout relativeLayout1 = findViewById(R.id.Main2ActivityLayout);
        RecyclerView recyclerView1 = (RecyclerView)findViewById(R.id.recyclerview2);
        recyclerView1.setVisibility(View.VISIBLE);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar4);
        toolbar.setVisibility(View.VISIBLE);
        noInternet.setVisibility(View.GONE);
        //relativeLayout1.setVisibility(View.VISIBLE);
    }

    public void set_Visibility_OFF(){
        TextView noInternet = findViewById(R.id.noInternet1);
//        RelativeLayout relativeLayout1 = findViewById(R.id.Main2ActivityLayout);
        RecyclerView recyclerView1 = (RecyclerView)findViewById(R.id.recyclerview2);
        noInternet.setVisibility(View.VISIBLE);
        recyclerView1.setVisibility(View.GONE);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar4);
        toolbar.setVisibility(View.GONE);
    }

}
