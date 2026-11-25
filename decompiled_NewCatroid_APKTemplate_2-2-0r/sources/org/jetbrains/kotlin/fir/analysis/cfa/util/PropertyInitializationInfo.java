package org.jetbrains.kotlin.fir.analysis.cfa.util;

import java.util.Collection;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRange;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.kotlinx.collections.immutable.ExtensionsKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: PropertyInitializationInfo.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u000b2\u000e\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u000bB\u001b\u0012\u0014\b\u0002\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\u0010\u0006R,\u0010\u0007\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0004\u0012\u00020\u00000\bX\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/cfa/util/PropertyInitializationInfo;", "Lorg/jetbrains/kotlin/fir/analysis/cfa/util/EventOccurrencesRangeInfo;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "map", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentMap;", "Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;", "(Lkotlinx/collections/immutable/PersistentMap;)V", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lkotlin/Function1;", "getConstructor", "()Lkotlin/jvm/functions/Function1;", "Companion", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PropertyInitializationInfo extends EventOccurrencesRangeInfo<PropertyInitializationInfo, FirPropertySymbol> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final PropertyInitializationInfo EMPTY = new PropertyInitializationInfo(0 == true ? 1 : 0, 1, 0 == true ? 1 : 0);
    private final Function1<PersistentMap<FirPropertySymbol, ? extends EventOccurrencesRange>, PropertyInitializationInfo> constructor;

    /* JADX WARN: Multi-variable type inference failed */
    public PropertyInitializationInfo() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.ControlFlowInfo, java.util.Map
    public final /* bridge */ boolean containsKey(Object obj) {
        if (obj instanceof FirPropertySymbol) {
            return containsKey((FirPropertySymbol) obj);
        }
        return false;
    }

    public /* bridge */ boolean containsKey(FirPropertySymbol firPropertySymbol) {
        return super.containsKey(firPropertySymbol);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.EventOccurrencesRangeInfo, org.jetbrains.kotlin.fir.analysis.cfa.util.ControlFlowInfo, java.util.Map
    public /* bridge */ /* synthetic */ Set entrySet() {
        return getEntries();
    }

    @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.ControlFlowInfo, java.util.Map
    public final /* bridge */ EventOccurrencesRange get(Object obj) {
        if (obj instanceof FirPropertySymbol) {
            return get((FirPropertySymbol) obj);
        }
        return null;
    }

    public /* bridge */ EventOccurrencesRange get(FirPropertySymbol firPropertySymbol) {
        return (EventOccurrencesRange) super.get(firPropertySymbol);
    }

    public final /* bridge */ EventOccurrencesRange getOrDefault(Object obj, EventOccurrencesRange eventOccurrencesRange) {
        return !(obj instanceof FirPropertySymbol) ? eventOccurrencesRange : getOrDefault((FirPropertySymbol) obj, eventOccurrencesRange);
    }

    public /* bridge */ EventOccurrencesRange getOrDefault(FirPropertySymbol firPropertySymbol, EventOccurrencesRange eventOccurrencesRange) {
        return (EventOccurrencesRange) super.getOrDefault((Object) firPropertySymbol, (Object) eventOccurrencesRange);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.EventOccurrencesRangeInfo, org.jetbrains.kotlin.fir.analysis.cfa.util.ControlFlowInfo, java.util.Map
    public /* bridge */ /* synthetic */ Set keySet() {
        return getKeys();
    }

    @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.EventOccurrencesRangeInfo, org.jetbrains.kotlin.fir.analysis.cfa.util.ControlFlowInfo, java.util.Map
    public /* bridge */ /* synthetic */ Collection values() {
        return getValues();
    }

    public /* synthetic */ PropertyInitializationInfo(PersistentMap persistentMap, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? ExtensionsKt.persistentMapOf(new Pair[0]) : persistentMap);
    }

    /* JADX INFO: compiled from: PropertyInitializationInfo.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/cfa/util/PropertyInitializationInfo$Companion;", "", "()V", "EMPTY", "Lorg/jetbrains/kotlin/fir/analysis/cfa/util/PropertyInitializationInfo;", "getEMPTY", "()Lorg/jetbrains/kotlin/fir/analysis/cfa/util/PropertyInitializationInfo;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final PropertyInitializationInfo getEMPTY() {
            return PropertyInitializationInfo.EMPTY;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PropertyInitializationInfo(PersistentMap<FirPropertySymbol, ? extends EventOccurrencesRange> map) {
        super(map);
        Intrinsics.checkNotNullParameter(map, "map");
        this.constructor = PropertyInitializationInfo$constructor$1.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.cfa.util.ControlFlowInfo
    protected Function1<PersistentMap<FirPropertySymbol, ? extends EventOccurrencesRange>, PropertyInitializationInfo> getConstructor() {
        return this.constructor;
    }
}
