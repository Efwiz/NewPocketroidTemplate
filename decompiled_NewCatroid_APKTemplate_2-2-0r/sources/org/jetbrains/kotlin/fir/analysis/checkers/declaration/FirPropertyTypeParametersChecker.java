package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjectionKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirPropertyTypeParametersChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirPropertyTypeParametersChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirPropertyChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirPropertyTypeParametersChecker extends FirDeclarationChecker<FirProperty> {
    public static final FirPropertyTypeParametersChecker INSTANCE = new FirPropertyTypeParametersChecker();

    private FirPropertyTypeParametersChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirProperty declaration, CheckerContext context, DiagnosticReporter reporter) {
        FirTypeRef typeRef;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        List<FirTypeParameter> typeParameters = declaration.getTypeParameters();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(typeParameters, 10)), 16));
        for (FirTypeParameter firTypeParameter : typeParameters) {
            Pair pair = TuplesKt.to(firTypeParameter.getName(), firTypeParameter.getSymbol().getResolvedBounds());
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        HashSet hashSet = new HashSet();
        FirReceiverParameter receiverParameter = declaration.getReceiverParameter();
        if (receiverParameter != null && (typeRef = receiverParameter.getTypeRef()) != null) {
            check$collectAllTypes(hashSet, linkedHashMap, FirTypeUtilsKt.getConeType(typeRef));
        }
        Iterator<T> it = declaration.getContextReceivers().iterator();
        while (it.hasNext()) {
            check$collectAllTypes(hashSet, linkedHashMap, FirTypeUtilsKt.getConeType(((FirContextReceiver) it.next()).getTypeRef()));
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : hashSet) {
            if (obj instanceof ConeTypeParameterType) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            arrayList3.add(((ConeTypeParameterType) it2.next()).getLookupTag().getName());
        }
        ArrayList arrayList4 = arrayList3;
        if (declaration.getIsLocal()) {
            return;
        }
        List<FirTypeParameter> typeParameters2 = declaration.getTypeParameters();
        ArrayList arrayList5 = new ArrayList();
        for (Object obj2 : typeParameters2) {
            if (!arrayList4.contains(((FirTypeParameter) obj2).getName())) {
                arrayList5.add(obj2);
            }
        }
        Iterator it3 = arrayList5.iterator();
        while (it3.hasNext()) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, ((FirTypeParameter) it3.next()).getSource(), FirErrors.INSTANCE.getTYPE_PARAMETER_OF_PROPERTY_NOT_USED_IN_RECEIVER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }

    private static final void check$collectAllTypes(HashSet<ConeKotlinType> hashSet, Map<Name, ? extends List<? extends FirResolvedTypeRef>> map, ConeKotlinType coneKotlinType) {
        List<? extends FirResolvedTypeRef> list;
        if (hashSet.add(coneKotlinType)) {
            for (ConeTypeProjection coneTypeProjection : coneKotlinType.getTypeArguments()) {
                ConeKotlinType type = ConeTypeProjectionKt.getType(coneTypeProjection);
                if (type != null) {
                    check$collectAllTypes(hashSet, map, type);
                }
            }
            if (!(coneKotlinType instanceof ConeTypeParameterType) || (list = map.get(((ConeTypeParameterType) coneKotlinType).getLookupTag().getName())) == null) {
                return;
            }
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                check$collectAllTypes(hashSet, map, FirTypeUtilsKt.getConeType((FirResolvedTypeRef) it.next()));
            }
        }
    }
}
