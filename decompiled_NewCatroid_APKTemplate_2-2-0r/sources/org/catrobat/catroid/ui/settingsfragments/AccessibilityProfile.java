package org.catrobat.catroid.ui.settingsfragments;

import android.content.SharedPreferences;
import android.content.res.Resources;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/settingsfragments/AccessibilityProfile.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class AccessibilityProfile {
    private static final String CUSTOM_ACCESSIBILITY_PROFILE = "custom_accessibility_profile";
    static final String DYSLEXIC = "dyslexic";
    private static final String FONT_STYLE = "accessibility_font_style";
    static final String SANS_SERIF = "sans_serif";
    static final String SERIF = "serif";
    private final Set<String> setPreferences;
    static final String LARGE_TEXT = "accessibility_large_text";
    static final String HIGH_CONTRAST = "accessibility_high_contrast";
    static final String ICONS = "accessibility_category_icons";
    static final String LARGE_ICONS = "accessibility_category_icons_big";
    static final String ICON_HIGH_CONTRAST = "accessibility_category_icons_high_contrast";
    static final String ELEMENT_SPACING = "accessibility_element_spacing";
    public static final String BEGINNER_BRICKS = "accessibility_beginner_bricks";
    public static final String DRAGNDROP_DELAY = "accessibility_dragndrop_delay";
    private static final Set<String> BOOLEAN_PREFERENCES = new HashSet(Arrays.asList(LARGE_TEXT, HIGH_CONTRAST, ICONS, LARGE_ICONS, ICON_HIGH_CONTRAST, ELEMENT_SPACING, BEGINNER_BRICKS, DRAGNDROP_DELAY));

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/settingsfragments/AccessibilityProfile$AccessibilityFlags.class
      classes14.dex
     */
    @Retention(RetentionPolicy.SOURCE)
    @interface AccessibilityFlags {
    }

    private AccessibilityProfile(Collection<String> preferences) {
        HashSet hashSet = new HashSet();
        this.setPreferences = hashSet;
        if (preferences != null) {
            hashSet.addAll(preferences);
        }
    }

    AccessibilityProfile(String... preferences) {
        this(Arrays.asList(preferences));
    }

    public static AccessibilityProfile fromCurrentPreferences(SharedPreferences sharedPreferences) {
        HashSet hashSet = new HashSet();
        for (String str : BOOLEAN_PREFERENCES) {
            if (sharedPreferences.getBoolean(str, false)) {
                hashSet.add(str);
            }
        }
        hashSet.add(sharedPreferences.getString(FONT_STYLE, SANS_SERIF));
        return new AccessibilityProfile(hashSet);
    }

    public static AccessibilityProfile fromCustomProfile(SharedPreferences sharedPreferences) {
        return new AccessibilityProfile(sharedPreferences.getStringSet(CUSTOM_ACCESSIBILITY_PROFILE, null));
    }

    void saveAsCustomProfile(SharedPreferences sharedPreferences) {
        sharedPreferences.edit().putStringSet(CUSTOM_ACCESSIBILITY_PROFILE, this.setPreferences).apply();
    }

    private void clearCurrent(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        Iterator<String> it = BOOLEAN_PREFERENCES.iterator();
        while (it.hasNext()) {
            editorEdit.putBoolean(it.next(), false);
        }
        editorEdit.putString(FONT_STYLE, SANS_SERIF);
        editorEdit.apply();
    }

    public void setAsCurrent(SharedPreferences sharedPreferences) {
        clearCurrent(sharedPreferences);
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        for (String str : this.setPreferences) {
            if (str.equals(SERIF) || str.equals(DYSLEXIC) || str.equals(SANS_SERIF)) {
                editorEdit.putString(FONT_STYLE, str);
            } else {
                editorEdit.putBoolean(str, true);
            }
        }
        editorEdit.apply();
    }

    public void applyAccessibilityStyles(Resources.Theme theme) {
        if (this.setPreferences.contains(LARGE_TEXT)) {
            theme.applyStyle(R.style.FontSizeLarge, true);
        }
        if (this.setPreferences.contains(HIGH_CONTRAST)) {
            theme.applyStyle(R.style.ContrastHigh, true);
        }
        if (this.setPreferences.contains(ELEMENT_SPACING)) {
            theme.applyStyle(R.style.SpacingLarge, true);
        }
        if (this.setPreferences.contains(ICON_HIGH_CONTRAST)) {
            theme.applyStyle(R.style.CategoryIconContrastHigh, true);
        }
        if (this.setPreferences.contains(ICONS)) {
            theme.applyStyle(R.style.CategoryIconVisible, true);
        }
        if (this.setPreferences.contains(LARGE_ICONS)) {
            theme.applyStyle(R.style.CategoryIconSizeLarge, true);
        }
        if (this.setPreferences.contains(SANS_SERIF)) {
            theme.applyStyle(R.style.FontSansSerif, true);
        }
        if (this.setPreferences.contains(SERIF)) {
            theme.applyStyle(R.style.FontSerif, true);
        }
        if (this.setPreferences.contains(DYSLEXIC)) {
            theme.applyStyle(R.style.FontDyslexic, true);
        }
    }
}
