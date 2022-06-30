package ultramodern.activity.milkdiary_collector;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayAdapter arrayAdapter;

    ArrayList<String> arrayList = new ArrayList();

    DatabaseReference databaseReference;

    FirebaseDatabase db;

    ListView listView;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_main);
        this.listView = (ListView)findViewById(R.id.lists);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("My Records");
        toolbar.setTitleTextColor(-1);
        setSupportActionBar(toolbar);
        final String name = getIntent().getStringExtra("username");
        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this, android.R.layout.activity_list_item, this.arrayList);
        this.arrayAdapter = arrayAdapter1;
        this.listView.setAdapter(arrayAdapter1);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("OPTIONS");
                DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                        if (param2Int != 0)
                            if (param2Int == 1) {
                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://milkdiary-farmer.firebaseio.com");
                                MainActivity.this.databaseReference = firebaseDatabase.getReference("Users");
                                MainActivity.this.databaseReference.addValueEventListener(new ValueEventListener() {
                                    public void onCancelled(DatabaseError param3DatabaseError) {}

                                    public void onDataChange(DataSnapshot param3DataSnapshot) { param3DataSnapshot.getRef().removeValue(); }
                                });
                            } else {
                                Intent intent = new Intent(MainActivity.this, UpdateRecord.class);
                                intent.putExtra("username", name);
                                MainActivity.this.startActivity(intent);
                            }
                    }
                };
                builder.setItems(new String[] { "Pay this farmer", "Remove from list", "Update record" }, onClickListener);
                builder.show();
            }
        });
        FirebaseApp.initializeApp(this, (new FirebaseOptions.Builder()).setProjectId("milkdiary-farmer").setApplicationId("1:874069115320:android:e9492a6c95631aaac54f4d").setApiKey("AIzaSyBQ1265BoFGFpzRoLtInUNfFpC5OBUXpB4").build(), "secondary").getInstance("secondary");
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance("https://milkdiary-farmer.firebaseio.com").getReference("Users");
        this.databaseReference = databaseReference1;
        databaseReference1.addValueEventListener(new ValueEventListener() {
            public void onCancelled(DatabaseError param1DatabaseError) {}

            public void onDataChange(DataSnapshot param1DataSnapshot) {
                String str = param1DataSnapshot.getValue().toString();
                MainActivity.this.arrayList.add(str);
                MainActivity.this.arrayAdapter.notifyDataSetChanged();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu paramMenu) {
        getMenuInflater().inflate(2131492865, paramMenu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
        if (paramMenuItem.getItemId() == 2131230861)
            startActivity(new Intent(this, Logout.class));
        return true;
    }
}
