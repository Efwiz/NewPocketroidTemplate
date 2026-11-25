package org.jetbrains.kotlin.com.intellij.openapi.util;

import org.eclipse.jgit.lib.Constants;

/* JADX INFO: loaded from: classes3.dex */
public final class NullUtils {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = Constants.OBJECTS;
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/NullUtils";
        if (i == 1) {
            objArr[2] = "hasNull";
        } else if (i != 2) {
            objArr[2] = "notNull";
        } else {
            objArr[2] = "hasNotNull";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static boolean hasNull(Object... objArr) {
        if (objArr == null) {
            $$$reportNull$$$0(1);
        }
        for (Object obj : objArr) {
            if (obj == null) {
                return true;
            }
        }
        return false;
    }
}
