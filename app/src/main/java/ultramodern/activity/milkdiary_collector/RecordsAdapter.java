package ultramodern.activity.milkdiary_collector;

import android.content.Context;
//import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.EditText;
import android.widget.TextView;
//import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.MyNewAdapterHolder> {
    private Context context;
    private ArrayList<Records> arrayList;
    private String username;

    public RecordsAdapter(Context context, ArrayList<Records> arrayList, String username) {
        this.context = context;
        this.arrayList = arrayList;
        this.username = username;
    }

    @NonNull
    @Override
    public MyNewAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyNewAdapterHolder(LayoutInflater.from(this.context).inflate(R.layout.records_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyNewAdapterHolder holder, int position) {
        //Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        Records records = arrayList.get(position);
        holder.farmer.setText(records.getFarmerName());
        holder.values.setText(records.getFarmerRecord());
    }

    @Override
    public int getItemCount() {
        return this.arrayList.size();
    }

    public class MyNewAdapterHolder extends RecyclerView.ViewHolder {
        TextView farmer;
        TextView values;
        public MyNewAdapterHolder(@NonNull View itemView) {
            super(itemView);
            farmer = itemView.findViewById(R.id.farmer_name);
            values = itemView.findViewById(R.id.record);
        }
        public void setDetails(Records records){
            farmer.setText(records.getFarmerName());
            values.setText(records.getFarmerRecord());
        }
    }
}
