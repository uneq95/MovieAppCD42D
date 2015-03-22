package nairit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sauhardsharma.myapplication.R;
import com.example.sauhardsharma.myapplication.Subactivity;

import java.util.Collections;
import java.util.List;

/**
 * Created by nairit on 25/2/15.
 */
public class NaiAdapter extends RecyclerView.Adapter<NaiAdapter.MyViewHolder> {
    private final LayoutInflater inflater;
    private ClickListener clickListener;
    List<Information> data= Collections.emptyList();
Context context;
    public NaiAdapter(Context context,List<Information> data){
    inflater= LayoutInflater.from(context);
        this.data=data;
        this.context=context;

}
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=inflater.inflate(R.layout.customrow,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
Information current=data.get(position);
        holder.title.setText(current.title);
        holder.icon.setImageResource(current.iconId);

    }

    public void setClickListener(ClickListener clickListener){
        this.clickListener=clickListener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView icon;
        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
           title= (TextView) itemView.findViewById(R.id.listText);
            icon= (ImageView) itemView.findViewById(R.id.listIcon);




        }

        @Override
        public void onClick(View v) {
            context.startActivity(new Intent(context,Subactivity.class));

            if(clickListener!=null){
clickListener.itemClicked(v,getPosition());
            }
        }

    }
    public interface ClickListener{
        public void itemClicked(View view, int position);

    }
}
