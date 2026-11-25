package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.config.LanguageVersionSettings;

/* JADX INFO: compiled from: VarianceChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/VarianceChecker;", "", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", ConfigConstants.CONFIG_CORE_SECTION, "Lorg/jetbrains/kotlin/resolve/VarianceCheckerCore;", "check", "", "c", "Lorg/jetbrains/kotlin/resolve/TopDownAnalysisContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VarianceChecker {
    private final VarianceCheckerCore core;

    public VarianceChecker(BindingTrace trace, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        BindingContext context = trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        this.core = new VarianceCheckerCore(context, trace, null, languageVersionSettings, 4, null);
    }

    public final void check(TopDownAnalysisContext c) {
        Intrinsics.checkNotNullParameter(c, "c");
        this.core.check(c);
    }
}
