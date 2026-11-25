package org.jetbrains.kotlin.utils.intellij;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;

/* JADX INFO: compiled from: intellijUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u001f\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\f\u0012\u0004\u0012\u0002H\u0001\u0012\u0002\b\u00030\u0002¢\u0006\u0002\u0010\u0003\u001a\u001f\u0010\u0004\u001a\u0002H\u0005\"\u0004\b\u0000\u0010\u0005*\f\u0012\u0002\b\u0003\u0012\u0004\u0012\u0002H\u00050\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0006"}, d2 = {"component1", "A", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/Pair;", "(Lcom/intellij/openapi/util/Pair;)Ljava/lang/Object;", "component2", "B", "util"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IntellijUtilKt {
    public static final <A> A component1(Pair<A, ?> pair) {
        Intrinsics.checkNotNullParameter(pair, "<this>");
        return pair.getFirst();
    }

    public static final <B> B component2(Pair<?, B> pair) {
        Intrinsics.checkNotNullParameter(pair, "<this>");
        return pair.getSecond();
    }
}
