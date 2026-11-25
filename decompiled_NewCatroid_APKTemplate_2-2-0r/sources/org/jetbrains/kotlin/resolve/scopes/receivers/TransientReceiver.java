package org.jetbrains.kotlin.resolve.scopes.receivers;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: loaded from: classes6.dex */
public class TransientReceiver extends AbstractReceiverValue {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 2) {
            objArr[0] = ModuleXmlParser.TYPE;
        } else {
            objArr[0] = "newType";
        }
        objArr[1] = "org/jetbrains/kotlin/resolve/scopes/receivers/TransientReceiver";
        if (i != 2) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "replaceType";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TransientReceiver(KotlinType kotlinType) {
        this(kotlinType, null);
        if (kotlinType == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private TransientReceiver(KotlinType kotlinType, ReceiverValue receiverValue) {
        super(kotlinType, receiverValue);
        if (kotlinType == null) {
            $$$reportNull$$$0(1);
        }
    }

    public String toString() {
        return "{Transient} : " + getType();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue
    /* JADX INFO: renamed from: replaceType */
    public ReceiverValue mo7466replaceType(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(2);
        }
        return new TransientReceiver(kotlinType, getOriginal());
    }
}
