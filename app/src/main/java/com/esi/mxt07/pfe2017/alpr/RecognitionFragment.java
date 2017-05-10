package com.esi.mxt07.pfe2017.alpr;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class RecognitionFragment extends Fragment {

    private View root;

    public RecognitionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_recognition, container, false);
        return root;
    }

    public void onClickBtnTakePicture(View view) {
        DialogFragment plateDialogFragment = new PlateDialogFragment();
        plateDialogFragment.show(getActivity().getSupportFragmentManager(), "RecognitionFragment");
    }

}
