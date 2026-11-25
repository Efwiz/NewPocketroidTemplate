package org.jetbrains.kotlin.fir.resolve.inference.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.resolve.calls.inference.model.ArgumentConstraintPosition;

/* JADX INFO: compiled from: FirConstraintPositionAndErrors.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/inference/model/ConeArgumentConstraintPosition;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ArgumentConstraintPosition;", "Lorg/jetbrains/kotlin/fir/FirElement;", "argument", "(Lorg/jetbrains/kotlin/fir/FirElement;)V", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeArgumentConstraintPosition extends ArgumentConstraintPosition<FirElement> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConeArgumentConstraintPosition(FirElement argument) {
        super(argument);
        Intrinsics.checkNotNullParameter(argument, "argument");
    }
}
