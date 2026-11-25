package org.jf.dexlib2.immutable.reference;

import java.util.List;
import org.jf.dexlib2.base.reference.BaseCallSiteReference;
import org.jf.dexlib2.iface.reference.CallSiteReference;
import org.jf.dexlib2.iface.reference.MethodHandleReference;
import org.jf.dexlib2.iface.reference.MethodProtoReference;
import org.jf.dexlib2.iface.value.EncodedValue;
import org.jf.dexlib2.immutable.value.ImmutableEncodedValue;
import org.jf.dexlib2.immutable.value.ImmutableEncodedValueFactory;
import org.jf.util.ImmutableUtils;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableCallSiteReference extends BaseCallSiteReference implements ImmutableReference {
    protected final List<? extends ImmutableEncodedValue> extraArguments;
    protected final ImmutableMethodHandleReference methodHandle;
    protected final String methodName;
    protected final ImmutableMethodProtoReference methodProto;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    protected final String f1030name;

    public ImmutableCallSiteReference(String str, MethodHandleReference methodHandleReference, String str2, MethodProtoReference methodProtoReference, Iterable<? extends EncodedValue> iterable) {
        this.f1030name = str;
        this.methodHandle = ImmutableMethodHandleReference.of(methodHandleReference);
        this.methodName = str2;
        this.methodProto = ImmutableMethodProtoReference.of(methodProtoReference);
        this.extraArguments = ImmutableEncodedValueFactory.immutableListOf(iterable);
    }

    public ImmutableCallSiteReference(String str, ImmutableMethodHandleReference immutableMethodHandleReference, String str2, ImmutableMethodProtoReference immutableMethodProtoReference, List<? extends ImmutableEncodedValue> list) {
        this.f1030name = str;
        this.methodHandle = immutableMethodHandleReference;
        this.methodName = str2;
        this.methodProto = immutableMethodProtoReference;
        this.extraArguments = ImmutableUtils.nullToEmptyList(list);
    }

    public static ImmutableCallSiteReference of(CallSiteReference callSiteReference) {
        if (callSiteReference instanceof ImmutableCallSiteReference) {
            return (ImmutableCallSiteReference) callSiteReference;
        }
        return new ImmutableCallSiteReference(callSiteReference.getName(), ImmutableMethodHandleReference.of(callSiteReference.getMethodHandle()), callSiteReference.getMethodName(), ImmutableMethodProtoReference.of(callSiteReference.getMethodProto()), (List<? extends ImmutableEncodedValue>) ImmutableEncodedValueFactory.immutableListOf(callSiteReference.getExtraArguments()));
    }

    @Override // org.jf.dexlib2.iface.reference.CallSiteReference
    public String getName() {
        return this.f1030name;
    }

    @Override // org.jf.dexlib2.iface.reference.CallSiteReference
    public MethodHandleReference getMethodHandle() {
        return this.methodHandle;
    }

    @Override // org.jf.dexlib2.iface.reference.CallSiteReference
    public String getMethodName() {
        return this.methodName;
    }

    @Override // org.jf.dexlib2.iface.reference.CallSiteReference
    public MethodProtoReference getMethodProto() {
        return this.methodProto;
    }

    @Override // org.jf.dexlib2.iface.reference.CallSiteReference
    public List<? extends EncodedValue> getExtraArguments() {
        return this.extraArguments;
    }
}
