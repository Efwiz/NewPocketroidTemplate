package org.jetbrains.kotlin.resolve.scopes.receivers;

import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: loaded from: classes6.dex */
public class ExtensionReceiver extends AbstractReceiverValue implements ImplicitReceiver {
    private final CallableDescriptor descriptor;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "receiverType";
        } else if (i == 2) {
            objArr[0] = "org/jetbrains/kotlin/resolve/scopes/receivers/ExtensionReceiver";
        } else if (i != 3) {
            objArr[0] = "callableDescriptor";
        } else {
            objArr[0] = "newType";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/resolve/scopes/receivers/ExtensionReceiver";
        } else {
            objArr[1] = "getDeclarationDescriptor";
        }
        if (i != 2) {
            if (i != 3) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "replaceType";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ExtensionReceiver(CallableDescriptor callableDescriptor, KotlinType kotlinType, ReceiverValue receiverValue) {
        super(kotlinType, receiverValue);
        if (callableDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(1);
        }
        this.descriptor = callableDescriptor;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.receivers.ImplicitReceiver
    public CallableDescriptor getDeclarationDescriptor() {
        CallableDescriptor callableDescriptor = this.descriptor;
        if (callableDescriptor == null) {
            $$$reportNull$$$0(2);
        }
        return callableDescriptor;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue
    /* JADX INFO: renamed from: replaceType */
    public ReceiverValue mo7466replaceType(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(3);
        }
        return new ExtensionReceiver(this.descriptor, kotlinType, getOriginal());
    }

    public String toString() {
        return getType() + ": Ext {" + this.descriptor + "}";
    }
}
