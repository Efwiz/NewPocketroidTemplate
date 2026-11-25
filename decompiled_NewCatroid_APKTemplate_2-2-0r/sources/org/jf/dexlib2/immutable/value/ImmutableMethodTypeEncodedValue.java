package org.jf.dexlib2.immutable.value;

import org.jf.dexlib2.base.value.BaseMethodTypeEncodedValue;
import org.jf.dexlib2.iface.value.MethodTypeEncodedValue;
import org.jf.dexlib2.immutable.reference.ImmutableMethodProtoReference;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableMethodTypeEncodedValue extends BaseMethodTypeEncodedValue implements ImmutableEncodedValue {
    protected final ImmutableMethodProtoReference methodProtoReference;

    public ImmutableMethodTypeEncodedValue(ImmutableMethodProtoReference immutableMethodProtoReference) {
        this.methodProtoReference = immutableMethodProtoReference;
    }

    public static ImmutableMethodTypeEncodedValue of(MethodTypeEncodedValue methodTypeEncodedValue) {
        if (methodTypeEncodedValue instanceof ImmutableMethodTypeEncodedValue) {
            return (ImmutableMethodTypeEncodedValue) methodTypeEncodedValue;
        }
        return new ImmutableMethodTypeEncodedValue(ImmutableMethodProtoReference.of(methodTypeEncodedValue.getValue()));
    }

    @Override // org.jf.dexlib2.iface.value.MethodTypeEncodedValue
    public ImmutableMethodProtoReference getValue() {
        return this.methodProtoReference;
    }
}
