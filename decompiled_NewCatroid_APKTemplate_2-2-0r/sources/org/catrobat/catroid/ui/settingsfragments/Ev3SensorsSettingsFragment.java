package org.catrobat.catroid.ui.settingsfragments;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.devices.mindstorms.ev3.sensors.EV3Sensor;
import org.catrobat.catroid.ui.recyclerview.dialog.AppStoreDialogFragment;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/settingsfragments/Ev3SensorsSettingsFragment.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Ev3SensorsSettingsFragment extends PreferenceFragment {
    public static final String TAG = "Ev3SensorsSettingsFragment";

    @Override // android.app.Fragment
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getPreferenceScreen().getTitle());
    }

    @Override // android.preference.PreferenceFragment, android.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SettingsFragment.setToChosenLanguage(getActivity());
        addPreferencesFromResource(R.xml.ev3_preferences);
        CheckBoxPreference checkBoxPreference = (CheckBoxPreference) findPreference(SettingsFragment.SETTINGS_MINDSTORMS_EV3_BRICKS_CHECKBOX_PREFERENCE);
        getPreferenceScreen().removePreference(findPreference(SettingsFragment.SETTINGS_MINDSTORMS_EV3_BRICKS_PREFERENCE));
        final PreferenceCategory preferenceCategory = (PreferenceCategory) findPreference(SettingsFragment.EV3_SETTINGS_CATEGORY);
        preferenceCategory.setEnabled(checkBoxPreference.isChecked());
        checkBoxPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() { // from class: org.catrobat.catroid.ui.settingsfragments.Ev3SensorsSettingsFragment$$ExternalSyntheticLambda0
            @Override // android.preference.Preference.OnPreferenceChangeListener
            public final boolean onPreferenceChange(Preference preference, Object obj) {
                return Ev3SensorsSettingsFragment.lambda$onActivityCreated$1(preferenceCategory, preference, obj);
            }
        });
        for (String str : SettingsFragment.EV3_SENSORS) {
            ListPreference listPreference = (ListPreference) findPreference(str);
            listPreference.setEntries(R.array.ev3_sensor_chooser);
            listPreference.setEntryValues(EV3Sensor.Sensor.getSensorCodes());
        }
    }

    private /* synthetic */ boolean lambda$onActivityCreated$0(Preference preference) {
        AppStoreDialogFragment.newInstance(AppStoreDialogFragment.Companion.Extension.LEGO_NXT_EV3).show(((FragmentActivity) getActivity()).getSupportFragmentManager(), AppStoreDialogFragment.INSTANCE.getTAG());
        return true;
    }

    static /* synthetic */ boolean lambda$onActivityCreated$1(PreferenceCategory preferenceCategory, Preference preference, Object obj) {
        preferenceCategory.setEnabled(((Boolean) obj).booleanValue());
        return true;
    }
}
