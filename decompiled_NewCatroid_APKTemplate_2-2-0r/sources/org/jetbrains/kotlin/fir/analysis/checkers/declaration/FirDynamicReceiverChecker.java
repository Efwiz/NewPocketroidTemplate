package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.types.ConeDynamicType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: FirDynamicReceiverChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDynamicReceiverChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirCallableDeclarationChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDynamicReceiverChecker extends FirDeclarationChecker<FirCallableDeclaration> {
    public static final FirDynamicReceiverChecker INSTANCE = new FirDynamicReceiverChecker();

    private FirDynamicReceiverChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirCallableDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        FirTypeRef typeRef;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirReceiverParameter receiverParameter = declaration.getReceiverParameter();
        if (!(((receiverParameter == null || (typeRef = receiverParameter.getTypeRef()) == null) ? null : FirTypeUtilsKt.getConeType(typeRef)) instanceof ConeDynamicType) || FirAnnotationUtilsKt.hasAnnotation((FirDeclaration) declaration, StandardClassIds.Annotations.INSTANCE.getDynamicExtension(), context.getSession()) || (declaration instanceof FirAnonymousFunction)) {
            return;
        }
        FirReceiverParameter receiverParameter2 = declaration.getReceiverParameter();
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, receiverParameter2 != null ? receiverParameter2.getSource() : null, FirErrors.INSTANCE.getDYNAMIC_RECEIVER_NOT_ALLOWED(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
    }
}
