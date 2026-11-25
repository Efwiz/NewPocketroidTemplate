package org.jetbrains.kotlin.resolve.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.AnalysisFlags;
import org.jetbrains.kotlin.config.ExplicitApiMode;
import org.jetbrains.kotlin.config.LanguageVersionSettings;

/* JADX INFO: compiled from: ExplicitApiDeclarationChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, d2 = {"explicitApiEnabled", "", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getExplicitApiEnabled", "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)Z", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ExplicitApiDeclarationCheckerKt {
    public static final boolean getExplicitApiEnabled(LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "<this>");
        return languageVersionSettings.getFlag(AnalysisFlags.getExplicitApiMode()) != ExplicitApiMode.DISABLED;
    }
}
