package vn.com.japfa.esigning_user.signer;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.com.japfa.esigning_user.R;
import vn.com.japfa.esigning_user.models.signer.DocumentsDatum;


public class AdapterSigned extends RecyclerView.Adapter<AdapterSigned.ViewHolderSigned>{
    private Activity context;
    private List<DocumentsDatum> documentsdatumList;

    public AdapterSigned(Activity context, List<DocumentsDatum> documentsdatumList) {
        this.context = context;
        this.documentsdatumList = documentsdatumList;
    }

    @NonNull
    @Override
    public ViewHolderSigned onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.line_recycle_check,parent,false);
        return new ViewHolderSigned(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSigned holder, int position) {
        holder.textViewUserName.setText(documentsdatumList.get(position).getUserName());
        holder.textViewSigned.setText((String) documentsdatumList.get(position).getSignedAtDisplay());
        holder.textViewComment.setText((String) documentsdatumList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return documentsdatumList.size();
    }

    public class ViewHolderSigned extends RecyclerView.ViewHolder{
        TextView textViewUserName,textViewSigned,textViewComment;
        public ViewHolderSigned(View itemView) {
            super(itemView);
            textViewUserName = (TextView) itemView.findViewById(R.id.textViewUserName);
            textViewSigned = (TextView) itemView.findViewById(R.id.textViewSigned);
            textViewComment = (TextView) itemView.findViewById(R.id.textViewComment);
        }
    }
}
