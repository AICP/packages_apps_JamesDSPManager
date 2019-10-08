package james.dsp.activity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import james.dsp.R;
import james.dsp.preference.EqualizerPreference;
import james.dsp.preference.SummariedListPreference;
import james.dsp.service.HeadsetService;

/**
 * This class implements a general PreferencesActivity that we can use to
 * adjust DSP settings. It adds a menu to clear the preferences on this page,
 * and a listener that ensures that our {@link HeadsetService} is running if
 * required.
 *
 * Co-founder alankila
 */
public final class DSPScreen extends PreferenceFragment
{
    private final OnSharedPreferenceChangeListener listener = new OnSharedPreferenceChangeListener()
    {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
        {
            /* If the listpref is updated, copy the changed setting to the eq. */
            if ("dsp.tone.eq".equals(key))
            {
                String newValue = sharedPreferences.getString(key, null);
                if (!"custom".equals(newValue))
                {
                    Editor e = sharedPreferences.edit();
                    e.putString("dsp.tone.eq.custom", newValue);
                    e.apply();
                    /* Now tell the equalizer that it must display something else. */
                    EqualizerPreference eq = (EqualizerPreference)
                                             getPreferenceScreen().findPreference("dsp.tone.eq.custom");
                    eq.refreshFromPreference();
                }
            }
            /* If the equalizer surface is updated, select matching pref entry or "custom". */
            if ("dsp.tone.eq.custom".equals(key))
            {
                String newValue = sharedPreferences.getString(key, null);
                String desiredValue = "custom";
                SummariedListPreference preset = (SummariedListPreference)
                                                 getPreferenceScreen().findPreference("dsp.tone.eq");
                for (CharSequence entry : preset.getEntryValues())
                {
                    if (entry.equals(newValue))
                    {
                        desiredValue = newValue;
                        break;
                    }
                }
                /* Tell listpreference that it must display something else. */
                if (!desiredValue.contentEquals(preset.getEntry()))
                {
                    Editor e = sharedPreferences.edit();
                    e.putString("dsp.tone.eq", desiredValue);
                    e.apply();
                    preset.refreshFromPreference();
                }
            }
            if (!"dsp.convolver.resampler".equals(key))
            	getActivity().sendBroadcast(new Intent(DSPManager.ACTION_UPDATE_PREFERENCES));
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        final String config = getArguments().getString("config");
        getPreferenceManager().setSharedPreferencesName(
            DSPManager.SHARED_PREFERENCES_BASENAME + "." + config);
        getPreferenceManager().setSharedPreferencesMode(Context.MODE_MULTI_PROCESS);
        try
        {
            int xmlId = R.xml.class.getField(config + "_preferences").getInt(null);
            addPreferencesFromResource(xmlId);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        getPreferenceManager().getSharedPreferences()
        .registerOnSharedPreferenceChangeListener(listener);

        Preference preference = getPreferenceManager().findPreference("advanced.options");
        if (preference != null) {
            preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Bundle bundle = new Bundle();
                    bundle.putString("config", config);

                    AdvancedOptions advancedOptions = new AdvancedOptions();
                    advancedOptions.setArguments(bundle);

                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.dsp_container, advancedOptions);
                    ft.addToBackStack(null);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();
                    return true;
                }
            });
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        getPreferenceManager().getSharedPreferences()
        .unregisterOnSharedPreferenceChangeListener(listener);
    }
}