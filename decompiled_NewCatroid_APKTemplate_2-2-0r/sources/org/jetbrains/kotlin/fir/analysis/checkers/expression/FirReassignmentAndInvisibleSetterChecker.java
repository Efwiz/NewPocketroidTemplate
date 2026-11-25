package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticContext;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory3;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirVisibilityChecker;
import org.jetbrains.kotlin.fir.FirVisibilityCheckerKt;
import org.jetbrains.kotlin.fir.analysis.cfa.FirPropertyInitializationAnalyzerKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeSimpleDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind;
import org.jetbrains.kotlin.fir.diagnostics.FirDiagnosticHolder;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression;
import org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression;
import org.jetbrains.kotlin.fir.expressions.FirVariableAssignment;
import org.jetbrains.kotlin.fir.references.FirBackingFieldReference;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.references.FirThisReference;
import org.jetbrains.kotlin.fir.resolve.calls.AbstractCandidate;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeDiagnosticWithCandidates;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnresolvedNameError;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirBackingFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertyAccessorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.name.CallableId;

/* JADX INFO: compiled from: FirReassignmentAndInvisibleSetterChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J \u0010\f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J \u0010\r\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J \u0010\u000e\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J \u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J \u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\b\u001a\u00020\tH\u0002J\f\u0010\u0015\u001a\u00020\u0012*\u00020\u0016H\u0002¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirReassignmentAndInvisibleSetterChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirVariableAssignmentChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkInvisibleSetter", "checkValReassignment", "checkValReassignmentOnValueParameter", "checkValReassignmentViaBackingField", "checkVariableExpected", "isInOwnersInitializer", "", "receiver", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "isConflictingError", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirReassignmentAndInvisibleSetterChecker extends FirExpressionChecker<FirVariableAssignment> {
    public static final FirReassignmentAndInvisibleSetterChecker INSTANCE = new FirReassignmentAndInvisibleSetterChecker();

    private FirReassignmentAndInvisibleSetterChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirVariableAssignment expression, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        checkInvisibleSetter(expression, context, reporter);
        checkValReassignmentViaBackingField(expression, context, reporter);
        checkValReassignmentOnValueParameter(expression, context, reporter);
        checkVariableExpected(expression, context, reporter);
        checkValReassignment(expression, context, reporter);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final boolean checkInvisibleSetter$shouldInvisibleSetterBeReported(CheckerContext checkerContext, FirVariableAssignment firVariableAssignment, FirPropertySymbol firPropertySymbol) {
        FirPropertyAccessor firPropertyAccessor;
        FirPropertyAccessorSymbol setterSymbol;
        FirPropertyAccessorSymbol setterSymbol2 = firPropertySymbol.getSetterSymbol();
        FirFile firFile = null;
        if (setterSymbol2 == null || (firPropertyAccessor = (FirPropertyAccessor) setterSymbol2.getFir()) == null) {
            FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) firPropertySymbol.getFir();
            FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
            FirPropertySymbol firPropertySymbol2 = (FirPropertySymbol) (originalForSubstitutionOverrideAttr != null ? originalForSubstitutionOverrideAttr.getSymbol() : null);
            firPropertyAccessor = (firPropertySymbol2 == null || (setterSymbol = firPropertySymbol2.getSetterSymbol()) == null) ? null : (FirPropertyAccessor) setterSymbol.getFir();
        }
        if (firPropertyAccessor == null) {
            return false;
        }
        FirVisibilityChecker visibilityChecker = FirVisibilityCheckerKt.getVisibilityChecker(checkerContext.getSession());
        FirPropertyAccessor firPropertyAccessor2 = firPropertyAccessor;
        FirSession session = checkerContext.getSession();
        for (FirDeclaration firDeclaration : CollectionsKt.asReversed(checkerContext.getContainingDeclarations())) {
            if (!(firDeclaration instanceof FirFile)) {
                firDeclaration = null;
            }
            FirFile firFile2 = (FirFile) firDeclaration;
            if (firFile2 != null && firFile2 != null) {
                firFile = firFile2;
                break;
            }
        }
        Intrinsics.checkNotNull(firFile);
        return !FirVisibilityChecker.isVisible$default(visibilityChecker, firPropertyAccessor2, session, firFile, checkerContext.getContainingDeclarations(), FirExpressionUtilKt.getDispatchReceiver(firVariableAssignment), false, null, false, null, 480, null);
    }

    private final void checkInvisibleSetter(FirVariableAssignment expression, CheckerContext context, DiagnosticReporter reporter) {
        Visibilities.Private visibility;
        FirRegularClassSymbol regularClassSymbol;
        FirReference calleeReference = FirExpressionUtilKt.getCalleeReference(expression);
        FirCallableSymbol resolvedCallableSymbol$default = calleeReference != null ? FirReferenceUtilsKt.toResolvedCallableSymbol$default(calleeReference, false, 1, null) : null;
        if (resolvedCallableSymbol$default instanceof FirPropertySymbol) {
            FirPropertySymbol firPropertySymbol = (FirPropertySymbol) resolvedCallableSymbol$default;
            if (checkInvisibleSetter$shouldInvisibleSetterBeReported(context, expression, firPropertySymbol)) {
                FirExpression explicitReceiver = FirExpressionUtilKt.getExplicitReceiver(expression);
                if ((explicitReceiver instanceof FirSmartCastExpression) && (regularClassSymbol = FirHelpersKt.toRegularClassSymbol(((FirSmartCastExpression) explicitReceiver).getOriginalExpression().getTypeRef(), context.getSession())) != null) {
                    for (FirBasedSymbol<?> firBasedSymbol : regularClassSymbol.getDeclarationSymbols()) {
                        if (firBasedSymbol instanceof FirPropertySymbol) {
                            FirPropertySymbol firPropertySymbol2 = (FirPropertySymbol) firBasedSymbol;
                            if (Intrinsics.areEqual(firPropertySymbol2.getName(), resolvedCallableSymbol$default.getName()) && !checkInvisibleSetter$shouldInvisibleSetterBeReported(context, expression, firPropertySymbol2)) {
                                return;
                            }
                        }
                    }
                }
                KtSourceElement source = expression.getLValue().getSource();
                KtDiagnosticFactory3<FirPropertySymbol, Visibility, CallableId> invisible_setter = FirErrors.INSTANCE.getINVISIBLE_SETTER();
                FirPropertyAccessorSymbol setterSymbol = firPropertySymbol.getSetterSymbol();
                if (setterSymbol == null || (visibility = setterSymbol.getResolvedStatus().getVisibility()) == null) {
                    visibility = Visibilities.Private.INSTANCE;
                }
                KtDiagnosticReportHelpersKt.reportOn(reporter, (AbstractKtSourceElement) source, (KtDiagnosticFactory3<FirCallableSymbol, Visibility, CallableId>) ((KtDiagnosticFactory3<Object, Object, Object>) invisible_setter), resolvedCallableSymbol$default, visibility, resolvedCallableSymbol$default.getCallableId(), (DiagnosticContext) context, (64 & 64) != 0 ? null : null);
            }
        }
    }

    private final void checkValReassignmentViaBackingField(FirVariableAssignment expression, CheckerContext context, DiagnosticReporter reporter) {
        FirPropertyAccessorSymbol symbol;
        FirReference calleeReference = FirExpressionUtilKt.getCalleeReference(expression);
        FirPropertyAccessor firPropertyAccessor = null;
        FirBackingFieldReference firBackingFieldReference = calleeReference instanceof FirBackingFieldReference ? (FirBackingFieldReference) calleeReference : null;
        if (firBackingFieldReference == null) {
            return;
        }
        FirBackingFieldSymbol resolvedSymbol = firBackingFieldReference.getResolvedSymbol();
        if (resolvedSymbol.isVar()) {
            return;
        }
        for (FirDeclaration firDeclaration : CollectionsKt.asReversed(context.getContainingDeclarations())) {
            if (!(firDeclaration instanceof FirPropertyAccessor)) {
                firDeclaration = null;
            }
            FirPropertyAccessor firPropertyAccessor2 = (FirPropertyAccessor) firDeclaration;
            if (firPropertyAccessor2 != null) {
                if (!firPropertyAccessor2.getIsGetter()) {
                    firPropertyAccessor2 = null;
                }
                if (firPropertyAccessor2 != null) {
                    firPropertyAccessor = firPropertyAccessor2;
                    break;
                }
            }
        }
        FirPropertyAccessor firPropertyAccessor3 = firPropertyAccessor;
        if (firPropertyAccessor3 == null || (symbol = firPropertyAccessor3.getSymbol()) == null || !Intrinsics.areEqual(resolvedSymbol.getGetterSymbol(), symbol)) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, firBackingFieldReference.getSource(), FirErrors.INSTANCE.getVAL_REASSIGNMENT_VIA_BACKING_FIELD(), resolvedSymbol, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
    }

    private final void checkValReassignmentOnValueParameter(FirVariableAssignment expression, CheckerContext context, DiagnosticReporter reporter) {
        FirValueParameterSymbol resolvedValueParameterSymbol$default;
        FirReference calleeReference = FirExpressionUtilKt.getCalleeReference(expression);
        if (calleeReference == null || (resolvedValueParameterSymbol$default = FirReferenceUtilsKt.toResolvedValueParameterSymbol$default(calleeReference, false, 1, null)) == null) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getLValue().getSource(), FirErrors.INSTANCE.getVAL_REASSIGNMENT(), resolvedValueParameterSymbol$default, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
    }

    private final void checkVariableExpected(FirVariableAssignment expression, CheckerContext context, DiagnosticReporter reporter) {
        FirReference calleeReference = FirExpressionUtilKt.getCalleeReference(expression);
        if (FirExpressionUtilKt.unwrapLValue(expression) instanceof FirPropertyAccessExpression) {
            if (calleeReference != null && isConflictingError(calleeReference)) {
                return;
            }
            if ((calleeReference != null ? FirReferenceUtilsKt.toResolvedVariableSymbol$default(calleeReference, false, 1, null) : null) != null) {
                return;
            }
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getLValue().getSource(), FirErrors.INSTANCE.getVARIABLE_EXPECTED(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean isConflictingError(FirReference firReference) {
        if (!FirReferenceUtilsKt.isError(firReference)) {
            return false;
        }
        ConeDiagnostic diagnostic = ((FirDiagnosticHolder) firReference).getDiagnostic();
        if (diagnostic instanceof ConeSimpleDiagnostic) {
            if (((ConeSimpleDiagnostic) diagnostic).getKind() != DiagnosticKind.VariableExpected) {
                return false;
            }
        } else if (!(diagnostic instanceof ConeUnresolvedNameError)) {
            if (!(diagnostic instanceof ConeDiagnosticWithCandidates)) {
                return false;
            }
            Collection<AbstractCandidate> candidates = ((ConeDiagnosticWithCandidates) diagnostic).getCandidates();
            if ((candidates instanceof Collection) && candidates.isEmpty()) {
                return false;
            }
            Iterator<T> it = candidates.iterator();
            while (it.hasNext()) {
                if (((AbstractCandidate) it.next()).getSymbol() instanceof FirPropertySymbol) {
                }
            }
            return false;
        }
        return true;
    }

    private final void checkValReassignment(FirVariableAssignment expression, CheckerContext context, DiagnosticReporter reporter) {
        FirPropertySymbol resolvedPropertySymbol$default;
        FirReference calleeReference = FirExpressionUtilKt.getCalleeReference(expression);
        if (calleeReference == null || (resolvedPropertySymbol$default = FirReferenceUtilsKt.toResolvedPropertySymbol$default(calleeReference, false, 1, null)) == null || resolvedPropertySymbol$default.isVar()) {
            return;
        }
        if (resolvedPropertySymbol$default.isLocal() && FirPropertyInitializationAnalyzerKt.requiresInitialization(resolvedPropertySymbol$default, false)) {
            return;
        }
        if (isInOwnersInitializer(FirExpressionUtilKt.unwrapSmartcastExpression(FirExpressionUtilKt.getDispatchReceiver(expression)), context) && FirPropertyInitializationAnalyzerKt.requiresInitialization(resolvedPropertySymbol$default, true)) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getLValue().getSource(), FirErrors.INSTANCE.getVAL_REASSIGNMENT(), resolvedPropertySymbol$default, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
    }

    private final boolean isInOwnersInitializer(FirExpression receiver, CheckerContext context) {
        FirThisReference calleeReference;
        FirBasedSymbol<?> boundSymbol;
        FirThisReceiverExpression firThisReceiverExpression = receiver instanceof FirThisReceiverExpression ? (FirThisReceiverExpression) receiver : null;
        if (firThisReceiverExpression == null || (calleeReference = firThisReceiverExpression.getCalleeReference()) == null || (boundSymbol = calleeReference.getBoundSymbol()) == null) {
            return false;
        }
        boolean z = false;
        int i = 0;
        for (FirDeclaration firDeclaration : context.getContainingDeclarations()) {
            i++;
            if (firDeclaration instanceof FirClass) {
                FirDeclaration firDeclaration2 = (FirDeclaration) CollectionsKt.getOrNull(context.getContainingDeclarations(), i);
                z = (firDeclaration2 == null || FirPropertyInitializationAnalyzerKt.getEvaluatedInPlace(firDeclaration2)) && (z || Intrinsics.areEqual(((FirClass) firDeclaration).getSymbol(), boundSymbol));
            }
        }
        return z;
    }
}
