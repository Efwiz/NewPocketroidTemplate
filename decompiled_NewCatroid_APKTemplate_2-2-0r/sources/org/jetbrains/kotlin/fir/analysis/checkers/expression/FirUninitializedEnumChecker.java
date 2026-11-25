package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousInitializer;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousFunctionExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirLambdaArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.ConeClassLookupTagWithFixedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirAnonymousInitializerSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirAnonymousObjectSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirEnumEntrySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;

/* JADX INFO: compiled from: FirUninitializedEnumChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0014\u0010\u0011\u001a\u00020\u0012*\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002R\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirUninitializedEnumChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirQualifiedAccessExpressionChecker;", "()V", "lazyDelegation", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "getLazyDelegation", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;)Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "isEnumEntryInitializer", "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirUninitializedEnumChecker extends FirExpressionChecker<FirQualifiedAccessExpression> {
    public static final FirUninitializedEnumChecker INSTANCE = new FirUninitializedEnumChecker();

    private FirUninitializedEnumChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirQualifiedAccessExpression expression, CheckerContext context, DiagnosticReporter reporter) {
        FirBasedSymbol resolvedBaseSymbol$default;
        FirDeclaration firDeclarationPrevious;
        FirBasedSymbol<? extends FirDeclaration> symbol;
        FirDeclaration firDeclaration;
        boolean zContains;
        FirAnonymousFunction lazyDelegation;
        FirDeclaration firDeclaration2;
        ArrayList arrayListEmptyList;
        List<FirBasedSymbol<?>> declarationSymbols;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        KtSourceElement source = expression.getSource();
        if (source == null || (source.getKind() instanceof KtFakeSourceElementKind) || (resolvedBaseSymbol$default = FirReferenceUtilsKt.toResolvedBaseSymbol$default(expression.getCalleeReference(), false, 1, null)) == null) {
            return;
        }
        FirClassLikeSymbol<?> containingClassSymbol = FirHelpersKt.getContainingClassSymbol((FirBasedSymbol<?>) resolvedBaseSymbol$default, context.getSession());
        FirRegularClassSymbol firRegularClassSymbol = containingClassSymbol instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) containingClassSymbol : null;
        if (firRegularClassSymbol != null && firRegularClassSymbol.getClassKind() == ClassKind.ENUM_CLASS) {
            List<FirDeclaration> containingDeclarations = context.getContainingDeclarations();
            if ((containingDeclarations instanceof Collection) && containingDeclarations.isEmpty()) {
                return;
            }
            Iterator<T> it = containingDeclarations.iterator();
            while (it.hasNext()) {
                if (Intrinsics.areEqual(FirHelpersKt.getContainingClassSymbol((FirDeclaration) it.next(), context.getSession()), firRegularClassSymbol)) {
                    List<FirBasedSymbol<?>> declarationSymbols2 = firRegularClassSymbol.getDeclarationSymbols();
                    ArrayList arrayList = new ArrayList();
                    for (Object obj : declarationSymbols2) {
                        if (obj instanceof FirPropertySymbol) {
                            arrayList.add(obj);
                        }
                    }
                    ArrayList arrayList2 = arrayList;
                    ArrayList arrayList3 = new ArrayList();
                    for (Object obj2 : declarationSymbols2) {
                        if (obj2 instanceof FirAnonymousInitializerSymbol) {
                            arrayList3.add(obj2);
                        }
                    }
                    ArrayList arrayList4 = arrayList3;
                    ArrayList arrayList5 = new ArrayList();
                    for (Object obj3 : declarationSymbols2) {
                        if (obj3 instanceof FirEnumEntrySymbol) {
                            arrayList5.add(obj3);
                        }
                    }
                    ArrayList arrayList6 = arrayList5;
                    Map mapCreateMapBuilder = MapsKt.createMapBuilder();
                    for (Object obj4 : arrayList6) {
                        FirAnonymousObjectSymbol initializerObjectSymbol = ((FirEnumEntrySymbol) obj4).getInitializerObjectSymbol();
                        if (initializerObjectSymbol == null || (declarationSymbols = initializerObjectSymbol.getDeclarationSymbols()) == null) {
                            arrayListEmptyList = null;
                        } else {
                            ArrayList arrayList7 = new ArrayList();
                            for (Object obj5 : declarationSymbols) {
                                if (obj5 instanceof FirAnonymousInitializerSymbol) {
                                    arrayList7.add(obj5);
                                }
                            }
                            arrayListEmptyList = arrayList7;
                        }
                        if (arrayListEmptyList == null) {
                            arrayListEmptyList = CollectionsKt.emptyList();
                        }
                        Iterator it2 = arrayListEmptyList.iterator();
                        while (it2.hasNext()) {
                            mapCreateMapBuilder.put(it2.next(), obj4);
                        }
                    }
                    Map mapBuild = MapsKt.build(mapCreateMapBuilder);
                    List<FirDeclaration> containingDeclarations2 = context.getContainingDeclarations();
                    ListIterator<FirDeclaration> listIterator = containingDeclarations2.listIterator(containingDeclarations2.size());
                    do {
                        if (!listIterator.hasPrevious()) {
                            firDeclarationPrevious = null;
                            break;
                        }
                        firDeclarationPrevious = listIterator.previous();
                        firDeclaration2 = firDeclarationPrevious;
                        if (Intrinsics.areEqual(FirHelpersKt.getContainingClassSymbol(firDeclaration2, context.getSession()), firRegularClassSymbol)) {
                            break;
                        }
                    } while (!mapBuild.containsKey(firDeclaration2.getSymbol()));
                    FirDeclaration firDeclaration3 = firDeclarationPrevious;
                    if (firDeclaration3 == null || (symbol = firDeclaration3.getSymbol()) == null) {
                        return;
                    }
                    List<FirDeclaration> containingDeclarations3 = context.getContainingDeclarations();
                    ListIterator<FirDeclaration> listIterator2 = containingDeclarations3.listIterator(containingDeclarations3.size());
                    while (true) {
                        if (!listIterator2.hasPrevious()) {
                            firDeclaration = null;
                            break;
                        }
                        FirDeclaration firDeclarationPrevious2 = listIterator2.previous();
                        FirDeclaration firDeclaration4 = firDeclarationPrevious2;
                        if (!(firDeclaration4 instanceof FirAnonymousFunction) || ((FirAnonymousFunction) firDeclaration4).getInvocationKind() == null) {
                            firDeclaration = firDeclarationPrevious2;
                            break;
                        }
                    }
                    FirDeclaration firDeclaration5 = firDeclaration;
                    ArrayList arrayList8 = arrayList2;
                    if (CollectionsKt.contains(arrayList8, symbol) && (lazyDelegation = getLazyDelegation((FirPropertySymbol) symbol)) != null && Intrinsics.areEqual(lazyDelegation, firDeclaration5)) {
                        return;
                    }
                    ArrayList arrayList9 = arrayList6;
                    if (!CollectionsKt.contains(arrayList9, symbol)) {
                        zContains = true;
                    } else {
                        if (firDeclaration5 == null) {
                            return;
                        }
                        zContains = true;
                        if (!isEnumEntryInitializer(firDeclaration5, context.getSession())) {
                            return;
                        }
                    }
                    if (CollectionsKt.contains(arrayList9, resolvedBaseSymbol$default)) {
                        FirEnumEntrySymbol firEnumEntrySymbol = (FirEnumEntrySymbol) resolvedBaseSymbol$default;
                        if (!CollectionsKt.contains(arrayList8, symbol)) {
                            zContains = CollectionsKt.contains(arrayList4, symbol);
                        }
                        if (zContains) {
                            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getUNINITIALIZED_ENUM_ENTRY(), firEnumEntrySymbol, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                            return;
                        }
                        if (CollectionsKt.contains(arrayList9, symbol)) {
                            check$reportIllegalAccessInEnumEntry(arrayList6, firEnumEntrySymbol, reporter, source, context, (FirEnumEntrySymbol) symbol);
                            return;
                        } else {
                            if (mapBuild.containsKey(symbol)) {
                                FirEnumEntrySymbol firEnumEntrySymbol2 = (FirEnumEntrySymbol) MapsKt.getValue(mapBuild, (FirAnonymousInitializerSymbol) symbol);
                                if (Intrinsics.areEqual(firEnumEntrySymbol2, resolvedBaseSymbol$default)) {
                                    return;
                                }
                                check$reportIllegalAccessInEnumEntry(arrayList6, firEnumEntrySymbol, reporter, source, context, firEnumEntrySymbol2);
                                return;
                            }
                            return;
                        }
                    }
                    return;
                }
            }
        }
    }

    private static final void check$reportIllegalAccessInEnumEntry(List<FirEnumEntrySymbol> list, FirEnumEntrySymbol firEnumEntrySymbol, DiagnosticReporter diagnosticReporter, KtSourceElement ktSourceElement, CheckerContext checkerContext, FirEnumEntrySymbol firEnumEntrySymbol2) {
        FirEnumEntrySymbol next;
        Iterator<FirEnumEntrySymbol> it = list.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            } else {
                next = it.next();
                if (Intrinsics.areEqual(next, firEnumEntrySymbol)) {
                    break;
                }
            }
        } while (!Intrinsics.areEqual(next, firEnumEntrySymbol2));
        if (Intrinsics.areEqual(next, firEnumEntrySymbol2)) {
            KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, ktSourceElement, FirErrors.INSTANCE.getUNINITIALIZED_ENUM_ENTRY(), firEnumEntrySymbol, checkerContext, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final FirAnonymousFunction getLazyDelegation(FirPropertySymbol firPropertySymbol) {
        FirLazyDeclarationResolverKt.lazyResolveToPhase(firPropertySymbol, FirResolvePhase.BODY_RESOLVE);
        FirProperty firProperty = (FirProperty) firPropertySymbol.getFir();
        if (firProperty.getDelegate() == null || !(firProperty.getDelegate() instanceof FirFunctionCall)) {
            return null;
        }
        FirExpression delegate = firProperty.getDelegate();
        Intrinsics.checkNotNull(delegate, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirFunctionCall");
        FirFunctionCall firFunctionCall = (FirFunctionCall) delegate;
        FirNamedFunctionSymbol resolvedNamedFunctionSymbol$default = FirReferenceUtilsKt.toResolvedNamedFunctionSymbol$default(firFunctionCall.getCalleeReference(), false, 1, null);
        if (resolvedNamedFunctionSymbol$default == null || !Intrinsics.areEqual(resolvedNamedFunctionSymbol$default.getCallableId().asSingleFqName().asString(), "kotlin.lazy")) {
            return null;
        }
        Object objSingleOrNull = CollectionsKt.singleOrNull((List<? extends Object>) firFunctionCall.getArgumentList().getArguments());
        FirLambdaArgumentExpression firLambdaArgumentExpression = objSingleOrNull instanceof FirLambdaArgumentExpression ? (FirLambdaArgumentExpression) objSingleOrNull : null;
        if (firLambdaArgumentExpression == null) {
            return null;
        }
        FirExpression expression = firLambdaArgumentExpression.getExpression();
        FirAnonymousFunctionExpression firAnonymousFunctionExpression = expression instanceof FirAnonymousFunctionExpression ? (FirAnonymousFunctionExpression) expression : null;
        if (firAnonymousFunctionExpression != null) {
            return firAnonymousFunctionExpression.getAnonymousFunction();
        }
        return null;
    }

    private final boolean isEnumEntryInitializer(FirDeclaration firDeclaration, FirSession firSession) {
        ConeClassLikeType dispatchReceiverType;
        FirClassLikeSymbol symbol = null;
        if (firDeclaration instanceof FirConstructor) {
            if (!((FirConstructor) firDeclaration).getIsPrimary()) {
                return false;
            }
            ConeClassLikeLookupTag containingClassForStaticMemberAttr = ClassMembersKt.getContainingClassForStaticMemberAttr((FirCallableDeclaration) firDeclaration);
            ConeClassLookupTagWithFixedSymbol coneClassLookupTagWithFixedSymbol = containingClassForStaticMemberAttr instanceof ConeClassLookupTagWithFixedSymbol ? (ConeClassLookupTagWithFixedSymbol) containingClassForStaticMemberAttr : null;
            symbol = coneClassLookupTagWithFixedSymbol != null ? coneClassLookupTagWithFixedSymbol.getSymbol() : null;
        } else if ((firDeclaration instanceof FirAnonymousInitializer) && (dispatchReceiverType = ((FirAnonymousInitializer) firDeclaration).getDispatchReceiverType()) != null) {
            symbol = TypeUtilsKt.toSymbol(dispatchReceiverType, firSession);
        }
        return symbol != null && FirHelpersKt.getClassKind(symbol) == ClassKind.ENUM_ENTRY;
    }
}
