package org.catrobat.catroid.ui.settingsfragments;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import androidx.appcompat.app.AppCompatActivity;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.DroneConfigPreference;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/settingsfragments/ParrotARDroneSettingsFragment.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ParrotARDroneSettingsFragment extends PreferenceFragment {
    public static final String TAG = "ParrotARDroneSettingsFragment";

    @Override // android.app.Fragment
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getPreferenceScreen().getTitle());
    }

    @Override // android.preference.PreferenceFragment, android.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SettingsFragment.setToChosenLanguage(getActivity());
        addPreferencesFromResource(R.xml.drone_preferences);
        CheckBoxPreference checkBoxPreference = (CheckBoxPreference) findPreference(SettingsFragment.SETTINGS_SHOW_PARROT_AR_DRONE_BRICKS);
        final PreferenceCategory preferenceCategory = (PreferenceCategory) findPreference(SettingsFragment.DRONE_SETTINGS_CATEGORY);
        preferenceCategory.setEnabled(checkBoxPreference.isChecked());
        final String[] strArr = {SettingsFragment.DRONE_CONFIGS, SettingsFragment.DRONE_ALTITUDE_LIMIT, SettingsFragment.DRONE_VERTICAL_SPEED, SettingsFragment.DRONE_ROTATION_SPEED, SettingsFragment.DRONE_TILT_ANGLE};
        checkBoxPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() { // from class: org.catrobat.catroid.ui.settingsfragments.ParrotARDroneSettingsFragment.1
            @Override // android.preference.Preference.OnPreferenceChangeListener
            public boolean onPreferenceChange(Preference preference, Object isChecked) {
                preferenceCategory.setEnabled(((Boolean) isChecked).booleanValue());
                return true;
            }
        });
        for (int i = 0; i < 5; i++) {
            String str = strArr[i];
            final ListPreference listPreference = (ListPreference) findPreference(str);
            str.hashCode();
            switch (str) {
                case "setting_drone_rotation_speed":
                    listPreference.setEntries(R.array.drone_max_rotation_speed_items);
                    break;
                case "setting_drone_vertical_speed":
                    listPreference.setEntries(R.array.drone_max_vertical_speed_items);
                    break;
                case "setting_drone_altitude_limit":
                    listPreference.setEntries(R.array.drone_altitude_spinner_items);
                    break;
                case "setting_drone_basic_configs":
                    listPreference.setEntries(R.array.drone_setting_default_config);
                    listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() { // from class: org.catrobat.catroid.ui.settingsfragments.ParrotARDroneSettingsFragment.2
                        @Override // android.preference.Preference.OnPreferenceChangeListener
                        public boolean onPreferenceChange(Preference preference, Object newValue) {
                            int iFindIndexOfValue = listPreference.findIndexOfValue(newValue.toString());
                            for (String str2 : strArr) {
                                ListPreference listPreference2 = (ListPreference) ParrotARDroneSettingsFragment.this.findPreference(str2);
                                str2.hashCode();
                                switch (str2) {
                                    case "setting_drone_rotation_speed":
                                        if (iFindIndexOfValue == 0 || iFindIndexOfValue == 1) {
                                            listPreference2.setValue("SECOND");
                                        }
                                        if (iFindIndexOfValue != 2 && iFindIndexOfValue != 3) {
                                            break;
                                        } else {
                                            listPreference2.setValue("THIRD");
                                            break;
                                        }
                                        break;
                                    case "setting_drone_vertical_speed":
                                        if (iFindIndexOfValue == 0 || iFindIndexOfValue == 1) {
                                            listPreference2.setValue("SECOND");
                                        }
                                        if (iFindIndexOfValue != 2 && iFindIndexOfValue != 3) {
                                            break;
                                        } else {
                                            listPreference2.setValue("THIRD");
                                            break;
                                        }
                                        break;
                                    case "setting_drone_altitude_limit":
                                        listPreference2.setValue("FIRST");
                                        break;
                                    case "setting_drone_tilt_angle":
                                        if (iFindIndexOfValue == 0 || iFindIndexOfValue == 1) {
                                            listPreference2.setValue("SECOND");
                                        }
                                        if (iFindIndexOfValue != 2 && iFindIndexOfValue != 3) {
                                            break;
                                        } else {
                                            listPreference2.setValue("THIRD");
                                            break;
                                        }
                                        break;
                                }
                            }
                            return true;
                        }
                    });
                    break;
                case "setting_drone_tilt_angle":
                    listPreference.setEntries(R.array.drone_max_tilt_angle_items);
                    break;
            }
            listPreference.setEntryValues(DroneConfigPreference.Preferences.getPreferenceCodes());
        }
    }
}
