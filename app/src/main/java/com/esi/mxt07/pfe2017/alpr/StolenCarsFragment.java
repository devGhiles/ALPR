package com.esi.mxt07.pfe2017.alpr;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class StolenCarsFragment extends Fragment implements StolenCarsAdapter.StolenCarCardViewClickListener {

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

    private class InsertPlateTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            if ((params == null) || (params.length == 0)) {
                return false;
            }

            String plateNumber = params[0];
            try {
                StolenCarsDatabaseHelper helper = new StolenCarsDatabaseHelper(getActivity());
                SQLiteDatabase dbForInsertion = helper.getWritableDatabase();
                helper.insertPlate(dbForInsertion, plateNumber);
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
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_stolen_cars,
                container, false);
        stolenCarsRecycler = (RecyclerView) layout.findViewById(R.id.rvStolenCars);
        FloatingActionButton fabInsertPlate = (FloatingActionButton)
                layout.findViewById(R.id.fabInsertPlate);
        fabInsertPlate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout layout = new LinearLayout(getActivity());
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(getResources().getDimensionPixelSize(R.dimen.dialog_content_padding),
                        getResources().getDimensionPixelSize(R.dimen.dialog_content_padding),
                        getResources().getDimensionPixelSize(R.dimen.dialog_content_padding),
                        getResources().getDimensionPixelSize(R.dimen.dialog_content_padding)
                );
                TextView tvAddPlate = new TextView(getActivity());
                tvAddPlate.setText(R.string.add_stolen_car_message);
                final EditText etPlateNumber = new EditText(getActivity());
                etPlateNumber.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS |
                        InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                layout.addView(tvAddPlate);
                layout.addView(etPlateNumber);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.add_stolen_car_dialog_title)
                        .setView(layout)
                        .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new InsertPlateTask().execute(etPlateNumber.getText().toString());
                            }
                        })
                        .setNegativeButton(R.string.cancel, null)
                        .show();
            }
        });

        new GetPlateNumbersCursorTask().execute();

        return layout;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }

    // Stolen car card views listener
    @Override
    public void onClick(final String plateNumber) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.confirm_deletion)
                .setMessage(getString(R.string.confirm_plate_deletion_message) + plateNumber)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new DeletePlateTask().execute(plateNumber);
                    }
                })
                .setNegativeButton(R.string.no, null)
                .show();
    }
}
