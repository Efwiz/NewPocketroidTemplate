package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKind;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirKeywordUtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.FirDiagnosticHolder;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirWhileLoop;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.resolve.calls.AbstractCandidate;
import org.jetbrains.kotlin.fir.resolve.calls.ResolutionDiagnostic;
import org.jetbrains.kotlin.fir.resolve.calls.UnsafeCall;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeAmbiguityError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeInapplicableCandidateError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeInapplicableWrongReceiver;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnresolvedNameError;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.lexer.KtKeywordToken;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicabilityKt;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: FirForLoopChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016Jr\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\b\u001a\u00020\t2\u0016\u0010\u0012\u001a\u0012\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00150\u00140\u00132\u0006\u0010\u0016\u001a\u00020\u00172\u001a\b\u0002\u0010\u0018\u001a\u0014\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00150\u0014\u0018\u00010\u00132\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u0017H\u0002¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirForLoopChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirBlockChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkSpecialFunctionCall", "", "call", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "reportSource", "Lorg/jetbrains/kotlin/KtSourceElement;", "ambiguityFactory", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;", "", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "missingFactory", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;", "noneApplicableFactory", "unsafeCallFactory", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirForLoopChecker extends FirExpressionChecker<FirBlock> {
    public static final FirForLoopChecker INSTANCE = new FirForLoopChecker();

    private FirForLoopChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirBlock expression, CheckerContext context, DiagnosticReporter reporter) {
        KtSourceElement source;
        KtSourceElement source2;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        KtSourceElement source3 = expression.getSource();
        KtSourceElementKind kind = null;
        if (Intrinsics.areEqual(source3 != null ? source3.getKind() : null, KtFakeSourceElementKind.DesugaredForLoop.INSTANCE)) {
            List<FirStatement> statements = expression.getStatements();
            FirStatement firStatement = statements.get(0);
            FirProperty firProperty = firStatement instanceof FirProperty ? (FirProperty) firStatement : null;
            if (firProperty == null) {
                return;
            }
            FirStatement firStatement2 = statements.get(1);
            FirWhileLoop firWhileLoop = firStatement2 instanceof FirWhileLoop ? (FirWhileLoop) firStatement2 : null;
            if (firWhileLoop == null) {
                return;
            }
            KtSourceElement source4 = firProperty.getSource();
            if (Intrinsics.areEqual(source4 != null ? source4.getKind() : null, KtFakeSourceElementKind.DesugaredForLoop.INSTANCE)) {
                FirExpression initializer = firProperty.getInitializer();
                Intrinsics.checkNotNull(initializer, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirFunctionCall");
                FirFunctionCall firFunctionCall = (FirFunctionCall) initializer;
                FirExpression explicitReceiver = firFunctionCall.getExplicitReceiver();
                if (explicitReceiver == null || (source = explicitReceiver.getSource()) == null) {
                    source = firFunctionCall.getSource();
                }
                KtSourceElement ktSourceElement = source;
                if (checkSpecialFunctionCall$default(this, firFunctionCall, reporter, ktSourceElement, context, FirErrors.INSTANCE.getITERATOR_AMBIGUITY(), FirErrors.INSTANCE.getITERATOR_MISSING(), null, FirErrors.INSTANCE.getITERATOR_ON_NULLABLE(), 64, null)) {
                    return;
                }
                FirExpression condition = firWhileLoop.getCondition();
                Intrinsics.checkNotNull(condition, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirFunctionCall");
                checkSpecialFunctionCall$default(this, (FirFunctionCall) condition, reporter, ktSourceElement, context, FirErrors.INSTANCE.getHAS_NEXT_FUNCTION_AMBIGUITY(), FirErrors.INSTANCE.getHAS_NEXT_MISSING(), FirErrors.INSTANCE.getHAS_NEXT_FUNCTION_NONE_APPLICABLE(), null, 128, null);
                Object objFirstOrNull = CollectionsKt.firstOrNull((List<? extends Object>) firWhileLoop.getBlock().getStatements());
                FirProperty firProperty2 = objFirstOrNull instanceof FirProperty ? (FirProperty) objFirstOrNull : null;
                if (firProperty2 == null) {
                    return;
                }
                FirExpression initializer2 = firProperty2.getInitializer();
                if (initializer2 != null && (source2 = initializer2.getSource()) != null) {
                    kind = source2.getKind();
                }
                if (Intrinsics.areEqual(kind, KtFakeSourceElementKind.DesugaredForLoop.INSTANCE)) {
                    FirExpression initializer3 = firProperty2.getInitializer();
                    Intrinsics.checkNotNull(initializer3, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirFunctionCall");
                    checkSpecialFunctionCall$default(this, (FirFunctionCall) initializer3, reporter, ktSourceElement, context, FirErrors.INSTANCE.getNEXT_AMBIGUITY(), FirErrors.INSTANCE.getNEXT_MISSING(), FirErrors.INSTANCE.getNEXT_NONE_APPLICABLE(), null, 128, null);
                    KtSourceElement source5 = firProperty2.getSource();
                    KtKeywordToken valOrVarKeyword = FirKeywordUtilsKt.getValOrVarKeyword(source5);
                    if (valOrVarKeyword != null) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, source5, FirErrors.INSTANCE.getVAL_OR_VAR_ON_LOOP_PARAMETER(), valOrVarKeyword, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                    }
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ boolean checkSpecialFunctionCall$default(FirForLoopChecker firForLoopChecker, FirFunctionCall firFunctionCall, DiagnosticReporter diagnosticReporter, KtSourceElement ktSourceElement, CheckerContext checkerContext, KtDiagnosticFactory1 ktDiagnosticFactory1, KtDiagnosticFactory0 ktDiagnosticFactory0, KtDiagnosticFactory1 ktDiagnosticFactory2, KtDiagnosticFactory0 ktDiagnosticFactory3, int i, Object obj) {
        return firForLoopChecker.checkSpecialFunctionCall(firFunctionCall, diagnosticReporter, ktSourceElement, checkerContext, ktDiagnosticFactory1, ktDiagnosticFactory0, (i & 64) != 0 ? null : ktDiagnosticFactory2, (i & 128) != 0 ? null : ktDiagnosticFactory3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean checkSpecialFunctionCall(FirFunctionCall call, DiagnosticReporter reporter, KtSourceElement reportSource, CheckerContext context, KtDiagnosticFactory1<Collection<FirBasedSymbol<?>>> ambiguityFactory, KtDiagnosticFactory0 missingFactory, KtDiagnosticFactory1<Collection<FirBasedSymbol<?>>> noneApplicableFactory, KtDiagnosticFactory0 unsafeCallFactory) {
        FirNamedReference calleeReference = call.getCalleeReference();
        if (FirReferenceUtilsKt.isError(calleeReference)) {
            ConeDiagnostic diagnostic = ((FirDiagnosticHolder) calleeReference).getDiagnostic();
            if (!(diagnostic instanceof ConeAmbiguityError)) {
                if (diagnostic instanceof ConeUnresolvedNameError) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, reportSource, missingFactory, context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                } else if (!(diagnostic instanceof ConeInapplicableWrongReceiver)) {
                    if ((diagnostic instanceof ConeInapplicableCandidateError) && (unsafeCallFactory != null || noneApplicableFactory != null)) {
                        ConeInapplicableCandidateError coneInapplicableCandidateError = (ConeInapplicableCandidateError) diagnostic;
                        List<ResolutionDiagnostic> diagnostics = coneInapplicableCandidateError.getCandidate().getDiagnostics();
                        ArrayList arrayList = new ArrayList();
                        for (Object obj : diagnostics) {
                            if (((ResolutionDiagnostic) obj).getApplicability() == coneInapplicableCandidateError.getApplicability()) {
                                arrayList.add(obj);
                            }
                        }
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            if (((ResolutionDiagnostic) it.next()) instanceof UnsafeCall) {
                                if (unsafeCallFactory != null) {
                                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, reportSource, unsafeCallFactory, context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                                    break;
                                }
                                Intrinsics.checkNotNull(noneApplicableFactory);
                                KtDiagnosticReportHelpersKt.reportOn$default(reporter, reportSource, noneApplicableFactory, CollectionsKt.listOf(coneInapplicableCandidateError.getCandidate().getSymbol()), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                                break;
                            }
                        }
                    }
                } else if (noneApplicableFactory != null) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, reportSource, noneApplicableFactory, ((ConeInapplicableWrongReceiver) diagnostic).getCandidateSymbols(), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                } else if (Intrinsics.areEqual(calleeReference.getName(), OperatorNameConventions.ITERATOR)) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, reportSource, missingFactory, context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                } else {
                    throw new IllegalStateException("ConeInapplicableWrongReceiver, but no diagnostic reported".toString());
                }
            } else {
                ConeAmbiguityError coneAmbiguityError = (ConeAmbiguityError) diagnostic;
                if (CandidateApplicabilityKt.isSuccess(coneAmbiguityError.getApplicability())) {
                    KtSourceElement ktSourceElement = reportSource;
                    Collection<AbstractCandidate> candidates = coneAmbiguityError.getCandidates();
                    ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(candidates, 10));
                    Iterator<T> it2 = candidates.iterator();
                    while (it2.hasNext()) {
                        arrayList2.add(((AbstractCandidate) it2.next()).getSymbol());
                    }
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, ktSourceElement, ambiguityFactory, arrayList2, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                } else if (noneApplicableFactory != null) {
                    KtSourceElement ktSourceElement2 = reportSource;
                    Collection<AbstractCandidate> candidates2 = coneAmbiguityError.getCandidates();
                    ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(candidates2, 10));
                    Iterator<T> it3 = candidates2.iterator();
                    while (it3.hasNext()) {
                        arrayList3.add(((AbstractCandidate) it3.next()).getSymbol());
                    }
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, ktSourceElement2, noneApplicableFactory, arrayList3, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                }
            }
            return true;
        }
        if (!(calleeReference instanceof FirResolvedNamedReference)) {
            return false;
        }
        FirBasedSymbol<?> resolvedSymbol = ((FirResolvedNamedReference) calleeReference).getResolvedSymbol();
        if (!(resolvedSymbol instanceof FirNamedFunctionSymbol) || ((FirCallableSymbol) resolvedSymbol).getResolvedStatus().isOperator()) {
            return false;
        }
        KtDiagnosticFactory2<FirNamedFunctionSymbol, String> operator_modifier_required = FirErrors.INSTANCE.getOPERATOR_MODIFIER_REQUIRED();
        String strAsString = ((FirNamedFunctionSymbol) resolvedSymbol).getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, reportSource, operator_modifier_required, resolvedSymbol, strAsString, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
        return false;
    }
}
