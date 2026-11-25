package org.jetbrains.kotlin.com.intellij.pom.java;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.function.Supplier;
import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.openapi.projectRoots.JavaSdkVersion;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.util.lang.JavaVersion;

/* JADX INFO: loaded from: classes3.dex */
public enum LanguageLevel {
    JDK_1_3(JavaPsiBundle.messagePointer("jdk.1.3.language.level.description", new Object[0]), 3),
    JDK_1_4(JavaPsiBundle.messagePointer("jdk.1.4.language.level.description", new Object[0]), 4),
    JDK_1_5(JavaPsiBundle.messagePointer("jdk.1.5.language.level.description", new Object[0]), 5),
    JDK_1_6(JavaPsiBundle.messagePointer("jdk.1.6.language.level.description", new Object[0]), 6),
    JDK_1_7(JavaPsiBundle.messagePointer("jdk.1.7.language.level.description", new Object[0]), 7),
    JDK_1_8(JavaPsiBundle.messagePointer("jdk.1.8.language.level.description", new Object[0]), 8),
    JDK_1_9(JavaPsiBundle.messagePointer("jdk.1.9.language.level.description", new Object[0]), 9),
    JDK_10(JavaPsiBundle.messagePointer("jdk.10.language.level.description", new Object[0]), 10),
    JDK_11(JavaPsiBundle.messagePointer("jdk.11.language.level.description", new Object[0]), 11),
    JDK_12(JavaPsiBundle.messagePointer("jdk.12.language.level.description", new Object[0]), 12),
    JDK_13(JavaPsiBundle.messagePointer("jdk.13.language.level.description", new Object[0]), 13),
    JDK_14(JavaPsiBundle.messagePointer("jdk.14.language.level.description", new Object[0]), 14),
    JDK_15(JavaPsiBundle.messagePointer("jdk.15.language.level.description", new Object[0]), 15),
    JDK_16(JavaPsiBundle.messagePointer("jdk.16.language.level.description", new Object[0]), 16),
    JDK_16_PREVIEW(JavaPsiBundle.messagePointer("jdk.16.preview.language.level.description", new Object[0]), 16),
    JDK_17(JavaPsiBundle.messagePointer("jdk.17.language.level.description", new Object[0]), 17),
    JDK_17_PREVIEW(JavaPsiBundle.messagePointer("jdk.17.preview.language.level.description", new Object[0]), 17),
    JDK_X(JavaPsiBundle.messagePointer("jdk.X.language.level.description", new Object[0]), 18);

    private final Supplier<String> myPresentableText;
    private final boolean myPreview = name().endsWith("_PREVIEW");
    private final JavaVersion myVersion;
    public static final LanguageLevel HIGHEST = JDK_17;
    public static final Key<LanguageLevel> KEY = Key.create("LANGUAGE_LEVEL");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 3 : 2];
        if (i == 1 || i == 2) {
            objArr[0] = FirebaseAnalytics.Param.LEVEL;
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/pom/java/LanguageLevel";
        }
        if (i == 1 || i == 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/pom/java/LanguageLevel";
        } else if (i != 3) {
            objArr[1] = "getPresentableText";
        } else {
            objArr[1] = "toJavaVersion";
        }
        if (i == 1) {
            objArr[2] = "isAtLeast";
        } else if (i == 2) {
            objArr[2] = "isLessThan";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    LanguageLevel(Supplier supplier, int i) {
        this.myPresentableText = supplier;
        this.myVersion = JavaVersion.compose(i);
    }

    public LanguageLevel getPreviewLevel() {
        if (this.myPreview) {
            return this;
        }
        try {
            return valueOf(name() + "_PREVIEW");
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }

    public boolean isAtLeast(LanguageLevel languageLevel) {
        if (languageLevel == null) {
            $$$reportNull$$$0(1);
        }
        return compareTo(languageLevel) >= 0;
    }

    public JavaVersion toJavaVersion() {
        JavaVersion javaVersion = this.myVersion;
        if (javaVersion == null) {
            $$$reportNull$$$0(3);
        }
        return javaVersion;
    }

    public static LanguageLevel parse(String str) {
        JavaSdkVersion javaSdkVersionFromVersionString;
        if (str == null || (javaSdkVersionFromVersionString = JavaSdkVersion.fromVersionString(str)) == null) {
            return null;
        }
        return javaSdkVersionFromVersionString.getMaxLanguageLevel();
    }
}
