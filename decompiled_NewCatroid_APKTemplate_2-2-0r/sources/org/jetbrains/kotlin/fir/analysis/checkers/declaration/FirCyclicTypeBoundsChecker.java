package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeCyclicTypeBound;
import org.jetbrains.kotlin.fir.symbols.ConeTypeParameterLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeTypesKt;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirCyclicTypeBoundsChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0012\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0016\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\r0\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0012\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00130\u0015*\u00020\u0016H\u0002J\u0012\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00130\u0015*\u00020\u0013H\u0002¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirCyclicTypeBoundsChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "extractTypeParamName", "Lorg/jetbrains/kotlin/name/Name;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "extractTypeParamNames", "", "ref", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "originalBounds", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "unwrapBound", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirCyclicTypeBoundsChecker extends FirDeclarationChecker<FirDeclaration> {
    public static final FirCyclicTypeBoundsChecker INSTANCE = new FirCyclicTypeBoundsChecker();

    private FirCyclicTypeBoundsChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        ArrayList arrayListListOf;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (!(declaration instanceof FirMemberDeclaration) || (declaration instanceof FirConstructor) || (declaration instanceof FirTypeAlias)) {
            return;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        LinkedHashSet linkedHashSet2 = new LinkedHashSet();
        FirMemberDeclaration firMemberDeclaration = (FirMemberDeclaration) declaration;
        List<FirTypeParameterRef> typeParameters = firMemberDeclaration.getTypeParameters();
        final LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(typeParameters, 10)), 16));
        for (FirTypeParameterRef firTypeParameterRef : typeParameters) {
            Name name2 = firTypeParameterRef.getSymbol().getName();
            List<FirResolvedTypeRef> resolvedBounds = firTypeParameterRef.getSymbol().getResolvedBounds();
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = resolvedBounds.iterator();
            while (it.hasNext()) {
                CollectionsKt.addAll(arrayList, INSTANCE.extractTypeParamNames((FirResolvedTypeRef) it.next()));
            }
            Pair pair = TuplesKt.to(name2, CollectionsKt.toSet(arrayList));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        Function1<Name, Set<? extends Name>> function1 = new Function1<Name, Set<? extends Name>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirCyclicTypeBoundsChecker$check$graphFunc$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Set<Name> invoke(Name name3) {
                Intrinsics.checkNotNullParameter(name3, "name");
                return linkedHashMap.getOrDefault(name3, SetsKt.emptySet());
            }
        };
        ArrayList arrayList2 = new ArrayList();
        Iterator<T> it2 = firMemberDeclaration.getTypeParameters().iterator();
        while (it2.hasNext()) {
            check$findCycles(linkedHashSet, arrayList2, function1, linkedHashSet2, ((FirTypeParameterRef) it2.next()).getSymbol().getName());
        }
        if (linkedHashSet2.isEmpty()) {
            return;
        }
        List<FirTypeParameterRef> typeParameters2 = firMemberDeclaration.getTypeParameters();
        ArrayList<FirTypeParameterRef> arrayList3 = new ArrayList();
        for (Object obj : typeParameters2) {
            if (linkedHashSet2.contains(((FirTypeParameterRef) obj).getSymbol().getName())) {
                arrayList3.add(obj);
            }
        }
        for (FirTypeParameterRef firTypeParameterRef2 : arrayList3) {
            if (declaration instanceof FirRegularClass) {
                List<FirTypeRef> listOriginalBounds = INSTANCE.originalBounds(firTypeParameterRef2.getSymbol());
                ArrayList arrayList4 = new ArrayList();
                for (Object obj2 : listOriginalBounds) {
                    if (CollectionsKt.contains(linkedHashSet2, INSTANCE.extractTypeParamName(FirTypeUtilsKt.getConeType((FirTypeRef) obj2)))) {
                        arrayList4.add(obj2);
                    }
                }
                ArrayList arrayList5 = new ArrayList();
                Iterator it3 = arrayList4.iterator();
                while (it3.hasNext()) {
                    KtSourceElement source = ((FirTypeRef) it3.next()).getSource();
                    if (source != null) {
                        arrayList5.add(source);
                    }
                }
                arrayListListOf = arrayList5;
            } else {
                arrayListListOf = CollectionsKt.listOf(firTypeParameterRef2.getSource());
            }
            Iterator it4 = arrayListListOf.iterator();
            while (it4.hasNext()) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, (KtSourceElement) it4.next(), FirErrors.INSTANCE.getCYCLIC_GENERIC_UPPER_BOUND(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }

    private static final void check$findCycles(Set<Name> set, List<Name> list, Function1<? super Name, ? extends Set<Name>> function1, Set<Name> set2, Name name2) {
        if (set.add(name2)) {
            list.add(name2);
            Iterator<T> it = function1.invoke(name2).iterator();
            while (it.hasNext()) {
                check$findCycles(set, list, function1, set2, (Name) it.next());
            }
            list.remove(list.size() - 1);
            return;
        }
        ArrayList arrayList = new ArrayList();
        boolean z = false;
        for (Object obj : list) {
            if (!z) {
                if (Intrinsics.areEqual((Name) obj, name2)) {
                    arrayList.add(obj);
                    z = true;
                }
            } else {
                arrayList.add(obj);
            }
        }
        set2.addAll(arrayList);
    }

    private final List<FirTypeRef> originalBounds(FirTypeParameterSymbol firTypeParameterSymbol) {
        List<FirResolvedTypeRef> resolvedBounds = firTypeParameterSymbol.getResolvedBounds();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = resolvedBounds.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, INSTANCE.unwrapBound((FirResolvedTypeRef) it.next()));
        }
        return arrayList;
    }

    private final List<FirTypeRef> unwrapBound(FirTypeRef firTypeRef) {
        if (firTypeRef instanceof FirErrorTypeRef) {
            FirErrorTypeRef firErrorTypeRef = (FirErrorTypeRef) firTypeRef;
            if (firErrorTypeRef.getDiagnostic() instanceof ConeCyclicTypeBound) {
                ConeDiagnostic diagnostic = firErrorTypeRef.getDiagnostic();
                Intrinsics.checkNotNull(diagnostic, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.diagnostics.ConeCyclicTypeBound");
                return ((ConeCyclicTypeBound) diagnostic).getBounds();
            }
        }
        return CollectionsKt.listOf(firTypeRef);
    }

    private final Set<Name> extractTypeParamNames(FirTypeRef ref) {
        List<FirTypeRef> listUnwrapBound = unwrapBound(ref);
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = listUnwrapBound.iterator();
        while (it.hasNext()) {
            Name nameExtractTypeParamName = INSTANCE.extractTypeParamName(FirTypeUtilsKt.getConeType((FirTypeRef) it.next()));
            if (nameExtractTypeParamName != null) {
                arrayList.add(nameExtractTypeParamName);
            }
        }
        return CollectionsKt.toSet(arrayList);
    }

    private final Name extractTypeParamName(ConeKotlinType type) {
        ConeTypeParameterLookupTag lookupTag;
        ConeSimpleKotlinType coneSimpleKotlinTypeUnwrapDefinitelyNotNull = ConeTypesKt.unwrapDefinitelyNotNull(ConeTypeUtilsKt.lowerBoundIfFlexible(type));
        ConeTypeParameterType coneTypeParameterType = coneSimpleKotlinTypeUnwrapDefinitelyNotNull instanceof ConeTypeParameterType ? (ConeTypeParameterType) coneSimpleKotlinTypeUnwrapDefinitelyNotNull : null;
        if (coneTypeParameterType == null || (lookupTag = coneTypeParameterType.getLookupTag()) == null) {
            return null;
        }
        return lookupTag.getName();
    }
}
