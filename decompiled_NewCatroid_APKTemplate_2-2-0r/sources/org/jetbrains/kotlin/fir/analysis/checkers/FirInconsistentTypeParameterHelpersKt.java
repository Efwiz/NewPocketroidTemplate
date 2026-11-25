package org.jetbrains.kotlin.fir.analysis.checkers;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.DiagnosticContext;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory3;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutorByMap;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjectionKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.types.AbstractTypeChecker;

/* JADX INFO: compiled from: FirInconsistentTypeParameterHelpers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u001a8\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\u001a\u0010\u0004\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0012\u0004\u0012\u00020\b0\u00060\u00052\u0006\u0010\t\u001a\u00020\nH\u0002\u001aD\u0010\u000b\u001a\u00020\f2\u001a\u0010\u0004\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0012\u0004\u0012\u00020\b0\u00060\u00052\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u0012¨\u0006\u0013"}, d2 = {"buildDeepSubstitutionMultimap", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/ClassSymbolAndProjections;", "firTypeRefClasses", "", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "checkInconsistentTypeParameters", "", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "isValues", "", "checkers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirInconsistentTypeParameterHelpersKt {
    public static final void checkInconsistentTypeParameters(List<? extends Pair<? extends FirTypeRef, FirRegularClassSymbol>> firTypeRefClasses, CheckerContext context, DiagnosticReporter reporter, KtSourceElement ktSourceElement, boolean z) {
        Intrinsics.checkNotNullParameter(firTypeRefClasses, "firTypeRefClasses");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        for (Map.Entry<FirTypeParameterSymbol, ClassSymbolAndProjections> entry : buildDeepSubstitutionMultimap(firTypeRefClasses, context).entrySet()) {
            FirTypeParameterSymbol key = entry.getKey();
            ClassSymbolAndProjections value = entry.getValue();
            List<ConeKotlinType> projections = value.getProjections();
            if (projections.size() > 1) {
                if (z) {
                    KtDiagnosticReportHelpersKt.reportOn(reporter, (AbstractKtSourceElement) ktSourceElement, (KtDiagnosticFactory3<FirTypeParameterSymbol, FirRegularClassSymbol, List<ConeKotlinType>>) ((KtDiagnosticFactory3<Object, Object, Object>) FirErrors.INSTANCE.getINCONSISTENT_TYPE_PARAMETER_VALUES()), key, value.getClassSymbol(), projections, (DiagnosticContext) context, (64 & 64) != 0 ? null : null);
                } else {
                    KtDiagnosticReportHelpersKt.reportOn(reporter, (AbstractKtSourceElement) ktSourceElement, (KtDiagnosticFactory3<FirTypeParameterSymbol, FirRegularClassSymbol, List<ConeKotlinType>>) ((KtDiagnosticFactory3<Object, Object, Object>) FirErrors.INSTANCE.getINCONSISTENT_TYPE_PARAMETER_BOUNDS()), key, value.getClassSymbol(), projections, (DiagnosticContext) context, (64 & 64) != 0 ? null : null);
                }
            }
        }
    }

    private static final Map<FirTypeParameterSymbol, ClassSymbolAndProjections> buildDeepSubstitutionMultimap(List<? extends Pair<? extends FirTypeRef, FirRegularClassSymbol>> list, CheckerContext checkerContext) {
        ConeKotlinType coneType;
        ConeKotlinType coneKotlinTypeFullyExpandedType;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        FirSession session = checkerContext.getSession();
        ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(session);
        for (Pair<? extends FirTypeRef, FirRegularClassSymbol> pair : list) {
            FirTypeRef first = pair.getFirst();
            buildDeepSubstitutionMultimap$fillInDeepSubstitutor(linkedHashMap2, session, linkedHashMap, linkedHashSet, typeContext, (first == null || (coneType = FirTypeUtilsKt.getConeType(first)) == null || (coneKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(coneType, session)) == null) ? null : coneKotlinTypeFullyExpandedType.getTypeArguments(), pair.getSecond(), checkerContext);
        }
        return linkedHashMap;
    }

    private static final void buildDeepSubstitutionMultimap$fillInDeepSubstitutor(Map<FirTypeParameterSymbol, ConeKotlinType> map, FirSession firSession, Map<FirTypeParameterSymbol, ClassSymbolAndProjections> map2, Set<ConeKotlinType> set, ConeInferenceContext coneInferenceContext, ConeTypeProjection[] coneTypeProjectionArr, FirRegularClassSymbol firRegularClassSymbol, CheckerContext checkerContext) {
        ArrayList projections;
        ConeKotlinType coneKotlinType;
        if (coneTypeProjectionArr != null) {
            List<FirTypeParameterSymbol> typeParameterSymbols = firRegularClassSymbol.getTypeParameterSymbols();
            int iMin = Math.min(coneTypeProjectionArr.length, typeParameterSymbols.size());
            for (int i = 0; i < iMin; i++) {
                ConeTypeProjection coneTypeProjection = coneTypeProjectionArr[i];
                ConeTypeProjection coneTypeProjectionSubstituteArgument = new ConeSubstitutorByMap(map, firSession).substituteArgument(coneTypeProjection, i);
                if (coneTypeProjectionSubstituteArgument != null) {
                    coneTypeProjection = coneTypeProjectionSubstituteArgument;
                }
                ConeKotlinType type = ConeTypeProjectionKt.getType(coneTypeProjection);
                if (type != null) {
                    FirTypeParameterSymbol firTypeParameterSymbol = typeParameterSymbols.get(i);
                    map.put(firTypeParameterSymbol, type);
                    ClassSymbolAndProjections classSymbolAndProjections = map2.get(firTypeParameterSymbol);
                    if (classSymbolAndProjections == null) {
                        projections = new ArrayList();
                        map2.put(firTypeParameterSymbol, new ClassSymbolAndProjections(firRegularClassSymbol, projections));
                    } else {
                        projections = classSymbolAndProjections.getProjections();
                    }
                    List<ConeKotlinType> list = projections;
                    if ((list instanceof Collection) && list.isEmpty()) {
                        projections.add(type);
                        break;
                        break;
                    }
                    Iterator<T> it = list.iterator();
                    do {
                        if (it.hasNext()) {
                            coneKotlinType = (ConeKotlinType) it.next();
                            if (Intrinsics.areEqual(coneKotlinType, type)) {
                                break;
                            }
                        } else {
                            projections.add(type);
                            break;
                        }
                    } while (!AbstractTypeChecker.equalTypes$default(AbstractTypeChecker.INSTANCE, coneInferenceContext, coneKotlinType, type, false, 8, null));
                }
            }
        }
        Iterator<FirResolvedTypeRef> it2 = firRegularClassSymbol.getResolvedSuperTypeRefs().iterator();
        while (it2.hasNext()) {
            ConeKotlinType coneKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(it2.next()), firSession);
            if (!set.add(coneKotlinTypeFullyExpandedType)) {
                return;
            }
            FirRegularClassSymbol regularClassSymbol = TypeUtilsKt.toRegularClassSymbol(coneKotlinTypeFullyExpandedType, firSession);
            if (!ConeBuiltinTypeUtilsKt.isEnum(coneKotlinTypeFullyExpandedType) && regularClassSymbol != null) {
                buildDeepSubstitutionMultimap$fillInDeepSubstitutor(map, firSession, map2, set, coneInferenceContext, coneKotlinTypeFullyExpandedType.getTypeArguments(), regularClassSymbol, checkerContext);
            }
        }
    }
}
