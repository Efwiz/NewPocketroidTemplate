package org.jetbrains.kotlin.resolve.calls.inference.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCall;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;

/* JADX INFO: compiled from: ConstraintPositionAndErrorsImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/ReceiverConstraintPositionImpl;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ReceiverConstraintPosition;", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "argument", "selectorCall", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "(Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;)V", "getSelectorCall", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReceiverConstraintPositionImpl extends ReceiverConstraintPosition<KotlinCallArgument> {
    private final KotlinCall selectorCall;

    public final KotlinCall getSelectorCall() {
        return this.selectorCall;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReceiverConstraintPositionImpl(KotlinCallArgument argument, KotlinCall kotlinCall) {
        super(argument);
        Intrinsics.checkNotNullParameter(argument, "argument");
        this.selectorCall = kotlinCall;
    }
}
