package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScopeKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjectionKt;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeTypesKt;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;

/* JADX INFO: compiled from: FirDelegateUsesExtensionPropertyTypeParameterChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J>\u0010\f\u001a\u0004\u0018\u00010\r*\u00020\u000e2\u0016\u0010\u000f\u001a\u0012\u0012\u0004\u0012\u00020\r0\u0010j\b\u0012\u0004\u0012\u00020\r`\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002¨\u0006\u0014²\u0006\n\u0010\u0015\u001a\u00020\u0016X\u008a\u0084\u0002"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDelegateUsesExtensionPropertyTypeParameterChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirPropertyChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "findUsedTypeParameterSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "typeParameterSymbols", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "checkers", "delegateClassScope", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDelegateUsesExtensionPropertyTypeParameterChecker extends FirDeclarationChecker<FirProperty> {
    public static final FirDelegateUsesExtensionPropertyTypeParameterChecker INSTANCE = new FirDelegateUsesExtensionPropertyTypeParameterChecker();

    private FirDelegateUsesExtensionPropertyTypeParameterChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirProperty declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirExpression delegate = declaration.getDelegate();
        FirFunctionCall firFunctionCall = delegate instanceof FirFunctionCall ? (FirFunctionCall) delegate : null;
        if (firFunctionCall == null) {
            return;
        }
        List<FirTypeParameter> typeParameters = declaration.getTypeParameters();
        HashSet hashSet = new HashSet();
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            hashSet.add(((FirTypeParameter) it.next()).getSymbol());
        }
        FirTypeParameterSymbol firTypeParameterSymbolFindUsedTypeParameterSymbol = findUsedTypeParameterSymbol(FirTypeUtilsKt.getConeType(firFunctionCall.getTypeRef()), hashSet, firFunctionCall, context, reporter);
        if (firTypeParameterSymbolFindUsedTypeParameterSymbol == null) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getDELEGATE_USES_EXTENSION_PROPERTY_TYPE_PARAMETER(), firTypeParameterSymbolFindUsedTypeParameterSymbol, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
    }

    private final FirTypeParameterSymbol findUsedTypeParameterSymbol(ConeKotlinType coneKotlinType, HashSet<FirTypeParameterSymbol> hashSet, FirFunctionCall firFunctionCall, final CheckerContext checkerContext, DiagnosticReporter diagnosticReporter) {
        ConeSimpleKotlinType coneSimpleKotlinTypeUnwrapDefinitelyNotNull = ConeTypesKt.unwrapDefinitelyNotNull(TypeExpansionUtilsKt.fullyExpandedType(ConeTypeUtilsKt.lowerBoundIfFlexible(FirTypeUtilsKt.getConeType(firFunctionCall.getTypeRef())), checkerContext.getSession()));
        ConeClassLikeType coneClassLikeType = coneSimpleKotlinTypeUnwrapDefinitelyNotNull instanceof ConeClassLikeType ? (ConeClassLikeType) coneSimpleKotlinTypeUnwrapDefinitelyNotNull : null;
        if (coneClassLikeType == null) {
            return null;
        }
        FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(coneClassLikeType.getLookupTag(), checkerContext.getSession());
        final FirRegularClassSymbol firRegularClassSymbol = symbol instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) symbol : null;
        if (firRegularClassSymbol == null) {
            return null;
        }
        Lazy lazy = LazyKt.lazy(new Function0<FirTypeScope>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDelegateUsesExtensionPropertyTypeParameterChecker$findUsedTypeParameterSymbol$delegateClassScope$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final FirTypeScope invoke() {
                return FirHelpersKt.unsubstitutedScope(firRegularClassSymbol, checkerContext);
            }
        });
        for (ConeTypeProjection coneTypeProjection : coneKotlinType.getTypeArguments()) {
            ConeKotlinType type = ConeTypeProjectionKt.getType(coneTypeProjection);
            if (type != null) {
                FirClassifierSymbol<?> symbol2 = TypeUtilsKt.toSymbol(type, checkerContext.getSession());
                FirTypeParameterSymbol firTypeParameterSymbol = symbol2 instanceof FirTypeParameterSymbol ? (FirTypeParameterSymbol) symbol2 : null;
                if (CollectionsKt.contains(hashSet, firTypeParameterSymbol)) {
                    final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
                    FirContainingNamesAwareScopeKt.processAllProperties(findUsedTypeParameterSymbol$lambda$1(lazy), new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDelegateUsesExtensionPropertyTypeParameterChecker.findUsedTypeParameterSymbol.1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                            invoke2(firVariableSymbol);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(FirVariableSymbol<?> symbol3) {
                            Intrinsics.checkNotNullParameter(symbol3, "symbol");
                            if (ConeTypeUtilsKt.contains(symbol3.getResolvedReturnType(), new Function1<ConeKotlinType, Boolean>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDelegateUsesExtensionPropertyTypeParameterChecker.findUsedTypeParameterSymbol.1.1
                                @Override // kotlin.jvm.functions.Function1
                                public final Boolean invoke(ConeKotlinType it) {
                                    Intrinsics.checkNotNullParameter(it, "it");
                                    return Boolean.valueOf(it instanceof ConeTypeParameterType);
                                }
                            })) {
                                booleanRef.element = true;
                            }
                        }
                    });
                    if (booleanRef.element) {
                        return firTypeParameterSymbol;
                    }
                }
                FirTypeParameterSymbol firTypeParameterSymbolFindUsedTypeParameterSymbol = findUsedTypeParameterSymbol(type, hashSet, firFunctionCall, checkerContext, diagnosticReporter);
                if (firTypeParameterSymbolFindUsedTypeParameterSymbol != null) {
                    return firTypeParameterSymbolFindUsedTypeParameterSymbol;
                }
            }
        }
        return null;
    }

    private static final FirTypeScope findUsedTypeParameterSymbol$lambda$1(Lazy<? extends FirTypeScope> lazy) {
        return lazy.getValue();
    }
}
