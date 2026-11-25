package org.jetbrains.kotlin.fir.analysis.collectors.components;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnostic;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.ConeDiagnosticToFirDiagnosticKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirErrorFunction;
import org.jetbrains.kotlin.fir.declarations.FirErrorImport;
import org.jetbrains.kotlin.fir.declarations.FirErrorProperty;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.diagnostics.ConeAmbiguousSuper;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeSimpleDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeSyntaxDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind;
import org.jetbrains.kotlin.fir.expressions.FirErrorAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirErrorExpression;
import org.jetbrains.kotlin.fir.expressions.FirErrorLoop;
import org.jetbrains.kotlin.fir.expressions.FirErrorResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.references.FirErrorNamedReference;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.references.FirResolvedErrorReference;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeAmbiguityError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeInapplicableCandidateError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeInapplicableWrongReceiver;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeInstanceAccessBeforeSuperCall;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnresolvedNameError;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;

/* JADX INFO: compiled from: ErrorNodeDiagnosticCollectorComponent.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u009a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J \u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J.\u0010\u000f\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\r\u001a\u00020\u000e2\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0011H\u0002J\u0018\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u000eH\u0016J\u0018\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0016\u001a\u00020\u000eH\u0016J\u0018\u0010\u001a\u001a\u00020\b2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0016\u001a\u00020\u000eH\u0016J\u0018\u0010\u001d\u001a\u00020\b2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0016\u001a\u00020\u000eH\u0016J\u0018\u0010 \u001a\u00020\b2\u0006\u0010!\u001a\u00020\"2\u0006\u0010\u0016\u001a\u00020\u000eH\u0016J\u0018\u0010#\u001a\u00020\b2\u0006\u0010$\u001a\u00020%2\u0006\u0010\u0016\u001a\u00020\u000eH\u0016J\u0018\u0010&\u001a\u00020\b2\u0006\u0010'\u001a\u00020(2\u0006\u0010\u0016\u001a\u00020\u000eH\u0016J\u0018\u0010)\u001a\u00020\b2\u0006\u0010*\u001a\u00020+2\u0006\u0010\u0016\u001a\u00020\u000eH\u0016J\u0018\u0010,\u001a\u00020\b2\u0006\u0010-\u001a\u00020.2\u0006\u0010\u0016\u001a\u00020\u000eH\u0016J\u0018\u0010/\u001a\u00020\b2\u0006\u00100\u001a\u0002012\u0006\u0010\u0016\u001a\u00020\u000eH\u0016J\u0018\u00102\u001a\u00020\b2\u0006\u00103\u001a\u0002042\u0006\u0010\u0016\u001a\u00020\u000eH\u0016J\u000e\u00105\u001a\u000206*\u0004\u0018\u000107H\u0002¨\u00068"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/collectors/components/ErrorNodeDiagnosticCollectorComponent;", "Lorg/jetbrains/kotlin/fir/analysis/collectors/components/AbstractDiagnosticCollectorComponent;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;)V", "processErrorReference", "", "reference", "Lorg/jetbrains/kotlin/fir/references/FirNamedReference;", "diagnostic", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reportFirDiagnostic", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "callOrAssignmentSource", "visitErrorAnnotationCall", "errorAnnotationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirErrorAnnotationCall;", "data", "visitErrorExpression", "errorExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirErrorExpression;", "visitErrorFunction", "errorFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirErrorFunction;", "visitErrorImport", "errorImport", "Lorg/jetbrains/kotlin/fir/declarations/FirErrorImport;", "visitErrorLoop", "errorLoop", "Lorg/jetbrains/kotlin/fir/expressions/FirErrorLoop;", "visitErrorNamedReference", "errorNamedReference", "Lorg/jetbrains/kotlin/fir/references/FirErrorNamedReference;", "visitErrorProperty", "errorProperty", "Lorg/jetbrains/kotlin/fir/declarations/FirErrorProperty;", "visitErrorResolvedQualifier", "errorResolvedQualifier", "Lorg/jetbrains/kotlin/fir/expressions/FirErrorResolvedQualifier;", "visitErrorTypeRef", "errorTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirErrorTypeRef;", "visitResolvedErrorReference", "resolvedErrorReference", "Lorg/jetbrains/kotlin/fir/references/FirResolvedErrorReference;", "visitResolvedTypeRef", "resolvedTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "cannotBeResolved", "", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ErrorNodeDiagnosticCollectorComponent extends AbstractDiagnosticCollectorComponent {
    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitErrorAnnotationCall(FirErrorAnnotationCall firErrorAnnotationCall, CheckerContext checkerContext) {
        visitErrorAnnotationCall2(firErrorAnnotationCall, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitErrorExpression(FirErrorExpression firErrorExpression, CheckerContext checkerContext) {
        visitErrorExpression2(firErrorExpression, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitErrorFunction(FirErrorFunction firErrorFunction, CheckerContext checkerContext) {
        visitErrorFunction2(firErrorFunction, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitErrorImport(FirErrorImport firErrorImport, CheckerContext checkerContext) {
        visitErrorImport2(firErrorImport, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitErrorLoop(FirErrorLoop firErrorLoop, CheckerContext checkerContext) {
        visitErrorLoop2(firErrorLoop, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitErrorNamedReference(FirErrorNamedReference firErrorNamedReference, CheckerContext checkerContext) {
        visitErrorNamedReference2(firErrorNamedReference, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitErrorProperty(FirErrorProperty firErrorProperty, CheckerContext checkerContext) {
        visitErrorProperty2(firErrorProperty, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitErrorResolvedQualifier(FirErrorResolvedQualifier firErrorResolvedQualifier, CheckerContext checkerContext) {
        visitErrorResolvedQualifier2(firErrorResolvedQualifier, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitErrorTypeRef(FirErrorTypeRef firErrorTypeRef, CheckerContext checkerContext) {
        visitErrorTypeRef2(firErrorTypeRef, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitResolvedErrorReference(FirResolvedErrorReference firResolvedErrorReference, CheckerContext checkerContext) {
        visitResolvedErrorReference2(firResolvedErrorReference, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitResolvedTypeRef(FirResolvedTypeRef firResolvedTypeRef, CheckerContext checkerContext) {
        visitResolvedTypeRef2(firResolvedTypeRef, checkerContext);
        return Unit.INSTANCE;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ErrorNodeDiagnosticCollectorComponent(FirSession session, DiagnosticReporter reporter) {
        super(session, reporter);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
    }

    /* JADX INFO: renamed from: visitErrorLoop, reason: avoid collision after fix types in other method */
    public void visitErrorLoop2(FirErrorLoop errorLoop, CheckerContext data) {
        Intrinsics.checkNotNullParameter(errorLoop, "errorLoop");
        Intrinsics.checkNotNullParameter(data, "data");
        reportFirDiagnostic$default(this, errorLoop.getDiagnostic(), errorLoop.getSource(), data, null, 8, null);
    }

    /* JADX INFO: renamed from: visitErrorTypeRef, reason: avoid collision after fix types in other method */
    public void visitErrorTypeRef2(FirErrorTypeRef errorTypeRef, CheckerContext data) {
        Intrinsics.checkNotNullParameter(errorTypeRef, "errorTypeRef");
        Intrinsics.checkNotNullParameter(data, "data");
        reportFirDiagnostic$default(this, errorTypeRef.getDiagnostic(), errorTypeRef.getSource(), data, null, 8, null);
    }

    /* JADX INFO: renamed from: visitResolvedTypeRef, reason: avoid collision after fix types in other method */
    public void visitResolvedTypeRef2(FirResolvedTypeRef resolvedTypeRef, CheckerContext data) {
        Intrinsics.checkNotNullParameter(resolvedTypeRef, "resolvedTypeRef");
        Intrinsics.checkNotNullParameter(data, "data");
        boolean z = resolvedTypeRef.getType() instanceof ConeErrorType;
    }

    /* JADX INFO: renamed from: visitErrorAnnotationCall, reason: avoid collision after fix types in other method */
    public void visitErrorAnnotationCall2(FirErrorAnnotationCall errorAnnotationCall, CheckerContext data) {
        Intrinsics.checkNotNullParameter(errorAnnotationCall, "errorAnnotationCall");
        Intrinsics.checkNotNullParameter(data, "data");
        reportFirDiagnostic$default(this, errorAnnotationCall.getDiagnostic(), errorAnnotationCall.getSource(), data, null, 8, null);
    }

    /* JADX INFO: renamed from: visitErrorNamedReference, reason: avoid collision after fix types in other method */
    public void visitErrorNamedReference2(FirErrorNamedReference errorNamedReference, CheckerContext data) {
        Intrinsics.checkNotNullParameter(errorNamedReference, "errorNamedReference");
        Intrinsics.checkNotNullParameter(data, "data");
        processErrorReference(errorNamedReference, errorNamedReference.getDiagnostic(), data);
    }

    /* JADX INFO: renamed from: visitResolvedErrorReference, reason: avoid collision after fix types in other method */
    public void visitResolvedErrorReference2(FirResolvedErrorReference resolvedErrorReference, CheckerContext data) {
        Intrinsics.checkNotNullParameter(resolvedErrorReference, "resolvedErrorReference");
        Intrinsics.checkNotNullParameter(data, "data");
        processErrorReference(resolvedErrorReference, resolvedErrorReference.getDiagnostic(), data);
    }

    private final void processErrorReference(FirNamedReference reference, ConeDiagnostic diagnostic, CheckerContext context) {
        FirDeclaration firDeclarationPrevious;
        FirExpression delegate;
        KtSourceElement source;
        KtSourceElement source2 = reference.getSource();
        FirStatement firStatement = (FirStatement) CollectionsKt.lastOrNull((List) context.getCallsOrAssignments());
        if (firStatement == null || !Intrinsics.areEqual(FirExpressionUtilKt.getCalleeReference(firStatement), reference)) {
            firStatement = null;
        }
        if (Intrinsics.areEqual(source2 != null ? source2.getElementType() : null, KtNodeTypes.ANNOTATION_ENTRY) && (diagnostic instanceof ConeUnresolvedNameError)) {
            return;
        }
        if (Intrinsics.areEqual(source2 != null ? source2.getKind() : null, KtFakeSourceElementKind.ArrayAccessNameReference.INSTANCE) && (diagnostic instanceof ConeUnresolvedNameError)) {
            return;
        }
        if (firStatement instanceof FirQualifiedAccessExpression) {
            FirQualifiedAccessExpression firQualifiedAccessExpression = (FirQualifiedAccessExpression) firStatement;
            if (cannotBeResolved(firQualifiedAccessExpression.getDispatchReceiver()) || cannotBeResolved(firQualifiedAccessExpression.getExtensionReceiver()) || cannotBeResolved(firQualifiedAccessExpression.getExplicitReceiver())) {
                return;
            }
        }
        if (Intrinsics.areEqual(source2 != null ? source2.getKind() : null, KtFakeSourceElementKind.DelegatedPropertyAccessor.INSTANCE)) {
            List<FirDeclaration> containingDeclarations = context.getContainingDeclarations();
            ListIterator<FirDeclaration> listIterator = containingDeclarations.listIterator(containingDeclarations.size());
            do {
                if (!listIterator.hasPrevious()) {
                    firDeclarationPrevious = null;
                    break;
                }
                firDeclarationPrevious = listIterator.previous();
            } while (!(firDeclarationPrevious instanceof FirProperty));
            FirProperty firProperty = firDeclarationPrevious instanceof FirProperty ? (FirProperty) firDeclarationPrevious : null;
            if (firProperty == null || (delegate = firProperty.getDelegate()) == null || (source = delegate.getSource()) == null || (source2 = KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.DelegatedPropertyAccessor.INSTANCE, 0, 0, 6, null)) == null) {
                return;
            }
        }
        reportFirDiagnostic(diagnostic, source2, context, firStatement != null ? firStatement.getSource() : null);
    }

    private final boolean cannotBeResolved(FirExpression firExpression) {
        FirTypeRef typeRef = firExpression != null ? firExpression.getTypeRef() : null;
        FirErrorTypeRef firErrorTypeRef = typeRef instanceof FirErrorTypeRef ? (FirErrorTypeRef) typeRef : null;
        ConeDiagnostic diagnostic = firErrorTypeRef != null ? firErrorTypeRef.getDiagnostic() : null;
        if (diagnostic instanceof ConeUnresolvedNameError ? true : diagnostic instanceof ConeInstanceAccessBeforeSuperCall ? true : diagnostic instanceof ConeAmbiguousSuper) {
            return true;
        }
        if (diagnostic instanceof ConeSimpleDiagnostic) {
            ConeSimpleDiagnostic coneSimpleDiagnostic = (ConeSimpleDiagnostic) diagnostic;
            if (coneSimpleDiagnostic.getKind() == DiagnosticKind.NotASupertype || coneSimpleDiagnostic.getKind() == DiagnosticKind.SuperNotAvailable || coneSimpleDiagnostic.getKind() == DiagnosticKind.UnresolvedLabel) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: renamed from: visitErrorExpression, reason: avoid collision after fix types in other method */
    public void visitErrorExpression2(FirErrorExpression errorExpression, CheckerContext data) {
        Intrinsics.checkNotNullParameter(errorExpression, "errorExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        KtSourceElement source = errorExpression.getSource();
        ConeDiagnostic diagnostic = errorExpression.getDiagnostic();
        if (source == null) {
            if (diagnostic instanceof ConeSyntaxDiagnostic) {
                return;
            }
            if ((diagnostic instanceof ConeSimpleDiagnostic) && ((ConeSimpleDiagnostic) diagnostic).getKind() == DiagnosticKind.ExpressionExpected) {
                return;
            }
        }
        reportFirDiagnostic$default(this, diagnostic, source, data, null, 8, null);
    }

    /* JADX INFO: renamed from: visitErrorFunction, reason: avoid collision after fix types in other method */
    public void visitErrorFunction2(FirErrorFunction errorFunction, CheckerContext data) {
        Intrinsics.checkNotNullParameter(errorFunction, "errorFunction");
        Intrinsics.checkNotNullParameter(data, "data");
        reportFirDiagnostic$default(this, errorFunction.getDiagnostic(), errorFunction.getSource(), data, null, 8, null);
    }

    /* JADX INFO: renamed from: visitErrorProperty, reason: avoid collision after fix types in other method */
    public void visitErrorProperty2(FirErrorProperty errorProperty, CheckerContext data) {
        Intrinsics.checkNotNullParameter(errorProperty, "errorProperty");
        Intrinsics.checkNotNullParameter(data, "data");
        reportFirDiagnostic$default(this, errorProperty.getDiagnostic(), errorProperty.getSource(), data, null, 8, null);
    }

    /* JADX INFO: renamed from: visitErrorResolvedQualifier, reason: avoid collision after fix types in other method */
    public void visitErrorResolvedQualifier2(FirErrorResolvedQualifier errorResolvedQualifier, CheckerContext data) {
        Intrinsics.checkNotNullParameter(errorResolvedQualifier, "errorResolvedQualifier");
        Intrinsics.checkNotNullParameter(data, "data");
        reportFirDiagnostic$default(this, errorResolvedQualifier.getDiagnostic(), errorResolvedQualifier.getSource(), data, null, 8, null);
    }

    /* JADX INFO: renamed from: visitErrorImport, reason: avoid collision after fix types in other method */
    public void visitErrorImport2(FirErrorImport errorImport, CheckerContext data) {
        Intrinsics.checkNotNullParameter(errorImport, "errorImport");
        Intrinsics.checkNotNullParameter(data, "data");
        reportFirDiagnostic$default(this, errorImport.getDiagnostic(), errorImport.getSource(), data, null, 8, null);
    }

    static /* synthetic */ void reportFirDiagnostic$default(ErrorNodeDiagnosticCollectorComponent errorNodeDiagnosticCollectorComponent, ConeDiagnostic coneDiagnostic, KtSourceElement ktSourceElement, CheckerContext checkerContext, KtSourceElement ktSourceElement2, int i, Object obj) {
        if ((i & 8) != 0) {
            ktSourceElement2 = null;
        }
        errorNodeDiagnosticCollectorComponent.reportFirDiagnostic(coneDiagnostic, ktSourceElement, checkerContext, ktSourceElement2);
    }

    private final void reportFirDiagnostic(ConeDiagnostic diagnostic, KtSourceElement source, CheckerContext context, KtSourceElement callOrAssignmentSource) {
        if (Intrinsics.areEqual(source != null ? source.getElementType() : null, KtNodeTypes.DESTRUCTURING_DECLARATION_ENTRY)) {
            return;
        }
        if (Intrinsics.areEqual(source != null ? source.getKind() : null, KtFakeSourceElementKind.DelegatedPropertyAccessor.INSTANCE) && ((diagnostic instanceof ConeUnresolvedNameError) || (diagnostic instanceof ConeAmbiguityError) || (diagnostic instanceof ConeInapplicableWrongReceiver) || (diagnostic instanceof ConeInapplicableCandidateError))) {
            return;
        }
        if (Intrinsics.areEqual(source != null ? source.getKind() : null, KtFakeSourceElementKind.ImplicitConstructor.INSTANCE)) {
            return;
        }
        if (Intrinsics.areEqual(source != null ? source.getKind() : null, KtFakeSourceElementKind.DesugaredForLoop.INSTANCE)) {
            return;
        }
        if (Intrinsics.areEqual(source != null ? source.getKind() : null, KtFakeSourceElementKind.DesugaredPrefixSecondGetReference.INSTANCE)) {
            return;
        }
        Iterator<KtDiagnostic> it = ConeDiagnosticToFirDiagnosticKt.toFirDiagnostics(diagnostic, getSession(), source, callOrAssignmentSource).iterator();
        while (it.hasNext()) {
            getReporter().report(it.next(), context);
        }
    }
}
