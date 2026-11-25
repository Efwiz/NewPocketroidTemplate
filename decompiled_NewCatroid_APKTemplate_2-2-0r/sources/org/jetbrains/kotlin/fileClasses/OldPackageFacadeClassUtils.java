package org.jetbrains.kotlin.fileClasses;

import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: loaded from: classes4.dex */
public final class OldPackageFacadeClassUtils {
    /* JADX WARN: Code duplicated, block: B:18:0x0028  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 3) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/fileClasses/OldPackageFacadeClassUtils";
        } else if (i == 2) {
            objArr[0] = "s";
        } else if (i != 3) {
            objArr[0] = "packageFQN";
        } else {
            objArr[0] = "org/jetbrains/kotlin/fileClasses/OldPackageFacadeClassUtils";
        }
        if (i == 1) {
            objArr[1] = "getPackageClassName";
        } else if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/fileClasses/OldPackageFacadeClassUtils";
        } else {
            objArr[1] = "capitalizeNonEmptyString";
        }
        if (i != 1) {
            if (i == 2) {
                objArr[2] = "capitalizeNonEmptyString";
            } else if (i != 3) {
                objArr[2] = "getPackageClassName";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private OldPackageFacadeClassUtils() {
    }

    public static String getPackageClassName(FqName fqName) {
        if (fqName == null) {
            $$$reportNull$$$0(0);
        }
        if (fqName.isRoot()) {
            return "_DefaultPackage";
        }
        String str = capitalizeNonEmptyString(fqName.shortName().asString()) + "Package";
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        return str;
    }

    private static String capitalizeNonEmptyString(String str) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        if (!Character.isUpperCase(str.charAt(0))) {
            str = Character.toUpperCase(str.charAt(0)) + str.substring(1);
        }
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        return str;
    }
}
