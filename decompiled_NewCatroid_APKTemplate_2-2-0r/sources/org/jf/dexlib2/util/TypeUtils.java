package org.jf.dexlib2.util;

import org.jf.dexlib2.AccessFlags;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.reference.TypeReference;

/* JADX INFO: loaded from: classes6.dex */
public final class TypeUtils {
    public static boolean isWideType(String str) {
        char cCharAt = str.charAt(0);
        return cCharAt == 'J' || cCharAt == 'D';
    }

    public static boolean isWideType(TypeReference typeReference) {
        return isWideType(typeReference.getType());
    }

    public static boolean isPrimitiveType(String str) {
        return str.length() == 1;
    }

    public static String getPackage(String str) {
        int iLastIndexOf = str.lastIndexOf(47);
        if (iLastIndexOf < 0) {
            return "";
        }
        return str.substring(1, iLastIndexOf);
    }

    public static boolean canAccessClass(String str, ClassDef classDef) {
        if (AccessFlags.PUBLIC.isSet(classDef.getAccessFlags())) {
            return true;
        }
        return getPackage(classDef.getType()).equals(getPackage(str));
    }

    private TypeUtils() {
    }
}
