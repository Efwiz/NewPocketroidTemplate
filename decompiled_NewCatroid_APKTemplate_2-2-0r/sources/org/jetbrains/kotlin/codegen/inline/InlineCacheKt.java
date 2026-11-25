package org.jetbrains.kotlin.codegen.inline;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.util.containers.SLRUMap;

/* JADX INFO: compiled from: InlineCache.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001aG\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0002\"\b\b\u0001\u0010\u0001*\u00020\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00010\u00042\u0006\u0010\u0005\u001a\u0002H\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0007H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\b\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\t"}, d2 = {"getOrPut", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "K", "", "Lorg/jetbrains/kotlin/com/intellij/util/containers/SLRUMap;", ASTExpr.DEFAULT_MAP_KEY_NAME, "defaultValue", "Lkotlin/Function0;", "(Lcom/intellij/util/containers/SLRUMap;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class InlineCacheKt {
    public static final <K, V> V getOrPut(SLRUMap<K, V> sLRUMap, K k, Function0<? extends V> defaultValue) {
        V vInvoke;
        Intrinsics.checkNotNullParameter(sLRUMap, "<this>");
        Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
        synchronized (sLRUMap) {
            try {
                vInvoke = sLRUMap.get(k);
                if (vInvoke == null) {
                    vInvoke = defaultValue.invoke();
                    sLRUMap.put(k, vInvoke);
                }
                InlineMarker.finallyStart(2);
            } finally {
                int i = 1;
                InlineMarker.finallyStart(i);
                InlineMarker.finallyEnd(i);
            }
        }
        return vInvoke;
    }
}
