package vn.com.japfa.esigning_user.base;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.japfa.esigning_user.R;

public abstract class CallBackCustom<T> implements Callback<T> {

    private AlertDialog dialog;


    public CallBackCustom(Activity context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.ProgressBarDialogTheme);
        builder.setCancelable(false); // if you want User123 to wait for some process to finish,
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.progress_massage, null);

        builder.setView(view);
        TextView textViewMessage = view.findViewById(R.id.textViewMessage);
        textViewMessage.setText(message);

        dialog = builder.create();
        dialog.show();
    }

    public CallBackCustom(Activity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.ProgressBarDialogTheme);
        builder.setCancelable(false); // if you want User123 to wait for some process to finish,
        builder.setView(R.layout.progress);
        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        onResponseCustom(call, response);
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        onFailureCustom(call, t);
    }

    public abstract void onResponseCustom(Call<T> call, Response<T> response);

    public abstract void onFailureCustom(Call<T> call, Throwable t);
}
