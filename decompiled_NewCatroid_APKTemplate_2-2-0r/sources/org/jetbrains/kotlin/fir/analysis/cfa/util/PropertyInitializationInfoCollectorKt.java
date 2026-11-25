package org.jetbrains.kotlin.fir.analysis.cfa.util;

import androidx.exifinterface.media.ExifInterface;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRange;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.EdgeLabel;
import org.jetbrains.kotlin.kotlinx.collections.immutable.ExtensionsKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap;

/* JADX INFO: compiled from: PropertyInitializationInfoCollector.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\u001aw\u0010\u0000\u001a\u0018\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u00030\u0001j\b\u0012\u0004\u0012\u0002H\u0003`\u0004\"\u001a\b\u0000\u0010\u0003*\u0014\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u0006\u0012\u0004\u0012\u00020\u00070\u0005\"\b\b\u0001\u0010\u0006*\u00020\b2\u001c\u0010\t\u001a\u0018\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u00030\u0001j\b\u0012\u0004\u0012\u0002H\u0003`\u00042\u0006\u0010\n\u001a\u0002H\u00062\u0006\u0010\u000b\u001a\u00020\u0007H\u0000¢\u0006\u0002\u0010\f\u001aw\u0010\r\u001a\u0018\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u00030\u0001j\b\u0012\u0004\u0012\u0002H\u0003`\u0004\"\u001a\b\u0000\u0010\u0003*\u0014\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u0006\u0012\u0004\u0012\u00020\u00070\u0005\"\b\b\u0001\u0010\u0006*\u00020\b2\u001c\u0010\t\u001a\u0018\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u00030\u0001j\b\u0012\u0004\u0012\u0002H\u0003`\u00042\u0006\u0010\n\u001a\u0002H\u00062\u0006\u0010\u000b\u001a\u00020\u0007H\u0002¢\u0006\u0002\u0010\f\u001ao\u0010\u000e\u001a\u0018\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u00030\u0001j\b\u0012\u0004\u0012\u0002H\u0003`\u0004\"\u001a\b\u0000\u0010\u0003*\u0014\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u0006\u0012\u0004\u0012\u00020\u00070\u0005\"\b\b\u0001\u0010\u0006*\u00020\b2\u001c\u0010\t\u001a\u0018\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u00030\u0001j\b\u0012\u0004\u0012\u0002H\u0003`\u00042\u0006\u0010\n\u001a\u0002H\u0006H\u0002¢\u0006\u0002\u0010\u000f\u001a\u0084\u0001\u0010\u0010\u001a\u0018\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u00030\u0001j\b\u0012\u0004\u0012\u0002H\u0003`\u0004\"\u001a\b\u0000\u0010\u0003*\u0014\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u0006\u0012\u0004\u0012\u00020\u00070\u0005\"\b\b\u0001\u0010\u0006*\u00020\b2\u001c\u0010\t\u001a\u0018\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u00030\u0001j\b\u0012\u0004\u0012\u0002H\u0003`\u00042\u0006\u0010\n\u001a\u0002H\u00062\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0012H\u0082\b¢\u0006\u0002\u0010\u0013¨\u0006\u0014"}, d2 = {"addRange", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentMap;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EdgeLabel;", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/fir/analysis/cfa/util/PathAwareControlFlowInfo;", "Lorg/jetbrains/kotlin/fir/analysis/cfa/util/ControlFlowInfo;", "K", "Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;", "", "pathAwareInfo", ASTExpr.DEFAULT_MAP_KEY_NAME, "range", "(Lkotlinx/collections/immutable/PersistentMap;Ljava/lang/Object;Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;)Lkotlinx/collections/immutable/PersistentMap;", "overwriteRange", "removeRange", "(Lkotlinx/collections/immutable/PersistentMap;Ljava/lang/Object;)Lkotlinx/collections/immutable/PersistentMap;", "updateRange", "computeNewRange", "Lkotlin/Function1;", "(Lkotlinx/collections/immutable/PersistentMap;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Lkotlinx/collections/immutable/PersistentMap;", "checkers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PropertyInitializationInfoCollectorKt {
    public static final <S extends ControlFlowInfo<S, K, EventOccurrencesRange>, K> PersistentMap<EdgeLabel, S> addRange(PersistentMap<EdgeLabel, ? extends S> pathAwareInfo, K key, EventOccurrencesRange range) {
        Intrinsics.checkNotNullParameter(pathAwareInfo, "pathAwareInfo");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(range, "range");
        Map map = (PersistentMap<EdgeLabel, S>) ExtensionsKt.persistentMapOf(new Pair[0]);
        for (Map.Entry<EdgeLabel, ? extends S> entry : pathAwareInfo.entrySet()) {
            EdgeLabel key2 = entry.getKey();
            S value = entry.getValue();
            EventOccurrencesRange eventOccurrencesRange = (EventOccurrencesRange) value.get(key);
            if (eventOccurrencesRange == null) {
                eventOccurrencesRange = EventOccurrencesRange.ZERO;
            }
            map = (PersistentMap<EdgeLabel, S>) ((PersistentMap) map).put(key2, value.put(key, eventOccurrencesRange.plus(range)));
        }
        return (PersistentMap<EdgeLabel, S>) map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final <S extends ControlFlowInfo<S, K, EventOccurrencesRange>, K> PersistentMap<EdgeLabel, S> overwriteRange(PersistentMap<EdgeLabel, ? extends S> persistentMap, K k, EventOccurrencesRange eventOccurrencesRange) {
        Map map = (PersistentMap<EdgeLabel, S>) ExtensionsKt.persistentMapOf(new Pair[0]);
        for (Map.Entry<EdgeLabel, ? extends S> entry : persistentMap.entrySet()) {
            EdgeLabel key = entry.getKey();
            S value = entry.getValue();
            if (((EventOccurrencesRange) value.get(k)) == null) {
                EventOccurrencesRange eventOccurrencesRange2 = EventOccurrencesRange.ZERO;
            }
            map = (PersistentMap<EdgeLabel, S>) ((PersistentMap) map).put(key, value.put(k, eventOccurrencesRange));
        }
        return (PersistentMap<EdgeLabel, S>) map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final <S extends ControlFlowInfo<S, K, EventOccurrencesRange>, K> PersistentMap<EdgeLabel, S> removeRange(PersistentMap<EdgeLabel, ? extends S> persistentMap, K k) {
        Map map = (PersistentMap<EdgeLabel, S>) ExtensionsKt.persistentMapOf(new Pair[0]);
        for (Map.Entry<EdgeLabel, ? extends S> entry : persistentMap.entrySet()) {
            map = (PersistentMap<EdgeLabel, S>) ((PersistentMap) map).put(entry.getKey(), entry.getValue().remove(k));
        }
        return (PersistentMap<EdgeLabel, S>) map;
    }
}
