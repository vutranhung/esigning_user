package vn.com.japfa.esigning_user.documents;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import vn.com.japfa.esigning_user.Interface.ItemLongClickListener;
import vn.com.japfa.esigning_user.Interface.Refresh;
import vn.com.japfa.esigning_user.R;
import vn.com.japfa.esigning_user.base.BaseApp;
import vn.com.japfa.esigning_user.base.CallBackCustom;
import vn.com.japfa.esigning_user.bringout.ActivityFromBringOut_;
import vn.com.japfa.esigning_user.exitform.ActivityFromExit_;
import vn.com.japfa.esigning_user.leaveform.ActivityFromLeaveForm_;
import vn.com.japfa.esigning_user.models.DeleteDocResponse;
import vn.com.japfa.esigning_user.models.Documents;
import vn.com.japfa.esigning_user.vehiclerequest.ActivityFromVehicleRequest_;
import vn.com.japfa.esigning_user.worktravel.ActivityFromWorkTravel_;


//adapter
public class AdapterDocuments extends RecyclerView.Adapter<AdapterDocuments.ViewHolder> {
    private List<Documents> list;
    private List<Documents> mFilteredList;
    private Activity context;
    private Refresh refresh;

    public void setRefresh(Refresh refresh) {
        this.refresh = refresh;
    }

    public AdapterDocuments(List<Documents> list, Activity context) {
        this.list = list;
        this.mFilteredList = new ArrayList<>();
        if (list != null) {
            this.mFilteredList.addAll(list);
        }
        this.context = context;
    }

    public void filter(String text) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mFilteredList.clear();
                if (TextUtils.isEmpty(text)) mFilteredList.addAll(list);
                else {
                    List<Documents> filteredList = new ArrayList<>();

                    for (Documents Documents : list) {

                        if (Documents.getDocumentNo().toLowerCase().contains(text.toLowerCase())
                                || (Documents.getDescription()!=null && Documents.getDescription().toLowerCase().contains(text.toLowerCase()))
                                || Documents.getDocumentType().toLowerCase().contains(text.toLowerCase())
                                || Documents.getStatus().toLowerCase().contains(text.toLowerCase())
                                || Documents.getCreatedAt().toLowerCase().contains(text.toLowerCase())
                        ) {
                            filteredList.add(Documents);
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

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.main_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String status = mFilteredList.get(position).getStatus();
        holder.textViewNo.setText(mFilteredList.get(position).getDocumentNo());
        holder.textViewType.setText(mFilteredList.get(position).getDocumentType());
        holder.textViewStatus.setText("(" + status + ")");
        holder.textViewLyDo.setText(mFilteredList.get(position).getDescription());
        holder.textViewDate.setText(mFilteredList.get(position).getCreatedAt());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void itemClick(View view, int position) {
                String type = mFilteredList.get(position).getDefineType();
                switch (type) {
                    case "WORK_TRAVEL":
                        Intent intentWorkTravel = new Intent(context, ActivityFromWorkTravel_.class);
                        BaseApp.documentID = mFilteredList.get(position).getId() + "";
                        BaseApp.status = status;
                        context.startActivity(intentWorkTravel);
                        break;
                    case "EXIT":
                        Intent intentExit = new Intent(context, ActivityFromExit_.class);
                        BaseApp.documentID = mFilteredList.get(position).getId() + "";
                        BaseApp.status = status;
                        context.startActivity(intentExit);
                        break;
                    case "VEHICLE_REQUEST":
                        Intent intentVehicle = new Intent(context, ActivityFromVehicleRequest_.class);
                        BaseApp.documentID = mFilteredList.get(position).getId() + "";
                        BaseApp.status = status;
                        context.startActivity(intentVehicle);
                        break;
                    case "BRING_OUT":
                        Intent intentBringOut = new Intent(context, ActivityFromBringOut_.class);
                        BaseApp.documentID = mFilteredList.get(position).getId() + "";
                        BaseApp.status = status;
                        context.startActivity(intentBringOut);
                        break;
                    case "LEAVE":
                        Intent intentLeave = new Intent(context, ActivityFromLeaveForm_.class);
                        BaseApp.documentID = mFilteredList.get(position).getId() + "";
                        BaseApp.status = status;
                        context.startActivity(intentLeave);
                        break;

                }
            }
        });

        holder.setItemLongClickListener(new ItemLongClickListener() {
            @Override
            public void itemLongClick(View view, int position) {
                if (mFilteredList.get(position).getStatus().equals("New") || mFilteredList.get(position).getStatus().equals("Rejected")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure want to delete?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteDocument(mFilteredList.get(position).getId() + "");
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else
                    Toast.makeText(context, "InSigning,Completed - cannot Delete", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    private void deleteDocument(String documentID) {
        Call<Void> call = BaseApp.service().delete(BaseApp.userID, documentID);
        call.enqueue(new CallBackCustom<Void>(context) {
            @Override
            public void onResponseCustom(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    refresh.refreshSuccess(mFilteredList);
                    Toast.makeText(context,"Delete record successful!",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context,"Delete record error",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailureCustom(Call<Void> call, Throwable t) {
                Toast.makeText(context,"Delete record error",Toast.LENGTH_SHORT).show();
            }
        });

    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView textViewNo, textViewType, textViewStatus, textViewLyDo, textViewDate;

        private ItemClickListener itemClickListener; // Khai báo interface

        //Tạo setter cho biến itemClickListenenr
        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        ItemLongClickListener itemLongClickListener;

        public void setItemLongClickListener(ItemLongClickListener itemLongClickListener) {
            this.itemLongClickListener = itemLongClickListener;
        }

        public ViewHolder(View itemView) {
            super(itemView);
            textViewNo = itemView.findViewById(R.id.textViewNo);
            textViewType = itemView.findViewById(R.id.textViewType);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            textViewLyDo = itemView.findViewById(R.id.textViewLyDo);
            textViewDate = itemView.findViewById(R.id.textViewDate);

            itemView.setOnClickListener(this);// Mấu chốt ở đây , set sự kiên onClick cho View
            itemView.setOnLongClickListener(this);
        }


        @Override
        public void onClick(View v) {
            itemClickListener.itemClick(v, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            itemLongClickListener.itemLongClick(v,getAdapterPosition());
            return true;
        }
    }

}
