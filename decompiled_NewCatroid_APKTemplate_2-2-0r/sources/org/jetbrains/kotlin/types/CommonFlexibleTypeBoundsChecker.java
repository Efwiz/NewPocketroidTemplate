package org.jetbrains.kotlin.types;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: CommonFlexibleTypeBoundsChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0007\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\n\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\u0005R\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001d\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/types/CommonFlexibleTypeBoundsChecker;", "", "()V", "baseTypesToMutableEquivalent", "", "Lorg/jetbrains/kotlin/name/FqName;", "getBaseTypesToMutableEquivalent", "()Ljava/util/Map;", "mutableToBaseMap", "getMutableToBaseMap", "getBaseBoundFqNameByMutability", "fqName", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CommonFlexibleTypeBoundsChecker {
    public static final CommonFlexibleTypeBoundsChecker INSTANCE = new CommonFlexibleTypeBoundsChecker();
    private static final Map<FqName, FqName> baseTypesToMutableEquivalent;
    private static final Map<FqName, FqName> mutableToBaseMap;

    private CommonFlexibleTypeBoundsChecker() {
    }

    public final Map<FqName, FqName> getBaseTypesToMutableEquivalent() {
        return baseTypesToMutableEquivalent;
    }

    static {
        Map<FqName, FqName> mapMapOf = MapsKt.mapOf(TuplesKt.to(StandardNames.FqNames.iterable, StandardNames.FqNames.mutableIterable), TuplesKt.to(StandardNames.FqNames.iterator, StandardNames.FqNames.mutableIterator), TuplesKt.to(StandardNames.FqNames.listIterator, StandardNames.FqNames.mutableListIterator), TuplesKt.to(StandardNames.FqNames.list, StandardNames.FqNames.mutableList), TuplesKt.to(StandardNames.FqNames.collection, StandardNames.FqNames.mutableCollection), TuplesKt.to(StandardNames.FqNames.set, StandardNames.FqNames.mutableSet), TuplesKt.to(StandardNames.FqNames.map, StandardNames.FqNames.mutableMap), TuplesKt.to(StandardNames.FqNames.mapEntry, StandardNames.FqNames.mutableMapEntry));
        baseTypesToMutableEquivalent = mapMapOf;
        Set<Map.Entry<FqName, FqName>> setEntrySet = mapMapOf.entrySet();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(setEntrySet, 10)), 16));
        Iterator<T> it = setEntrySet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            linkedHashMap.put((FqName) entry.getValue(), (FqName) entry.getKey());
        }
        mutableToBaseMap = linkedHashMap;
    }

    public final Map<FqName, FqName> getMutableToBaseMap() {
        return mutableToBaseMap;
    }

    public final FqName getBaseBoundFqNameByMutability(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return baseTypesToMutableEquivalent.containsKey(fqName) ? fqName : mutableToBaseMap.get(fqName);
    }
}
