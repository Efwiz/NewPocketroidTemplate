package org.jetbrains.kotlin.fir.caches;

import androidx.exifinterface.media.ExifInterface;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.eclipse.jgit.lib.Constants;

/* JADX INFO: compiled from: FirThreadUnsafeCachesFactory.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0002\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u0002*\u0004\b\u0001\u0010\u0003*\u0004\b\u0002\u0010\u0004*\u0004\b\u0003\u0010\u00052\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u00040\u0006BK\u0012$\u0010\u0007\u001a \u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0002\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00030\t0\b\u0012\u001e\u0010\n\u001a\u001a\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u0003\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\u0002\u0010\rJ\u001d\u0010\u0011\u001a\u00028\u00012\u0006\u0010\u0012\u001a\u00028\u00002\u0006\u0010\u0013\u001a\u00028\u0002H\u0016¢\u0006\u0002\u0010\u0014J\u0017\u0010\u0015\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0012\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0016R,\u0010\u0007\u001a \u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0002\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00030\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u000fX\u0082\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\u0010R&\u0010\n\u001a\u001a\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u0003\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/fir/caches/FirThreadUnsafeCacheWithPostCompute;", "K", "", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "CONTEXT", "DATA", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "createValue", "Lkotlin/Function2;", "Lkotlin/Pair;", "postCompute", "Lkotlin/Function3;", "", "(Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function3;)V", "map", "Lorg/jetbrains/kotlin/fir/caches/NullableMap;", "Ljava/util/Map;", "getValue", ASTExpr.DEFAULT_MAP_KEY_NAME, "context", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "getValueIfComputed", "(Ljava/lang/Object;)Ljava/lang/Object;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
final class FirThreadUnsafeCacheWithPostCompute<K, V, CONTEXT, DATA> extends FirCache<K, V, CONTEXT> {
    private final Function2<K, CONTEXT, Pair<V, DATA>> createValue;
    private final Map<K, Object> map;
    private final Function3<K, V, DATA, Unit> postCompute;

    /* JADX WARN: Multi-variable type inference failed */
    public FirThreadUnsafeCacheWithPostCompute(Function2<? super K, ? super CONTEXT, ? extends Pair<? extends V, ? extends DATA>> createValue, Function3<? super K, ? super V, ? super DATA, Unit> postCompute) {
        Intrinsics.checkNotNullParameter(createValue, "createValue");
        Intrinsics.checkNotNullParameter(postCompute, "postCompute");
        this.createValue = createValue;
        this.postCompute = postCompute;
        this.map = NullableMap.m6790constructorimpl$default(null, 1, null);
    }

    @Override // org.jetbrains.kotlin.fir.caches.FirCache
    public V getValue(K key, CONTEXT context) {
        Intrinsics.checkNotNullParameter(key, "key");
        V v = (V) this.map.get(key);
        if (v == null) {
            Pair<V, DATA> pairInvoke = this.createValue.invoke(key, context);
            V vComponent1 = pairInvoke.component1();
            DATA dataComponent2 = pairInvoke.component2();
            this.map.put(key, vComponent1 == null ? NullableMap.NullValue.INSTANCE : vComponent1);
            this.postCompute.invoke(key, vComponent1, dataComponent2);
            return vComponent1;
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
