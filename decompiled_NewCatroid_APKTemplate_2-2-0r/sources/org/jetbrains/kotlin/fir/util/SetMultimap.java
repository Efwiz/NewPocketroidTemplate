package org.jetbrains.kotlin.fir.util;

import androidx.exifinterface.media.ExifInterface;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;

/* JADX INFO: compiled from: Multimap.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0010#\n\u0002\b\u0004\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022&\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00050\u0003B\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00010\u0005H\u0014J\u000e\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00010\u0004H\u0014¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/util/SetMultimap;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/kotlin/fir/util/BaseMultimap;", "", "", "()V", "createContainer", "createEmptyContainer", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetMultimap<K, V> extends BaseMultimap<K, V, Set<? extends V>, Set<V>> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.fir.util.BaseMultimap
    public Set<V> createContainer() {
        return new LinkedHashSet();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.fir.util.BaseMultimap
    public Set<V> createEmptyContainer() {
        return SetsKt.emptySet();
    }
}
