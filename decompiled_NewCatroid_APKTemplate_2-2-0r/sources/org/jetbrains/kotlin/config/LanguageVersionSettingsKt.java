package org.jetbrains.kotlin.config;

import kotlin.KotlinVersion;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LanguageVersionSettings.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0001*\u00020\u0004\u001a\n\u0010\u0005\u001a\u00020\u0006*\u00020\u0004¨\u0006\u0007"}, d2 = {"forcesPreReleaseBinariesIfEnabled", "", "Lorg/jetbrains/kotlin/config/LanguageFeature;", "isStableOrReadyForPreview", "Lorg/jetbrains/kotlin/config/LanguageVersion;", "toKotlinVersion", "Lkotlin/KotlinVersion;", "util"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LanguageVersionSettingsKt {
    public static final boolean isStableOrReadyForPreview(LanguageVersion languageVersion) {
        Intrinsics.checkNotNullParameter(languageVersion, "<this>");
        return languageVersion.isStable() || languageVersion == LanguageVersion.KOTLIN_1_9 || languageVersion == LanguageVersion.KOTLIN_2_0;
    }

    public static final KotlinVersion toKotlinVersion(LanguageVersion languageVersion) {
        Intrinsics.checkNotNullParameter(languageVersion, "<this>");
        return new KotlinVersion(languageVersion.getMajor(), languageVersion.getMinor());
    }

    public static final boolean forcesPreReleaseBinariesIfEnabled(LanguageFeature languageFeature) {
        Intrinsics.checkNotNullParameter(languageFeature, "<this>");
        LanguageVersion sinceVersion = languageFeature.getSinceVersion();
        return !(sinceVersion != null && sinceVersion.isStable()) && languageFeature.getKind().getForcesPreReleaseBinaries();
    }
}
