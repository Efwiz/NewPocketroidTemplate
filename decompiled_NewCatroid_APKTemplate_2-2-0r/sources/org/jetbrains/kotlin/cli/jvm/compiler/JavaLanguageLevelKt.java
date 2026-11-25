package org.jetbrains.kotlin.cli.jvm.compiler;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.roots.LanguageLevelProjectExtension;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;

/* JADX INFO: compiled from: JavaLanguageLevel.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"setupHighestLanguageLevel", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "cli-base"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JavaLanguageLevelKt {
    public static final void setupHighestLanguageLevel(Project project) {
        LanguageLevel languageLevel;
        LanguageLevel languageLevel2;
        Intrinsics.checkNotNullParameter(project, "<this>");
        LanguageLevelProjectExtension languageLevelProjectExtension = LanguageLevelProjectExtension.getInstance(project);
        LanguageLevel[] languageLevelArrValues = LanguageLevel.values();
        int length = languageLevelArrValues.length;
        int i = 0;
        while (true) {
            languageLevel = null;
            if (i >= length) {
                languageLevel2 = null;
                break;
            }
            languageLevel2 = languageLevelArrValues[i];
            if (Intrinsics.areEqual(languageLevel2.name(), "JDK_17")) {
                break;
            } else {
                i++;
            }
        }
        if (languageLevel2 == null) {
            for (LanguageLevel languageLevel3 : LanguageLevel.values()) {
                if (Intrinsics.areEqual(languageLevel3.name(), "JDK_15_PREVIEW")) {
                    languageLevel = languageLevel3;
                    break;
                }
            }
            languageLevel2 = languageLevel == null ? LanguageLevel.JDK_X : languageLevel;
        }
        languageLevelProjectExtension.setLanguageLevel(languageLevel2);
    }
}
