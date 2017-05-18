package com.esi.mxt07.pfe2017.alpr;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class StolenCarsFragment extends Fragment {

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView stolenCarsRecycler = (RecyclerView) inflater.inflate(
                R.layout.fragment_stolen_cars, container, false);

        try {
            SQLiteOpenHelper helper = new StolenCarsDatabaseHelper(getActivity());
            db = helper.getReadableDatabase();
            cursor = db.query("Plate", new String[]{"PlateNumber"},
                    null, null, null, null, "PlateNumber ASC");

            StolenCarsAdapter adapter = new StolenCarsAdapter(cursor);
            stolenCarsRecycler.setAdapter(adapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            stolenCarsRecycler.setLayoutManager(layoutManager);
            return stolenCarsRecycler;

        } catch (SQLiteException e) {
            Toast.makeText(getActivity(), R.string.stolen_cars_database_unavailable,
                    Toast.LENGTH_SHORT).show();
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
