package com.project.yuvraj;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.project.yuvraj.myapplication.MyApplication;

/**
 * Created by Lenovo on 6/9/2016.
 */
public class EditDialog extends DialogFragment implements View.OnClickListener {

    Button save, cancel;
    TextView title;
    NumberPicker numberPicker;
    Communicator communicator;
    String getname , getquantity, getposition;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        communicator = (Communicator) activity;
    }

    public void getValues(String name,String quantity,String position){

        getname = name;
        getquantity = quantity;
        getposition = position;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.edit_dialog, null);
        cancel = (Button) view.findViewById(R.id.btnCancel);
        save = (Button) view.findViewById(R.id.btnSave);
        title = (TextView) view.findViewById(R.id.dialogTitle);
        numberPicker = (NumberPicker) view.findViewById(R.id.dialogPickNumber);

        title.setText(getname);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(100);
        int quant = Integer.parseInt(getquantity);
        numberPicker.setValue(quant);
        numberPicker.setWrapSelectorWheel(false);


        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
        setCancelable(false);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSave) {

            final int data = numberPicker.getValue();
            dismiss();
            final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading Data...");
            progressDialog.show();


            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            communicator.onDialogMessage(getname,String.valueOf(data),getposition);
                            progressDialog.dismiss();
                        }
                    }, 2000);


        } else {

            dismiss();
            // communicator.onDialogMessage(" The Card Has Been Dismissed ");
            Toast.makeText(getActivity(), " The Card Has Been Dismissed ", Toast.LENGTH_LONG).show();
        }
    }

    interface Communicator {
        public void onDialogMessage(String title, String quantity, String position);
    }
}
