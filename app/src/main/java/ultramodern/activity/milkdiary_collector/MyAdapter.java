package ultramodern.activity.milkdiary_collector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

class MyAdapter extends RecyclerView.Adapter<MyAdapterHolder> {
    private Context context;

    private ArrayList<Model> nameList;

    public MyAdapter(Context paramContext, ArrayList<Model> paramArrayList) {
        this.context = paramContext;
        this.nameList = paramArrayList;
    }

    public int getItemCount() { return this.nameList.size(); }

    public void onBindViewHolder(MyAdapterHolder paramMyAdapterHolder, int paramInt) { paramMyAdapterHolder.setDetails((Model)this.nameList.get(paramInt)); }

    public MyAdapterHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
    { return new MyAdapterHolder(LayoutInflater.from(this.context).inflate(R.layout.farmerlistrecycler, paramViewGroup, false)); }
}


class MyAdapterHolder extends RecyclerView.ViewHolder {
    private TextView textViewname;

    public MyAdapterHolder(View paramView) {
        super(paramView);
        this.textViewname = (TextView)paramView.findViewById(R.id.textView16);
    }

    public void setDetails(Model paramModel) { this.textViewname.setText(paramModel.getName()); }
}
