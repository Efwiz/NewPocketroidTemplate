package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtRealSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKind;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticContext;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory3;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirVisibilityChecker;
import org.jetbrains.kotlin.fir.FirVisibilityCheckerKt;
import org.jetbrains.kotlin.fir.analysis.FirOverridesBackwardCompatibilityHelperKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirOptInUsageBaseChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.DeprecationsPerUseSite;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.substitution.SubstitutorsKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirNestedClassifierScopeKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirIntersectionOverrideFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirIntersectionOverridePropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertyAccessorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.TypeCheckerState;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: FirOverrideChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J<\u0010\f\u001a\u00020\u00062\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000e2\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\b\u001a\u00020\tH\u0002J \u0010\u0014\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000e2\u0010\u0010\u0015\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e0\u0016H\u0002J6\u0010\u0017\u001a\u00020\u00062\n\u0010\u0018\u001a\u0006\u0012\u0002\b\u00030\u000e2\u0010\u0010\u0019\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e0\u00162\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J:\u0010\u001a\u001a\u00020\u0006*\u0006\u0012\u0002\b\u00030\u000e2\u0006\u0010\n\u001a\u00020\u000b2\u0010\u0010\u0019\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e0\u00162\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\tH\u0002J \u0010\u001b\u001a\u00020\u0006*\u0006\u0012\u0002\b\u00030\u001c2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\tH\u0002J2\u0010\u001d\u001a\u00020\u0006*\u0006\u0012\u0002\b\u00030\u000e2\u0006\u0010\n\u001a\u00020\u000b2\u0010\u0010\u0015\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e0\u00162\u0006\u0010\b\u001a\u00020\tH\u0002J$\u0010\u001e\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000e*\u00020\u001f2\u0010\u0010\u0015\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e0\u0016H\u0002J8\u0010 \u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000e*\u0006\u0012\u0002\b\u00030\u000e2\u0010\u0010\u0015\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e0\u00162\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\b\u001a\u00020\tH\u0002J:\u0010!\u001a\u00020\u0006*\u0006\u0012\u0002\b\u00030\u000e2\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000b2\u0010\u0010\u0015\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e0\u00162\u0006\u0010\b\u001a\u00020\tH\u0002J,\u0010\"\u001a\u00020\u0006*\u00020\u000b2\n\u0010#\u001a\u0006\u0012\u0002\b\u00030\u000e2\n\u0010$\u001a\u0006\u0012\u0002\b\u00030\u000e2\u0006\u0010\b\u001a\u00020\tH\u0002J,\u0010%\u001a\u00020\u0006*\u00020\u000b2\n\u0010#\u001a\u0006\u0012\u0002\b\u00030\u000e2\n\u0010$\u001a\u0006\u0012\u0002\b\u00030\u000e2\u0006\u0010\b\u001a\u00020\tH\u0002J \u0010&\u001a\u00020\u0006*\u00020\u000b2\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u000e2\u0006\u0010\b\u001a\u00020\tH\u0002J,\u0010'\u001a\u00020\u0006*\u00020\u000b2\n\u0010#\u001a\u0006\u0012\u0002\b\u00030\u000e2\n\u0010$\u001a\u0006\u0012\u0002\b\u00030\u000e2\u0006\u0010\b\u001a\u00020\tH\u0002J,\u0010(\u001a\u00020\u0006*\u00020\u000b2\n\u0010#\u001a\u0006\u0012\u0002\b\u00030\u000e2\n\u0010$\u001a\u0006\u0012\u0002\b\u00030\u000e2\u0006\u0010\b\u001a\u00020\tH\u0002J,\u0010)\u001a\u00020\u0006*\u00020\u000b2\n\u0010#\u001a\u0006\u0012\u0002\b\u00030\u000e2\n\u0010$\u001a\u0006\u0012\u0002\b\u00030\u000e2\u0006\u0010\b\u001a\u00020\tH\u0002J,\u0010*\u001a\u00020\u0006*\u00020\u000b2\n\u0010#\u001a\u0006\u0012\u0002\b\u00030\u000e2\n\u0010$\u001a\u0006\u0012\u0002\b\u00030\u000e2\u0006\u0010\b\u001a\u00020\tH\u0002J,\u0010+\u001a\u00020\u0006*\u00020\u000b2\n\u0010#\u001a\u0006\u0012\u0002\b\u00030\u000e2\n\u0010$\u001a\u0006\u0012\u0002\b\u00030\u000e2\u0006\u0010\b\u001a\u00020\tH\u0002J,\u0010,\u001a\u00020-*\u00020-2\n\u0010.\u001a\u0006\u0012\u0002\b\u00030\u000e2\n\u0010/\u001a\u0006\u0012\u0002\b\u00030\u000e2\u0006\u0010\b\u001a\u00020\tH\u0002¨\u00060"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirOverrideChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirClassChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkMember", "member", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "containingClass", "typeCheckerState", "Lorg/jetbrains/kotlin/types/TypeCheckerState;", "firTypeScope", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "checkModality", "overriddenSymbols", "", "checkOverriddenExperimentalities", "memberSymbol", "overriddenMemberSymbols", "checkDataClassCopy", "checkDefaultValues", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "checkDeprecation", "checkMutability", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "checkReturnType", "checkVisibility", "reportCannotChangeAccessPrivilege", "overriding", "overridden", "reportCannotWeakenAccessPrivilege", "reportNothingToOverride", "reportOverridingFinalMember", "reportReturnTypeMismatchOnFunction", "reportTypeMismatchOnProperty", "reportTypeMismatchOnVariable", "reportVarOverriddenByVal", "substituteAllTypeParameters", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "overrideDeclaration", "baseDeclaration", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirOverrideChecker extends FirDeclarationChecker<FirClass> {
    public static final FirOverrideChecker INSTANCE = new FirOverrideChecker();

    private FirOverrideChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirClass declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        TypeCheckerState typeCheckerStateNewTypeCheckerState = TypeComponentsKt.getTypeContext(context.getSession()).newTypeCheckerState(false, false);
        FirTypeScope firTypeScopeUnsubstitutedScope = FirHelpersKt.unsubstitutedScope(declaration, context);
        for (FirDeclaration firDeclaration : declaration.getDeclarations()) {
            if ((firDeclaration instanceof FirSimpleFunction) || (firDeclaration instanceof FirProperty)) {
                checkMember(((FirCallableDeclaration) firDeclaration).getSymbol(), declaration, reporter, typeCheckerStateNewTypeCheckerState, firTypeScopeUnsubstitutedScope, context);
            }
        }
    }

    private final ConeKotlinType substituteAllTypeParameters(ConeKotlinType coneKotlinType, FirCallableSymbol<?> firCallableSymbol, FirCallableSymbol<?> firCallableSymbol2, CheckerContext checkerContext) {
        List<FirTypeParameterSymbol> typeParameterSymbols = firCallableSymbol.getTypeParameterSymbols();
        if (typeParameterSymbols.isEmpty()) {
            return coneKotlinType;
        }
        List<FirTypeParameterSymbol> typeParameterSymbols2 = firCallableSymbol2.getTypeParameterSymbols();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        int iMin = Math.min(typeParameterSymbols.size(), typeParameterSymbols2.size());
        for (int i = 0; i < iMin; i++) {
            linkedHashMap.put(typeParameterSymbols2.get(i), FirNestedClassifierScopeKt.toConeType(typeParameterSymbols.get(i)));
        }
        return SubstitutorsKt.substitutorByMap(linkedHashMap, checkerContext.getSession()).substituteOrSelf(coneKotlinType);
    }

    private final FirCallableSymbol<?> checkModality(List<? extends FirCallableSymbol<?>> overriddenSymbols) {
        for (FirCallableSymbol<?> firCallableSymbol : overriddenSymbols) {
            Modality modality = firCallableSymbol.getResolvedStatus().getModality();
            if (modality == null || modality == Modality.FINAL) {
                return firCallableSymbol;
            }
        }
        return null;
    }

    private final FirCallableSymbol<?> checkMutability(FirPropertySymbol firPropertySymbol, List<? extends FirCallableSymbol<?>> list) {
        Object obj = null;
        if (firPropertySymbol.isVar()) {
            return null;
        }
        for (Object obj2 : list) {
            FirCallableSymbol firCallableSymbol = (FirCallableSymbol) obj2;
            FirPropertySymbol firPropertySymbol2 = firCallableSymbol instanceof FirPropertySymbol ? (FirPropertySymbol) firCallableSymbol : null;
            if (firPropertySymbol2 != null && firPropertySymbol2.isVar()) {
                obj = obj2;
                break;
            }
        }
        return (FirCallableSymbol) obj;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void checkVisibility(final FirCallableSymbol<?> firCallableSymbol, FirClass firClass, DiagnosticReporter diagnosticReporter, List<? extends FirCallableSymbol<?>> list, CheckerContext checkerContext) {
        FirFile containingFile;
        if (list.isEmpty()) {
            return;
        }
        List<? extends FirCallableSymbol<?>> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            FirCallableSymbol firCallableSymbol2 = (FirCallableSymbol) it.next();
            arrayList.add(TuplesKt.to(firCallableSymbol2, firCallableSymbol2.getResolvedStatus().getVisibility()));
        }
        List<Pair> listSortedWith = CollectionsKt.sortedWith(arrayList, new Comparator() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirOverrideChecker$checkVisibility$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                Integer numCompare = Visibilities.INSTANCE.compare(firCallableSymbol.getResolvedStatus().getVisibility(), (Visibility) ((Pair) t).getSecond());
                Integer numValueOf = Integer.valueOf(numCompare != null ? numCompare.intValue() : Integer.MIN_VALUE);
                Integer numCompare2 = Visibilities.INSTANCE.compare(firCallableSymbol.getResolvedStatus().getVisibility(), (Visibility) ((Pair) t2).getSecond());
                return ComparisonsKt.compareValues(numValueOf, Integer.valueOf(numCompare2 != null ? numCompare2.intValue() : Integer.MIN_VALUE));
            }
        });
        if (firCallableSymbol instanceof FirPropertySymbol) {
            FirPropertySymbol firPropertySymbol = (FirPropertySymbol) firCallableSymbol;
            FirPropertyAccessorSymbol getterSymbol = firPropertySymbol.getGetterSymbol();
            if (getterSymbol != null) {
                FirPropertyAccessorSymbol firPropertyAccessorSymbol = getterSymbol;
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                Iterator<T> it2 = list2.iterator();
                while (it2.hasNext()) {
                    FirPropertyAccessorSymbol firPropertyAccessorSymbol2 = (FirCallableSymbol) it2.next();
                    Intrinsics.checkNotNull(firPropertyAccessorSymbol2, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol");
                    FirPropertyAccessorSymbol getterSymbol2 = ((FirPropertySymbol) firPropertyAccessorSymbol2).getGetterSymbol();
                    if (getterSymbol2 != null) {
                        firPropertyAccessorSymbol2 = getterSymbol2;
                    }
                    arrayList2.add(firPropertyAccessorSymbol2);
                }
                checkVisibility(firPropertyAccessorSymbol, firClass, diagnosticReporter, arrayList2, checkerContext);
            }
            FirPropertyAccessorSymbol setterSymbol = firPropertySymbol.getSetterSymbol();
            if (setterSymbol != null) {
                FirPropertyAccessorSymbol firPropertyAccessorSymbol3 = setterSymbol;
                ArrayList arrayList3 = new ArrayList();
                Iterator<T> it3 = list2.iterator();
                while (it3.hasNext()) {
                    FirCallableSymbol firCallableSymbol3 = (FirCallableSymbol) it3.next();
                    Intrinsics.checkNotNull(firCallableSymbol3, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol");
                    FirPropertyAccessorSymbol setterSymbol2 = ((FirPropertySymbol) firCallableSymbol3).getSetterSymbol();
                    if (setterSymbol2 != null) {
                        arrayList3.add(setterSymbol2);
                    }
                }
                checkVisibility(firPropertyAccessorSymbol3, firClass, diagnosticReporter, arrayList3, checkerContext);
            }
        } else {
            for (Pair pair : listSortedWith) {
                FirCallableSymbol<?> firCallableSymbol4 = (FirCallableSymbol) pair.component1();
                Integer numCompare = Visibilities.INSTANCE.compare(firCallableSymbol.getResolvedStatus().getVisibility(), (Visibility) pair.component2());
                if (numCompare == null) {
                    reportCannotChangeAccessPrivilege(diagnosticReporter, firCallableSymbol, firCallableSymbol4, checkerContext);
                    break;
                } else if (numCompare.intValue() < 0) {
                    reportCannotWeakenAccessPrivilege(diagnosticReporter, firCallableSymbol, firCallableSymbol4, checkerContext);
                    break;
                }
            }
        }
        if ((firCallableSymbol instanceof FirPropertyAccessorSymbol) || (containingFile = checkerContext.getContainingFile()) == null) {
            return;
        }
        List listPlus = CollectionsKt.plus((Collection<? extends FirClass>) checkerContext.getContainingDeclarations(), firClass);
        FirVisibilityChecker visibilityChecker = FirVisibilityCheckerKt.getVisibilityChecker(checkerContext.getSession());
        if (!(list2 instanceof Collection) || !list2.isEmpty()) {
            Iterator<T> it4 = list2.iterator();
            while (it4.hasNext()) {
                FirCallableSymbol firCallableSymbol5 = (FirCallableSymbol) it4.next();
                FirLazyDeclarationResolverKt.lazyResolveToPhase(firCallableSymbol5, FirResolvePhase.STATUS);
                if (FirVisibilityChecker.isVisible$default(visibilityChecker, (FirCallableDeclaration) firCallableSymbol5.getFir(), checkerContext.getSession(), containingFile, listPlus, null, false, null, true, null, 352, null)) {
                    return;
                }
            }
        }
        KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, firCallableSymbol.getSource(), FirErrors.INSTANCE.getCANNOT_OVERRIDE_INVISIBLE_MEMBER(), firCallableSymbol, CollectionsKt.first((List) list), checkerContext, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
    }

    private final void checkDeprecation(FirCallableSymbol<?> firCallableSymbol, DiagnosticReporter diagnosticReporter, List<? extends FirCallableSymbol<?>> list, CheckerContext checkerContext) {
        DeprecationInfo deprecationInfo;
        Collection<DeprecationInfo> collectionValues;
        DeprecationsPerUseSite deprecation = firCallableSymbol.getDeprecation(FirLanguageSettingsComponentKt.getLanguageVersionSettings(checkerContext.getSession()));
        if (deprecation == null || deprecation.isNotEmpty()) {
            return;
        }
        for (FirCallableSymbol<?> firCallableSymbol2 : list) {
            DeprecationsPerUseSite deprecation2 = firCallableSymbol2.getDeprecation(FirLanguageSettingsComponentKt.getLanguageVersionSettings(checkerContext.getSession()));
            if (deprecation2 != null) {
                DeprecationInfo all = deprecation2.getAll();
                if (all == null) {
                    Map<AnnotationUseSiteTarget, DeprecationInfo> bySpecificSite = deprecation2.getBySpecificSite();
                    DeprecationInfo deprecationInfo2 = (bySpecificSite == null || (collectionValues = bySpecificSite.values()) == null) ? null : (DeprecationInfo) CollectionsKt.firstOrNull(collectionValues);
                    if (deprecationInfo2 != null) {
                        deprecationInfo = deprecationInfo2;
                    }
                } else {
                    deprecationInfo = all;
                }
                KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, firCallableSymbol.getSource(), FirErrors.INSTANCE.getOVERRIDE_DEPRECATION(), firCallableSymbol2, deprecationInfo, checkerContext, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                return;
            }
        }
    }

    private final FirCallableSymbol<?> checkReturnType(FirCallableSymbol<?> firCallableSymbol, List<? extends FirCallableSymbol<?>> list, TypeCheckerState typeCheckerState, CheckerContext checkerContext) {
        boolean zIsSubtypeOf$default;
        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(firCallableSymbol.getResolvedReturnTypeRef());
        if (coneType instanceof ConeErrorType) {
            return null;
        }
        List<? extends FirCallableSymbol<?>> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(FirTypeUtilsKt.getConeType(checkerContext.getReturnTypeCalculator().tryCalculateReturnType((FirCallableSymbol<?>) it.next())));
        }
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        for (int i = 0; i < size; i++) {
            FirCallableSymbol<?> firCallableSymbol2 = list.get(i);
            ConeKotlinType coneKotlinTypeSubstituteAllTypeParameters = substituteAllTypeParameters((ConeKotlinType) arrayList2.get(i), firCallableSymbol, firCallableSymbol2, checkerContext);
            if ((firCallableSymbol2 instanceof FirPropertySymbol) && ((FirPropertySymbol) firCallableSymbol2).isVar()) {
                zIsSubtypeOf$default = AbstractTypeChecker.INSTANCE.equalTypes(typeCheckerState, coneType, coneKotlinTypeSubstituteAllTypeParameters);
            } else {
                zIsSubtypeOf$default = AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, typeCheckerState, (KotlinTypeMarker) coneType, (KotlinTypeMarker) coneKotlinTypeSubstituteAllTypeParameters, false, 8, (Object) null);
            }
            if (!zIsSubtypeOf$default) {
                return firCallableSymbol2;
            }
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void checkDefaultValues(FirFunctionSymbol<?> firFunctionSymbol, DiagnosticReporter diagnosticReporter, CheckerContext checkerContext) {
        Iterator<FirValueParameterSymbol> it = firFunctionSymbol.getValueParameterSymbols().iterator();
        while (it.hasNext()) {
            FirExpression defaultValue = ((FirValueParameter) it.next().getFir()).getDefaultValue();
            if (defaultValue != null) {
                KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, defaultValue.getSource(), FirErrors.INSTANCE.getDEFAULT_VALUE_NOT_ALLOWED_IN_OVERRIDE(), checkerContext, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }

    private final void checkDataClassCopy(FirCallableSymbol<?> firCallableSymbol, DiagnosticReporter diagnosticReporter, List<? extends FirCallableSymbol<?>> list, FirClass firClass, CheckerContext checkerContext) {
        FirCallableSymbol firCallableSymbol2 = (FirCallableSymbol) CollectionsKt.firstOrNull((List) list);
        if (firCallableSymbol2 == null) {
            return;
        }
        FirClassLikeSymbol<?> containingClassSymbol = FirHelpersKt.getContainingClassSymbol(firCallableSymbol2, checkerContext.getSession());
        FirClassSymbol firClassSymbol = containingClassSymbol instanceof FirClassSymbol ? (FirClassSymbol) containingClassSymbol : null;
        if (firClassSymbol == null) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, firClass.getSource(), FirErrors.INSTANCE.getDATA_CLASS_OVERRIDE_DEFAULT_VALUES(), firCallableSymbol, firClassSymbol, checkerContext, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void checkMember(FirCallableSymbol<?> member, FirClass containingClass, DiagnosticReporter reporter, TypeCheckerState typeCheckerState, FirTypeScope firTypeScope, CheckerContext context) {
        FirCallableSymbol<?> firCallableSymbolCheckMutability;
        Object next;
        FirCallableSymbol<? extends FirCallableDeclaration> symbol;
        Object obj;
        List<FirCallableSymbol<?>> listRetrieveDirectOverriddenOf = FirTypeScopeKt.retrieveDirectOverriddenOf(firTypeScope, member);
        if (!member.getRawStatus().isOverride()) {
            if (listRetrieveDirectOverriddenOf.isEmpty() || FirOverridesBackwardCompatibilityHelperKt.getOverridesBackwardCompatibilityHelper(context.getSession()).overrideCanBeOmitted(listRetrieveDirectOverriddenOf, context)) {
                return;
            }
            KtSourceElement source = member.getSource();
            KtSourceElementKind kind = source != null ? source.getKind() : null;
            if (kind instanceof KtFakeSourceElementKind.DataClassGeneratedMembers) {
                Iterator<T> it = listRetrieveDirectOverriddenOf.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        obj = null;
                        break;
                    }
                    Object next2 = it.next();
                    Modality modality = ((FirCallableSymbol) next2).getResolvedStatus().getModality();
                    if (modality == null || modality == Modality.FINAL) {
                        obj = next2;
                        break;
                    }
                }
                FirCallableSymbol firCallableSymbol = (FirCallableSymbol) obj;
                if (firCallableSymbol != null) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, containingClass.getSource(), FirErrors.INSTANCE.getDATA_CLASS_OVERRIDE_CONFLICT(), member, firCallableSymbol, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                }
                if (Intrinsics.areEqual(member.getName(), StandardNames.DATA_CLASS_COPY)) {
                    checkDataClassCopy(member, reporter, listRetrieveDirectOverriddenOf, containingClass, context);
                    return;
                }
                return;
            }
            if ((kind instanceof KtRealSourceElementKind) || (kind instanceof KtFakeSourceElementKind.PropertyFromParameter)) {
                FirVisibilityChecker visibilityChecker = FirVisibilityCheckerKt.getVisibilityChecker(context.getSession());
                FirFile containingFile = context.getContainingFile();
                if (containingFile == null) {
                    return;
                }
                List listPlus = CollectionsKt.plus((Collection<? extends FirClass>) context.getContainingDeclarations(), containingClass);
                Iterator<T> it2 = listRetrieveDirectOverriddenOf.iterator();
                do {
                    if (!it2.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it2.next();
                    FirCallableSymbol firCallableSymbol2 = (FirCallableSymbol) next;
                    FirLazyDeclarationResolverKt.lazyResolveToPhase(firCallableSymbol2, FirResolvePhase.STATUS);
                    FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) firCallableSymbol2.getFir();
                    while (ClassMembersKt.isSubstitutionOrIntersectionOverride(firCallableDeclaration)) {
                        FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
                        if (originalForSubstitutionOverrideAttr == null) {
                            originalForSubstitutionOverrideAttr = ClassMembersKt.isIntersectionOverride(firCallableDeclaration) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firCallableDeclaration) : null;
                        }
                        if (originalForSubstitutionOverrideAttr == null) {
                            break;
                        } else {
                            firCallableDeclaration = originalForSubstitutionOverrideAttr;
                        }
                    }
                    symbol = firCallableDeclaration.getSymbol();
                    if (symbol == null) {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol<*>");
                    }
                } while (!FirVisibilityChecker.isVisible$default(visibilityChecker, symbol.getFir(), context.getSession(), containingFile, listPlus, null, false, null, true, null, 352, null));
                FirCallableSymbol firCallableSymbol3 = (FirCallableSymbol) next;
                if (firCallableSymbol3 != null) {
                    FirCallableDeclaration firCallableDeclaration2 = (FirCallableDeclaration) firCallableSymbol3.getFir();
                    while (ClassMembersKt.isSubstitutionOrIntersectionOverride(firCallableDeclaration2)) {
                        FirCallableDeclaration originalForSubstitutionOverrideAttr2 = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration2) || (firCallableDeclaration2.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration2) : null;
                        if (originalForSubstitutionOverrideAttr2 == null) {
                            originalForSubstitutionOverrideAttr2 = ClassMembersKt.isIntersectionOverride(firCallableDeclaration2) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firCallableDeclaration2) : null;
                        }
                        if (originalForSubstitutionOverrideAttr2 == null) {
                            break;
                        } else {
                            firCallableDeclaration2 = originalForSubstitutionOverrideAttr2;
                        }
                    }
                    FirCallableSymbol<? extends FirCallableDeclaration> symbol2 = firCallableDeclaration2.getSymbol();
                    if (symbol2 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol<*>");
                    }
                    if (symbol2 != null) {
                        ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(symbol2);
                        FirClassLikeSymbol<?> symbol3 = coneClassLikeLookupTagContainingClassLookupTag != null ? LookupTagUtilsKt.toSymbol(coneClassLikeLookupTagContainingClassLookupTag, context.getSession()) : null;
                        FirRegularClassSymbol firRegularClassSymbol = symbol3 instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) symbol3 : null;
                        if (firRegularClassSymbol == null) {
                            return;
                        }
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, member.getSource(), FirErrors.INSTANCE.getVIRTUAL_MEMBER_HIDDEN(), member, firRegularClassSymbol, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }
        if (listRetrieveDirectOverriddenOf.isEmpty()) {
            reportNothingToOverride(reporter, member, context);
            return;
        }
        checkOverriddenExperimentalities(member, listRetrieveDirectOverriddenOf, context, reporter);
        FirCallableSymbol<?> firCallableSymbolCheckModality = checkModality(listRetrieveDirectOverriddenOf);
        if (firCallableSymbolCheckModality != null) {
            INSTANCE.reportOverridingFinalMember(reporter, member, firCallableSymbolCheckModality, context);
        }
        boolean z = member instanceof FirPropertySymbol;
        if (z && (firCallableSymbolCheckMutability = checkMutability((FirPropertySymbol) member, listRetrieveDirectOverriddenOf)) != null) {
            INSTANCE.reportVarOverriddenByVal(reporter, member, firCallableSymbolCheckMutability, context);
        }
        checkVisibility(member, containingClass, reporter, listRetrieveDirectOverriddenOf, context);
        checkDeprecation(member, reporter, listRetrieveDirectOverriddenOf, context);
        if (member instanceof FirFunctionSymbol) {
            checkDefaultValues((FirFunctionSymbol) member, reporter, context);
        }
        FirCallableSymbol<?> firCallableSymbolCheckReturnType = checkReturnType(member, listRetrieveDirectOverriddenOf, typeCheckerState, context);
        if (firCallableSymbolCheckReturnType == null) {
            return;
        }
        if (member instanceof FirNamedFunctionSymbol) {
            reportReturnTypeMismatchOnFunction(reporter, member, firCallableSymbolCheckReturnType, context);
        } else if (z) {
            if (((FirPropertySymbol) member).isVar()) {
                reportTypeMismatchOnVariable(reporter, member, firCallableSymbolCheckReturnType, context);
            } else {
                reportTypeMismatchOnProperty(reporter, member, firCallableSymbolCheckReturnType, context);
            }
        }
    }

    private final void checkOverriddenExperimentalities(FirCallableSymbol<?> memberSymbol, List<? extends FirCallableSymbol<?>> overriddenMemberSymbols, CheckerContext context, DiagnosticReporter reporter) {
        List intersections;
        FirOptInUsageBaseChecker firOptInUsageBaseChecker = FirOptInUsageBaseChecker.INSTANCE;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        FirSession session = context.getSession();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = overriddenMemberSymbols.iterator();
        while (it.hasNext()) {
            FirCallableSymbol firCallableSymbol = (FirCallableSymbol) it.next();
            if (firCallableSymbol instanceof FirIntersectionOverridePropertySymbol) {
                intersections = ((FirIntersectionOverridePropertySymbol) firCallableSymbol).getIntersections();
            } else {
                intersections = firCallableSymbol instanceof FirIntersectionOverrideFunctionSymbol ? ((FirIntersectionOverrideFunctionSymbol) firCallableSymbol).getIntersections() : CollectionsKt.listOf(firCallableSymbol);
            }
            CollectionsKt.addAll(arrayList, intersections);
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            firOptInUsageBaseChecker.loadExperimentalitiesFromAnnotationTo((FirCallableSymbol) it2.next(), session, linkedHashSet);
        }
        firOptInUsageBaseChecker.reportNotAcceptedOverrideExperimentalities(linkedHashSet, memberSymbol, context, reporter);
    }

    private final void reportNothingToOverride(DiagnosticReporter diagnosticReporter, FirCallableSymbol<?> firCallableSymbol, CheckerContext checkerContext) {
        KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, firCallableSymbol.getSource(), FirErrors.INSTANCE.getNOTHING_TO_OVERRIDE(), firCallableSymbol, checkerContext, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
    }

    private final void reportOverridingFinalMember(DiagnosticReporter diagnosticReporter, FirCallableSymbol<?> firCallableSymbol, FirCallableSymbol<?> firCallableSymbol2, CheckerContext checkerContext) {
        ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(firCallableSymbol2);
        if (coneClassLikeLookupTagContainingClassLookupTag != null) {
            KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, firCallableSymbol.getSource(), FirErrors.INSTANCE.getOVERRIDING_FINAL_MEMBER(), firCallableSymbol2, coneClassLikeLookupTagContainingClassLookupTag.getName(), checkerContext, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
        }
    }

    private final void reportVarOverriddenByVal(DiagnosticReporter diagnosticReporter, FirCallableSymbol<?> firCallableSymbol, FirCallableSymbol<?> firCallableSymbol2, CheckerContext checkerContext) {
        KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, firCallableSymbol.getSource(), FirErrors.INSTANCE.getVAR_OVERRIDDEN_BY_VAL(), firCallableSymbol, firCallableSymbol2, checkerContext, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
    }

    private final void reportCannotWeakenAccessPrivilege(DiagnosticReporter diagnosticReporter, FirCallableSymbol<?> firCallableSymbol, FirCallableSymbol<?> firCallableSymbol2, CheckerContext checkerContext) {
        ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(firCallableSymbol2);
        if (coneClassLikeLookupTagContainingClassLookupTag == null) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn(diagnosticReporter, (AbstractKtSourceElement) firCallableSymbol.getSource(), (KtDiagnosticFactory3<Visibility, FirCallableSymbol<?>, Name>) ((KtDiagnosticFactory3<Object, Object, Object>) FirErrors.INSTANCE.getCANNOT_WEAKEN_ACCESS_PRIVILEGE()), firCallableSymbol.getResolvedStatus().getVisibility(), firCallableSymbol2, coneClassLikeLookupTagContainingClassLookupTag.getName(), (DiagnosticContext) checkerContext, (64 & 64) != 0 ? null : null);
    }

    private final void reportCannotChangeAccessPrivilege(DiagnosticReporter diagnosticReporter, FirCallableSymbol<?> firCallableSymbol, FirCallableSymbol<?> firCallableSymbol2, CheckerContext checkerContext) {
        ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(firCallableSymbol2);
        if (coneClassLikeLookupTagContainingClassLookupTag == null) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn(diagnosticReporter, (AbstractKtSourceElement) firCallableSymbol.getSource(), (KtDiagnosticFactory3<Visibility, FirCallableSymbol<?>, Name>) ((KtDiagnosticFactory3<Object, Object, Object>) FirErrors.INSTANCE.getCANNOT_CHANGE_ACCESS_PRIVILEGE()), firCallableSymbol.getResolvedStatus().getVisibility(), firCallableSymbol2, coneClassLikeLookupTagContainingClassLookupTag.getName(), (DiagnosticContext) checkerContext, (64 & 64) != 0 ? null : null);
    }

    private final void reportReturnTypeMismatchOnFunction(DiagnosticReporter diagnosticReporter, FirCallableSymbol<?> firCallableSymbol, FirCallableSymbol<?> firCallableSymbol2, CheckerContext checkerContext) {
        KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, firCallableSymbol.getSource(), FirErrors.INSTANCE.getRETURN_TYPE_MISMATCH_ON_OVERRIDE(), firCallableSymbol, firCallableSymbol2, checkerContext, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
    }

    private final void reportTypeMismatchOnProperty(DiagnosticReporter diagnosticReporter, FirCallableSymbol<?> firCallableSymbol, FirCallableSymbol<?> firCallableSymbol2, CheckerContext checkerContext) {
        KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, firCallableSymbol.getSource(), FirErrors.INSTANCE.getPROPERTY_TYPE_MISMATCH_ON_OVERRIDE(), firCallableSymbol, firCallableSymbol2, checkerContext, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
    }

    private final void reportTypeMismatchOnVariable(DiagnosticReporter diagnosticReporter, FirCallableSymbol<?> firCallableSymbol, FirCallableSymbol<?> firCallableSymbol2, CheckerContext checkerContext) {
        KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, firCallableSymbol.getSource(), FirErrors.INSTANCE.getVAR_TYPE_MISMATCH_ON_OVERRIDE(), firCallableSymbol, firCallableSymbol2, checkerContext, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
    }
}
