package org.jetbrains.kotlin.fir.util;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;

/* JADX INFO: compiled from: Multimap.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\u001a\u001e\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0005\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003¨\u0006\u0006"}, d2 = {"listMultimapOf", "Lorg/jetbrains/kotlin/fir/util/ListMultimap;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "setMultimapOf", "Lorg/jetbrains/kotlin/fir/util/SetMultimap;", "cones"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class MultimapKt {
    public static final <K, V> SetMultimap<K, V> setMultimapOf() {
        return new SetMultimap<>();
    }

    public static final <K, V> ListMultimap<K, V> listMultimapOf() {
        return new ListMultimap<>();
    }
}
