package org.jetbrains.kotlin.resolve.calls.inference.model;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import net.bytebuddy.implementation.auxiliary.TypeProxy;

/* JADX INFO: compiled from: ConstraintPositionAndErrors.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0000\b&\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u00020\u0003B\u0015\u0012\u0006\u0010\u0004\u001a\u00028\u0000\u0012\u0006\u0010\u0005\u001a\u00028\u0001¢\u0006\u0002\u0010\u0006J\b\u0010\u000b\u001a\u00020\fH\u0016R\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0005\u001a\u00028\u0001¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\n\u0010\b¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/LHSArgumentConstraintPosition;", ExifInterface.GPS_DIRECTION_TRUE, "R", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintPosition;", "argument", "receiver", "(Ljava/lang/Object;Ljava/lang/Object;)V", "getArgument", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "getReceiver", "toString", "", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class LHSArgumentConstraintPosition<T, R> extends ConstraintPosition {
    private final T argument;
    private final R receiver;

    public final T getArgument() {
        return this.argument;
    }

    public final R getReceiver() {
        return this.receiver;
    }

    public LHSArgumentConstraintPosition(T t, R r) {
        super(null);
        this.argument = t;
        this.receiver = r;
    }

    public String toString() {
        return "LHS receiver " + this.receiver;
    }
}
