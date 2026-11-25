package org.jetbrains.kotlin.resolve.scopes.receivers;

import kotlin.Metadata;
import kotlin.collections.SetsKt;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;

/* JADX INFO: compiled from: QualifierReceiver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u00012\u00020\u0002R\u0014\u0010\u0003\u001a\u0004\u0018\u00010\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0012\u0010\u000f\u001a\u00020\u0010X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0013À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/receivers/QualifierReceiver;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/Receiver;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/DetailedReceiver;", "classValueReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "getClassValueReceiver", "()Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "classValueReceiverWithSmartCastInfo", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "getClassValueReceiverWithSmartCastInfo", "()Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "staticScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "getStaticScope", "()Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface QualifierReceiver extends DetailedReceiver, Receiver {
    ReceiverValue getClassValueReceiver();

    DeclarationDescriptor getDescriptor();

    MemberScope getStaticScope();

    default ReceiverValueWithSmartCastInfo getClassValueReceiverWithSmartCastInfo() {
        ReceiverValue classValueReceiver = getClassValueReceiver();
        if (classValueReceiver != null) {
            return new ReceiverValueWithSmartCastInfo(classValueReceiver, SetsKt.emptySet(), true, null, 8, null);
        }
        return null;
    }
}
