package org.jetbrains.kotlin.fir.analysis.jvm;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirJvmNamesChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/jvm/FirJvmNamesChecker;", "", "()V", "DANGEROUS_CHARS", "", "", "INVALID_CHARS", "checkNameAndReport", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "declarationSource", "Lorg/jetbrains/kotlin/KtSourceElement;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmNamesChecker {
    public static final FirJvmNamesChecker INSTANCE = new FirJvmNamesChecker();
    private static final Set<Character> INVALID_CHARS = SetsKt.setOf((Object[]) new Character[]{'.', ';', '[', ']', '/', '<', '>', ':', '\\'});
    private static final Set<Character> DANGEROUS_CHARS = SetsKt.setOf((Object[]) new Character[]{Character.valueOf(MangleConstant.Q_MARK), '*', '\"', '|', Character.valueOf(MangleConstant.PLATFORM_FUNCTION_MARKER)});

    private FirJvmNamesChecker() {
    }

    public final void checkNameAndReport(Name name2, KtSourceElement declarationSource, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (declarationSource == null || (declarationSource.getKind() instanceof KtFakeSourceElementKind) || name2.isSpecial()) {
            return;
        }
        String strAsString = name2.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        String str = strAsString;
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            Set<Character> set = INVALID_CHARS;
            if (set.contains(Character.valueOf(cCharAt))) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declarationSource, FirErrors.INSTANCE.getINVALID_CHARACTERS(), "contains illegal characters: " + CollectionsKt.joinToString$default(CollectionsKt.intersect(set, StringsKt.toSet(str)), "", null, null, 0, null, null, 62, null), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                return;
            }
        }
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt2 = str.charAt(i2);
            Set<Character> set2 = DANGEROUS_CHARS;
            if (set2.contains(Character.valueOf(cCharAt2))) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declarationSource, FirErrors.INSTANCE.getDANGEROUS_CHARACTERS(), CollectionsKt.joinToString$default(CollectionsKt.intersect(set2, StringsKt.toSet(str)), "", null, null, 0, null, null, 62, null), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                return;
            }
        }
    }
}
