package org.jetbrains.kotlin.resolve.calls.inference.model;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;

/* JADX INFO: compiled from: ConstraintPositionAndErrors.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0000\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00028\u0000¢\u0006\u0002\u0010\u0006J\b\u0010\f\u001a\u00020\rH\u0016R\u0013\u0010\u0005\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/FixVariableConstraintPosition;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintPosition;", "variable", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "resolvedAtom", "(Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;Ljava/lang/Object;)V", "getResolvedAtom", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "getVariable", "()Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "toString", "", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FixVariableConstraintPosition<T> extends ConstraintPosition {
    private final T resolvedAtom;
    private final TypeVariableMarker variable;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FixVariableConstraintPosition(TypeVariableMarker variable, T t) {
        super(null);
        Intrinsics.checkNotNullParameter(variable, "variable");
        this.variable = variable;
        this.resolvedAtom = t;
    }

    public final T getResolvedAtom() {
        return this.resolvedAtom;
    }

    public final TypeVariableMarker getVariable() {
        return this.variable;
    }

    public String toString() {
        return "Fix variable " + this.variable;
    }
}
