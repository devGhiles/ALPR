package com.esi.mxt07.pfe2017.alpr;

import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    public interface UserCardClickListener {
        void onUserCardClicked(User user);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView cv, final UserCardClickListener clickListener) {
            super(cv);
            cardView = cv;
        }
    }

    private Cursor cursor;
    private UserCardClickListener listener;

    public UsersAdapter(Cursor cursor, UserCardClickListener listener) {
        this.cursor = cursor;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_user, parent, false);
        return new ViewHolder(cv, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView tvUsername = (TextView) cardView.findViewById(R.id.tvUsername);
        if (cursor.moveToPosition(position)) {
            final int id = cursor.getInt(0);
            final String username = cursor.getString(1);
            final String passwordSha256 = cursor.getString(2);
            final boolean isAdmin = (cursor.getInt(3) != 0);
            if (isAdmin) {
                tvUsername.setText(username + " (Administrator)");
            } else {
                tvUsername.setText(username);
            }
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onUserCardClicked(new User(id, username, passwordSha256, isAdmin));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public void changeCursor(Cursor cursor) {
        this.cursor.close();
        this.cursor = cursor;
        notifyDataSetChanged();
    }
}
