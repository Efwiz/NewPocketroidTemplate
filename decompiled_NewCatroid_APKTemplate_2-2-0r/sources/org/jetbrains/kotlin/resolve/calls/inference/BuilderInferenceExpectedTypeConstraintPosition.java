package org.jetbrains.kotlin.resolve.calls.inference;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.calls.inference.model.ExpectedTypeConstraintPosition;

/* JADX INFO: compiled from: BuilderInferenceSession.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/BuilderInferenceExpectedTypeConstraintPosition;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ExpectedTypeConstraintPosition;", "Lorg/jetbrains/kotlin/psi/KtExpression;", "callElement", "(Lorg/jetbrains/kotlin/psi/KtExpression;)V", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BuilderInferenceExpectedTypeConstraintPosition extends ExpectedTypeConstraintPosition<KtExpression> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BuilderInferenceExpectedTypeConstraintPosition(KtExpression callElement) {
        super(callElement);
        Intrinsics.checkNotNullParameter(callElement, "callElement");
    }
}
