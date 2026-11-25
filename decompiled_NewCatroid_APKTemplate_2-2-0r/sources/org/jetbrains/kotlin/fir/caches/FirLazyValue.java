package org.jetbrains.kotlin.fir.caches;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import org.eclipse.jgit.lib.Constants;

/* JADX INFO: compiled from: FirCacheWithPostCompute.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\b&\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\r\u0010\u0004\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/fir/caches/FirLazyValue;", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "", "()V", "getValue", "()Ljava/lang/Object;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirLazyValue<V> {
    public abstract V getValue();
}
