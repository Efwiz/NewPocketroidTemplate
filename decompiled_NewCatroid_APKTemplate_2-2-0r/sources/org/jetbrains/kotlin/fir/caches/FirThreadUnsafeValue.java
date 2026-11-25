package org.jetbrains.kotlin.fir.caches;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;

/* JADX INFO: compiled from: FirThreadUnsafeCachesFactory.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004¢\u0006\u0002\u0010\u0005J\r\u0010\u000b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\bR\u001b\u0010\u0006\u001a\u00028\u00008BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/caches/FirThreadUnsafeValue;", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/kotlin/fir/caches/FirLazyValue;", "createValue", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)V", "lazyValue", "getLazyValue", "()Ljava/lang/Object;", "lazyValue$delegate", "Lkotlin/Lazy;", "getValue", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
final class FirThreadUnsafeValue<V> extends FirLazyValue<V> {

    /* JADX INFO: renamed from: lazyValue$delegate, reason: from kotlin metadata */
    private final Lazy lazyValue;

    public FirThreadUnsafeValue(Function0<? extends V> createValue) {
        Intrinsics.checkNotNullParameter(createValue, "createValue");
        this.lazyValue = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) createValue);
    }

    private final V getLazyValue() {
        return (V) this.lazyValue.getValue();
    }

    @Override // org.jetbrains.kotlin.fir.caches.FirLazyValue
    public V getValue() {
        return getLazyValue();
    }
}
