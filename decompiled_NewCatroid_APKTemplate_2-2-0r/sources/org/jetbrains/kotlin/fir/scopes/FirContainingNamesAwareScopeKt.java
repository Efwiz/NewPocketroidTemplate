package org.jetbrains.kotlin.fir.scopes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirContainingNamesAwareScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003\u001a\u0014\u0010\u0004\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00050\u0001*\u00020\u0003\u001a\"\u0010\u0006\u001a\u00020\u0007*\u00020\u00032\u0016\u0010\b\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n\u0012\u0004\u0012\u00020\u00070\t\u001a\u001e\u0010\u000b\u001a\u00020\u0007*\u00020\u00032\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00070\t\u001a\"\u0010\f\u001a\u00020\u0007*\u00020\u00032\u0016\u0010\b\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0012\u0004\u0012\u00020\u00070\t¨\u0006\r"}, d2 = {"collectAllFunctions", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", "collectAllProperties", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "processAllCallables", "", "processor", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "processAllFunctions", "processAllProperties", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirContainingNamesAwareScopeKt {
    public static final void processAllFunctions(FirContainingNamesAwareScope firContainingNamesAwareScope, Function1<? super FirNamedFunctionSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(firContainingNamesAwareScope, "<this>");
        Intrinsics.checkNotNullParameter(processor, "processor");
        Iterator<Name> it = firContainingNamesAwareScope.getCallableNames().iterator();
        while (it.hasNext()) {
            firContainingNamesAwareScope.processFunctionsByName(it.next(), processor);
        }
    }

    public static final void processAllProperties(FirContainingNamesAwareScope firContainingNamesAwareScope, Function1<? super FirVariableSymbol<?>, Unit> processor) {
        Intrinsics.checkNotNullParameter(firContainingNamesAwareScope, "<this>");
        Intrinsics.checkNotNullParameter(processor, "processor");
        Iterator<Name> it = firContainingNamesAwareScope.getCallableNames().iterator();
        while (it.hasNext()) {
            firContainingNamesAwareScope.processPropertiesByName(it.next(), processor);
        }
    }

    public static final void processAllCallables(FirContainingNamesAwareScope firContainingNamesAwareScope, Function1<? super FirCallableSymbol<?>, Unit> processor) {
        Intrinsics.checkNotNullParameter(firContainingNamesAwareScope, "<this>");
        Intrinsics.checkNotNullParameter(processor, "processor");
        for (Name name2 : firContainingNamesAwareScope.getCallableNames()) {
            firContainingNamesAwareScope.processFunctionsByName(name2, processor);
            firContainingNamesAwareScope.processPropertiesByName(name2, processor);
        }
    }

    public static final Collection<FirVariableSymbol<?>> collectAllProperties(FirContainingNamesAwareScope firContainingNamesAwareScope) {
        Intrinsics.checkNotNullParameter(firContainingNamesAwareScope, "<this>");
        ArrayList arrayList = new ArrayList();
        processAllProperties(firContainingNamesAwareScope, new FirContainingNamesAwareScopeKt$collectAllProperties$1$1(arrayList));
        return arrayList;
    }

    public static final Collection<FirNamedFunctionSymbol> collectAllFunctions(FirContainingNamesAwareScope firContainingNamesAwareScope) {
        Intrinsics.checkNotNullParameter(firContainingNamesAwareScope, "<this>");
        ArrayList arrayList = new ArrayList();
        processAllFunctions(firContainingNamesAwareScope, new FirContainingNamesAwareScopeKt$collectAllFunctions$1$1(arrayList));
        return arrayList;
    }
}
