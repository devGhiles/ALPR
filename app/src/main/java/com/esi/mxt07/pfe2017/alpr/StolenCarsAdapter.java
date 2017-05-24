package com.esi.mxt07.pfe2017.alpr;

import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class StolenCarsAdapter extends RecyclerView.Adapter<StolenCarsAdapter.ViewHolder> {

    private Cursor stolenCarsCursor;
    private StolenCarCardViewClickListener clickListener;

    public interface StolenCarCardViewClickListener {
        void onClick(String plateNumber);
    }

    public StolenCarsAdapter(Cursor stolenCarsCursor, StolenCarCardViewClickListener clickListener) {
        this.stolenCarsCursor = stolenCarsCursor;
        this.clickListener = clickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView cv, final StolenCarCardViewClickListener clickListener) {
            super(cv);
            cardView = cv;
        }
    }

    @Override
    public StolenCarsAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_stolen_car, parent, false);
        return new ViewHolder(cv, clickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView textView = (TextView) cardView.findViewById(R.id.stolen_car_number);
        if (stolenCarsCursor.moveToPosition(position)) {
            final String plateNumber = stolenCarsCursor.getString(0);
            textView.setText(plateNumber);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onClick(plateNumber);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return stolenCarsCursor.getCount();
    }

    public void changeCursor(Cursor cursor) {
        this.stolenCarsCursor.close();
        this.stolenCarsCursor = cursor;
        notifyDataSetChanged();
    }
}
