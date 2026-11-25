package org.jetbrains.kotlin.resolve.scopes.receivers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: ContextClassReceiver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B)\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bJ\u0010\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\u0006H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0016\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/receivers/ContextClassReceiver;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/AbstractReceiverValue;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ImplicitContextReceiver;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "receiverType", "Lorg/jetbrains/kotlin/types/KotlinType;", "customLabelName", "Lorg/jetbrains/kotlin/name/Name;", "original", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;)V", "getClassDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getCustomLabelName", "()Lorg/jetbrains/kotlin/name/Name;", "declarationDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getDeclarationDescriptor", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "replaceType", "newType", "toString", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ContextClassReceiver extends AbstractReceiverValue implements ImplicitContextReceiver {
    private final ClassDescriptor classDescriptor;
    private final Name customLabelName;

    public final ClassDescriptor getClassDescriptor() {
        return this.classDescriptor;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.receivers.ImplicitContextReceiver
    public Name getCustomLabelName() {
        return this.customLabelName;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ContextClassReceiver(ClassDescriptor classDescriptor, KotlinType receiverType, Name name2, ReceiverValue receiverValue) {
        super(receiverType, receiverValue);
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        Intrinsics.checkNotNullParameter(receiverType, "receiverType");
        this.classDescriptor = classDescriptor;
        this.customLabelName = name2;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.receivers.ImplicitReceiver
    public DeclarationDescriptor getDeclarationDescriptor() {
        return this.classDescriptor;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue
    /* JADX INFO: renamed from: replaceType */
    public ReceiverValue mo7466replaceType(KotlinType newType) {
        Intrinsics.checkNotNullParameter(newType, "newType");
        return new ContextClassReceiver(this.classDescriptor, newType, getCustomLabelName(), getOriginal());
    }

    public String toString() {
        return getType() + ": Ctx { " + this.classDescriptor + " }";
    }
}
