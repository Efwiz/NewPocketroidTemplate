package org.jetbrains.kotlin.resolve.scopes.receivers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: ContextReceiver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B)\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bJ\u0010\u0010\u0010\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u0006H\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/receivers/ContextReceiver;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/AbstractReceiverValue;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ImplicitContextReceiver;", "declarationDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "receiverType", "Lorg/jetbrains/kotlin/types/KotlinType;", "customLabelName", "Lorg/jetbrains/kotlin/name/Name;", "original", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;)V", "getCustomLabelName", "()Lorg/jetbrains/kotlin/name/Name;", "getDeclarationDescriptor", "()Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "replaceType", "newType", "toString", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ContextReceiver extends AbstractReceiverValue implements ImplicitContextReceiver {
    private final Name customLabelName;
    private final CallableDescriptor declarationDescriptor;

    @Override // org.jetbrains.kotlin.resolve.scopes.receivers.ImplicitReceiver
    public CallableDescriptor getDeclarationDescriptor() {
        return this.declarationDescriptor;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.receivers.ImplicitContextReceiver
    public Name getCustomLabelName() {
        return this.customLabelName;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ContextReceiver(CallableDescriptor declarationDescriptor, KotlinType receiverType, Name name2, ReceiverValue receiverValue) {
        super(receiverType, receiverValue);
        Intrinsics.checkNotNullParameter(declarationDescriptor, "declarationDescriptor");
        Intrinsics.checkNotNullParameter(receiverType, "receiverType");
        this.declarationDescriptor = declarationDescriptor;
        this.customLabelName = name2;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue
    /* JADX INFO: renamed from: replaceType */
    public ReceiverValue mo7466replaceType(KotlinType newType) {
        Intrinsics.checkNotNullParameter(newType, "newType");
        return new ContextReceiver(getDeclarationDescriptor(), newType, getCustomLabelName(), getOriginal());
    }

    public String toString() {
        return "Cxt { " + getDeclarationDescriptor() + " }";
    }
}
