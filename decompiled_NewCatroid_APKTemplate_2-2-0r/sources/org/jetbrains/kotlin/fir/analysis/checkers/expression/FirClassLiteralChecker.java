package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.FirSourceUtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirGetClassCall;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.FirResolvedReifiedParameterReference;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirNestedClassifierScopeKt;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeStarProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.lexer.KtSingleValueToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.resolve.checkers.OptInNames;

/* JADX INFO: compiled from: FirClassLiteralChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0014\u0010\u0015\u001a\u00020\u0006*\u00020\u00162\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0014\u0010\u0017\u001a\u00020\u0006*\u00020\u00162\u0006\u0010\u0011\u001a\u00020\u0018H\u0002R\u0018\u0010\u0005\u001a\u00020\u0006*\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u0004\u0018\u00010\u000b*\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirClassLiteralChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirGetClassCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirGetClassCallChecker;", "()V", "canBeDoubleColonLHSAsType", "", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getCanBeDoubleColonLHSAsType", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)Z", "safeAsTypeParameterSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "getSafeAsTypeParameterSymbol", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "isAllowedInClassLiteral", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "isNullableTypeParameter", "Lorg/jetbrains/kotlin/fir/types/ConeInferenceContext;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirClassLiteralChecker extends FirExpressionChecker<FirGetClassCall> {
    public static final FirClassLiteralChecker INSTANCE = new FirClassLiteralChecker();

    private FirClassLiteralChecker() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirGetClassCall expression, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        KtSourceElement source = expression.getSource();
        if (source == null || (source.getKind() instanceof KtFakeSourceElementKind)) {
            return;
        }
        FirExpression argument = expression.getArgument();
        boolean z = argument instanceof FirResolvedQualifier;
        if (z) {
            ClassId classId = ((FirResolvedQualifier) argument).getClassId();
            if (Intrinsics.areEqual(classId, OptInNames.INSTANCE.getREQUIRES_OPT_IN_CLASS_ID()) || Intrinsics.areEqual(classId, OptInNames.INSTANCE.getOPT_IN_CLASS_ID())) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, argument.getSource(), FirErrors.INSTANCE.getOPT_IN_CAN_ONLY_BE_USED_AS_ANNOTATION(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
        KtSingleValueToken QUEST = KtTokens.QUEST;
        Intrinsics.checkNotNullExpressionValue(QUEST, "QUEST");
        if (FirSourceUtilsKt.getChild$default(source, (IElementType) QUEST, 0, 1, false, 10, (Object) null) == null) {
            FirResolvedQualifier firResolvedQualifier = z ? (FirResolvedQualifier) argument : null;
            if ((firResolvedQualifier == null || !firResolvedQualifier.getIsNullableLHSForCallableReference()) && !ConeTypeUtilsKt.isMarkedNullable(FirTypeUtilsKt.getConeType(argument.getTypeRef())) && !isNullableTypeParameter(FirTypeUtilsKt.getConeType(argument.getTypeRef()), TypeComponentsKt.getTypeContext(context.getSession()))) {
                FirTypeParameterSymbol safeAsTypeParameterSymbol = getSafeAsTypeParameterSymbol(argument);
                if (safeAsTypeParameterSymbol != null && !safeAsTypeParameterSymbol.isReified()) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getTYPE_PARAMETER_AS_REIFIED(), safeAsTypeParameterSymbol, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                }
                if (z) {
                    FirResolvedQualifier firResolvedQualifier2 = (FirResolvedQualifier) argument;
                    if (firResolvedQualifier2.getTypeArguments().isEmpty() || isAllowedInClassLiteral(TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(argument.getTypeRef()), context.getSession()), context)) {
                        return;
                    }
                    FirClassLikeSymbol<?> symbol = firResolvedQualifier2.getSymbol();
                    if (symbol != null) {
                        FirLazyDeclarationResolverKt.lazyResolveToPhase(symbol, FirResolvePhase.TYPES);
                    }
                    FirClassLikeDeclaration firClassLikeDeclaration = symbol != null ? (FirClassLikeDeclaration) symbol.getFir() : null;
                    FirClassLikeDeclaration firClassLikeDeclaration2 = firClassLikeDeclaration instanceof FirTypeParameterRefsOwner ? firClassLikeDeclaration : null;
                    List<FirTypeParameterRef> typeParameters = firClassLikeDeclaration2 != null ? firClassLikeDeclaration2.getTypeParameters() : null;
                    int i = 0;
                    if (typeParameters != null) {
                        List<FirTypeParameterRef> list = typeParameters;
                        if (!(list instanceof Collection) || !list.isEmpty()) {
                            Iterator<T> it = list.iterator();
                            while (it.hasNext()) {
                                if ((((FirTypeParameterRef) it.next()) instanceof FirTypeParameter) && (i = i + 1) < 0) {
                                    CollectionsKt.throwCountOverflow();
                                }
                            }
                        }
                    }
                    if (i == firResolvedQualifier2.getTypeArguments().size()) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getCLASS_LITERAL_LHS_NOT_A_CLASS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                        return;
                    } else {
                        if (symbol != null) {
                            KtDiagnosticReportHelpersKt.reportOn$default(reporter, argument.getSource(), FirErrors.INSTANCE.getWRONG_NUMBER_OF_TYPE_ARGUMENTS(), Integer.valueOf(i), symbol, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                            return;
                        }
                        return;
                    }
                }
                return;
            }
        }
        if (getCanBeDoubleColonLHSAsType(argument)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getNULLABLE_TYPE_IN_CLASS_LITERAL_LHS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        } else {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, argument.getSource(), FirErrors.INSTANCE.getEXPRESSION_OF_NULLABLE_TYPE_IN_CLASS_LITERAL_LHS(), FirTypeUtilsKt.getConeType(argument.getTypeRef()), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        }
    }

    private final boolean isNullableTypeParameter(ConeKotlinType coneKotlinType, ConeInferenceContext coneInferenceContext) {
        if (!(coneKotlinType instanceof ConeTypeParameterType)) {
            return false;
        }
        FirTypeParameterSymbol typeParameterSymbol = ((ConeTypeParameterType) coneKotlinType).getLookupTag().getTypeParameterSymbol();
        return !typeParameterSymbol.isReified() && coneInferenceContext.isNullableType(FirNestedClassifierScopeKt.toConeType(typeParameterSymbol));
    }

    private final boolean getCanBeDoubleColonLHSAsType(FirExpression firExpression) {
        return (firExpression instanceof FirResolvedQualifier) || (firExpression instanceof FirResolvedReifiedParameterReference) || getSafeAsTypeParameterSymbol(firExpression) != null;
    }

    private final FirTypeParameterSymbol getSafeAsTypeParameterSymbol(FirExpression firExpression) {
        FirReference calleeReference;
        FirQualifiedAccessExpression firQualifiedAccessExpression = firExpression instanceof FirQualifiedAccessExpression ? (FirQualifiedAccessExpression) firExpression : null;
        if (firQualifiedAccessExpression == null || (calleeReference = firQualifiedAccessExpression.getCalleeReference()) == null) {
            return null;
        }
        return FirReferenceUtilsKt.toResolvedTypeParameterSymbol$default(calleeReference, false, 1, null);
    }

    private final boolean isAllowedInClassLiteral(ConeKotlinType coneKotlinType, CheckerContext checkerContext) {
        if (coneKotlinType instanceof ConeClassLikeType) {
            if (ConeBuiltinTypeUtilsKt.isNonPrimitiveArray(coneKotlinType)) {
                for (ConeTypeProjection coneTypeProjection : coneKotlinType.getTypeArguments()) {
                    if (coneTypeProjection instanceof ConeStarProjection) {
                        return false;
                    }
                    if (!(coneTypeProjection instanceof ConeKotlinTypeProjection)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    if (!INSTANCE.isAllowedInClassLiteral(((ConeKotlinTypeProjection) coneTypeProjection).getType(), checkerContext)) {
                        return false;
                    }
                }
            } else if (coneKotlinType.getTypeArguments().length != 0) {
                return false;
            }
            return true;
        }
        if (coneKotlinType instanceof ConeTypeParameterType) {
            return ((ConeTypeParameterType) coneKotlinType).getLookupTag().getTypeParameterSymbol().isReified();
        }
        return false;
    }
}
