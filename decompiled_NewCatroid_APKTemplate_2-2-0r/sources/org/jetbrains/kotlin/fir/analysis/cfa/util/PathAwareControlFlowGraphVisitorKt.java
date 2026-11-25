package org.jetbrains.kotlin.fir.analysis.cfa.util;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.EdgeLabel;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap;

/* JADX INFO: compiled from: PathAwareControlFlowGraphVisitor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u001at\u0010\u0000\u001a\u0018\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u00030\u0001j\b\u0012\u0004\u0012\u0002H\u0003`\u0004\"\u0016\b\u0000\u0010\u0003*\u0010\u0012\u0004\u0012\u0002H\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0005*\u0018\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u00030\u0001j\b\u0012\u0004\u0012\u0002H\u0003`\u00042\u001c\u0010\u0006\u001a\u0018\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u00030\u0001j\b\u0012\u0004\u0012\u0002H\u0003`\u00042\u0006\u0010\u0007\u001a\u00020\b*(\u0010\t\u001a\u0004\b\u0000\u0010\u0003\"\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u00030\u0001¨\u0006\n"}, d2 = {"join", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentMap;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EdgeLabel;", "I", "Lorg/jetbrains/kotlin/fir/analysis/cfa/util/PathAwareControlFlowInfo;", "Lorg/jetbrains/kotlin/fir/analysis/cfa/util/ControlFlowInfo;", "other", SchemaSymbols.ATTVAL_UNION, "", "PathAwareControlFlowInfo", "checkers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PathAwareControlFlowGraphVisitorKt {
    public static final <I extends ControlFlowInfo<I, ?, ?>> PersistentMap<EdgeLabel, I> join(PersistentMap<EdgeLabel, ? extends I> persistentMap, PersistentMap<EdgeLabel, ? extends I> other, boolean z) {
        Intrinsics.checkNotNullParameter(persistentMap, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        PersistentMap.Builder<EdgeLabel, ? extends I> builder = persistentMap.builder();
        PersistentMap.Builder<EdgeLabel, ? extends I> builder2 = builder;
        for (Map.Entry<EdgeLabel, ? extends I> entry : other.entrySet()) {
            EdgeLabel key = entry.getKey();
            I value = entry.getValue();
            ControlFlowInfo controlFlowInfo = (ControlFlowInfo) persistentMap.get(key);
            if (controlFlowInfo != null) {
                ControlFlowInfo controlFlowInfoPlus = z ? controlFlowInfo.plus(value) : controlFlowInfo.merge(value);
                if (controlFlowInfoPlus != null) {
                    value = controlFlowInfoPlus;
                }
            }
            builder2.put(key, value);
        }
        return builder.build();
    }
}
