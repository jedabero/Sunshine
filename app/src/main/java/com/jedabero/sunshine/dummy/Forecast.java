package com.jedabero.sunshine.dummy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class Forecast {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<ForecastEntry> ITEMS = new ArrayList<>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<Long, ForecastEntry> ITEM_MAP = new HashMap<>();

    public static void createFrom(JSONObject forecast) {
        ITEMS.clear();
        try {
            JSONArray list = forecast.getJSONArray("list");

            for (int i = 0; i < list.length(); i++) {
                JSONObject day = list.getJSONObject(i);
                long datetime = day.getLong("dt");
                JSONObject weather = day.getJSONArray("weather").getJSONObject(0);
                String main = weather.getString("main");
                JSONObject temperature = day.getJSONObject("temp");
                double min = temperature.getDouble("min");
                double max = temperature.getDouble("max");
                ForecastEntry fe = new ForecastEntry(datetime, main, max, min);
                ITEMS.add(fe);
                ITEM_MAP.put(datetime, fe);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class ForecastEntry {
        public final long datetime;
        public final String description;
        public final double max;
        public final double min;

        public ForecastEntry(long dt, String description, double max, double min) {
            this.datetime = dt;
            this.description = description;
            this.max = max;
            this.min = min;
        }

        @Override
        public String toString() {
            return description;
        }
    }
}
