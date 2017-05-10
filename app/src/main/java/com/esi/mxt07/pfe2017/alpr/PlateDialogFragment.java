package com.esi.mxt07.pfe2017.alpr;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PlateDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Get a layout inflater and inflate the layout
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_plate, null);

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(layout);

        // Set the lp number in the edit text
        EditText etPlateNumber = (EditText) layout.findViewById(R.id.etPlateNumber);
        etPlateNumber.setText(OldActivity.recognizeLicensePlate(0L));

        // Set a listener to the OK button
        Button btnPlateDialogOK = (Button) layout.findViewById(R.id.btnPlateDialogOK);
        btnPlateDialogOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlateDialogFragment.this.dismiss();
            }
        });

        // Create the AlertDialog object and return it
        return builder.create();
    }

}
