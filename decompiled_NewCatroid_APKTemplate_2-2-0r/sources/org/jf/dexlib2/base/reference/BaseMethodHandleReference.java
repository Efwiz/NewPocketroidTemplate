package org.jf.dexlib2.base.reference;

import org.jf.dexlib2.formatter.DexFormatter;
import org.jf.dexlib2.iface.reference.FieldReference;
import org.jf.dexlib2.iface.reference.MethodHandleReference;
import org.jf.dexlib2.iface.reference.MethodReference;
import org.jf.dexlib2.iface.reference.Reference;

/* JADX INFO: loaded from: classes6.dex */
public abstract class BaseMethodHandleReference extends BaseReference implements MethodHandleReference {
    @Override // org.jf.dexlib2.iface.reference.MethodHandleReference
    public int hashCode() {
        return (getMethodHandleType() * 31) + getMemberReference().hashCode();
    }

    @Override // org.jf.dexlib2.iface.reference.MethodHandleReference
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof MethodHandleReference)) {
            return false;
        }
        MethodHandleReference methodHandleReference = (MethodHandleReference) obj;
        return getMethodHandleType() == methodHandleReference.getMethodHandleType() && getMemberReference().equals(methodHandleReference.getMemberReference());
    }

    @Override // org.jf.dexlib2.iface.reference.MethodHandleReference, java.lang.Comparable
    public int compareTo(MethodHandleReference methodHandleReference) {
        int iCompare = Integer.compare(getMethodHandleType(), methodHandleReference.getMethodHandleType());
        if (iCompare != 0) {
            return iCompare;
        }
        Reference memberReference = getMemberReference();
        if (memberReference instanceof FieldReference) {
            if (methodHandleReference.getMemberReference() instanceof FieldReference) {
                return ((FieldReference) memberReference).compareTo((FieldReference) methodHandleReference.getMemberReference());
            }
            return -1;
        }
        if (methodHandleReference.getMemberReference() instanceof MethodReference) {
            return ((MethodReference) memberReference).compareTo((MethodReference) methodHandleReference.getMemberReference());
        }
        return 1;
    }

    public String toString() {
        return DexFormatter.INSTANCE.getMethodHandle(this);
    }
}
