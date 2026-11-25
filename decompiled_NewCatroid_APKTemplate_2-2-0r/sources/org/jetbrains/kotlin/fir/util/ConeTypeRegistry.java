package org.jetbrains.kotlin.fir.util;

import androidx.exifinterface.media.ExifInterface;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.util.TypeRegistry;

/* JADX INFO: compiled from: ConeTypeRegistry.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030\u0004B\u0005¢\u0006\u0002\u0010\u0005J4\u0010\u0006\u001a\u00020\u0007*\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00070\b2\u0006\u0010\n\u001a\u00020\t2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00070\fH\u0016¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/util/ConeTypeRegistry;", "K", "", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/kotlin/util/TypeRegistry;", "()V", "customComputeIfAbsent", "", "Ljava/util/concurrent/ConcurrentHashMap;", "", ASTExpr.DEFAULT_MAP_KEY_NAME, "compute", "Lkotlin/Function1;", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ConeTypeRegistry<K, V> extends TypeRegistry<K, V> {
    /* JADX INFO: Access modifiers changed from: private */
    public static final Integer customComputeIfAbsent$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (Integer) tmp0.invoke(obj);
    }

    @Override // org.jetbrains.kotlin.util.TypeRegistry
    public int customComputeIfAbsent(ConcurrentHashMap<String, Integer> concurrentHashMap, String key, final Function1<? super String, Integer> compute) {
        Intrinsics.checkNotNullParameter(concurrentHashMap, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(compute, "compute");
        Integer numComputeIfAbsent = concurrentHashMap.computeIfAbsent(key, new Function() { // from class: org.jetbrains.kotlin.fir.util.ConeTypeRegistry$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ConeTypeRegistry.customComputeIfAbsent$lambda$0(compute, obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(numComputeIfAbsent, "computeIfAbsent(...)");
        return numComputeIfAbsent.intValue();
    }
}
