package org.jetbrains.kotlin.fir.caches;

import androidx.exifinterface.media.ExifInterface;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.eclipse.jgit.lib.Constants;

/* JADX INFO: compiled from: FirThreadUnsafeCachesFactory.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\b\u0002\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u0002*\u0004\b\u0001\u0010\u0003*\u0004\b\u0002\u0010\u00042\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u00040\u0005B5\u0012\u0014\b\u0002\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0007\u0012\u0018\u0010\b\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00010\t¢\u0006\u0002\u0010\nJ\u001d\u0010\f\u001a\u00028\u00012\u0006\u0010\r\u001a\u00028\u00002\u0006\u0010\u000e\u001a\u00028\u0002H\u0016¢\u0006\u0002\u0010\u000fJ\u0017\u0010\u0010\u001a\u0004\u0018\u00018\u00012\u0006\u0010\r\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0011R \u0010\b\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00010\tX\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0007X\u0082\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\u000b\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/caches/FirThreadUnsafeCache;", "K", "", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "CONTEXT", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "map", "Lorg/jetbrains/kotlin/fir/caches/NullableMap;", "createValue", "Lkotlin/Function2;", "(Ljava/util/Map;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "Ljava/util/Map;", "getValue", ASTExpr.DEFAULT_MAP_KEY_NAME, "context", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "getValueIfComputed", "(Ljava/lang/Object;)Ljava/lang/Object;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
final class FirThreadUnsafeCache<K, V, CONTEXT> extends FirCache<K, V, CONTEXT> {
    private final Function2<K, CONTEXT, V> createValue;
    private final Map<K, Object> map;

    public /* synthetic */ FirThreadUnsafeCache(Map map, Function2 function2, DefaultConstructorMarker defaultConstructorMarker) {
        this(map, function2);
    }

    public /* synthetic */ FirThreadUnsafeCache(Map map, Function2 function2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? NullableMap.m6790constructorimpl$default(null, 1, null) : map, function2, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private FirThreadUnsafeCache(Map<K, Object> map, Function2<? super K, ? super CONTEXT, ? extends V> createValue) {
        Intrinsics.checkNotNullParameter(map, "map");
        Intrinsics.checkNotNullParameter(createValue, "createValue");
        this.map = map;
        this.createValue = createValue;
    }

    @Override // org.jetbrains.kotlin.fir.caches.FirCache
    public V getValue(K key, CONTEXT context) {
        Intrinsics.checkNotNullParameter(key, "key");
        V v = (V) this.map.get(key);
        if (v == null) {
            V vInvoke = this.createValue.invoke(key, context);
            this.map.put(key, vInvoke == null ? NullableMap.NullValue.INSTANCE : vInvoke);
            return vInvoke;
        }
        if (Intrinsics.areEqual(v, NullableMap.NullValue.INSTANCE)) {
            return null;
        }
        return v;
    }

    @Override // org.jetbrains.kotlin.fir.caches.FirCache
    public V getValueIfComputed(K key) {
        Intrinsics.checkNotNullParameter(key, "key");
        V v = (V) this.map.get(key);
        if (v == null || Intrinsics.areEqual(v, NullableMap.NullValue.INSTANCE)) {
            return null;
        }
        return v;
    }
}
