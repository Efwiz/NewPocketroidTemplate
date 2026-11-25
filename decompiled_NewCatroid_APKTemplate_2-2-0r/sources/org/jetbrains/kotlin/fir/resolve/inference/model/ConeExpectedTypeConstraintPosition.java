package org.jetbrains.kotlin.fir.resolve.inference.model;

import kotlin.Metadata;
import org.jetbrains.kotlin.resolve.calls.inference.model.ExpectedTypeConstraintPosition;

/* JADX INFO: compiled from: FirConstraintPositionAndErrors.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/inference/model/ConeExpectedTypeConstraintPosition;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ExpectedTypeConstraintPosition;", "", "()V", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeExpectedTypeConstraintPosition extends ExpectedTypeConstraintPosition {
    public static final ConeExpectedTypeConstraintPosition INSTANCE = new ConeExpectedTypeConstraintPosition();

    private ConeExpectedTypeConstraintPosition() {
        super(null);
    }
}
