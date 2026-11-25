package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.fir.analysis.checkers.FirTypeRefSource;
import org.jetbrains.kotlin.fir.analysis.checkers.FirUpperBoundViolatedHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.references.FirResolvedErrorReference;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.resolve.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeInapplicableWrongReceiver;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeProjectionWithVariance;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;

/* JADX INFO: compiled from: FirUpperBoundViolatedExpressionChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0014\u0010\f\u001a\u00020\r*\u00020\r2\u0006\u0010\u0007\u001a\u00020\u0002H\u0002¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirUpperBoundViolatedExpressionChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirQualifiedAccessExpressionChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "withSourceRecursive", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirUpperBoundViolatedExpressionChecker extends FirExpressionChecker<FirQualifiedAccessExpression> {
    public static final FirUpperBoundViolatedExpressionChecker INSTANCE = new FirUpperBoundViolatedExpressionChecker();

    private FirUpperBoundViolatedExpressionChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirQualifiedAccessExpression expression, CheckerContext context, DiagnosticReporter reporter) {
        FirCallableSymbol resolvedCallableSymbol$default;
        ArrayList typeArgumentsWithSourceInfo;
        List<FirTypeParameterSymbol> typeParameterSymbols;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirReference calleeReference = expression.getCalleeReference();
        if (calleeReference instanceof FirResolvedErrorReference) {
            if (((FirResolvedErrorReference) calleeReference).getDiagnostic() instanceof ConeInapplicableWrongReceiver) {
                return;
            } else {
                resolvedCallableSymbol$default = FirReferenceUtilsKt.toResolvedCallableSymbol$default(calleeReference, false, 1, null);
            }
        } else {
            resolvedCallableSymbol$default = calleeReference instanceof FirResolvedNamedReference ? FirReferenceUtilsKt.toResolvedCallableSymbol$default(calleeReference, false, 1, null) : null;
        }
        if ((resolvedCallableSymbol$default instanceof FirConstructorSymbol) && DeclarationUtilsKt.isTypeAliasedConstructor((FirConstructorSymbol) resolvedCallableSymbol$default)) {
            ConeKotlinType coneKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(expression.getTypeRef()), context.getSession());
            ConeTypeProjection[] typeArguments = coneKotlinTypeFullyExpandedType.getTypeArguments();
            ArrayList arrayList = new ArrayList(typeArguments.length);
            for (ConeTypeProjection coneTypeProjection : typeArguments) {
                arrayList.add(INSTANCE.withSourceRecursive(coneTypeProjection, expression));
            }
            typeArgumentsWithSourceInfo = arrayList;
            FirClassifierSymbol<?> symbol = TypeUtilsKt.toSymbol(coneKotlinTypeFullyExpandedType, context.getSession());
            FirRegularClassSymbol firRegularClassSymbol = symbol instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) symbol : null;
            if (firRegularClassSymbol == null || (typeParameterSymbols = firRegularClassSymbol.getTypeParameterSymbols()) == null) {
                return;
            }
        } else {
            typeArgumentsWithSourceInfo = FirUpperBoundViolatedHelpersKt.toTypeArgumentsWithSourceInfo(expression.getTypeArguments());
            if (resolvedCallableSymbol$default == null || (typeParameterSymbols = resolvedCallableSymbol$default.getTypeParameterSymbols()) == null) {
                return;
            }
        }
        List<FirTypeParameterSymbol> list = typeParameterSymbols;
        List<ConeTypeProjection> list2 = typeArgumentsWithSourceInfo;
        List<ConeTypeProjection> list3 = list2;
        if (!(list3 instanceof Collection) || !list3.isEmpty()) {
            Iterator<T> it = list3.iterator();
            while (it.hasNext()) {
                if (!(((ConeTypeProjection) it.next()) instanceof ConeKotlinType)) {
                    return;
                }
            }
        }
        if (list2.size() != list.size()) {
            return;
        }
        FirUpperBoundViolatedHelpersKt.checkUpperBoundViolated(context, reporter, list, list2, FirUpperBoundViolatedHelpersKt.createSubstitutorForUpperBoundViolationCheck(list, list2, context.getSession()), (96 & 32) != 0 ? false : false, (96 & 64) != 0 ? false : false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:10:0x0024  */
    public final ConeTypeProjection withSourceRecursive(ConeTypeProjection coneTypeProjection, final FirQualifiedAccessExpression firQualifiedAccessExpression) {
        ConeKotlinType coneKotlinTypeWithArguments;
        Object next;
        if (coneTypeProjection instanceof ConeClassLikeType) {
            if (((ConeClassLikeType) coneTypeProjection).getTypeArguments().length == 0) {
                coneKotlinTypeWithArguments = coneTypeProjection;
            } else {
                coneKotlinTypeWithArguments = TypeUtilsKt.withArguments((ConeKotlinType) coneTypeProjection, new Function1<ConeTypeProjection, ConeTypeProjection>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.expression.FirUpperBoundViolatedExpressionChecker$withSourceRecursive$type$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final ConeTypeProjection invoke(ConeTypeProjection it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return FirUpperBoundViolatedExpressionChecker.INSTANCE.withSourceRecursive(it, firQualifiedAccessExpression);
                    }
                });
            }
        } else {
            coneKotlinTypeWithArguments = coneTypeProjection;
        }
        Iterator<T> it = firQualifiedAccessExpression.getTypeArguments().iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (FirTypeUtilsKt.toConeTypeProjection((FirTypeProjection) next) != coneTypeProjection);
        FirTypeProjection firTypeProjection = (FirTypeProjection) next;
        if (firTypeProjection == null) {
            return FirUpperBoundViolatedHelpersKt.withSource(coneKotlinTypeWithArguments, new FirTypeRefSource(null, firQualifiedAccessExpression.getSource()));
        }
        FirTypeProjectionWithVariance firTypeProjectionWithVariance = firTypeProjection instanceof FirTypeProjectionWithVariance ? (FirTypeProjectionWithVariance) firTypeProjection : null;
        return FirUpperBoundViolatedHelpersKt.withSource(coneKotlinTypeWithArguments, new FirTypeRefSource(firTypeProjectionWithVariance != null ? firTypeProjectionWithVariance.getTypeRef() : null, firTypeProjection.getSource()));
    }
}
