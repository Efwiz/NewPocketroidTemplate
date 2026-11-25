package org.jetbrains.kotlin.util.slicedMap;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AbstractWritableSlice.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b&\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022 \u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00040\u00032\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0014\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0000H\u0016R \u0010\b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/util/slicedMap/AbstractWritableSlice;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/kotlin/util/slicedMap/KeyWithSlice;", "Lorg/jetbrains/kotlin/util/slicedMap/WritableSlice;", "debugName", "", "(Ljava/lang/String;)V", "slice", "getSlice", "()Lorg/jetbrains/kotlin/util/slicedMap/WritableSlice;", "getKey", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractWritableSlice<K, V> extends KeyWithSlice<K, V, WritableSlice<K, V>> implements WritableSlice<K, V> {
    @Override // org.jetbrains.kotlin.util.slicedMap.WritableSlice, org.jetbrains.kotlin.util.slicedMap.ReadOnlySlice
    public AbstractWritableSlice<K, V> getKey() {
        return this;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractWritableSlice(String debugName) {
        super(debugName);
        Intrinsics.checkNotNullParameter(debugName, "debugName");
    }

    @Override // org.jetbrains.kotlin.util.slicedMap.KeyWithSlice
    public WritableSlice<K, V> getSlice() {
        return this;
    }
}
