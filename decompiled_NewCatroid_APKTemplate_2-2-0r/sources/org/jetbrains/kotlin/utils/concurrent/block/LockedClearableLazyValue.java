package org.jetbrains.kotlin.utils.concurrent.block;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX INFO: compiled from: concurrent.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u0002B\u001b\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\r\u001a\u00020\u000eJ\u000b\u0010\u000f\u001a\u00028\u0000¢\u0006\u0002\u0010\nR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0003\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u0004\u0018\u00018\u0000X\u0088\u000e¢\u0006\u0004\n\u0002\u0010\f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/utils/concurrent/block/LockedClearableLazyValue;", ExifInterface.GPS_DIRECTION_TRUE, "", "lock", ConfigConstants.CONFIG_INIT_SECTION, "Lkotlin/Function0;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)V", "getInit", "()Lkotlin/jvm/functions/Function0;", "getLock", "()Ljava/lang/Object;", "value", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "drop", "", "get", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LockedClearableLazyValue<T> {
    private final Function0<T> init;
    private final Object lock;
    private volatile T value;

    /* JADX WARN: Multi-variable type inference failed */
    public LockedClearableLazyValue(Object lock, Function0<? extends T> init) {
        Intrinsics.checkNotNullParameter(lock, "lock");
        Intrinsics.checkNotNullParameter(init, "init");
        this.lock = lock;
        this.init = init;
    }

    public final Function0<T> getInit() {
        return this.init;
    }

    public final Object getLock() {
        return this.lock;
    }

    public final T get() {
        T tInvoke;
        T t = this.value;
        if (t != null) {
            return t;
        }
        synchronized (this.lock) {
            tInvoke = this.value;
            if (tInvoke == null) {
                tInvoke = this.init.invoke();
                this.value = tInvoke;
            }
        }
        return tInvoke;
    }

    public final void drop() {
        synchronized (this.lock) {
            this.value = null;
            Unit unit = Unit.INSTANCE;
        }
    }
}
