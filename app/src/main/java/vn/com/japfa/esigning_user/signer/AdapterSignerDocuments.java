package vn.com.japfa.esigning_user.signer;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import vn.com.japfa.esigning_user.Interface.ItemClickListener;
import vn.com.japfa.esigning_user.R;
import vn.com.japfa.esigning_user.base.BaseApp;
import vn.com.japfa.esigning_user.base.CallBackCustom;
import vn.com.japfa.esigning_user.models.signer.Data;
import vn.com.japfa.esigning_user.models.signer.DocumentsDatum;

public class AdapterSignerDocuments extends RecyclerView.Adapter<AdapterSignerDocuments.ViewHolder> {
    private List<DocumentsDatum> documentsDatumList;
    private List<DocumentsDatum> mFilteredList;
    private Activity context;

    public AdapterSignerDocuments(List<DocumentsDatum> documentsDatumList, Activity context) {
        this.documentsDatumList = documentsDatumList;
        this.mFilteredList = new ArrayList<>();
        mFilteredList.addAll(documentsDatumList);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.line_recycle, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
//        holder.setIsRecyclable(false);
        holder.setStatus(mFilteredList.get(position).getStatusApproval());
        holder.setIsView(mFilteredList.get(position).getIsViewed());

        holder.textViewNo.setText(mFilteredList.get(position).getDocumentNo());
        holder.textViewUser.setText(mFilteredList.get(position).getUserName());
        holder.textViewType.setText(mFilteredList.get(position).getDocumentType());
        holder.textViewDepartment.setText("from " + mFilteredList.get(position).getLocationName());
        holder.textViewDate.setText(mFilteredList.get(position).getRecievedAt());
        holder.textViewStatus.setText(mFilteredList.get(position).getStatusApproval());
        String lyDo = mFilteredList.get(position).getDocDescription();
        if(lyDo==null || lyDo.isEmpty()){
            holder.textViewLyDo.setText("");
        }else
            holder.textViewLyDo.setText(lyDo);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void itemClick(View view, int position) {
                String documentID = mFilteredList.get(position).getDocumentId();
                if (mFilteredList.get(position).getIsViewed() == 0) {
                    isView(BaseApp.userID, documentID);
                }
                BaseApp.documentID = documentID;
                BaseApp.status = mFilteredList.get(position).getStatusApproval();
                Intent intentShowInfo = new Intent(context, ActivityDocumentHD_.class);
                context.startActivity(intentShowInfo);
            }
        });
    }


    private void isView(int userid, String documentid) {

        Call<Void> call = BaseApp.service().isView(userid, documentid);

        call.enqueue(new CallBackCustom<Void>(context) {
            @Override
            public void onResponseCustom(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context.getApplicationContext(), "Toggle is viewed successfully", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context.getApplicationContext(), "Toggle is viewed unsuccessfully", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailureCustom(Call<Void> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "Check on your Internet connection and try again.(isView error)", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    public void filter(final String text) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mFilteredList.clear();
                if (TextUtils.isEmpty(text)) {
                    mFilteredList.addAll(documentsDatumList);
                } else {
                    List<DocumentsDatum> filteredList = new ArrayList<>();
                    for (DocumentsDatum documentsDatum : documentsDatumList) {
                        if (documentsDatum.getDocumentNo().toLowerCase().contains(text.toLowerCase())
                                || documentsDatum.getDocumentType().toLowerCase().contains(text.toLowerCase())
                                || documentsDatum.getLocationName().toLowerCase().contains(text.toLowerCase())
                                || documentsDatum.getRecievedAt().toLowerCase().contains(text.toLowerCase())
                                || documentsDatum.getStatusApproval().toLowerCase().contains(text.toLowerCase())
                                || documentsDatum.getDocDescription().toLowerCase().contains(text.toLowerCase())
                                || documentsDatum.getUserName().toLowerCase().contains(text.toLowerCase())) {

                            filteredList.add(documentsDatum);
                        }
                    }
                    mFilteredList.addAll(filteredList);
                }
                // Set on UI Thread
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewNo, textViewUser, textViewType, textViewDepartment, textViewLyDo, textViewDate, textViewStatus;

        private ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this::onClick);
            textViewNo = (TextView) itemView.findViewById(R.id.textViewNo);
            textViewUser = (TextView) itemView.findViewById(R.id.textViewUser);
            textViewType = (TextView) itemView.findViewById(R.id.textViewType);
            textViewDepartment = (TextView) itemView.findViewById(R.id.textViewDepartment);
            textViewLyDo = (TextView) itemView.findViewById(R.id.textViewLyDo);
            textViewDate = (TextView) itemView.findViewById(R.id.textViewDate);
            textViewStatus = (TextView) itemView.findViewById(R.id.textViewStatus);

        }

        @Override
        public void onClick(View v) {
            itemClickListener.itemClick(v, getAdapterPosition());
        }

        public void setStatus(String status) {
            if (status.equals("InSigning")) {
                textViewStatus.setTextColor(ContextCompat.getColor(context, R.color.InSigning));
            } else if (status.equals("Pending")) {
                textViewStatus.setTextColor(ContextCompat.getColor(context, R.color.Pending));
            }
        }

        public void setIsView(int isView) {
            if (isView == 0) {
                textViewNo.setTextColor(ContextCompat.getColor(context, R.color.colortext));
                textViewUser.setTextColor(ContextCompat.getColor(context, R.color.colortext));
                textViewDepartment.setTextColor(ContextCompat.getColor(context, R.color.colortext));
            } else {
                itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.isviewed));
            }
        }
    }
}
