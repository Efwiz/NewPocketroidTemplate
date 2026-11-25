package org.jetbrains.kotlin.fir.declarations;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.SymbolInternals;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectActualCompatibility;

/* JADX INFO: compiled from: ExpectActualAttributes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0014\u0010\u0011\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0003*\u0006\u0012\u0002\b\u00030\u0003\u001a\u0014\u0010\u0011\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0012*\u0006\u0012\u0002\b\u00030\u0012\"\u0085\u0001\u0010\u0006\u001a*\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0002\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0004\u0018\u00010\u0001j\u0004\u0018\u0001`\u0005*\u00020\u00072.\u0010\u0000\u001a*\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0002\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0004\u0018\u00010\u0001j\u0004\u0018\u0001`\u00058F@FX\u0087\u008e\u0002¢\u0006\u0018\n\u0004\b\u000e\u0010\u000f\u0012\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r\"A\u0010\u0006\u001a*\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0002\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0004\u0018\u00010\u0001j\u0004\u0018\u0001`\u0005*\u0006\u0012\u0002\b\u00030\u00038F¢\u0006\u0006\u001a\u0004\b\n\u0010\u0010*J\u0010\u0013\"\"\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0002\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u00040\u00012\"\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0002\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u00040\u0001¨\u0006\u0014"}, d2 = {"<set-?>", "", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility;", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "", "Lorg/jetbrains/kotlin/fir/declarations/ExpectForActualData;", "expectForActual", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "getExpectForActual$annotations", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;)V", "getExpectForActual", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;)Ljava/util/Map;", "setExpectForActual", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;Ljava/util/Map;)V", "expectForActual$delegate", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationDataRegistry$DeclarationDataAccessor;", "(Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;)Ljava/util/Map;", "getSingleExpectForActualOrNull", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "ExpectForActualData", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ExpectActualAttributesKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ExpectActualAttributesKt.class, Constants.TYPE_TREE), "expectForActual", "getExpectForActual(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;)Ljava/util/Map;"))};
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor expectForActual$delegate = FirDeclarationDataRegistry.INSTANCE.data(ExpectForActualAttributeKey.INSTANCE);

    @SymbolInternals
    public static /* synthetic */ void getExpectForActual$annotations(FirDeclaration firDeclaration) {
    }

    public static final Map<ExpectActualCompatibility<FirBasedSymbol<?>>, List<FirBasedSymbol<?>>> getExpectForActual(FirDeclaration firDeclaration) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        return (Map) expectForActual$delegate.getValue(firDeclaration, $$delegatedProperties[0]);
    }

    public static final void setExpectForActual(FirDeclaration firDeclaration, Map<ExpectActualCompatibility<FirBasedSymbol<?>>, ? extends List<? extends FirBasedSymbol<?>>> map) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        expectForActual$delegate.setValue(firDeclaration, $$delegatedProperties[0], map);
    }

    public static final FirFunctionSymbol<?> getSingleExpectForActualOrNull(FirFunctionSymbol<?> firFunctionSymbol) {
        Intrinsics.checkNotNullParameter(firFunctionSymbol, "<this>");
        FirBasedSymbol<?> singleExpectForActualOrNull = getSingleExpectForActualOrNull((FirBasedSymbol<?>) firFunctionSymbol);
        if (singleExpectForActualOrNull instanceof FirFunctionSymbol) {
            return (FirFunctionSymbol) singleExpectForActualOrNull;
        }
        return null;
    }

    public static final FirBasedSymbol<?> getSingleExpectForActualOrNull(FirBasedSymbol<?> firBasedSymbol) {
        Collection<List<FirBasedSymbol<?>>> collectionValues;
        List list;
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        Map<ExpectActualCompatibility<FirBasedSymbol<?>>, List<FirBasedSymbol<?>>> expectForActual = getExpectForActual(firBasedSymbol);
        if (expectForActual == null || (collectionValues = expectForActual.values()) == null || (list = (List) CollectionsKt.singleOrNull(collectionValues)) == null) {
            return null;
        }
        return (FirBasedSymbol) CollectionsKt.singleOrNull(list);
    }

    public static final Map<ExpectActualCompatibility<FirBasedSymbol<?>>, List<FirBasedSymbol<?>>> getExpectForActual(FirBasedSymbol<?> firBasedSymbol) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        FirLazyDeclarationResolverKt.lazyResolveToPhase(firBasedSymbol, FirResolvePhase.EXPECT_ACTUAL_MATCHING);
        return getExpectForActual(firBasedSymbol.getFir());
    }
}
