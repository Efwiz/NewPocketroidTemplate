package org.jetbrains.kotlin.fir.analysis.js.checkers.declaration;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.js.FirJsErrors;
import org.jetbrains.kotlin.fir.analysis.js.checkers.FirJsHelpersKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: FirJsBuiltinNameClashChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/js/checkers/declaration/FirJsBuiltinNameClashChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "()V", "PROHIBITED_MEMBER_NAMES", "", "", "PROHIBITED_STATIC_NAMES", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJsBuiltinNameClashChecker extends FirDeclarationChecker<FirDeclaration> {
    public static final FirJsBuiltinNameClashChecker INSTANCE = new FirJsBuiltinNameClashChecker();
    private static final Set<String> PROHIBITED_STATIC_NAMES = SetsKt.setOf((Object[]) new String[]{"prototype", "length", Namer.METADATA});
    private static final Set<String> PROHIBITED_MEMBER_NAMES = SetsKt.setOf(JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME);

    private FirJsBuiltinNameClashChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        String stableNameInJavaScript;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (FirJsHelpersKt.isNativeObject(declaration.getSymbol(), context.getSession()) || FirHelpersKt.getContainingClassSymbol(declaration, context.getSession()) == null || (stableNameInJavaScript = FirJsHelpersKt.getStableNameInJavaScript(declaration.getSymbol(), context.getSession())) == null) {
            return;
        }
        if ((declaration instanceof FirClassLikeDeclaration) && PROHIBITED_STATIC_NAMES.contains(stableNameInJavaScript)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJsErrors.INSTANCE.getJS_BUILTIN_NAME_CLASH(), "Function." + stableNameInJavaScript, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        }
        if ((declaration instanceof FirCallableDeclaration) && PROHIBITED_MEMBER_NAMES.contains(stableNameInJavaScript)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJsErrors.INSTANCE.getJS_BUILTIN_NAME_CLASH(), "Object.prototype." + stableNameInJavaScript, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        }
    }
}
