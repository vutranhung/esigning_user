package vn.com.japfa.esigning_user.bringout;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.com.japfa.esigning_user.Interface.ItemClickListener;
import vn.com.japfa.esigning_user.Interface.ItemLongClickListener;
import vn.com.japfa.esigning_user.R;
import vn.com.japfa.esigning_user.models.Goods;

public class AdapterBringOut extends RecyclerView.Adapter<AdapterBringOut.ViewHolderBring> {

    private Context context;
    private List<Goods> listGoods;


    public AdapterBringOut(Context context, List<Goods> list) {
        this.context = context;
        listGoods = list;
    }

    @NonNull
    @Override
    public ViewHolderBring onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.dialog_bring_out_item, parent, false);
        return new ViewHolderBring(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderBring holder, int position) {
        holder.textViewNameBring.setText(listGoods.get(position).getProductName());
        holder.textViewQtyBring.setText(listGoods.get(position).getProductQuantity() + "");
        holder.textViewDescriptionBring.setText(listGoods.get(position).getProductDescription());
        holder.textViewReasonBring.setText(listGoods.get(position).getProductReason());


        holder.setItemLongClickListener(new ItemLongClickListener() {
            @Override
            public void itemLongClick(View view, int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure want to delete this product?");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listGoods.remove(position);
                        notifyItemRemoved(position);
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listGoods.size();
    }


    class ViewHolderBring extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView textViewNameBring, textViewQtyBring, textViewDescriptionBring, textViewReasonBring;

        ItemLongClickListener itemLongClickListener;

        public void setItemLongClickListener(ItemLongClickListener itemLongClickListener) {
            this.itemLongClickListener = itemLongClickListener;
        }

        public ViewHolderBring(View itemView) {
            super(itemView);
            textViewNameBring = itemView.findViewById(R.id.textViewNameBring);
            textViewQtyBring = itemView.findViewById(R.id.textViewQtyBring);
            textViewDescriptionBring = itemView.findViewById(R.id.textViewDescriptionBring);
            textViewReasonBring = itemView.findViewById(R.id.textViewReasonBring);

            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            itemLongClickListener.itemLongClick(view,getAdapterPosition());
            return true;
        }
    }
}
