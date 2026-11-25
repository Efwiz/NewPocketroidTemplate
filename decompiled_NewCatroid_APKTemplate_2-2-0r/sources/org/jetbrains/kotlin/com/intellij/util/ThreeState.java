package org.jetbrains.kotlin.com.intellij.util;

/* JADX INFO: loaded from: classes3.dex */
public enum ThreeState {
    YES,
    NO,
    UNSURE;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 5) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 2 || i == 5) ? 3 : 2];
        if (i == 2 || i == 5) {
            objArr[0] = "states";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/ThreeState";
        }
        switch (i) {
            case 1:
            case 6:
            case 7:
            case 8:
                objArr[1] = "merge";
                break;
            case 2:
            case 5:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/ThreeState";
                break;
            case 3:
            case 4:
                objArr[1] = "mostPositive";
                break;
            default:
                objArr[1] = "fromBoolean";
                break;
        }
        if (i == 2) {
            objArr[2] = "mostPositive";
        } else if (i == 5) {
            objArr[2] = "merge";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 5) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    public static ThreeState fromBoolean(boolean z) {
        ThreeState threeState = z ? YES : NO;
        if (threeState == null) {
            $$$reportNull$$$0(0);
        }
        return threeState;
    }
}
