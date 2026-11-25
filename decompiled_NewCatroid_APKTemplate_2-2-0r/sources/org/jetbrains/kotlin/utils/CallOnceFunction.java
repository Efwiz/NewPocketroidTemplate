package org.jetbrains.kotlin.utils;

import androidx.exifinterface.media.ExifInterface;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.auxiliary.TypeProxy;

/* JADX INFO: compiled from: CallOnceFunction.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003B!\u0012\u0006\u0010\u0004\u001a\u00028\u0001\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0003¢\u0006\u0002\u0010\u0006J\u0016\u0010\n\u001a\u00028\u00012\u0006\u0010\u000b\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\fR\u0010\u0010\u0004\u001a\u00028\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0007R\"\u0010\b\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u00030\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/utils/CallOnceFunction;", "F", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/Function1;", "defaultValue", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "functionRef", "Ljava/util/concurrent/atomic/AtomicReference;", "invoke", "p1", "(Ljava/lang/Object;)Ljava/lang/Object;", "util.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CallOnceFunction<F, T> implements Function1<F, T> {
    private final T defaultValue;
    private final AtomicReference<Function1<F, T>> functionRef;

    public CallOnceFunction(T t, Function1<? super F, ? extends T> delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.defaultValue = t;
        this.functionRef = new AtomicReference<>(delegate);
    }

    @Override // kotlin.jvm.functions.Function1
    public T invoke(F p1) {
        T tInvoke;
        Function1<F, T> andSet = this.functionRef.getAndSet(null);
        return (andSet == null || (tInvoke = andSet.invoke(p1)) == null) ? this.defaultValue : tInvoke;
    }
}
