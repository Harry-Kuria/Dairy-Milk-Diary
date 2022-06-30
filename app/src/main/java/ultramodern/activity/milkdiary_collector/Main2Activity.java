package ultramodern.activity.milkdiary_collector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    private MyAdapter adapter;

    private FirebaseRecyclerAdapter adapter2,adapter3;

    private ArrayList<Model> arrayList;
    private ArrayList<Records> recordsArrayList;

    private Context context;

    private DatabaseReference databaseReference;

    private String name;

    private RecyclerView recyclerView;

    private Toolbar toolbar;
    private IntentFilter intentFilter;

    private void fetch() {
        String str = PreferenceManager.getDefaultSharedPreferences(this).getString("Name", "get");
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance("https://milkdiary-farmer.firebaseio.com").getReference("Users").child("Users").child(str).child("Farmers-list");
        FirebaseRecyclerOptions<Model> options = new FirebaseRecyclerOptions.Builder<Model>().setQuery(databaseReference1, new SnapshotParser<Model>() {
            public Model parseSnapshot(DataSnapshot param1DataSnapshot) { return new Model(param1DataSnapshot.getValue().toString()); }
        }).build(); {
            adapter2 = new FirebaseRecyclerAdapter<Model, ViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Model model) {
                    holder.setTxtname(model.getName());
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View param2View) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
                            builder.setTitle("OPTIONS");
                            DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface param3DialogInterface, int param3Int) {
                                    if (param3Int != 0) {
                                        if (param3Int != 1) {
                                            if (param3Int == 2) {
                                                AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
                                                builder.setMessage("Please contact the Administrator for this support. Use this contact: 0765911279 or Email Address: harrykuria23@gmail.com");
                                                builder.show();
                                            }
                                            if (param3Int == 3){
                                                DatabaseReference databaseReference1 = FirebaseDatabase.getInstance("https://milkdiary-farmer.firebaseio.com").getReference("Collector").child(str).child("Farmer-list");
                                                databaseReference1.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()){
                                                            String value = childSnapshot.getValue().toString();
                                                            Toast.makeText(Main2Activity.this, value, Toast.LENGTH_SHORT).show();
                                                            //viewFarmerRecord(Main2Activity.this,value);

                                                        }
                                                    }
                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
                                                //Toast.makeText(Main2Activity.this, key, Toast.LENGTH_LONG).show();
                                            }
                                        } else {
                                            Intent intent = new Intent(Main2Activity.this, UpdateRecord.class);
                                            String value = holder.name.getText().toString();
                                            intent.putExtra("username", value);
                                            Main2Activity.this.startActivity(intent);
                                        }
                                    } else {
                                        //Intent intent = new Intent(Main2Activity.this.this$0, MpesaActivity.class);
                                        //Main2Activity.null.this.this$0.startActivity(intent);
                                    }
                                }
                            };
                            builder.setItems(new String[] { "Pay this farmer", "Update records", "Remove from list","View Records" }, onClickListener);
                            builder.show();
                        }
                    });
                }


                @NonNull
                @Override
                public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerrow, parent, false);
                    return new Main2Activity.ViewHolder(view);

                }
            };

            recyclerView.setAdapter(adapter2);

        };

    }

    public void viewFarmerRecord(Context context,String value){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://milkdiary-farmer.firebaseio.com/");
        DatabaseReference databaseReference = firebaseDatabase.getReference("Farmer").child(value).child("Records");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()){
                    String record = childSnapshot.getValue().toString();
                    Toast.makeText(context, record, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void onClick(View paramView) {}

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar3);
        RelativeLayout relativeLayout = findViewById(R.id.Main2ActivityLayout);
//        NetworkInterceptor networkInterceptor = new NetworkInterceptor(relativeLayout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Records");
        toolbar.setTitleTextColor(-1);
        getIntent().getStringExtra("username");
        RecyclerView recyclerView1 = (RecyclerView)findViewById(R.id.recycler);


        this.recordsArrayList = new ArrayList<>();
        //RecordsAdapter recordsAdapter = new RecordsAdapter(this,this.recordsArrayList);
        //recyclerView2.addItemDecoration(new DividerItemDecoration(this,1));
        //recyclerView2.setAdapter(recordsAdapter);
        //recyclerView2.setLayoutManager(new LinearLayoutManager(this));



        this.recyclerView = recyclerView1;
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        this.arrayList = new ArrayList();
        this.adapter = new MyAdapter(this, this.arrayList);
        this.recyclerView.addItemDecoration(new DividerItemDecoration(this, 1));
        this.recyclerView.setAdapter(this.adapter);
        adapter.notifyDataSetChanged();

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

    public boolean onCreateOptionsMenu(Menu paramMenu) {
        getMenuInflater().inflate(R.menu.supplier_menu, paramMenu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
        int i = paramMenuItem.getItemId();
        if (i == R.id.logout){
            startActivity(new Intent(this, Logout.class));
        }
        if (i == R.id.addFarmers){
            startActivity(new Intent(this, FarmerList.class));
        }
        if (i == R.id.viewFarmerRecords){
            startActivity(new Intent(this,ViewAllRecords.class));
        }
        return true;
    }

    protected void onStart() {
        super.onStart();
        this.adapter2.startListening();
        registerReceiver(myReceiver,intentFilter);
//        this.adapter3.startListening();
    }

    protected void onStop() {
        super.onStop();
        this.adapter2.stopListening();
//        this.adapter3.stopListening();
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
        public TextView name;

        public ViewHolder(View param1View) {
            super(param1View);
            this.name = (TextView)param1View.findViewById(R.id.textView11);
        }

        public void setTxtname(String param1String) { this.name.setText(param1String); }
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
        RelativeLayout relativeLayout1 = findViewById(R.id.Main2ActivityLayout);
        RecyclerView recyclerView1 = (RecyclerView)findViewById(R.id.recycler);
        recyclerView1.setVisibility(View.VISIBLE);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar3);
        toolbar.setVisibility(View.VISIBLE);
        noInternet.setVisibility(View.GONE);
        //relativeLayout1.setVisibility(View.VISIBLE);
    }

    public void set_Visibility_OFF(){
        TextView noInternet = findViewById(R.id.noInternet1);
        RelativeLayout relativeLayout1 = findViewById(R.id.Main2ActivityLayout);
        RecyclerView recyclerView1 = (RecyclerView)findViewById(R.id.recycler);
        noInternet.setVisibility(View.VISIBLE);
        recyclerView1.setVisibility(View.GONE);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar3);
        toolbar.setVisibility(View.GONE);
    }


}
