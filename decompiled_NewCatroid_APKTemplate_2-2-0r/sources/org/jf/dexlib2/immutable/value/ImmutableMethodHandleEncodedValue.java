package org.jf.dexlib2.immutable.value;

import org.jf.dexlib2.base.value.BaseMethodHandleEncodedValue;
import org.jf.dexlib2.iface.value.MethodHandleEncodedValue;
import org.jf.dexlib2.immutable.reference.ImmutableMethodHandleReference;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableMethodHandleEncodedValue extends BaseMethodHandleEncodedValue implements ImmutableEncodedValue {
    protected final ImmutableMethodHandleReference methodHandleReference;

    public ImmutableMethodHandleEncodedValue(ImmutableMethodHandleReference immutableMethodHandleReference) {
        this.methodHandleReference = immutableMethodHandleReference;
    }

    public static ImmutableMethodHandleEncodedValue of(MethodHandleEncodedValue methodHandleEncodedValue) {
        if (methodHandleEncodedValue instanceof ImmutableMethodHandleEncodedValue) {
            return (ImmutableMethodHandleEncodedValue) methodHandleEncodedValue;
        }
        return new ImmutableMethodHandleEncodedValue(ImmutableMethodHandleReference.of(methodHandleEncodedValue.getValue()));
    }

    @Override // org.jf.dexlib2.iface.value.MethodHandleEncodedValue
    public ImmutableMethodHandleReference getValue() {
        return this.methodHandleReference;
    }
}
