package vn.com.japfa.esigning_user.signer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import vn.com.japfa.esigning_user.ActivityPdfView_;
import vn.com.japfa.esigning_user.R;
import vn.com.japfa.esigning_user.base.BaseApp;
import vn.com.japfa.esigning_user.base.CallBackCustom;
import vn.com.japfa.esigning_user.base.activity.BaseActivity;
import vn.com.japfa.esigning_user.models.signer.Data;
import vn.com.japfa.esigning_user.models.signer.DocumentsDatum;
import vn.com.japfa.esigning_user.models.signer.UserName;

@EActivity
public class ActivityDocumentHD extends BaseActivity {
    @ViewById
    Button buttonAccept;
    @ViewById
    Button buttonReject;
    @ViewById
    Button buttonFW;
    @ViewById
    Button buttonPending;
    @ViewById
    TextInputLayout textInputLayoutComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);

        setTitle("Signing Confirmation");


        if (BaseApp.status.equals("Completed") || BaseApp.status.equals("Signed")) {
            buttonAccept.setVisibility(View.INVISIBLE);
            buttonReject.setVisibility(View.INVISIBLE);
            buttonFW.setVisibility(View.GONE);
            buttonPending.setVisibility(View.GONE);
            textInputLayoutComment.setVisibility(View.GONE);
        }

        listSigned();

        //enable backup actionbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        editTextComment.requestFocus();

    }

    @OptionsItem(android.R.id.home)
    protected void home() {
        onBackPressed();
    }

    //region Accept

    @ViewById
    EditText editTextComment;

    @Click
    protected void buttonAccept() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure want to accept this document?");
        builder.setCancelable(true);
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                accept();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void accept() {

        Call<Data> call = BaseApp.service().accept(BaseApp.userID, BaseApp.documentID
                , "", editTextComment.getText().toString());

        call.enqueue(new CallBackCustom<Data>(this) {
            @Override
            public void onResponseCustom(Call<Data> call, Response<Data> response) {
                if (response.body() != null) {
                    String statusCode = response.body().getResponseMeta().getStatusCode();
                    String message = response.body().getResponseMeta().getMessage();
                    if (statusCode.equals("201")) {
                        Toast.makeText(ActivityDocumentHD.this, message, Toast.LENGTH_SHORT).show();
                        finishAndRemoveTask();
                    } else {
                        Toast.makeText(ActivityDocumentHD.this, message, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailureCustom(Call<Data> call, Throwable t) {
                Toast.makeText(ActivityDocumentHD.this, "Check on your Internet connection and try again.(Accept error)", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //endregion

    //region sign document
    @ViewById
    RecyclerView recycleViewSigned;

    @AfterViews
    protected void createView() {
        recycleViewSigned = BaseApp.createRecycler(getApplicationContext(), recycleViewSigned);
    }

    private void listSigned() {

        Call<Data> call = BaseApp.service().listSigned(BaseApp.userID, BaseApp.documentID);

        call.enqueue(new CallBackCustom<Data>(this) {
            @Override
            public void onResponseCustom(Call<Data> call, Response<Data> response) {
                if (response.body() != null) {
                    String statusCode = response.body().getResponseMeta().getStatusCode();
                    if (statusCode.equals("200")) {
                        List<DocumentsDatum> listSigned = response.body().getDocumentsData();
                        AdapterSigned adapter = new AdapterSigned(ActivityDocumentHD.this, listSigned);
                        recycleViewSigned.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailureCustom(Call<Data> call, Throwable t) {
                Toast.makeText(ActivityDocumentHD.this, "Check on your Internet connection and try again.(listSigned - error)", Toast.LENGTH_SHORT).show();
            }
        });

    }
    //endregion

    //region Pending
    @Click
    protected void buttonPending() {
        pending();
    }

    private void pending() {
        Call<Data> call = BaseApp.service().pending(BaseApp.userID, BaseApp.documentID
                , editTextComment.getText().toString());
        call.enqueue(new CallBackCustom<Data>(this) {
            @Override
            public void onResponseCustom(Call<Data> call, Response<Data> response) {
                if (response.body() != null) {
                    String statusCode = response.body().getResponseMeta().getStatusCode();
                    String message = response.body().getResponseMeta().getMessage();
                    if (statusCode.equals("201")) {
                        Toast.makeText(ActivityDocumentHD.this, message, Toast.LENGTH_SHORT).show();
                        finishAndRemoveTask();
                    } else {
                        Toast.makeText(ActivityDocumentHD.this, message, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailureCustom(Call<Data> call, Throwable t) {
                Toast.makeText(ActivityDocumentHD.this, "Check on your Internet connection and try again.(Pending error)", Toast.LENGTH_SHORT).show();
            }
        });

    }
    //endregion

    //regionreject
    @Click
    protected void buttonReject() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure want to reject this document?");
        builder.setCancelable(true);
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reject();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void reject() {

        Call<Data> call = BaseApp.service().reject(BaseApp.userID, BaseApp.documentID
                , editTextComment.getText().toString());

        call.enqueue(new CallBackCustom<Data>(this) {
            @Override
            public void onResponseCustom(Call<Data> call, Response<Data> response) {
                if (response.body() != null) {
                    String statusCode = response.body().getResponseMeta().getStatusCode();
                    String message = response.body().getResponseMeta().getMessage();

                    if (statusCode.equals("201")) {
                        Toast.makeText(ActivityDocumentHD.this, message, Toast.LENGTH_SHORT).show();
                        finishAndRemoveTask();
                    } else {
                        Toast.makeText(ActivityDocumentHD.this, message, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailureCustom(Call<Data> call, Throwable t) {
                Toast.makeText(ActivityDocumentHD.this, "Check on your Internet connection and try again.(Reject error)", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //endregion

    //region Preview
    @Click
    protected void buttonPreview() {
        Intent intentPDFView = new Intent(ActivityDocumentHD.this, ActivityPdfView_.class);
        startActivity(intentPDFView);
    }
    //endregion

    //region forward
    EditText editTextCommentDialog;
    AutoCompleteTextView autoCompleteTextViewFW;

    @Click
    void buttonFW() {
        getUserName();
    }

    List<String> listUserName;

    private void getUserName() {
        Call<UserName> call = BaseApp.service().getAllUser(BaseApp.userID);
        call.enqueue(new CallBackCustom<UserName>(ActivityDocumentHD.this) {
            @Override
            public void onResponseCustom(Call<UserName> call, Response<UserName> response) {
                UserName userName = response.body();
                if (userName != null) {
                    String status = userName.getResponseMeta().getStatusCode();
                    String message = userName.getResponseMeta().getMessage();
                    if (status.equals("200")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityDocumentHD.this, R.style.Theme_Dialog_Alert);
                        LayoutInflater layoutInflater = LayoutInflater.from(ActivityDocumentHD.this);
                        View dialogLayout = layoutInflater.inflate(R.layout.dialog_forward, null);
                        builder.setView(dialogLayout);
                        builder.setMessage("Choose a person to forward");

                        autoCompleteTextViewFW = dialogLayout.findViewById(R.id.autoCompleteTextViewFW);
                        editTextCommentDialog = dialogLayout.findViewById(R.id.editTextCommentDialog);
                        autoCompleteTextViewFW.setThreshold(2);

                        listUserName = userName.getUsernameData();
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(ActivityDocumentHD.this, android.R.layout.simple_list_item_1, listUserName);
                        autoCompleteTextViewFW.setAdapter(adapter);

                        builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String userName = autoCompleteTextViewFW.getText().toString();
                                boolean check = true;
                                for (String name : listUserName) {
                                    if (userName.equals(name)) {
                                        forward(userName, editTextCommentDialog.getText().toString());
                                        check = false;
                                        break;
                                    }
                                }
                                if (check) {
                                    Toast.makeText(ActivityDocumentHD.this, "Pls check UserName", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else
                        Toast.makeText(ActivityDocumentHD.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailureCustom(Call<UserName> call, Throwable t) {
                Toast.makeText(ActivityDocumentHD.this, "getUserName onFailureCustom(" + t.getMessage() + ")", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void forward(String username, String comment) {

        Call<UserName> call = BaseApp.service().forward(BaseApp.userID, BaseApp.documentID, username, comment);
        Log.e("++", BaseApp.userID + "   " + BaseApp.documentID);
        call.enqueue(new CallBackCustom<UserName>(this) {
            @Override
            public void onResponseCustom(Call<UserName> call, Response<UserName> response) {
                UserName userNameResponse = response.body();
                if (userNameResponse != null) {
                    String status = userNameResponse.getResponseMeta().getStatusCode();
                    String message = userNameResponse.getResponseMeta().getMessage();
                    if (status.equals("201")) {
                        Toast.makeText(ActivityDocumentHD.this, message, Toast.LENGTH_SHORT).show();
                        finishAndRemoveTask();
                    } else {
                        Toast.makeText(ActivityDocumentHD.this, message, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailureCustom(Call<UserName> call, Throwable t) {
                Toast.makeText(ActivityDocumentHD.this, "forward error(" + t.getMessage() + ")", Toast.LENGTH_SHORT).show();

            }
        });
    }
    //endregion
}
