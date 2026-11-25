package org.jetbrains.kotlin.fir.analysis.jvm.checkers.expression;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.jvm.FirJvmErrors;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCallOrigin;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: FirJvmInconsistentOperatorFromJavaCallChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J&\u0010\u0007\u001a\u00020\u000e*\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/jvm/checkers/expression/FirJvmInconsistentOperatorFromJavaCallChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirFunctionCallChecker;", "()V", "CONCURRENT_HASH_MAP_CALLABLE_ID", "Lorg/jetbrains/kotlin/name/CallableId;", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "checkers.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmInconsistentOperatorFromJavaCallChecker extends FirExpressionChecker<FirFunctionCall> {
    private static final CallableId CONCURRENT_HASH_MAP_CALLABLE_ID;
    public static final FirJvmInconsistentOperatorFromJavaCallChecker INSTANCE = new FirJvmInconsistentOperatorFromJavaCallChecker();

    private FirJvmInconsistentOperatorFromJavaCallChecker() {
    }

    static {
        ClassId classIdFromString = ClassId.fromString("java/util/concurrent/ConcurrentHashMap");
        Intrinsics.checkNotNullExpressionValue(classIdFromString, "fromString(...)");
        CONCURRENT_HASH_MAP_CALLABLE_ID = new CallableId(classIdFromString, OperatorNameConventions.CONTAINS);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirFunctionCall expression, CheckerContext context, DiagnosticReporter reporter) {
        FirValueParameterSymbol firValueParameterSymbol;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (expression.getOrigin() != FirFunctionCallOrigin.Operator) {
            return;
        }
        FirCallableSymbol resolvedCallableSymbol$default = FirReferenceUtilsKt.toResolvedCallableSymbol$default(expression.getCalleeReference(), false, 1, null);
        FirNamedFunctionSymbol firNamedFunctionSymbol = resolvedCallableSymbol$default instanceof FirNamedFunctionSymbol ? (FirNamedFunctionSymbol) resolvedCallableSymbol$default : null;
        if (firNamedFunctionSymbol == null || !Intrinsics.areEqual(firNamedFunctionSymbol.getName(), OperatorNameConventions.CONTAINS) || (firValueParameterSymbol = (FirValueParameterSymbol) CollectionsKt.singleOrNull((List) firNamedFunctionSymbol.getValueParameterSymbols())) == null) {
            return;
        }
        ConeSimpleKotlinType coneSimpleKotlinTypeLowerBoundIfFlexible = ConeTypeUtilsKt.lowerBoundIfFlexible(FirTypeUtilsKt.getConeType(firValueParameterSymbol.getResolvedReturnTypeRef()));
        if (ConeBuiltinTypeUtilsKt.isAny(coneSimpleKotlinTypeLowerBoundIfFlexible) || ConeBuiltinTypeUtilsKt.isNullableAny(coneSimpleKotlinTypeLowerBoundIfFlexible)) {
            check(firNamedFunctionSymbol, expression.getCalleeReference().getSource(), context, reporter);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean check(FirNamedFunctionSymbol firNamedFunctionSymbol, KtSourceElement ktSourceElement, CheckerContext checkerContext, DiagnosticReporter diagnosticReporter) {
        FirRegularClassSymbol firRegularClassSymbol;
        FirNamedFunctionSymbol firNamedFunctionSymbol2 = firNamedFunctionSymbol;
        FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) firNamedFunctionSymbol2.getFir();
        while (ClassMembersKt.isSubstitutionOrIntersectionOverride(firCallableDeclaration)) {
            FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
            if (originalForSubstitutionOverrideAttr == null) {
                originalForSubstitutionOverrideAttr = ClassMembersKt.isIntersectionOverride(firCallableDeclaration) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firCallableDeclaration) : null;
            }
            if (originalForSubstitutionOverrideAttr == null) {
                break;
            }
            firCallableDeclaration = originalForSubstitutionOverrideAttr;
        }
        FirCallableSymbol<? extends FirCallableDeclaration> symbol = firCallableDeclaration.getSymbol();
        if (symbol != null) {
            if (Intrinsics.areEqual(((FirNamedFunctionSymbol) symbol).getCallableId(), CONCURRENT_HASH_MAP_CALLABLE_ID)) {
                KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, ktSourceElement, FirJvmErrors.INSTANCE.getCONCURRENT_HASH_MAP_CONTAINS_OPERATOR(), checkerContext, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return true;
            }
            ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(firNamedFunctionSymbol2);
            if (coneClassLikeLookupTagContainingClassLookupTag != null && (firRegularClassSymbol = LookupTagUtilsKt.toFirRegularClassSymbol(coneClassLikeLookupTagContainingClassLookupTag, checkerContext.getSession())) != null) {
                for (FirFunctionSymbol<?> firFunctionSymbol : FirHelpersKt.overriddenFunctions(firNamedFunctionSymbol, firRegularClassSymbol, checkerContext)) {
                    if ((firFunctionSymbol instanceof FirNamedFunctionSymbol) && check((FirNamedFunctionSymbol) firFunctionSymbol, ktSourceElement, checkerContext, diagnosticReporter)) {
                        return true;
                    }
                }
            }
            return false;
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol");
    }
}
