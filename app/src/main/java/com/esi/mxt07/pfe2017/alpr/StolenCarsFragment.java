package com.esi.mxt07.pfe2017.alpr;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StolenCarsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView stolenCarsRecycler = (RecyclerView) inflater.inflate(
                R.layout.fragment_stolen_cars, container, false);
        String[] stolenCarsNumbers = {"ABC123", "WXC789", "QSD456",
                "ABC123", "WXC789", "QSD456",
                "ABC123", "WXC789", "QSD456",
                "ABC123", "WXC789", "QSD456",
                "ABC123", "WXC789", "QSD456",
                "ABC123", "WXC789", "QSD456",
                "ABC123", "WXC789", "QSD456",
                "ABC123", "WXC789", "QSD456",
                "ABC123", "WXC789", "QSD456",
                "ABC123", "WXC789", "QSD456",
                "ABC123", "WXC789", "QSD456",
                "ABC123", "WXC789", "QSD456",
                "ABC123", "WXC789", "QSD456",
                "ABC123", "WXC789", "QSD456",
                "ABC123", "WXC789", "QSD456",
                "ABC123", "WXC789", "QSD456",
                "ABC123", "WXC789", "QSD456",
                "ABC123", "WXC789", "QSD456",
                "ABC123", "WXC789", "QSD456",
                "ABC123", "WXC789", "QSD456",
                "ABC123", "WXC789", "QSD456",
                "ABC123", "WXC789", "QSD456",
                "ABC123", "WXC789", "QSD456",
                "ABC123", "WXC789", "QSD456",
                "ABC123", "WXC789", "QSD456",
                "ABC123", "WXC789", "QSD456",
                "ABC123", "WXC789", "QSD456",
                "ABC123", "WXC789", "QSD456",
                "ABC123", "WXC789", "QSD456",
                "ABC123", "WXC789", "QSD456",
                "ABC123", "WXC789", "QSD456",
                "ABC123", "WXC789", "QSD456",
                "ABC123", "WXC789", "QSD456"
        };
        StolenCarsAdapter adapter = new StolenCarsAdapter(stolenCarsNumbers);
        stolenCarsRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        stolenCarsRecycler.setLayoutManager(layoutManager);
        return stolenCarsRecycler;
    }

}
