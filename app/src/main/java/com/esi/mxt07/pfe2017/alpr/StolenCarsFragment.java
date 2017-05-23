package com.esi.mxt07.pfe2017.alpr;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class StolenCarsFragment extends Fragment implements StolenCarCardViewClickListener {

    private class GetPlateNumbersCursorTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SQLiteOpenHelper helper = new StolenCarsDatabaseHelper(getActivity());
                db = helper.getReadableDatabase();
                cursor = db.query("Plate", new String[]{"PlateNumber"},
                        null, null, null, null, "PlateNumber ASC");
                return true;
            } catch (SQLiteException e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                adapter = new StolenCarsAdapter(cursor, StolenCarsFragment.this);
                stolenCarsRecycler.setAdapter(adapter);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                stolenCarsRecycler.setLayoutManager(layoutManager);
            } else {
                Toast.makeText(getActivity(), R.string.stolen_cars_database_unavailable,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class DeletePlateTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            if ((params == null) || (params.length == 0)) {
                return false;
            }

            String plateNumber = params[0];
            try {
                StolenCarsDatabaseHelper helper = new StolenCarsDatabaseHelper(getActivity());
                SQLiteDatabase dbForDeletion = helper.getWritableDatabase();
                helper.deletePlate(dbForDeletion, plateNumber);
                cursor = db.query("Plate", new String[]{"PlateNumber"},
                        null, null, null, null, "PlateNumber ASC");
                return true;
            } catch (SQLiteException e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                adapter.changeCursor(cursor);
            } else {
                Toast.makeText(getActivity(), R.string.stolen_cars_database_unavailable,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private SQLiteDatabase db;
    private Cursor cursor;
    private StolenCarsAdapter adapter;
    private RecyclerView stolenCarsRecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        stolenCarsRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_stolen_cars,
                container, false);
        new GetPlateNumbersCursorTask().execute();
        return stolenCarsRecycler;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }

    @Override
    public void onClick(String plateNumber) {
        new DeletePlateTask().execute(plateNumber);
    }
}
