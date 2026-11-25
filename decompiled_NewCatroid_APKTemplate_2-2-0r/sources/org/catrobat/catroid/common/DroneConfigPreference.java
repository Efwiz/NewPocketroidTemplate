package org.catrobat.catroid.common;

import org.catrobat.catroid.utils.EnumUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/common/DroneConfigPreference.class
  classes12.dex
 */
/* JADX INFO: loaded from: classes12.dex */
public abstract class DroneConfigPreference {
    public static final String TAG = "DroneConfigPreference";

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/common/DroneConfigPreference$Preferences.class
      classes12.dex
     */
    public enum Preferences {
        FIRST,
        SECOND,
        THIRD,
        FOURTH;

        public static String[] getPreferenceCodes() {
            String[] strArr = new String[values().length];
            for (int i = 0; i < values().length; i++) {
                strArr[i] = values()[i].name();
            }
            return strArr;
        }

        public static Preferences getPreferenceFromPreferenceCode(String preferenceCode) {
            Preferences preferences = (Preferences) EnumUtils.getEnum(Preferences.class, preferenceCode);
            return preferences != null ? preferences : FIRST;
        }
    }
}
