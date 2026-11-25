package org.jetbrains.kotlin.fir.caches;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;
import org.antlr.stringtemplate.language.ASTExpr;
import org.eclipse.jgit.lib.Constants;

/* JADX INFO: compiled from: FirCacheWithPostCompute.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a<\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003\"\u0004\b\u0001\u0010\u0004*\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0007\u001a\u0002H\u0002H\u0086\u0002¢\u0006\u0002\u0010\b\u001a>\u0010\t\u001a\u0002H\u0004\"\b\b\u0000\u0010\u0002*\u00020\u0003\"\u0004\b\u0001\u0010\u0004*\u0016\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00052\u0006\u0010\u0007\u001a\u0002H\u0002H\u0086\b¢\u0006\u0002\u0010\n\u001a4\u0010\t\u001a\u0002H\u0004\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u00032\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000eH\u0086\u0002¢\u0006\u0002\u0010\u000f¨\u0006\u0010"}, d2 = {"contains", "", "K", "", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "", ASTExpr.DEFAULT_MAP_KEY_NAME, "(Lorg/jetbrains/kotlin/fir/caches/FirCache;Ljava/lang/Object;)Z", "getValue", "(Lorg/jetbrains/kotlin/fir/caches/FirCache;Ljava/lang/Object;)Ljava/lang/Object;", "Lorg/jetbrains/kotlin/fir/caches/FirLazyValue;", "thisRef", "property", "Lkotlin/reflect/KProperty;", "(Lorg/jetbrains/kotlin/fir/caches/FirLazyValue;Ljava/lang/Object;Lkotlin/reflect/KProperty;)Ljava/lang/Object;", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirCacheWithPostComputeKt {
    public static final <K, V> V getValue(FirCache firCache, K key) {
        Intrinsics.checkNotNullParameter(firCache, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        return (V) firCache.getValue(key, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <K, V> boolean contains(FirCache<? super K, ? extends V, ?> firCache, K key) {
        Intrinsics.checkNotNullParameter(firCache, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        return firCache.getValueIfComputed(key) != null;
    }

    public static final <V> V getValue(FirLazyValue<? extends V> firLazyValue, Object obj, KProperty<?> property) {
        Intrinsics.checkNotNullParameter(firLazyValue, "<this>");
        Intrinsics.checkNotNullParameter(property, "property");
        return firLazyValue.getValue();
    }
}
