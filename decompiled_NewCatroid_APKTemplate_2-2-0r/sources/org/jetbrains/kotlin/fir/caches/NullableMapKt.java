package org.jetbrains.kotlin.fir.caches;

import androidx.exifinterface.media.ExifInterface;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.eclipse.jgit.lib.Constants;

/* JADX INFO: compiled from: NullableMap.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001aH\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0001*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00010\u00032\u0006\u0010\u0004\u001a\u0002H\u00022\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0006H\u0086\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0007\u0010\b\u0082\u0002\u000e\n\u0005\b\u009920\u0001\n\u0005\b¡\u001e0\u0001¨\u0006\t"}, d2 = {"getOrPut", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "K", "Lorg/jetbrains/kotlin/fir/caches/NullableMap;", ASTExpr.DEFAULT_MAP_KEY_NAME, "defaultValue", "Lkotlin/Function0;", "getOrPut-LcU4HiU", "(Ljava/util/Map;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class NullableMapKt {
    /* JADX INFO: renamed from: getOrPut-LcU4HiU, reason: not valid java name */
    public static final <K, V> V m6798getOrPutLcU4HiU(Map<K, Object> getOrPut, K k, Function0<? extends V> defaultValue) {
        Intrinsics.checkNotNullParameter(getOrPut, "$this$getOrPut");
        Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
        V v = (V) getOrPut.get(k);
        if (v == null) {
            V vInvoke = defaultValue.invoke();
            getOrPut.put(k, vInvoke == null ? NullableMap.NullValue.INSTANCE : vInvoke);
            return vInvoke;
        }
        if (Intrinsics.areEqual(v, NullableMap.NullValue.INSTANCE)) {
            return null;
        }
        return v;
    }
}
