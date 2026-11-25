package org.jetbrains.kotlin.resolve.checkers;

import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.resolve.BindingTrace;

/* JADX INFO: compiled from: TrailingCommaChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/TrailingCommaChecker;", "", "()V", "check", "", "trailingComma", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TrailingCommaChecker {
    public static final TrailingCommaChecker INSTANCE = new TrailingCommaChecker();

    private TrailingCommaChecker() {
    }

    public final void check(PsiElement trailingComma, BindingTrace trace, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        if (languageVersionSettings.supportsFeature(LanguageFeature.TrailingCommas) || trailingComma == null) {
            return;
        }
        trace.report(Errors.UNSUPPORTED_FEATURE.on(trailingComma, TuplesKt.to(LanguageFeature.TrailingCommas, languageVersionSettings)));
    }
}
