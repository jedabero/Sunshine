package com.jedabero.sunshine;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jedabero.sunshine.ForecastFragment.OnListFragmentInteractionListener;
import com.jedabero.sunshine.dummy.Forecast.ForecastEntry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ForecastEntry} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ForecastRecyclerViewAdapter extends RecyclerView.Adapter<ForecastRecyclerViewAdapter.ViewHolder> {

    private final List<ForecastEntry> mValues;
    private final OnListFragmentInteractionListener mListener;

    public ForecastRecyclerViewAdapter(List<ForecastEntry> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_forecast_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE h:m a", Locale.getDefault());
        String date = sdf.format(new Date(holder.mItem.datetime));
        holder.mDateTimeView.setText(date);
        holder.mDescriptionView.setText(holder.mItem.description);
        String max = holder.mView.getResources().getString(R.string.temperature_max, holder.mItem.max);
        holder.mTemperatureMaxView.setText(max);
        String min = holder.mView.getResources().getString(R.string.temperature_min, holder.mItem.min);
        holder.mTemperatureMinView.setText(min);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mDateTimeView;
        public final TextView mDescriptionView;
        public final TextView mTemperatureMaxView;
        public final TextView mTemperatureMinView;
        public ForecastEntry mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mDateTimeView = (TextView) view.findViewById(R.id.datetime);
            mDescriptionView = (TextView) view.findViewById(R.id.description);
            mTemperatureMaxView = (TextView) view.findViewById(R.id.temperature_max);
            mTemperatureMinView = (TextView) view.findViewById(R.id.temperature_min);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mDescriptionView.getText() + "'";
        }
    }
}
