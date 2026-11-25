package org.jf.dexlib2.immutable.reference;

import java.util.List;
import org.jf.dexlib2.base.reference.BaseMethodReference;
import org.jf.dexlib2.iface.reference.MethodReference;
import org.jf.dexlib2.immutable.util.CharSequenceConverter;
import org.jf.util.ImmutableUtils;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableMethodReference extends BaseMethodReference implements ImmutableReference {
    protected final String definingClass;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    protected final String f1032name;
    protected final List<String> parameters;
    protected final String returnType;

    public ImmutableMethodReference(String str, String str2, Iterable<? extends CharSequence> iterable, String str3) {
        this.definingClass = str;
        this.f1032name = str2;
        this.parameters = CharSequenceConverter.immutableStringList(iterable);
        this.returnType = str3;
    }

    public ImmutableMethodReference(String str, String str2, List<String> list, String str3) {
        this.definingClass = str;
        this.f1032name = str2;
        this.parameters = ImmutableUtils.nullToEmptyList(list);
        this.returnType = str3;
    }

    public static ImmutableMethodReference of(MethodReference methodReference) {
        if (methodReference instanceof ImmutableMethodReference) {
            return (ImmutableMethodReference) methodReference;
        }
        return new ImmutableMethodReference(methodReference.getDefiningClass(), methodReference.getName(), methodReference.getParameterTypes(), methodReference.getReturnType());
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method, org.jf.dexlib2.iface.Member
    public String getDefiningClass() {
        return this.definingClass;
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method, org.jf.dexlib2.iface.Member
    public String getName() {
        return this.f1032name;
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference
    public List<String> getParameterTypes() {
        return this.parameters;
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method
    public String getReturnType() {
        return this.returnType;
    }
}
