package amamin.com.mathfacts;

import android.content.Context;
import android.content.SharedPreferences;

public class MathFactsUtility {
    public static void setPreference(Context context, String prefId, int prefValue)
    {
        SharedPreferences preferences = context.getSharedPreferences(MathFactsConstants.SharedPreferences, Context.MODE_PRIVATE);
        preferences.edit().putInt(prefId, prefValue).apply();
    }

    public static int getPreference(Context context, String prefId) {
        SharedPreferences preferences = context.getSharedPreferences(MathFactsConstants.SharedPreferences, Context.MODE_PRIVATE);
        return preferences.getInt(prefId, 0) + 1;
    }

    public static int getPreference(Context context, String prefId, int defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(MathFactsConstants.SharedPreferences, Context.MODE_PRIVATE);
        return preferences.getInt(prefId, defaultValue);
    }
}
