package org.jf.dexlib2.immutable.value;

import org.jf.dexlib2.base.value.BaseMethodEncodedValue;
import org.jf.dexlib2.iface.value.MethodEncodedValue;
import org.jf.dexlib2.immutable.reference.ImmutableMethodReference;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableMethodEncodedValue extends BaseMethodEncodedValue implements ImmutableEncodedValue {
    protected final ImmutableMethodReference value;

    public ImmutableMethodEncodedValue(ImmutableMethodReference immutableMethodReference) {
        this.value = immutableMethodReference;
    }

    public static ImmutableMethodEncodedValue of(MethodEncodedValue methodEncodedValue) {
        if (methodEncodedValue instanceof ImmutableMethodEncodedValue) {
            return (ImmutableMethodEncodedValue) methodEncodedValue;
        }
        return new ImmutableMethodEncodedValue(ImmutableMethodReference.of(methodEncodedValue.getValue()));
    }

    @Override // org.jf.dexlib2.iface.value.MethodEncodedValue
    public ImmutableMethodReference getValue() {
        return this.value;
    }
}
