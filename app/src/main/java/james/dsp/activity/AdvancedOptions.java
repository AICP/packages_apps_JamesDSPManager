package james.dsp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.util.Log;

import james.dsp.R;

public class AdvancedOptions extends PreferenceFragment {
    private static final String TAG = DSPScreen.class.getSimpleName();

    private final SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            Log.e(TAG, "Preferenced changed!");
            getActivity().sendBroadcast(new Intent(DSPManager.ACTION_UPDATE_PREFERENCES));
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        String config = getArguments().getString("config");
        getPreferenceManager().setSharedPreferencesName(
                DSPManager.SHARED_PREFERENCES_BASENAME + "." + "advanced" + "." + config);
        getPreferenceManager().setSharedPreferencesMode(Context.MODE_MULTI_PROCESS);
        try
        {
            int xmlId = R.xml.class.getField( "advanced_preferences").getInt(null);
            addPreferencesFromResource(xmlId);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        getPreferenceManager().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        getPreferenceManager().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(listener);
    }
}
