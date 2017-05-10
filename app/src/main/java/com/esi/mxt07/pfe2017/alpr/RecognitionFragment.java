package com.esi.mxt07.pfe2017.alpr;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class RecognitionFragment extends Fragment implements View.OnClickListener {

    public RecognitionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recognition, container, false);
        FloatingActionButton fabTakePicture = (FloatingActionButton)
                root.findViewById(R.id.fabTakePicture);
        fabTakePicture.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fabTakePicture) {
            DialogFragment plateDialogFragment = new PlateDialogFragment();
            plateDialogFragment.show(getActivity().getSupportFragmentManager(), "RecognitionFragment");
        }
    }
}
