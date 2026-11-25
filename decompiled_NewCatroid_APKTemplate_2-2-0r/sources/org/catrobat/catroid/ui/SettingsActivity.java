package org.catrobat.catroid.ui;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.settingsfragments.AccessibilityProfilesFragment;
import org.catrobat.catroid.ui.settingsfragments.AccessibilitySettingsFragment;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/SettingsActivity.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SettingsActivity extends BaseActivity {
    @Override // org.catrobat.catroid.ui.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference);
        getFragmentManager().beginTransaction().replace(R.id.content_frame, new SettingsFragment()).commit();
        if (getIntent().getExtras() != null && getIntent().getBooleanExtra(AccessibilityProfilesFragment.SETTINGS_FRAGMENT_INTENT_KEY, false)) {
            getFragmentManager().beginTransaction().replace(R.id.content_frame, new AccessibilitySettingsFragment()).addToBackStack(AccessibilitySettingsFragment.TAG).commit();
        }
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.preference_title);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
