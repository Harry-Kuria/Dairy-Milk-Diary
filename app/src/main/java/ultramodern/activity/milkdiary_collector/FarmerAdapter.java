package ultramodern.activity.milkdiary_collector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

class FarmerAdapter extends RecyclerView.Adapter<FarmerAdapterHolder> {
    private ArrayList<Model> arraylist;

    private Context context;

    public FarmerAdapter(Context paramContext, ArrayList<Model> paramArrayList) {
        this.context = paramContext;
        this.arraylist = paramArrayList;
    }

    public int getItemCount() { return this.arraylist.size(); }

    public void onBindViewHolder(FarmerAdapterHolder paramFarmerAdapterHolder, int paramInt) { paramFarmerAdapterHolder.setDetailsOfFarmer((Model)this.arraylist.get(paramInt)); }

    public FarmerAdapterHolder onCreateViewHolder
            (ViewGroup paramViewGroup, int paramInt)
    { return new FarmerAdapterHolder(LayoutInflater.from(this.context).inflate(R.layout.farmerlistrecycler, paramViewGroup, false)); }
}


class FarmerAdapterHolder extends RecyclerView.ViewHolder {
    private CheckBox checkBox;

    private TextView textView;

    public FarmerAdapterHolder(View paramView) {
        super(paramView);
        this.textView = (TextView)paramView.findViewById(R.id.textView16);
    }

    public void setDetailsOfFarmer(Model paramModel) { this.textView.setText(paramModel.getName()); }
}
