package org.catrobat.catroid.ui.settingsfragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.ui.MainMenuActivity;
import org.catrobat.catroid.ui.SettingsActivity;
import org.catrobat.catroid.utils.ToastUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/settingsfragments/AccessibilityProfilesFragment.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class AccessibilityProfilesFragment extends Fragment implements View.OnClickListener {
    public static final String SETTINGS_FRAGMENT_INTENT_KEY = "rollBackToAccessibilityFragment";
    public static final String TAG = "AccessibilityProfilesFragment";
    private View parent;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/settingsfragments/AccessibilityProfilesFragment$AccessibilityProfileViewHolder.class
      classes14.dex
     */
    private class AccessibilityProfileViewHolder {
        ImageView imageView;
        RadioButton radioButton;
        TextView subtitle;
        TextView title;
        View view;

        AccessibilityProfileViewHolder(View view) {
            this.view = view;
            this.radioButton = (RadioButton) view.findViewById(R.id.radio_button);
            this.imageView = (ImageView) view.findViewById(R.id.image_view);
            this.title = (TextView) view.findViewById(R.id.title_view);
            this.subtitle = (TextView) view.findViewById(R.id.subtitle_view);
        }
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewInflate = inflater.inflate(R.layout.fragment_accesibility_profiles, container, false);
        this.parent = viewInflate;
        return viewInflate;
    }

    @Override // android.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AccessibilityProfileViewHolder accessibilityProfileViewHolder = new AccessibilityProfileViewHolder(this.parent.findViewById(R.id.custom_profile));
        accessibilityProfileViewHolder.view.setOnClickListener(this);
        accessibilityProfileViewHolder.imageView.setImageDrawable(ContextCompat.getDrawable(this.parent.getContext(), R.drawable.nolb_default_myprofile));
        accessibilityProfileViewHolder.title.setText(R.string.preference_access_title_profile_custom);
        accessibilityProfileViewHolder.subtitle.setText(R.string.preference_access_summary_profile_custom);
        AccessibilityProfileViewHolder accessibilityProfileViewHolder2 = new AccessibilityProfileViewHolder(this.parent.findViewById(R.id.default_profile));
        accessibilityProfileViewHolder2.view.setOnClickListener(this);
        accessibilityProfileViewHolder2.imageView.setImageDrawable(ContextCompat.getDrawable(this.parent.getContext(), R.drawable.nolb_default_myprofile));
        accessibilityProfileViewHolder2.title.setText(R.string.preference_access_title_profile_default);
        accessibilityProfileViewHolder2.subtitle.setText(R.string.preference_access_summary_profile_default);
        AccessibilityProfileViewHolder accessibilityProfileViewHolder3 = new AccessibilityProfileViewHolder(this.parent.findViewById(R.id.argus));
        accessibilityProfileViewHolder3.view.setOnClickListener(this);
        accessibilityProfileViewHolder3.imageView.setImageDrawable(ContextCompat.getDrawable(this.parent.getContext(), R.drawable.nolb_argus));
        accessibilityProfileViewHolder3.title.setText(R.string.preference_access_title_profile_argus);
        accessibilityProfileViewHolder3.subtitle.setText(R.string.preference_access_summary_profile_argus);
        AccessibilityProfileViewHolder accessibilityProfileViewHolder4 = new AccessibilityProfileViewHolder(this.parent.findViewById(R.id.fenrir));
        accessibilityProfileViewHolder4.view.setOnClickListener(this);
        accessibilityProfileViewHolder4.imageView.setImageDrawable(ContextCompat.getDrawable(this.parent.getContext(), R.drawable.nolb_fenrir));
        accessibilityProfileViewHolder4.title.setText(R.string.preference_access_title_profile_fenrir);
        accessibilityProfileViewHolder4.subtitle.setText(R.string.preference_access_summary_profile_fenrir);
        AccessibilityProfileViewHolder accessibilityProfileViewHolder5 = new AccessibilityProfileViewHolder(this.parent.findViewById(R.id.odin));
        accessibilityProfileViewHolder5.view.setOnClickListener(this);
        accessibilityProfileViewHolder5.imageView.setImageDrawable(ContextCompat.getDrawable(this.parent.getContext(), R.drawable.nolb_odin));
        accessibilityProfileViewHolder5.title.setText(R.string.preference_access_title_profile_odin);
        accessibilityProfileViewHolder5.subtitle.setText(R.string.preference_access_summary_profile_odin);
        AccessibilityProfileViewHolder accessibilityProfileViewHolder6 = new AccessibilityProfileViewHolder(this.parent.findViewById(R.id.tiro));
        accessibilityProfileViewHolder6.view.setOnClickListener(this);
        accessibilityProfileViewHolder6.imageView.setImageDrawable(ContextCompat.getDrawable(this.parent.getContext(), R.drawable.nolb_tiro));
        accessibilityProfileViewHolder6.title.setText(R.string.preference_access_title_profile_tiro);
        accessibilityProfileViewHolder6.subtitle.setText(R.string.preference_access_summary_profile_tiro);
        new AccessibilityProfileViewHolder(this.parent.findViewById(PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt(SharedPreferenceKeys.ACCESSIBILITY_PROFILE_PREFERENCE_KEY, R.id.default_profile))).radioButton.setChecked(true);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        AccessibilityProfile accessibilityProfile;
        new AccessibilityProfileViewHolder(v).radioButton.setChecked(true);
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        AccessibilityProfile accessibilityProfileFromCurrentPreferences = AccessibilityProfile.fromCurrentPreferences(defaultSharedPreferences);
        if (defaultSharedPreferences.getBoolean("accessibility_profile_is_custom", false)) {
            accessibilityProfileFromCurrentPreferences.saveAsCustomProfile(defaultSharedPreferences);
            defaultSharedPreferences.edit().putBoolean("accessibility_profile_is_custom", false).apply();
        }
        switch (v.getId()) {
            case R.id.argus /* 2131361911 */:
                accessibilityProfile = new AccessibilityProfile("accessibility_high_contrast", "accessibility_category_icons");
                break;
            case R.id.custom_profile /* 2131364214 */:
                accessibilityProfile = AccessibilityProfile.fromCustomProfile(defaultSharedPreferences);
                defaultSharedPreferences.edit().putBoolean("accessibility_profile_is_custom", true).apply();
                break;
            case R.id.fenrir /* 2131364413 */:
                accessibilityProfile = new AccessibilityProfile("accessibility_element_spacing", AccessibilityProfile.DRAGNDROP_DELAY);
                break;
            case R.id.odin /* 2131364804 */:
                accessibilityProfile = new AccessibilityProfile("accessibility_large_text", "accessibility_high_contrast", "accessibility_category_icons", "accessibility_category_icons_big", "accessibility_element_spacing");
                break;
            case R.id.tiro /* 2131365415 */:
                accessibilityProfile = new AccessibilityProfile(AccessibilityProfile.BEGINNER_BRICKS);
                break;
            default:
                accessibilityProfile = new AccessibilityProfile(new String[0]);
                break;
        }
        defaultSharedPreferences.edit().putInt(SharedPreferenceKeys.ACCESSIBILITY_PROFILE_PREFERENCE_KEY, v.getId()).apply();
        accessibilityProfile.setAsCurrent(defaultSharedPreferences);
        startActivity(new Intent(getActivity().getBaseContext(), (Class<?>) MainMenuActivity.class));
        Intent intent = new Intent(getActivity().getBaseContext(), (Class<?>) SettingsActivity.class);
        intent.putExtra(SETTINGS_FRAGMENT_INTENT_KEY, true);
        startActivity(intent);
        ToastUtil.showSuccess(getActivity(), getString(R.string.accessibility_settings_applied));
        getActivity().finishAffinity();
    }

    @Override // android.app.Fragment
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.preference_title_accessibility_profiles);
    }
}
