package org.jetbrains.kotlin.resolve.calls.smartcasts;

import androidx.exifinterface.media.ExifInterface;
import javaslang.collection.LinkedHashSet;
import javaslang.collection.Map;
import javaslang.collection.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX INFO: compiled from: DataFlowInfoImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001aq\u0010\u0000\u001a$\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00040\u00030\u0001j\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0004`\u0005\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0004*$\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00040\u00030\u0001j\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0004`\u00052\u0006\u0010\u0006\u001a\u0002H\u00022\u0006\u0010\u0007\u001a\u0002H\u0004H\u0002¢\u0006\u0002\u0010\b*<\b\u0002\u0010\t\u001a\u0004\b\u0000\u0010\u0002\u001a\u0004\b\u0001\u0010\u0004\"\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u0004`\u000b`\n2\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00040\u00030\u0001¨\u0006\f"}, d2 = {"put", "Ljavaslang/collection/Map;", "K", "Ljavaslang/collection/Set;", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/ImmutableMultimap;", ASTExpr.DEFAULT_MAP_KEY_NAME, "value", "(Ljavaslang/collection/Map;Ljava/lang/Object;Ljava/lang/Object;)Ljavaslang/collection/Map;", "ImmutableMultimap", "Lorg/jetbrains/kotlin/util/javaslang/ImmutableMap;", "Lorg/jetbrains/kotlin/util/javaslang/ImmutableSet;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DataFlowInfoImplKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final <K, V> Map<K, Set<V>> put(Map<K, Set<V>> map, K k, V v) {
        Set<V> orElse = map.get(k).getOrElse(LinkedHashSet.empty());
        if (orElse.contains(v)) {
            return map;
        }
        Map<K, Set<V>> mapPut = map.put(k, orElse.add(v));
        Intrinsics.checkNotNullExpressionValue(mapPut, "put(...)");
        return mapPut;
    }
}
