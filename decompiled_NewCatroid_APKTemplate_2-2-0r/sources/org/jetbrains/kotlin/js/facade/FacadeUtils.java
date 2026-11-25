package org.jetbrains.kotlin.js.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/* JADX INFO: loaded from: classes5.dex */
public final class FacadeUtils {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "argumentString";
        } else {
            objArr[0] = "org/jetbrains/kotlin/js/facade/FacadeUtils";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/js/facade/FacadeUtils";
        } else {
            objArr[1] = "parseString";
        }
        if (i != 1) {
            objArr[2] = "parseString";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    private FacadeUtils() {
    }

    public static List<String> parseString(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        ArrayList arrayList = new ArrayList();
        StringTokenizer stringTokenizer = new StringTokenizer(str);
        while (stringTokenizer.hasMoreTokens()) {
            arrayList.add(stringTokenizer.nextToken());
        }
        return arrayList;
    }
}
