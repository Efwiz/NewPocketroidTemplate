package org.jetbrains.kotlin.load.java.typeEnhancement;

import java.util.Arrays;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: predefinedEnhancementInfo.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0004\u0018\u00002\u00020\u0001B3\b\u0016\u0012*\u0010\u0002\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00040\u0003\"\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\u0002\u0010\u0007B\u0019\u0012\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\t¢\u0006\u0002\u0010\nR\u001d\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\t¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/load/java/typeEnhancement/TypeEnhancementInfo;", "", "pairs", "", "Lkotlin/Pair;", "", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/JavaTypeQualifiers;", "([Lkotlin/Pair;)V", "map", "", "(Ljava/util/Map;)V", "getMap", "()Ljava/util/Map;", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeEnhancementInfo {
    private final Map<Integer, JavaTypeQualifiers> map;

    public TypeEnhancementInfo(Map<Integer, JavaTypeQualifiers> map) {
        Intrinsics.checkNotNullParameter(map, "map");
        this.map = map;
    }

    public final Map<Integer, JavaTypeQualifiers> getMap() {
        return this.map;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TypeEnhancementInfo(Pair<Integer, JavaTypeQualifiers>... pairs) {
        this((Map<Integer, JavaTypeQualifiers>) MapsKt.mapOf((Pair[]) Arrays.copyOf(pairs, pairs.length)));
        Intrinsics.checkNotNullParameter(pairs, "pairs");
    }
}
