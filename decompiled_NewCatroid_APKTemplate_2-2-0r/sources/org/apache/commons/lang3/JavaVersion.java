package org.apache.commons.lang3;

import org.apache.commons.lang3.math.NumberUtils;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.konan.target.Android;

/* JADX INFO: loaded from: classes13.dex */
public enum JavaVersion {
    JAVA_0_9(1.5f, "0.9"),
    JAVA_1_1(1.1f, CompilerOptions.VERSION_1_1),
    JAVA_1_2(1.2f, CompilerOptions.VERSION_1_2),
    JAVA_1_3(1.3f, CompilerOptions.VERSION_1_3),
    JAVA_1_4(1.4f, CompilerOptions.VERSION_1_4),
    JAVA_1_5(1.5f, CompilerOptions.VERSION_1_5),
    JAVA_1_6(1.6f, CompilerOptions.VERSION_1_6),
    JAVA_1_7(1.7f, CompilerOptions.VERSION_1_7),
    JAVA_1_8(1.8f, CompilerOptions.VERSION_1_8),
    JAVA_1_9(9.0f, "9"),
    JAVA_9(9.0f, "9"),
    JAVA_10(10.0f, "10"),
    JAVA_11(11.0f, "11"),
    JAVA_12(12.0f, "12"),
    JAVA_13(13.0f, "13"),
    JAVA_14(14.0f, "14"),
    JAVA_15(15.0f, "15"),
    JAVA_16(16.0f, "16"),
    JAVA_17(17.0f, "17"),
    JAVA_18(18.0f, "18"),
    JAVA_19(19.0f, "19"),
    JAVA_20(20.0f, "20"),
    JAVA_21(21.0f, Android.API),
    JAVA_RECENT(maxVersion(), Float.toString(maxVersion()));


    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f462name;
    private final float value;

    static JavaVersion get(String str) {
        if (str == null) {
            return null;
        }
        str.hashCode();
        switch (str) {
            case "9":
                return JAVA_9;
            case "10":
                return JAVA_10;
            case "11":
                return JAVA_11;
            case "12":
                return JAVA_12;
            case "13":
                return JAVA_13;
            case "14":
                return JAVA_14;
            case "15":
                return JAVA_15;
            case "16":
                return JAVA_16;
            case "17":
                return JAVA_17;
            case "18":
                return JAVA_18;
            case "19":
                return JAVA_19;
            case "20":
                return JAVA_20;
            case "21":
                return JAVA_21;
            case "0.9":
                return JAVA_0_9;
            case "1.1":
                return JAVA_1_1;
            case "1.2":
                return JAVA_1_2;
            case "1.3":
                return JAVA_1_3;
            case "1.4":
                return JAVA_1_4;
            case "1.5":
                return JAVA_1_5;
            case "1.6":
                return JAVA_1_6;
            case "1.7":
                return JAVA_1_7;
            case "1.8":
                return JAVA_1_8;
            default:
                float floatVersion = toFloatVersion(str);
                if (((double) floatVersion) - 1.0d < 1.0d) {
                    int iMax = Math.max(str.indexOf(46), str.indexOf(44));
                    if (Float.parseFloat(str.substring(iMax + 1, Math.max(str.length(), str.indexOf(44, iMax)))) > 0.9f) {
                        return JAVA_RECENT;
                    }
                } else if (floatVersion > 10.0f) {
                    return JAVA_RECENT;
                }
                return null;
        }
    }

    static JavaVersion getJavaVersion(String str) {
        return get(str);
    }

    private static float maxVersion() {
        float floatVersion = toFloatVersion(System.getProperty(SystemProperties.JAVA_SPECIFICATION_VERSION, "99.0"));
        if (floatVersion > 0.0f) {
            return floatVersion;
        }
        return 99.0f;
    }

    private static float toFloatVersion(String str) {
        if (!str.contains(".")) {
            return NumberUtils.toFloat(str, -1.0f);
        }
        String[] strArrSplit = str.split("\\.");
        if (strArrSplit.length >= 2) {
            return NumberUtils.toFloat(strArrSplit[0] + '.' + strArrSplit[1], -1.0f);
        }
        return -1.0f;
    }

    JavaVersion(float f, String str) {
        this.value = f;
        this.f462name = str;
    }

    public boolean atLeast(JavaVersion javaVersion) {
        return this.value >= javaVersion.value;
    }

    public boolean atMost(JavaVersion javaVersion) {
        return this.value <= javaVersion.value;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.f462name;
    }
}
