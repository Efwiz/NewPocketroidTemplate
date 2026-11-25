package org.jetbrains.kotlin.com.intellij.openapi.projectRoots;

import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.util.lang.JavaVersion;

/* JADX INFO: loaded from: classes3.dex */
public enum JavaSdkVersion {
    JDK_1_0(LanguageLevel.JDK_1_3),
    JDK_1_1(LanguageLevel.JDK_1_3),
    JDK_1_2(LanguageLevel.JDK_1_3),
    JDK_1_3(LanguageLevel.JDK_1_3),
    JDK_1_4(LanguageLevel.JDK_1_4),
    JDK_1_5(LanguageLevel.JDK_1_5),
    JDK_1_6(LanguageLevel.JDK_1_6),
    JDK_1_7(LanguageLevel.JDK_1_7),
    JDK_1_8(LanguageLevel.JDK_1_8),
    JDK_1_9(LanguageLevel.JDK_1_9),
    JDK_10(LanguageLevel.JDK_10),
    JDK_11(LanguageLevel.JDK_11),
    JDK_12(LanguageLevel.JDK_12),
    JDK_13(LanguageLevel.JDK_13),
    JDK_14(LanguageLevel.JDK_14),
    JDK_15(LanguageLevel.JDK_15),
    JDK_16(LanguageLevel.JDK_16),
    JDK_17(LanguageLevel.JDK_17),
    JDK_18(LanguageLevel.JDK_X);

    private final LanguageLevel myMaxLanguageLevel;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 5 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 5 || i == 6) ? 2 : 3];
        switch (i) {
            case 1:
            case 2:
            case 5:
            case 6:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/projectRoots/JavaSdkVersion";
                break;
            case 3:
            case 8:
                objArr[0] = "version";
                break;
            case 4:
                objArr[0] = "languageLevel";
                break;
            case 7:
                objArr[0] = "versionString";
                break;
            default:
                objArr[0] = "maxLanguageLevel";
                break;
        }
        if (i == 1) {
            objArr[1] = "getMaxLanguageLevel";
        } else if (i == 2) {
            objArr[1] = "getDescription";
        } else if (i == 5 || i == 6) {
            objArr[1] = "fromLanguageLevel";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/projectRoots/JavaSdkVersion";
        }
        switch (i) {
            case 1:
            case 2:
            case 5:
            case 6:
                break;
            case 3:
                objArr[2] = "isAtLeast";
                break;
            case 4:
                objArr[2] = "fromLanguageLevel";
                break;
            case 7:
                objArr[2] = "fromVersionString";
                break;
            case 8:
                objArr[2] = "fromJavaVersion";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 5 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    JavaSdkVersion(LanguageLevel languageLevel) {
        if (languageLevel == null) {
            $$$reportNull$$$0(0);
        }
        this.myMaxLanguageLevel = languageLevel;
    }

    public LanguageLevel getMaxLanguageLevel() {
        LanguageLevel languageLevel = this.myMaxLanguageLevel;
        if (languageLevel == null) {
            $$$reportNull$$$0(1);
        }
        return languageLevel;
    }

    public static JavaSdkVersion fromVersionString(String str) {
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        JavaVersion javaVersionTryParse = JavaVersion.tryParse(str);
        if (javaVersionTryParse != null) {
            return fromJavaVersion(javaVersionTryParse);
        }
        return null;
    }

    public static JavaSdkVersion fromJavaVersion(JavaVersion javaVersion) {
        if (javaVersion == null) {
            $$$reportNull$$$0(8);
        }
        JavaSdkVersion[] javaSdkVersionArrValues = values();
        if (javaVersion.feature < javaSdkVersionArrValues.length) {
            return javaSdkVersionArrValues[javaVersion.feature];
        }
        return null;
    }
}
