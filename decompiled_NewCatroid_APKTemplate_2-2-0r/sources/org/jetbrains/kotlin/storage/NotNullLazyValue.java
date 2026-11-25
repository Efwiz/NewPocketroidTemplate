package org.jetbrains.kotlin.storage;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: storage.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\bf\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003J\b\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0005H&J\b\u0010\u0007\u001a\u00020\bH\u0016ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", ExifInterface.GPS_DIRECTION_TRUE, "", "Lkotlin/Function0;", "isComputed", "", "isComputing", "renderDebugInformation", "", "util.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface NotNullLazyValue<T> extends Function0<T> {
    boolean isComputed();

    boolean isComputing();

    default String renderDebugInformation() {
        return "";
    }
}
