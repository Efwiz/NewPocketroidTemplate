package org.catrobat.catroid.utils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/EnumUtils.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class EnumUtils {
    private EnumUtils() {
    }

    public static <E extends Enum<E>> boolean isValidEnum(Class<E> clazz, String name2) {
        if (name2 == null) {
            return false;
        }
        try {
            Enum.valueOf(clazz, name2);
            return true;
        } catch (IllegalArgumentException unused) {
            return false;
        }
    }

    public static <E extends Enum<E>> E getEnum(Class<E> cls, String str) {
        if (str == null) {
            return null;
        }
        try {
            return (E) Enum.valueOf(cls, str);
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }
}
