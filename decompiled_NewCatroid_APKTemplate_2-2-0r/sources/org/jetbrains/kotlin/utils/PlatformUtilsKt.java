package org.jetbrains.kotlin.utils;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.util.containers.Interner;
import org.jetbrains.kotlin.com.intellij.util.containers.MultiMap;

/* JADX INFO: compiled from: platformUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\u001a\u001e\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\u001a\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¨\u0006\u0007"}, d2 = {"createConcurrentMultiMap", "Lorg/jetbrains/kotlin/com/intellij/util/containers/MultiMap;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "createStringInterner", "Lorg/jetbrains/kotlin/com/intellij/util/containers/Interner;", "", "util"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PlatformUtilsKt {
    public static final Interner<String> createStringInterner() {
        Interner<String> internerCreateStringInterner = Interner.createStringInterner();
        Intrinsics.checkNotNullExpressionValue(internerCreateStringInterner, "createStringInterner(...)");
        return internerCreateStringInterner;
    }

    public static final <K, V> MultiMap<K, V> createConcurrentMultiMap() {
        MultiMap<K, V> multiMapCreateConcurrent = MultiMap.createConcurrent();
        Intrinsics.checkNotNullExpressionValue(multiMapCreateConcurrent, "createConcurrent(...)");
        return multiMapCreateConcurrent;
    }
}
