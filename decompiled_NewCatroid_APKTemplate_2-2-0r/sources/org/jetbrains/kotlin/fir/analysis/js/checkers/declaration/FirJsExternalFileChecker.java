package org.jetbrains.kotlin.fir.analysis.js.checkers.declaration;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.js.FirJsErrors;
import org.jetbrains.kotlin.fir.analysis.js.checkers.FirJsHelpersKt;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.name.JsStandardClassIds;

/* JADX INFO: compiled from: FirJsExternalFileChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/js/checkers/declaration/FirJsExternalFileChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJsExternalFileChecker extends FirDeclarationChecker<FirDeclaration> {
    public static final FirJsExternalFileChecker INSTANCE = new FirJsExternalFileChecker();

    private FirJsExternalFileChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        FirBasedSymbol<? extends FirDeclaration> symbol;
        List<FirAnnotation> annotations;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirDeclaration firDeclarationClosestNonLocalWith = FirHelpersKt.closestNonLocalWith(context, declaration);
        if (firDeclarationClosestNonLocalWith == null || (symbol = firDeclarationClosestNonLocalWith.getSymbol()) == null || FirJsHelpersKt.isNativeObject(symbol, context) || !FirHelpersKt.isTopLevel(context)) {
            return;
        }
        FirFile containingFile = context.getContainingFile();
        FirAnnotation firAnnotation = null;
        Object obj = null;
        firAnnotation = null;
        if (containingFile != null && (annotations = containingFile.getAnnotations()) != null) {
            for (Object obj2 : annotations) {
                if (CollectionsKt.contains(JsStandardClassIds.Annotations.annotationsRequiringExternal, FirAnnotationUtilsKt.toAnnotationClassId((FirAnnotation) obj2, context.getSession()))) {
                    obj = obj2;
                    break;
                }
            }
            firAnnotation = (FirAnnotation) obj;
        }
        if (firAnnotation != null) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJsErrors.INSTANCE.getNON_EXTERNAL_DECLARATION_IN_INAPPROPRIATE_FILE(), FirTypeUtilsKt.getConeType(firAnnotation.getTypeRef()), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        }
    }
}
