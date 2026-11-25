package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.scopes.impl.FirNestedClassifierScopeKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ProjectionKind;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.utils.DFS;

/* JADX INFO: compiled from: FirFiniteBoundRestrictionChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J*\u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\b0\u00062\u0006\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000bH\u0002J \u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J*\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00072\u0018\u0010\u0015\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\b0\u0006H\u0002¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirFiniteBoundRestrictionChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirRegularClassChecker;", "()V", "buildTypeEdges", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "", "declaration", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "check", "", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "isInCycle", "", "start", "edges", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirFiniteBoundRestrictionChecker extends FirDeclarationChecker<FirRegularClass> {
    public static final FirFiniteBoundRestrictionChecker INSTANCE = new FirFiniteBoundRestrictionChecker();

    private FirFiniteBoundRestrictionChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirRegularClass declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (declaration.getTypeParameters().isEmpty()) {
            return;
        }
        Map<ConeKotlinType, Set<ConeKotlinType>> mapBuildTypeEdges = buildTypeEdges(declaration, context.getSession());
        Set<ConeKotlinType> setKeySet = mapBuildTypeEdges.keySet();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (Object obj : setKeySet) {
            if (INSTANCE.isInCycle((ConeKotlinType) obj, mapBuildTypeEdges)) {
                linkedHashSet.add(obj);
            }
        }
        LinkedHashSet linkedHashSet2 = linkedHashSet;
        if (linkedHashSet2.isEmpty()) {
            return;
        }
        for (FirTypeParameterRef firTypeParameterRef : declaration.getTypeParameters()) {
            if (linkedHashSet2.remove(FirNestedClassifierScopeKt.toConeType(firTypeParameterRef))) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firTypeParameterRef.getSource(), FirErrors.INSTANCE.getFINITE_BOUNDS_VIOLATION(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return;
            }
        }
        LinkedHashSet linkedHashSet3 = new LinkedHashSet();
        Iterator it = linkedHashSet2.iterator();
        while (it.hasNext()) {
            FirClassifierSymbol<?> symbol = TypeUtilsKt.toSymbol((ConeKotlinType) it.next(), context.getSession());
            FirTypeParameterSymbol firTypeParameterSymbol = symbol instanceof FirTypeParameterSymbol ? (FirTypeParameterSymbol) symbol : null;
            if (firTypeParameterSymbol != null) {
                linkedHashSet3.add(firTypeParameterSymbol);
            }
        }
        LinkedHashSet linkedHashSet4 = linkedHashSet3;
        if (!(linkedHashSet4 instanceof Collection) || !linkedHashSet4.isEmpty()) {
            Iterator it2 = linkedHashSet4.iterator();
            while (it2.hasNext()) {
                if (((FirTypeParameterSymbol) it2.next()).getSource() != null) {
                    return;
                }
            }
        }
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(linkedHashSet4, 10));
        Iterator it3 = linkedHashSet4.iterator();
        while (it3.hasNext()) {
            arrayList.add(((FirTypeParameterSymbol) it3.next()).getContainingDeclarationSymbol());
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getFINITE_BOUNDS_VIOLATION_IN_JAVA(), arrayList, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
    }

    private final Map<ConeKotlinType, Set<ConeKotlinType>> buildTypeEdges(FirRegularClass declaration, FirSession session) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<T> it = declaration.getTypeParameters().iterator();
        while (it.hasNext()) {
            buildTypeEdges$visit(session, linkedHashSet, linkedHashMap, FirNestedClassifierScopeKt.toConeType((FirTypeParameterRef) it.next()));
        }
        return linkedHashMap;
    }

    private static final void buildTypeEdges$visit(FirSession firSession, Set<FirClassifierSymbol<?>> set, Map<ConeKotlinType, Set<ConeKotlinType>> map, ConeKotlinType coneKotlinType) {
        List<FirTypeParameterSymbol> typeParameterSymbols;
        final LinkedHashSet<ConeKotlinType> linkedHashSet = new LinkedHashSet();
        Iterator<ConeClassLikeType> it = FirHelpersKt.collectUpperBounds(coneKotlinType).iterator();
        while (it.hasNext()) {
            ConeTypeUtilsKt.forEachType(it.next(), new Function1<ConeKotlinType, Unit>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirFiniteBoundRestrictionChecker$buildTypeEdges$visit$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ConeKotlinType coneKotlinType2) {
                    invoke2(coneKotlinType2);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ConeKotlinType it2) {
                    Intrinsics.checkNotNullParameter(it2, "it");
                    linkedHashSet.add(it2);
                }
            });
        }
        for (ConeKotlinType coneKotlinType2 : linkedHashSet) {
            FirClassifierSymbol<?> symbol = TypeUtilsKt.toSymbol(coneKotlinType2, firSession);
            if (symbol != null && (typeParameterSymbols = FirHelpersKt.getTypeParameterSymbols(symbol)) != null) {
                if (set.add(symbol)) {
                    Iterator<T> it2 = typeParameterSymbols.iterator();
                    while (it2.hasNext()) {
                        buildTypeEdges$visit(firSession, set, map, FirNestedClassifierScopeKt.toConeType((FirTypeParameterSymbol) it2.next()));
                    }
                }
                if (typeParameterSymbols.size() == coneKotlinType2.getTypeArguments().length) {
                    int size = typeParameterSymbols.size();
                    for (int i = 0; i < size; i++) {
                        if (coneKotlinType2.getTypeArguments()[i].getKind() != ProjectionKind.INVARIANT) {
                            ConeKotlinType coneType = FirNestedClassifierScopeKt.toConeType(typeParameterSymbols.get(i));
                            LinkedHashSet linkedHashSet2 = map.get(coneKotlinType);
                            if (linkedHashSet2 == null) {
                                linkedHashSet2 = new LinkedHashSet();
                                map.put(coneKotlinType, linkedHashSet2);
                            }
                            linkedHashSet2.add(coneType);
                            if (map.get(coneType) == null) {
                                map.put(coneType, new LinkedHashSet());
                            }
                        }
                    }
                }
            }
        }
    }

    private final boolean isInCycle(final ConeKotlinType start, final Map<ConeKotlinType, ? extends Set<? extends ConeKotlinType>> edges) {
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        DFS.dfs(CollectionsKt.listOf(start), new DFS.Neighbors() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirFiniteBoundRestrictionChecker$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
            public final Iterable getNeighbors(Object obj) {
                return FirFiniteBoundRestrictionChecker.isInCycle$lambda$8(edges, (ConeKotlinType) obj);
            }
        }, new DFS.VisitedWithSet<ConeKotlinType>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirFiniteBoundRestrictionChecker$isInCycle$dfsVisited$1
            @Override // org.jetbrains.kotlin.utils.DFS.VisitedWithSet, org.jetbrains.kotlin.utils.DFS.Visited
            public boolean checkAndMarkVisited(ConeKotlinType current) {
                Intrinsics.checkNotNullParameter(current, "current");
                boolean zCheckAndMarkVisited = super.checkAndMarkVisited(current);
                if (!zCheckAndMarkVisited && Intrinsics.areEqual(current, start)) {
                    booleanRef.element = true;
                }
                return zCheckAndMarkVisited;
            }
        }, new DFS.AbstractNodeHandler<ConeKotlinType, Unit>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirFiniteBoundRestrictionChecker$isInCycle$dfsHandler$1
            /* JADX INFO: renamed from: result, reason: collision with other method in class */
            public void m6778result() {
            }

            @Override // org.jetbrains.kotlin.utils.DFS.NodeHandler
            public /* bridge */ /* synthetic */ Object result() {
                m6778result();
                return Unit.INSTANCE;
            }
        });
        return booleanRef.element;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Iterable isInCycle$lambda$8(Map edges, ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(edges, "$edges");
        Iterable iterableEmptyList = (Set) edges.get(coneKotlinType);
        if (iterableEmptyList == null) {
            iterableEmptyList = CollectionsKt.emptyList();
        }
        return iterableEmptyList;
    }
}
