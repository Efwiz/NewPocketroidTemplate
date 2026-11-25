package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.receivers.QualifierReceiver;

/* JADX INFO: compiled from: KotlinCallArguments.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000f\u001a\u00020\u0010H\u0016R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u000bR\u0014\u0010\f\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u000bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/QualifierReceiverKotlinCallArgument;", "Lorg/jetbrains/kotlin/resolve/calls/model/ReceiverKotlinCallArgument;", "receiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/QualifierReceiver;", "(Lorg/jetbrains/kotlin/resolve/scopes/receivers/QualifierReceiver;)V", "argumentName", "Lorg/jetbrains/kotlin/name/Name;", "getArgumentName", "()Lorg/jetbrains/kotlin/name/Name;", "isSafeCall", "", "()Z", "isSpread", "getReceiver", "()Lorg/jetbrains/kotlin/resolve/scopes/receivers/QualifierReceiver;", "toString", "", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class QualifierReceiverKotlinCallArgument implements ReceiverKotlinCallArgument {
    private final QualifierReceiver receiver;

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument
    /* JADX INFO: renamed from: getArgumentName */
    public Name getName() {
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ReceiverKotlinCallArgument
    public boolean isSafeCall() {
        return false;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument
    public boolean isSpread() {
        return false;
    }

    public QualifierReceiverKotlinCallArgument(QualifierReceiver receiver) {
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        this.receiver = receiver;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ReceiverKotlinCallArgument
    public QualifierReceiver getReceiver() {
        return this.receiver;
    }

    public String toString() {
        return String.valueOf(getReceiver());
    }
}
