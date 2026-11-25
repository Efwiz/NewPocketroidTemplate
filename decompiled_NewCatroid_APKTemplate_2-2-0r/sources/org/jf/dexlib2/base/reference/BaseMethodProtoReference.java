package org.jf.dexlib2.base.reference;

import org.jf.dexlib2.formatter.DexFormatter;
import org.jf.dexlib2.iface.reference.MethodProtoReference;
import org.jf.util.CharSequenceUtils;
import org.jf.util.CollectionUtils;
import org.jf.util.collection.ListUtil;

/* JADX INFO: loaded from: classes6.dex */
public abstract class BaseMethodProtoReference extends BaseReference implements MethodProtoReference {
    @Override // org.jf.dexlib2.iface.reference.MethodProtoReference
    public int hashCode() {
        return (getReturnType().hashCode() * 31) + getParameterTypes().hashCode();
    }

    @Override // org.jf.dexlib2.iface.reference.MethodProtoReference
    public boolean equals(Object obj) {
        if (!(obj instanceof MethodProtoReference)) {
            return false;
        }
        MethodProtoReference methodProtoReference = (MethodProtoReference) obj;
        return getReturnType().equals(methodProtoReference.getReturnType()) && CharSequenceUtils.listEquals(getParameterTypes(), methodProtoReference.getParameterTypes());
    }

    @Override // org.jf.dexlib2.iface.reference.MethodProtoReference, java.lang.Comparable
    public int compareTo(MethodProtoReference methodProtoReference) {
        int iCompareTo = getReturnType().compareTo(methodProtoReference.getReturnType());
        return iCompareTo != 0 ? iCompareTo : CollectionUtils.compareAsIterable(ListUtil.toStringComparator(), getParameterTypes(), methodProtoReference.getParameterTypes());
    }

    public String toString() {
        return DexFormatter.INSTANCE.getMethodProtoDescriptor(this);
    }
}
