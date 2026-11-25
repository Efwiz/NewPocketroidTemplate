package org.jetbrains.kotlin.util.slicedMap;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.util.slicedMap.ReadOnlySlice;

/* JADX INFO: compiled from: KeyWithSlice.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b&\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u0002*\u0016\b\u0002\u0010\u0003 \u0001*\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00042\b\u0012\u0004\u0012\u0002H\u00020\u0005B\r\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0012\u0010\t\u001a\u00028\u0002X¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/util/slicedMap/KeyWithSlice;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Slice", "Lorg/jetbrains/kotlin/util/slicedMap/ReadOnlySlice;", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/Key;", "debugName", "", "(Ljava/lang/String;)V", "slice", "getSlice", "()Lorg/jetbrains/kotlin/util/slicedMap/ReadOnlySlice;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KeyWithSlice<K, V, Slice extends ReadOnlySlice<K, V>> extends Key<V> {
    public abstract Slice getSlice();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyWithSlice(String debugName) {
        super(debugName);
        Intrinsics.checkNotNullParameter(debugName, "debugName");
    }
}
