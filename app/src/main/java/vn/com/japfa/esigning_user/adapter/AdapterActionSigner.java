package vn.com.japfa.esigning_user.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vn.com.japfa.esigning_user.R;
import vn.com.japfa.esigning_user.models.SignActivityDatum;

import java.util.List;

public class AdapterActionSigner extends RecyclerView.Adapter<AdapterActionSigner.ViewHolder> {
   private Activity context;
   private List<SignActivityDatum> list;

    public AdapterActionSigner(Activity context, List<SignActivityDatum> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.dialog_action_signer_item,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewType.setText(list.get(position).getActionType());
        holder.textViewTime.setText(list.get(position).getCreatedAt());
        holder.textViewComment.setText(list.get(position).getSignerComment());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewType,textViewComment,textViewTime;
        public ViewHolder(View itemView) {
            super(itemView);
            textViewType = itemView.findViewById(R.id.textViewType);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            textViewComment = itemView.findViewById(R.id.textViewComment);
        }
    }
}
