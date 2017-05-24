package com.esi.mxt07.pfe2017.alpr;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class UsersFragment extends Fragment implements UsersAdapter.UserCardClickListener {

    private class GetUsersCursorTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SQLiteOpenHelper helper = new UsersDatabaseHelper(getActivity());
                db = helper.getReadableDatabase();
                cursor = db.query("User", new String[]{"_id", "Username", "PasswordSHA256", "IsAdmin"},
                        null, null, null, null, "Username ASC");
                return true;
            } catch (SQLiteException e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                adapter = new UsersAdapter(cursor, UsersFragment.this);
                usersRecycler.setAdapter(adapter);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                usersRecycler.setLayoutManager(layoutManager);
            } else {
                Toast.makeText(getActivity(), R.string.users_database_unavailable,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private SQLiteDatabase db;
    private Cursor cursor;
    private UsersAdapter adapter;
    private RecyclerView usersRecycler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_users,
                container, false);
        usersRecycler = (RecyclerView) layout.findViewById(R.id.rvUsers);
        new GetUsersCursorTask().execute();
        return layout;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }

    @Override
    public void onUserCardClicked(User user) {
        Toast.makeText(getActivity(), "Click on user " + user.getUsername(), Toast.LENGTH_SHORT).show();
    }
}
