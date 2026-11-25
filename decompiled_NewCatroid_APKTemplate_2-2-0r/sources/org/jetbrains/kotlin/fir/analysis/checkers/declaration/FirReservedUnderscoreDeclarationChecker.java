package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirUnderscoreHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.SourceNavigator;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirImport;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.types.FirQualifierPart;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirUserTypeRef;

/* JADX INFO: compiled from: FirReservedUnderscoreDeclarationChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J*\u0010\f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\r\u001a\u00020\u000eH\u0002¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirReservedUnderscoreDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "reportIfUnderscore", "isSingleUnderscoreAllowed", "", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirReservedUnderscoreDeclarationChecker extends FirDeclarationChecker<FirDeclaration> {
    public static final FirReservedUnderscoreDeclarationChecker INSTANCE = new FirReservedUnderscoreDeclarationChecker();

    private FirReservedUnderscoreDeclarationChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (!(declaration instanceof FirRegularClass) && !(declaration instanceof FirTypeParameter)) {
            boolean z = true;
            if ((!(declaration instanceof FirProperty) || Intrinsics.areEqual((Object) ClassMembersKt.isCatchParameter((FirProperty) declaration), (Object) true)) && !(declaration instanceof FirTypeAlias)) {
                if (declaration instanceof FirFunction) {
                    if (declaration instanceof FirSimpleFunction) {
                        reportIfUnderscore$default(this, declaration, context, reporter, false, 8, null);
                    }
                    if (!(declaration instanceof FirAnonymousFunction) && !(declaration instanceof FirPropertyAccessor)) {
                        z = false;
                    }
                    Iterator<FirValueParameter> it = ((FirFunction) declaration).getValueParameters().iterator();
                    while (it.hasNext()) {
                        reportIfUnderscore(it.next(), context, reporter, z);
                    }
                    return;
                }
                if (declaration instanceof FirFile) {
                    Iterator<FirImport> it2 = ((FirFile) declaration).getImports().iterator();
                    while (it2.hasNext()) {
                        FirUnderscoreHelpersKt.checkUnderscoreDiagnostics(it2.next().getAliasSource(), context, reporter, false);
                    }
                    return;
                }
                return;
            }
        }
        reportIfUnderscore$default(this, declaration, context, reporter, false, 8, null);
    }

    static /* synthetic */ void reportIfUnderscore$default(FirReservedUnderscoreDeclarationChecker firReservedUnderscoreDeclarationChecker, FirDeclaration firDeclaration, CheckerContext checkerContext, DiagnosticReporter diagnosticReporter, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = false;
        }
        firReservedUnderscoreDeclarationChecker.reportIfUnderscore(firDeclaration, checkerContext, diagnosticReporter, z);
    }

    private final void reportIfUnderscore(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter, boolean isSingleUnderscoreAllowed) {
        FirTypeRef typeRef;
        FirReceiverParameter receiverParameter;
        String rawName;
        KtSourceElement source = declaration.getSource();
        if (source != null && !(source.getKind() instanceof KtFakeSourceElementKind) && (rawName = SourceNavigator.INSTANCE.forElement(declaration).getRawName(declaration)) != null && FirUnderscoreHelpersKt.isUnderscore(rawName) && (!isSingleUnderscoreAllowed || !Intrinsics.areEqual(rawName, "_"))) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getUNDERSCORE_IS_RESERVED(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
        if (declaration instanceof FirValueParameter) {
            typeRef = ((FirValueParameter) declaration).getReturnTypeRef();
        } else {
            typeRef = (!(declaration instanceof FirFunction) || (receiverParameter = ((FirFunction) declaration).getReceiverParameter()) == null) ? null : receiverParameter.getTypeRef();
        }
        if (typeRef instanceof FirResolvedTypeRef) {
            FirTypeRef delegatedTypeRef = ((FirResolvedTypeRef) typeRef).getDelegatedTypeRef();
            if (delegatedTypeRef instanceof FirUserTypeRef) {
                for (FirQualifierPart firQualifierPart : ((FirUserTypeRef) delegatedTypeRef).getQualifier()) {
                    FirUnderscoreHelpersKt.checkUnderscoreDiagnostics(firQualifierPart.getSource(), context, reporter, true);
                    Iterator<FirTypeProjection> it = firQualifierPart.getTypeArgumentList().getTypeArguments().iterator();
                    while (it.hasNext()) {
                        FirUnderscoreHelpersKt.checkUnderscoreDiagnostics(it.next().getSource(), context, reporter, true);
                    }
                }
            }
        }
    }
}
