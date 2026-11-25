package org.jetbrains.kotlin.resolve.calls.inference.model;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: ConstraintPositionAndErrors.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00028\u0000¢\u0006\u0002\u0010\u0005J\b\u0010\t\u001a\u00020\nH\u0016R\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/KnownTypeParameterConstraintPosition;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintPosition;", "typeArgument", "(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;)V", "getTypeArgument", "()Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "toString", "", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KnownTypeParameterConstraintPosition<T extends KotlinTypeMarker> extends ConstraintPosition {
    private final T typeArgument;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KnownTypeParameterConstraintPosition(T typeArgument) {
        super(null);
        Intrinsics.checkNotNullParameter(typeArgument, "typeArgument");
        this.typeArgument = typeArgument;
    }

    public final T getTypeArgument() {
        return this.typeArgument;
    }

    public String toString() {
        return "TypeArgument " + this.typeArgument;
    }
}
