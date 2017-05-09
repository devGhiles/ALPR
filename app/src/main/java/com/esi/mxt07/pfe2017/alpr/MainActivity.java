package com.esi.mxt07.pfe2017.alpr;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("native-lib");
    }

    public native String recognizeLicensePlate(long imgAddr);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(recognizeLicensePlate(0L));
    }

    public void onClickBtnTakePicture(View view) {
        DialogFragment plateDialogFragment = new PlateDialogFragment();
        plateDialogFragment.show(getSupportFragmentManager(), "TAG");
    }

}
