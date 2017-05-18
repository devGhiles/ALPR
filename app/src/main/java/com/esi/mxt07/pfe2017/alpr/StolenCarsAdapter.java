package com.esi.mxt07.pfe2017.alpr;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

public class StolenCarsAdapter extends RecyclerView.Adapter<StolenCarsAdapter.ViewHolder> {

    private String[] stolenCarsNumbers;

    public StolenCarsAdapter(String[] stolenCarsNumbers) {
        this.stolenCarsNumbers = stolenCarsNumbers;
    }

    //Provide a reference to the views used in the recycler view
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    @Override
    public StolenCarsAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType){
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_stolen_car, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        CardView cardView = holder.cardView;
        TextView textView = (TextView) cardView.findViewById(R.id.stolen_car_number);
        textView.setText(stolenCarsNumbers[position]);
    }

    @Override
    public int getItemCount(){
        return stolenCarsNumbers.length;
    }
}
