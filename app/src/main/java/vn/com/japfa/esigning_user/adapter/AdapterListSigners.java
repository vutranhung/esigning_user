package vn.com.japfa.esigning_user.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import vn.com.japfa.esigning_user.Interface.ItemClickListener;
import vn.com.japfa.esigning_user.R;
import vn.com.japfa.esigning_user.base.BaseApp;
import vn.com.japfa.esigning_user.models.ActionSigner;
import vn.com.japfa.esigning_user.base.CallBackCustom;
import vn.com.japfa.esigning_user.models.SignActivityDatum;
import vn.com.japfa.esigning_user.models.SignFollow;
import vn.com.japfa.esigning_user.models.SignFollowDatum;
import vn.com.japfa.esigning_user.Interface.Service;

import java.lang.annotation.Repeatable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.com.japfa.esigning_user.util.Constant;

public class AdapterListSigners extends RecyclerView.Adapter<AdapterListSigners.ViewHolder> {

  private   Activity context;
  private   List<SignFollow> list;

    public AdapterListSigners(Activity context, java.util.List<SignFollow> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.include_list_signers_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewUserNamePeople.setText(list.get(position).getUserName());
        holder.textViewManagerPeople.setText(list.get(position).getUserPosition());
        holder.textViewCommentPeople.setText(list.get(position).getSignComment());
        holder.textViewTimePeople.setText(list.get(position).getSignedAt());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void itemClick(View view, int position) {
                getActionSigner(list.get(position).getSignprocessId() + "");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewUserNamePeople, textViewManagerPeople, textViewCommentPeople, textViewTimePeople;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewUserNamePeople = itemView.findViewById(R.id.textViewUserNamePeople);
            textViewManagerPeople = itemView.findViewById(R.id.textViewManagerPeople);
            textViewCommentPeople = itemView.findViewById(R.id.textViewCommentPeople);
            textViewTimePeople = itemView.findViewById(R.id.textViewTimePeople);

            itemView.setOnClickListener(this);
        }

        private ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.itemClick(v,getAdapterPosition());
        }
    }

    private void getActionSigner(String signprocess_id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.SERVICE_URL_VALUE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Call<List<SignActivityDatum>> call = service.getActionSigner(signprocess_id);
        call.enqueue(new CallBackCustom<List<SignActivityDatum>>(context) {
            @Override
            public void onResponseCustom(Call<List<SignActivityDatum>> call, Response<List<SignActivityDatum>> response) {
                if(response.isSuccessful()){
                    List<SignActivityDatum> lstData=response.body();
                    if(lstData!=null){
                        if(lstData.size()>0)
                            showDialogActionSigner(lstData);
                    }else {
                        Toast.makeText(context, "Get list sign activities error", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(context, "Get list sign activities error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailureCustom(Call<List<SignActivityDatum>> call, Throwable t) {
                Toast.makeText(context, "onFailureCustom", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialogActionSigner(List<SignActivityDatum> list) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context, R.style.Theme_Dialog_Alert);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View dialogLayout = layoutInflater.inflate(R.layout.dialog_action_signer, null);
        builder.setView(dialogLayout);
        android.support.v7.app.AlertDialog dialog = builder.create();

        RecyclerView recyclerActionSigner = dialogLayout.findViewById(R.id.recyclerActionSigner);
        recyclerActionSigner = BaseApp.createRecycler(context.getApplicationContext(),recyclerActionSigner);
        AdapterActionSigner adapter = new AdapterActionSigner(context, list);
        recyclerActionSigner.setAdapter(adapter);

        dialog.show();

    }
}
