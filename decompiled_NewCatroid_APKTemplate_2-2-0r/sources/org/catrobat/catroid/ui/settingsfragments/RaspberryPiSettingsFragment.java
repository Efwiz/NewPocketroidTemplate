package org.catrobat.catroid.ui.settingsfragments;

import android.content.Context;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/settingsfragments/RaspberryPiSettingsFragment.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class RaspberryPiSettingsFragment extends PreferenceFragment {
    public static final String TAG = "RaspberryPiSettingsFragment";

    @Override // android.app.Fragment
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getPreferenceScreen().getTitle());
    }

    @Override // android.preference.PreferenceFragment, android.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SettingsFragment.setToChosenLanguage(getActivity());
        addPreferencesFromResource(R.xml.raspberry_preferences);
        CheckBoxPreference checkBoxPreference = (CheckBoxPreference) findPreference(SettingsFragment.SETTINGS_SHOW_RASPI_BRICKS);
        final PreferenceCategory preferenceCategory = (PreferenceCategory) findPreference(SettingsFragment.RASPI_CONNECTION_SETTINGS_CATEGORY);
        preferenceCategory.setEnabled(checkBoxPreference.isChecked());
        checkBoxPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() { // from class: org.catrobat.catroid.ui.settingsfragments.RaspberryPiSettingsFragment.1
            @Override // android.preference.Preference.OnPreferenceChangeListener
            public boolean onPreferenceChange(Preference preference, Object isChecked) {
                preferenceCategory.setEnabled(((Boolean) isChecked).booleanValue());
                return true;
            }
        });
        final EditTextPreference editTextPreference = (EditTextPreference) findPreference(SettingsFragment.RASPI_HOST);
        editTextPreference.setSummary(editTextPreference.getText());
        editTextPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() { // from class: org.catrobat.catroid.ui.settingsfragments.RaspberryPiSettingsFragment.2
            @Override // android.preference.Preference.OnPreferenceChangeListener
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                editTextPreference.setSummary(newValue.toString());
                return true;
            }
        });
        final EditTextPreference editTextPreference2 = (EditTextPreference) findPreference(SettingsFragment.RASPI_PORT);
        editTextPreference2.setSummary(editTextPreference2.getText());
        editTextPreference2.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() { // from class: org.catrobat.catroid.ui.settingsfragments.RaspberryPiSettingsFragment.3
            @Override // android.preference.Preference.OnPreferenceChangeListener
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                editTextPreference2.setSummary(newValue.toString());
                return true;
            }
        });
    }

    public static boolean isRaspiSharedPreferenceEnabled(Context context) {
        PreferenceManager.setDefaultValues(context, R.xml.raspberry_preferences, true);
        Boolean boolValueOf = Boolean.valueOf(PreferenceManager.getDefaultSharedPreferences(context).getBoolean(SettingsFragment.SETTINGS_SHOW_RASPI_BRICKS, false));
        PreferenceManager.setDefaultValues(context, R.xml.preferences, true);
        return boolValueOf.booleanValue();
    }
}
