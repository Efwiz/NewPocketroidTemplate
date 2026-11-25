package org.jetbrains.kotlin.fir.analysis.cfa.util;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRange;
import org.jetbrains.kotlin.fir.analysis.cfa.util.EventOccurrencesRangeInfo;
import org.jetbrains.kotlin.kotlinx.collections.immutable.ExtensionsKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap;

/* JADX INFO: compiled from: PropertyInitializationInfo.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000*\u0014\b\u0000\u0010\u0001*\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0000*\b\b\u0001\u0010\u0002*\u00020\u00032\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00050\u0004B\u001b\u0012\u0014\b\u0002\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\u00050\u0007¢\u0006\u0002\u0010\bJ\u0015\u0010\t\u001a\u00028\u00002\u0006\u0010\n\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u000bJ0\u0010\f\u001a\u00028\u00002\u0006\u0010\n\u001a\u00028\u00002\u0018\u0010\r\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u000eH\u0082\b¢\u0006\u0002\u0010\u000fJ\u0015\u0010\u0010\u001a\u00028\u00002\u0006\u0010\n\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/cfa/util/EventOccurrencesRangeInfo;", ExifInterface.LONGITUDE_EAST, "K", "", "Lorg/jetbrains/kotlin/fir/analysis/cfa/util/ControlFlowInfo;", "Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;", "map", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentMap;", "(Lkotlinx/collections/immutable/PersistentMap;)V", "merge", "other", "(Lorg/jetbrains/kotlin/fir/analysis/cfa/util/EventOccurrencesRangeInfo;)Lorg/jetbrains/kotlin/fir/analysis/cfa/util/EventOccurrencesRangeInfo;", "operation", "op", "Lkotlin/Function2;", "(Lorg/jetbrains/kotlin/fir/analysis/cfa/util/EventOccurrencesRangeInfo;Lkotlin/jvm/functions/Function2;)Lorg/jetbrains/kotlin/fir/analysis/cfa/util/EventOccurrencesRangeInfo;", "plus", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class EventOccurrencesRangeInfo<E extends EventOccurrencesRangeInfo<E, K>, K> extends ControlFlowInfo<E, K, EventOccurrencesRange> {
    /* JADX WARN: Multi-variable type inference failed */
    public EventOccurrencesRangeInfo() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.ControlFlowInfo, java.util.Map
    public final /* bridge */ boolean containsValue(Object obj) {
        if (obj instanceof EventOccurrencesRange) {
            return containsValue((EventOccurrencesRange) obj);
        }
        return false;
    }

    public /* bridge */ boolean containsValue(EventOccurrencesRange eventOccurrencesRange) {
        return super.containsValue(eventOccurrencesRange);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.ControlFlowInfo, java.util.Map
    public /* bridge */ /* synthetic */ Set entrySet() {
        return getEntries();
    }

    @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.ControlFlowInfo, java.util.Map
    public /* bridge */ /* synthetic */ Set keySet() {
        return getKeys();
    }

    @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.ControlFlowInfo, java.util.Map
    public /* bridge */ /* synthetic */ Collection values() {
        return getValues();
    }

    public /* synthetic */ EventOccurrencesRangeInfo(PersistentMap persistentMap, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? ExtensionsKt.persistentMapOf(new Pair[0]) : persistentMap);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EventOccurrencesRangeInfo(PersistentMap<K, ? extends EventOccurrencesRange> map) {
        super(map);
        Intrinsics.checkNotNullParameter(map, "map");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.ControlFlowInfo
    public E plus(E other) {
        Intrinsics.checkNotNullParameter(other, "other");
        if (isEmpty()) {
            return other;
        }
        if (other.isEmpty()) {
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.analysis.cfa.util.EventOccurrencesRangeInfo");
            return this;
        }
        Intrinsics.checkNotNull(this, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.analysis.cfa.util.EventOccurrencesRangeInfo");
        Map map = this;
        for (Object obj : CollectionsKt.union(keySet(), other.keySet())) {
            EventOccurrencesRange eventOccurrencesRange = (EventOccurrencesRange) get(obj);
            if (eventOccurrencesRange == null) {
                eventOccurrencesRange = EventOccurrencesRange.ZERO;
            }
            EventOccurrencesRange eventOccurrencesRange2 = (EventOccurrencesRange) other.get(obj);
            if (eventOccurrencesRange2 == null) {
                eventOccurrencesRange2 = EventOccurrencesRange.ZERO;
            }
            map = (EventOccurrencesRangeInfo) map.put(obj, eventOccurrencesRange.plus(eventOccurrencesRange2));
        }
        return (E) map;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.ControlFlowInfo
    public E merge(E other) {
        Intrinsics.checkNotNullParameter(other, "other");
        Intrinsics.checkNotNull(this, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.analysis.cfa.util.EventOccurrencesRangeInfo");
        E e = this;
        for (Object obj : CollectionsKt.union(keySet(), other.keySet())) {
            EventOccurrencesRange eventOccurrencesRange = (EventOccurrencesRange) get(obj);
            if (eventOccurrencesRange == null) {
                eventOccurrencesRange = EventOccurrencesRange.ZERO;
            }
            EventOccurrencesRange eventOccurrencesRange2 = (EventOccurrencesRange) other.get(obj);
            if (eventOccurrencesRange2 == null) {
                eventOccurrencesRange2 = EventOccurrencesRange.ZERO;
            }
            e = (E) e.put(obj, eventOccurrencesRange.or(eventOccurrencesRange2));
        }
        return e;
    }
}
