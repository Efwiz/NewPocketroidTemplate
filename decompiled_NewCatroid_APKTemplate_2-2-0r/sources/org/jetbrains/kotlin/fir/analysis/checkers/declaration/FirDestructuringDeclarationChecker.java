package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticContext;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory3;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.ConeDiagnosticToFirDiagnosticKt;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeSyntaxDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.FirDiagnosticHolder;
import org.jetbrains.kotlin.fir.expressions.FirComponentCall;
import org.jetbrains.kotlin.fir.expressions.FirErrorExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression;
import org.jetbrains.kotlin.fir.expressions.FirWrappedExpression;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.resolve.calls.AbstractCandidate;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeAmbiguityError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeConstraintSystemHasContradiction;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeInapplicableCandidateError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeInapplicableWrongReceiver;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnresolvedNameError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeVisibilityError;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeCheckerProviderContext;

/* JADX INFO: compiled from: FirDestructuringDeclarationChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016JH\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u00022\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J*\u0010#\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u001a2\b\u0010$\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0014\u001a\u00020\u0015H\u0002R\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u0004\u0018\u00010\u000b*\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0018\u0010\u000e\u001a\u00020\u0007*\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDestructuringDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirPropertyChecker;", "()V", "explicitReceiverOfQualifiedAccess", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getExplicitReceiverOfQualifiedAccess", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "resolvedVariable", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "getResolvedVariable", "(Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;)Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "unwrapped", "getUnwrapped", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkComponentCall", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "destructuringDeclarationType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "diagnostic", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "property", "componentCall", "Lorg/jetbrains/kotlin/fir/expressions/FirComponentCall;", "destructuringDeclaration", "checkInitializer", "initializer", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDestructuringDeclarationChecker extends FirDeclarationChecker<FirProperty> {
    public static final FirDestructuringDeclarationChecker INSTANCE = new FirDestructuringDeclarationChecker();

    private FirDestructuringDeclarationChecker() {
    }

    /* JADX WARN: Code duplicated, block: B:71:0x00e3 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:72:0x00e4  */
    /* JADX WARN: Code duplicated, block: B:74:0x00f1  */
    /* JADX WARN: Code duplicated, block: B:77:0x00fa  */
    /* JADX WARN: Code duplicated, block: B:79:? A[RETURN, SYNTHETIC] */
    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirProperty declaration, CheckerContext context, DiagnosticReporter reporter) {
        FirQualifiedAccessExpression explicitReceiverOfQualifiedAccess;
        FirVariable resolvedVariable;
        FirVariable firVariable;
        KtSourceElement source;
        ConeKotlinType coneKotlinType;
        ConeKotlinType coneType;
        ConeDiagnostic diagnostic;
        FirQualifiedAccessExpression initializer;
        FirExpression initializer2;
        KtSourceElement source2;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        KtSourceElement source3 = declaration.getSource();
        if (source3 == null) {
            return;
        }
        if (Intrinsics.areEqual(source3.getElementType(), KtNodeTypes.DESTRUCTURING_DECLARATION)) {
            checkInitializer(source3, declaration.getInitializer(), reporter, context);
            return;
        }
        if (Intrinsics.areEqual(source3.getElementType(), KtNodeTypes.DESTRUCTURING_DECLARATION_ENTRY)) {
            FirExpression initializer3 = declaration.getInitializer();
            FirComponentCall firComponentCall = initializer3 instanceof FirComponentCall ? (FirComponentCall) initializer3 : null;
            if (firComponentCall == null || (explicitReceiverOfQualifiedAccess = getExplicitReceiverOfQualifiedAccess(firComponentCall)) == null || (resolvedVariable = getResolvedVariable(explicitReceiverOfQualifiedAccess)) == null) {
                return;
            }
            if (resolvedVariable instanceof FirProperty) {
                FirExpression initializer4 = resolvedVariable.getInitializer();
                if (Intrinsics.areEqual((initializer4 == null || (source2 = initializer4.getSource()) == null) ? null : source2.getElementType(), KtNodeTypes.FOR)) {
                    FirExpression initializer5 = resolvedVariable.getInitializer();
                    FirQualifiedAccessExpression explicitReceiverOfQualifiedAccess2 = initializer5 != null ? getExplicitReceiverOfQualifiedAccess(initializer5) : null;
                    FirVariable resolvedVariable2 = explicitReceiverOfQualifiedAccess2 != null ? getResolvedVariable(explicitReceiverOfQualifiedAccess2) : null;
                    FirProperty firProperty = resolvedVariable2 instanceof FirProperty ? (FirProperty) resolvedVariable2 : null;
                    initializer = (firProperty == null || (initializer2 = firProperty.getInitializer()) == null) ? null : getExplicitReceiverOfQualifiedAccess(initializer2);
                } else {
                    initializer = resolvedVariable.getInitializer();
                }
                firVariable = initializer;
            } else {
                firVariable = resolvedVariable instanceof FirValueParameter ? resolvedVariable : null;
            }
            if (firVariable == null || (source = firVariable.getSource()) == null) {
                return;
            }
            if (!(firVariable instanceof FirVariable)) {
                if (firVariable instanceof FirExpression) {
                    coneType = FirTypeUtilsKt.getConeType(((FirExpression) firVariable).getTypeRef());
                } else {
                    coneKotlinType = null;
                }
                if (coneKotlinType == null) {
                    return;
                }
                Object calleeReference = firComponentCall.getCalleeReference();
                diagnostic = FirReferenceUtilsKt.isError((FirReference) calleeReference) ? ((FirDiagnosticHolder) calleeReference).getDiagnostic() : null;
                if (diagnostic != null) {
                    checkComponentCall(source, coneKotlinType, diagnostic, declaration, firComponentCall, resolvedVariable, reporter, context);
                }
            }
            coneType = FirTypeUtilsKt.getConeType(((FirVariable) firVariable).getReturnTypeRef());
            coneKotlinType = coneType;
            if (coneKotlinType == null) {
                return;
            }
            Object calleeReference2 = firComponentCall.getCalleeReference();
            diagnostic = FirReferenceUtilsKt.isError((FirReference) calleeReference2) ? ((FirDiagnosticHolder) calleeReference2).getDiagnostic() : null;
            if (diagnostic != null) {
                checkComponentCall(source, coneKotlinType, diagnostic, declaration, firComponentCall, resolvedVariable, reporter, context);
            }
        }
    }

    private final void checkInitializer(KtSourceElement source, FirExpression initializer, DiagnosticReporter reporter, CheckerContext context) {
        boolean z;
        if (initializer == null) {
            z = true;
        } else {
            z = initializer instanceof FirErrorExpression ? ((FirErrorExpression) initializer).getDiagnostic() instanceof ConeSyntaxDiagnostic : false;
        }
        if (z) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getINITIALIZER_REQUIRED_FOR_DESTRUCTURING_DECLARATION(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }

    private final void checkComponentCall(KtSourceElement source, ConeKotlinType destructuringDeclarationType, ConeDiagnostic diagnostic, FirProperty property, FirComponentCall componentCall, FirVariable destructuringDeclaration, DiagnosticReporter reporter, CheckerContext context) {
        if (diagnostic instanceof ConeUnresolvedNameError) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getCOMPONENT_FUNCTION_MISSING(), ((ConeUnresolvedNameError) diagnostic).getName(), destructuringDeclarationType, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
            return;
        }
        if (diagnostic instanceof ConeInapplicableWrongReceiver) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getCOMPONENT_FUNCTION_MISSING(), ((AbstractCandidate) CollectionsKt.first(((ConeInapplicableWrongReceiver) diagnostic).getCandidates())).getCallInfo().getName(), destructuringDeclarationType, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
            return;
        }
        if (diagnostic instanceof ConeAmbiguityError) {
            KtSourceElement ktSourceElement = source;
            KtDiagnosticFactory2<Name, Collection<FirBasedSymbol<?>>> component_function_ambiguity = FirErrors.INSTANCE.getCOMPONENT_FUNCTION_AMBIGUITY();
            ConeAmbiguityError coneAmbiguityError = (ConeAmbiguityError) diagnostic;
            Name name2 = coneAmbiguityError.getName();
            Collection<AbstractCandidate> candidates = coneAmbiguityError.getCandidates();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(candidates, 10));
            Iterator<T> it = candidates.iterator();
            while (it.hasNext()) {
                arrayList.add(((AbstractCandidate) it.next()).getSymbol());
            }
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, ktSourceElement, component_function_ambiguity, name2, arrayList, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
            return;
        }
        if (diagnostic instanceof ConeInapplicableCandidateError) {
            if (ConeTypeUtilsKt.isNullable(destructuringDeclarationType)) {
                KtDiagnosticFactory1<Name> component_function_on_nullable = FirErrors.INSTANCE.getCOMPONENT_FUNCTION_ON_NULLABLE();
                FirBasedSymbol<?> symbol = ((ConeInapplicableCandidateError) diagnostic).getCandidate().getSymbol();
                Intrinsics.checkNotNull(symbol, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol");
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, component_function_on_nullable, ((FirNamedFunctionSymbol) symbol).getCallableId().getCallableName(), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                return;
            }
            return;
        }
        if (diagnostic instanceof ConeConstraintSystemHasContradiction) {
            ConeKotlinType coneType = FirTypeUtilsKt.getConeType(componentCall.getTypeRef());
            if (coneType instanceof ConeErrorType) {
                return;
            }
            ConeKotlinType coneType2 = FirTypeUtilsKt.getConeType(property.getReturnTypeRef());
            if (AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, (TypeCheckerProviderContext) TypeComponentsKt.getTypeContext(context.getSession()), (KotlinTypeMarker) coneType, (KotlinTypeMarker) coneType2, false, 8, (Object) null)) {
                return;
            }
            KtDiagnosticReportHelpersKt.reportOn(reporter, (AbstractKtSourceElement) (destructuringDeclaration instanceof FirValueParameter ? property.getSource() : source), (KtDiagnosticFactory3<Name, ConeKotlinType, ConeKotlinType>) ((KtDiagnosticFactory3<Object, Object, Object>) FirErrors.INSTANCE.getCOMPONENT_FUNCTION_RETURN_TYPE_MISMATCH()), ((ConeConstraintSystemHasContradiction) diagnostic).getCandidate().getCallInfo().getName(), coneType, coneType2, (DiagnosticContext) context, (64 & 64) != 0 ? null : null);
            return;
        }
        if (diagnostic instanceof ConeVisibilityError) {
            reporter.report(ConeDiagnosticToFirDiagnosticKt.toInvisibleReferenceDiagnostic(((ConeVisibilityError) diagnostic).getSymbol(), property.getSource()), context);
            return;
        }
        throw new IllegalStateException("Unhandled error during a component function call".toString());
    }

    private final FirQualifiedAccessExpression getExplicitReceiverOfQualifiedAccess(FirExpression firExpression) {
        FirExpression explicitReceiver;
        FirQualifiedAccessExpression firQualifiedAccessExpression = firExpression instanceof FirQualifiedAccessExpression ? (FirQualifiedAccessExpression) firExpression : null;
        FirExpression unwrapped = (firQualifiedAccessExpression == null || (explicitReceiver = firQualifiedAccessExpression.getExplicitReceiver()) == null) ? null : getUnwrapped(explicitReceiver);
        if (unwrapped instanceof FirQualifiedAccessExpression) {
            return (FirQualifiedAccessExpression) unwrapped;
        }
        return null;
    }

    private final FirExpression getUnwrapped(FirExpression firExpression) {
        if (firExpression instanceof FirSmartCastExpression) {
            return ((FirSmartCastExpression) firExpression).getOriginalExpression();
        }
        return firExpression instanceof FirWrappedExpression ? ((FirWrappedExpression) firExpression).getExpression() : firExpression;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final FirVariable getResolvedVariable(FirQualifiedAccessExpression firQualifiedAccessExpression) {
        FirVariableSymbol resolvedVariableSymbol$default = FirReferenceUtilsKt.toResolvedVariableSymbol$default(firQualifiedAccessExpression.getCalleeReference(), false, 1, null);
        if (resolvedVariableSymbol$default == null) {
            return null;
        }
        FirLazyDeclarationResolverKt.lazyResolveToPhase(resolvedVariableSymbol$default, FirResolvePhase.BODY_RESOLVE);
        return (FirVariable) resolvedVariableSymbol$default.getFir();
    }
}
