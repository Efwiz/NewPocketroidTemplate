package org.catrobat.catroid.ui.settingsfragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import androidx.appcompat.app.AppCompatActivity;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.MainMenuActivity;
import org.catrobat.catroid.ui.SettingsActivity;
import org.catrobat.catroid.utils.ToastUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/settingsfragments/AccessibilitySettingsFragment.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class AccessibilitySettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String ACCESSIBILITY_PROFILES_SCREEN_KEY = "setting_accessibility_profile_screen";
    static final String CUSTOM_PROFILE = "accessibility_profile_is_custom";
    public static final String TAG = "AccessibilitySettingsFragment";
    private boolean preferenceChanged = false;

    @Override // android.preference.PreferenceFragment, android.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.accessibility_preferences);
    }

    @Override // android.preference.PreferenceFragment, android.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SettingsFragment.setToChosenLanguage(getActivity());
    }

    @Override // android.app.Fragment
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getPreferenceScreen().getTitle());
    }

    @Override // android.app.Fragment
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override // android.app.Fragment
    public void onDetach() {
        super.onDetach();
        if (this.preferenceChanged) {
            startActivity(new Intent(getActivity().getBaseContext(), (Class<?>) MainMenuActivity.class));
            startActivity(new Intent(getActivity().getBaseContext(), (Class<?>) SettingsActivity.class));
            ToastUtil.showSuccess(getActivity(), getString(R.string.accessibility_settings_applied));
            getActivity().finishAffinity();
        }
    }

    @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        this.preferenceChanged = true;
        sharedPreferences.edit().putBoolean(CUSTOM_PROFILE, true).apply();
    }

    @Override // android.preference.PreferenceFragment
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        String key = preference.getKey();
        key.hashCode();
        if (key.equals(ACCESSIBILITY_PROFILES_SCREEN_KEY)) {
            getFragmentManager().beginTransaction().replace(R.id.content_frame, new AccessibilityProfilesFragment(), AccessibilityProfilesFragment.TAG).addToBackStack(AccessibilityProfilesFragment.TAG).commit();
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
}
