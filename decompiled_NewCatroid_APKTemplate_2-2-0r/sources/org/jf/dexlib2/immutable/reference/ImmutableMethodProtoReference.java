package org.jf.dexlib2.immutable.reference;

import java.util.List;
import org.jf.dexlib2.base.reference.BaseMethodProtoReference;
import org.jf.dexlib2.iface.reference.MethodProtoReference;
import org.jf.dexlib2.immutable.util.CharSequenceConverter;
import org.jf.util.ImmutableUtils;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableMethodProtoReference extends BaseMethodProtoReference implements ImmutableReference {
    protected final List<String> parameters;
    protected final String returnType;

    public ImmutableMethodProtoReference(List<String> list, String str) {
        this.parameters = ImmutableUtils.nullToEmptyList(list);
        this.returnType = str;
    }

    public ImmutableMethodProtoReference(Iterable<? extends CharSequence> iterable, String str) {
        this.parameters = CharSequenceConverter.immutableStringList(iterable);
        this.returnType = str;
    }

    public static ImmutableMethodProtoReference of(MethodProtoReference methodProtoReference) {
        if (methodProtoReference instanceof ImmutableMethodProtoReference) {
            return (ImmutableMethodProtoReference) methodProtoReference;
        }
        return new ImmutableMethodProtoReference(methodProtoReference.getParameterTypes(), methodProtoReference.getReturnType());
    }

    @Override // org.jf.dexlib2.iface.reference.MethodProtoReference
    public List<? extends CharSequence> getParameterTypes() {
        return this.parameters;
    }

    @Override // org.jf.dexlib2.iface.reference.MethodProtoReference
    public String getReturnType() {
        return this.returnType;
    }
}
