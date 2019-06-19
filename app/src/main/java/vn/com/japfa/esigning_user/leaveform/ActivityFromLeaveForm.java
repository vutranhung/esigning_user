package vn.com.japfa.esigning_user.leaveform;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemSelect;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;
import vn.com.japfa.esigning_user.R;
import vn.com.japfa.esigning_user.base.BaseApp;
import vn.com.japfa.esigning_user.base.activity.BaseActivityFrom;
import vn.com.japfa.esigning_user.models.AnnualLeave;
import vn.com.japfa.esigning_user.models.AnnualLeaveResponse;
import vn.com.japfa.esigning_user.base.CallBackCustom;
import vn.com.japfa.esigning_user.models.ShowDocument;
import vn.com.japfa.esigning_user.worktravel.ActivityFromWorkTravel;

@EActivity
public class ActivityFromLeaveForm extends BaseActivityFrom {

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setLayout(R.layout.leave_form_activity);
        BaseApp.type = "LEAVE";
        setTitle(getResources().getString(R.string.leave));

    }

    @AfterViews
    protected void createView() {
        if (BaseApp.documentID != null) {
            showDocument();
        } else {
            Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 0);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.ENGLISH);
            String date = simpleDateFormat.format(calendar.getTime());
            editTextFromDate1.setText(date);
            editTextFromDate2.setText(date);
            autoCompleteTextViewtQty1.setText(qty1);
            autoCompleteTextViewtQty2.setText(qty2);

            autoCompleteTextViewtQty1.addTextChangedListener(new TextWatcher() {
                Handler handler = new Handler();
                Runnable runnable;

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    handler.removeCallbacks(runnable);
                }


                @Override
                public void afterTextChanged(Editable s) {
                    runnable = new Runnable() {
                        @Override
                        public void run() {
                            if (s.length() > 0) {
                                qty1 = s.toString();
                                createDocument();
                            }
                        }
                    };

                    handler.postDelayed(runnable, 0);
                }
            });

            autoCompleteTextViewtQty2.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() > 0) {
                        qty2 = s.toString();
                        createDocument();
                    }
                }
            });

            createDocument();
        }
    }

    private String qty1 = "0";
    private String qty2 = "0";

    //region view
    @ViewById
    EditText editTextMieuTa;
    @ViewById
    TextView textViewTon;
    @ViewById
    TextView textViewNgayNghi;
    @ViewById
    TextView textViewNamNay;
    @ViewById
    TextView textViewDaNghi;
    @ViewById
    TextView textViewConLai;

    @ViewById
    EditText editTextFromDate1;
    @ViewById
    EditText editTextToDate1;
    @ViewById
    EditText editTextFromDate2;
    @ViewById
    EditText editTextToDate2;
    @ViewById
    AutoCompleteTextView autoCompleteTextViewtQty1;
    @ViewById
    AutoCompleteTextView autoCompleteTextViewtQty2;
    //endregion

    @AfterViews
    protected void autoCompleteTextViewtQty() {
        List<String> qtys = new ArrayList<>();
        qtys.add("0");
        qtys.add("0.5");
        qtys.add("1");
        qtys.add("1.5");
        qtys.add("2");
        qtys.add("2.5");
        qtys.add("3");
        qtys.add("3.5");
        qtys.add("4");
        qtys.add("4.5");
        qtys.add("5");
        qtys.add("5.5");
        qtys.add("6");

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_item, qtys);
        autoCompleteTextViewtQty1.setAdapter(adapter);
        autoCompleteTextViewtQty2.setAdapter(adapter);
        autoCompleteTextViewtQty1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCompleteTextViewtQty1.showDropDown();
            }
        });
        autoCompleteTextViewtQty2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCompleteTextViewtQty2.showDropDown();
            }
        });

    }

    //region setdate
    @Click
    protected void editTextFromDate1() {
        final Calendar currentDate = Calendar.getInstance(Locale.ENGLISH);
        Calendar date = Calendar.getInstance(Locale.ENGLISH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                date.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(ActivityFromLeaveForm.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date.set(Calendar.MINUTE, minute);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.ENGLISH);
                        editTextFromDate1.setText(simpleDateFormat.format(date.getTime()));
                        createDocument();

                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();

            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE));
//        datePickerDialog.getDatePicker().setMinDate(currentDate.getTimeInMillis());
        datePickerDialog.show();
    }

    @Click
    protected void editTextFromDate2() {
        final Calendar currentDate = Calendar.getInstance(Locale.ENGLISH);
        Calendar date = Calendar.getInstance(Locale.ENGLISH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                date.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(ActivityFromLeaveForm.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date.set(Calendar.MINUTE, minute);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy h:mm a", Locale.ENGLISH);
                        editTextFromDate2.setText(simpleDateFormat.format(date.getTime()));
                        createDocument();

                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();

            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE));
//        datePickerDialog.getDatePicker().setMinDate(currentDate.getTimeInMillis());
        datePickerDialog.show();
    }
    //endregion

    //region add,get data spinner
    @ViewById
    Spinner spinnerType;
    private String typeOfLeave = "Annual Leave/Nghỉ phép";
    private ArrayAdapter adapterType;

    @AfterViews
    protected void addDataSpinner() {
        List<String> listType = new ArrayList<>();
        listType.add("Annual Leave/Nghỉ phép");
        listType.add("Unpaid Leave/Nghỉ Không Lương");
        listType.add("Sick/Nghỉ ốm");
        adapterType = new ArrayAdapter(this, R.layout.spinner_item, listType);
        spinnerType.setAdapter(adapterType);
    }

    private boolean checkspinner = false;

    @ItemSelect
    void spinnerType(boolean selected) {
        if (selected) {
            typeOfLeave = spinnerType.getSelectedItem().toString();
            if (checkspinner) {
                createDocument();
            } else checkspinner = true;
        }
    }
    //endregion

    @Override
    public void showDocument() {
        Call<String> call = BaseApp.service().editDocument(BaseApp.userID, BaseApp.documentID);

        call.enqueue(new CallBackCustom<String>(this) {
            @Override
            public void onResponseCustom(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {
                    String strData = response.body();
                    if (strData!=null && !strData.isEmpty()) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<HashMap<String, String>>() {
                        }.getType();
                        HashMap<String, String> hashMapData = gson.fromJson(strData, type);

                        textViewTon.setText(hashMapData.get("TxtTotalLastYear"));
                        textViewNgayNghi.setText(hashMapData.get("TxtDaysWillTake"));
                        textViewNamNay.setText(hashMapData.get("TotalCurrentYear"));
                        textViewDaNghi.setText(hashMapData.get("TxtTotalDaysTaken"));
                        textViewConLai.setText(hashMapData.get("BalanceOfLeave"));
                        editTextFromDate1.setText(BaseApp.convertDate(hashMapData.get("From1"), "view"));
                        editTextToDate1.setText(BaseApp.convertDate(hashMapData.get("To1"), "view"));
                        editTextFromDate2.setText(BaseApp.convertDate(hashMapData.get("From2"), "view"));
                        editTextToDate2.setText(BaseApp.convertDate(hashMapData.get("To2"), "view"));
                        autoCompleteTextViewtQty1.setText(hashMapData.get("txtDayWillTake1"));
                        autoCompleteTextViewtQty2.setText(hashMapData.get("txtDayWillTake2"));


                        int i = adapterType.getPosition(hashMapData.get("TypeOfLeave"));
                        spinnerType.setSelection(i);
                        editTextMieuTa.setText(hashMapData.get("documentDesc"));

                        autoCompleteTextViewtQty1.addTextChangedListener(new TextWatcher() {
                            Handler handler = new Handler();
                            Runnable runnable;

                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                handler.removeCallbacks(runnable);
                            }


                            @Override
                            public void afterTextChanged(Editable s) {
                                runnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        if (s.length() > 0) {
                                            qty1 = s.toString();
                                            createDocument();
                                        }
                                    }
                                };

                                handler.postDelayed(runnable, 0);
                            }
                        });
                        autoCompleteTextViewtQty2.addTextChangedListener(new TextWatcher() {

                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if (s.length() > 0) {
                                    qty2 = s.toString();
                                    createDocument();
                                }
                            }
                        });

                        editTextFromDate1.setEnabled(false);
                        editTextFromDate2.setEnabled(false);
                        autoCompleteTextViewtQty1.setEnabled(false);
                        autoCompleteTextViewtQty2.setEnabled(false);
                        editTextToDate1.setEnabled(false);
                        editTextToDate2.setEnabled(false);
                        editTextMieuTa.requestFocus();
                        editTextMieuTa.requestFocusFromTouch();

                        if (BaseApp.status.equals("New") || BaseApp.status.equals("Rejected")) {
                            qty1 = hashMapData.get("txtDayWillTake1");
                            qty2 = hashMapData.get("txtDayWillTake2");
                            createDocument();
                            editTextFromDate1.setEnabled(true);
                            editTextFromDate2.setEnabled(true);
                            autoCompleteTextViewtQty1.setEnabled(true);
                            autoCompleteTextViewtQty2.setEnabled(true);
                            editTextToDate1.setEnabled(true);
                            editTextToDate2.setEnabled(true);
                        }


                    } else {
                        Toast.makeText(ActivityFromLeaveForm.this, "View document error", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(ActivityFromLeaveForm.this, "View document error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailureCustom(Call<String> call, Throwable t) {
                Toast.makeText(ActivityFromLeaveForm.this, "showDocument error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void createDocument() {
        Call<AnnualLeave> call = BaseApp.service().getAnnualLeaveByUserId(BaseApp.userID,
                BaseApp.convertDate(editTextFromDate1.getText().toString(), "send"),
                qty1,
                BaseApp.convertDate(editTextFromDate2.getText().toString(), "send"),
                qty2,
                typeOfLeave);

        call.enqueue(new CallBackCustom<AnnualLeave>(this) {
            @Override
            public void onResponseCustom(Call<AnnualLeave> call, Response<AnnualLeave> response) {

                if(response.isSuccessful()){
                    AnnualLeave annualLeave = response.body();
                    if(annualLeave!=null){
                        textViewTon.setText(annualLeave.getLastYearRest() + "");
                        textViewNgayNghi.setText(annualLeave.getCurrentNumberDaysLeave() + "");
                        textViewNamNay.setText(annualLeave.getTotalCurrentYear() + "");
                        textViewDaNghi.setText(annualLeave.getTotalDaysLeaveThisYear() + "");
                        textViewConLai.setText(annualLeave.getCurrentYearRest() + "");

                        editTextToDate1.setText(BaseApp.convertDate(annualLeave.getTo1(), "view"));
                        editTextToDate2.setText(BaseApp.convertDate(annualLeave.getTo2(), "view"));
                        editTextFromDate1.setText(BaseApp.convertDate(annualLeave.getFrom1(), "view"));
                        editTextFromDate2.setText(BaseApp.convertDate(annualLeave.getFrom2(), "view"));
                    }else {
                        Toast.makeText(ActivityFromLeaveForm.this,"Get annual leave error" , Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(ActivityFromLeaveForm.this, "Get annual leave error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailureCustom(Call<AnnualLeave> call, Throwable t) {
                Toast.makeText(ActivityFromLeaveForm.this, "createDocument error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void save() {
        HashMap<String, String> data = new HashMap<>();
        data.put("TotalLastYear", textViewTon.getText().toString());
        data.put("DaysWillTake", textViewNgayNghi.getText().toString());
        data.put("TotalCurrentYear", textViewNamNay.getText().toString());
        data.put("TotalDaysTaken", textViewDaNghi.getText().toString());
        data.put("TypeOfLeave", typeOfLeave);
        data.put("From1", BaseApp.convertDate(editTextFromDate1.getText().toString(), "send"));
        data.put("To1", BaseApp.convertDate(editTextToDate1.getText().toString(), "send"));
        data.put("From2", BaseApp.convertDate(editTextFromDate2.getText().toString(), "send"));
        data.put("To2", BaseApp.convertDate(editTextToDate2.getText().toString(), "send"));
        data.put("priority", "Normal");
        data.put("documentDesc", editTextMieuTa.getText().toString());
        data.put("txtDayWillTake1", autoCompleteTextViewtQty1.getText().toString());
        data.put("txtDayWillTake2", autoCompleteTextViewtQty2.getText().toString());
        data.put("documentID", BaseApp.documentID);


        data.put("TxtDaysWillTake", textViewNgayNghi.getText().toString());
        data.put("TxtThisTimeTaken", textViewNgayNghi.getText().toString());
        data.put("TxtTotalCurrentYear", textViewNamNay.getText().toString());
        data.put("TxtTotalDaysTaken", textViewDaNghi.getText().toString());
        data.put("TxtTotalLastYear", textViewTon.getText().toString());
        data.put("BalanceOfLeave", textViewConLai.getText().toString());

        saveData(data);
    }

}
