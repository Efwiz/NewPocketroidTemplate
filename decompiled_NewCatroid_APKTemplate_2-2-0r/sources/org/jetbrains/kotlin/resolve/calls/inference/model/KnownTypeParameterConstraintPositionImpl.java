package org.jetbrains.kotlin.resolve.calls.inference.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: ConstraintPositionAndErrorsImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/KnownTypeParameterConstraintPositionImpl;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/KnownTypeParameterConstraintPosition;", "Lorg/jetbrains/kotlin/types/KotlinType;", "typeArgument", "(Lorg/jetbrains/kotlin/types/KotlinType;)V", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KnownTypeParameterConstraintPositionImpl extends KnownTypeParameterConstraintPosition<KotlinType> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KnownTypeParameterConstraintPositionImpl(KotlinType typeArgument) {
        super(typeArgument);
        Intrinsics.checkNotNullParameter(typeArgument, "typeArgument");
    }
}
