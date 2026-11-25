package org.jetbrains.kotlin.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtReturnExpression;
import org.jetbrains.kotlin.resolve.calls.model.ExpressionKotlinCallArgument;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;
import org.jetbrains.kotlin.resolve.scopes.receivers.TransientReceiver;

/* JADX INFO: compiled from: NewCallArguments.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\rR\u0014\u0010\u000e\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\rR\u0014\u0010\u000f\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/EmptyLabeledReturn;", "Lorg/jetbrains/kotlin/resolve/calls/model/ExpressionKotlinCallArgument;", "returnExpression", "Lorg/jetbrains/kotlin/psi/KtReturnExpression;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "(Lorg/jetbrains/kotlin/psi/KtReturnExpression;Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;)V", "argumentName", "Lorg/jetbrains/kotlin/name/Name;", "getArgumentName", "()Lorg/jetbrains/kotlin/name/Name;", "isSafeCall", "", "()Z", "isSpread", "receiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "getReceiver", "()Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "getReturnExpression", "()Lorg/jetbrains/kotlin/psi/KtReturnExpression;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EmptyLabeledReturn implements ExpressionKotlinCallArgument {
    private final ReceiverValueWithSmartCastInfo receiver;
    private final KtReturnExpression returnExpression;

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument
    public Name getArgumentName() {
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ReceiverKotlinCallArgument
    /* JADX INFO: renamed from: isSafeCall */
    public boolean getIsSafeCall() {
        return false;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument
    public boolean isSpread() {
        return false;
    }

    public EmptyLabeledReturn(KtReturnExpression returnExpression, KotlinBuiltIns builtIns) {
        Intrinsics.checkNotNullParameter(returnExpression, "returnExpression");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        this.returnExpression = returnExpression;
        this.receiver = new ReceiverValueWithSmartCastInfo(new TransientReceiver(builtIns.getUnitType()), SetsKt.emptySet(), true, null, 8, null);
    }

    public final KtReturnExpression getReturnExpression() {
        return this.returnExpression;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ReceiverKotlinCallArgument
    public ReceiverValueWithSmartCastInfo getReceiver() {
        return this.receiver;
    }
}
