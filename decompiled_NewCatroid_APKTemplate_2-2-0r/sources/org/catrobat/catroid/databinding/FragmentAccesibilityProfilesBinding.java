package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/FragmentAccesibilityProfilesBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class FragmentAccesibilityProfilesBinding implements ViewBinding {
    public final AccessibilityProfileBinding argus;
    public final AccessibilityProfileBinding customProfile;
    public final AccessibilityProfileBinding defaultProfile;
    public final AccessibilityProfileBinding fenrir;
    public final AccessibilityProfileBinding odin;
    private final ScrollView rootView;
    public final AccessibilityProfileBinding tiro;

    private FragmentAccesibilityProfilesBinding(ScrollView rootView, AccessibilityProfileBinding argus, AccessibilityProfileBinding customProfile, AccessibilityProfileBinding defaultProfile, AccessibilityProfileBinding fenrir, AccessibilityProfileBinding odin, AccessibilityProfileBinding tiro) {
        this.rootView = rootView;
        this.argus = argus;
        this.customProfile = customProfile;
        this.defaultProfile = defaultProfile;
        this.fenrir = fenrir;
        this.odin = odin;
        this.tiro = tiro;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ScrollView getRoot() {
        return this.rootView;
    }

    public static FragmentAccesibilityProfilesBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentAccesibilityProfilesBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.fragment_accesibility_profiles, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static FragmentAccesibilityProfilesBinding bind(View rootView) {
        int i = R.id.argus;
        View viewFindChildViewById = ViewBindings.findChildViewById(rootView, R.id.argus);
        if (viewFindChildViewById != null) {
            AccessibilityProfileBinding accessibilityProfileBindingBind = AccessibilityProfileBinding.bind(viewFindChildViewById);
            i = R.id.custom_profile;
            View viewFindChildViewById2 = ViewBindings.findChildViewById(rootView, R.id.custom_profile);
            if (viewFindChildViewById2 != null) {
                AccessibilityProfileBinding accessibilityProfileBindingBind2 = AccessibilityProfileBinding.bind(viewFindChildViewById2);
                i = R.id.default_profile;
                View viewFindChildViewById3 = ViewBindings.findChildViewById(rootView, R.id.default_profile);
                if (viewFindChildViewById3 != null) {
                    AccessibilityProfileBinding accessibilityProfileBindingBind3 = AccessibilityProfileBinding.bind(viewFindChildViewById3);
                    i = R.id.fenrir;
                    View viewFindChildViewById4 = ViewBindings.findChildViewById(rootView, R.id.fenrir);
                    if (viewFindChildViewById4 != null) {
                        AccessibilityProfileBinding accessibilityProfileBindingBind4 = AccessibilityProfileBinding.bind(viewFindChildViewById4);
                        i = R.id.odin;
                        View viewFindChildViewById5 = ViewBindings.findChildViewById(rootView, R.id.odin);
                        if (viewFindChildViewById5 != null) {
                            AccessibilityProfileBinding accessibilityProfileBindingBind5 = AccessibilityProfileBinding.bind(viewFindChildViewById5);
                            i = R.id.tiro;
                            View viewFindChildViewById6 = ViewBindings.findChildViewById(rootView, R.id.tiro);
                            if (viewFindChildViewById6 != null) {
                                return new FragmentAccesibilityProfilesBinding((ScrollView) rootView, accessibilityProfileBindingBind, accessibilityProfileBindingBind2, accessibilityProfileBindingBind3, accessibilityProfileBindingBind4, accessibilityProfileBindingBind5, AccessibilityProfileBinding.bind(viewFindChildViewById6));
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
