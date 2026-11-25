package org.jetbrains.kotlin.resolve.calls.checkers;

import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.resolve.scopes.receivers.ImplicitReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.Receiver;

/* JADX INFO: compiled from: ConstructorHeaderCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u001c\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u0004\u0018\u00010\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, d2 = {"classDescriptorForImplicitReceiver", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/Receiver;", "getClassDescriptorForImplicitReceiver", "(Lorg/jetbrains/kotlin/resolve/scopes/receivers/Receiver;)Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConstructorHeaderCallCheckerKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassDescriptor getClassDescriptorForImplicitReceiver(Receiver receiver) {
        ImplicitReceiver implicitReceiver = receiver instanceof ImplicitReceiver ? (ImplicitReceiver) receiver : null;
        DeclarationDescriptor declarationDescriptor = implicitReceiver != null ? implicitReceiver.getDeclarationDescriptor() : null;
        if (declarationDescriptor instanceof ClassDescriptor) {
            return (ClassDescriptor) declarationDescriptor;
        }
        return null;
    }
}
