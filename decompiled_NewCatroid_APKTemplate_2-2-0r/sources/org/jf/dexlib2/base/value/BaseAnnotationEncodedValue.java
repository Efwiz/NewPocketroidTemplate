package org.jf.dexlib2.base.value;

import org.jf.dexlib2.formatter.DexFormatter;
import org.jf.dexlib2.iface.value.AnnotationEncodedValue;
import org.jf.dexlib2.iface.value.EncodedValue;
import org.jf.util.CollectionUtils;

/* JADX INFO: loaded from: classes6.dex */
public abstract class BaseAnnotationEncodedValue implements AnnotationEncodedValue {
    @Override // org.jf.dexlib2.iface.value.EncodedValue
    public int getValueType() {
        return 29;
    }

    @Override // org.jf.dexlib2.iface.value.AnnotationEncodedValue
    public int hashCode() {
        return (getType().hashCode() * 31) + getElements().hashCode();
    }

    @Override // org.jf.dexlib2.iface.value.AnnotationEncodedValue
    public boolean equals(Object obj) {
        if (!(obj instanceof AnnotationEncodedValue)) {
            return false;
        }
        AnnotationEncodedValue annotationEncodedValue = (AnnotationEncodedValue) obj;
        return getType().equals(annotationEncodedValue.getType()) && getElements().equals(annotationEncodedValue.getElements());
    }

    @Override // org.jf.dexlib2.iface.value.AnnotationEncodedValue, java.lang.Comparable
    public int compareTo(EncodedValue encodedValue) {
        int iCompare = Integer.compare(getValueType(), encodedValue.getValueType());
        if (iCompare != 0) {
            return iCompare;
        }
        AnnotationEncodedValue annotationEncodedValue = (AnnotationEncodedValue) encodedValue;
        int iCompareTo = getType().compareTo(annotationEncodedValue.getType());
        return iCompareTo != 0 ? iCompareTo : CollectionUtils.compareAsSet(getElements(), annotationEncodedValue.getElements());
    }

    public String toString() {
        return DexFormatter.INSTANCE.getEncodedValue(this);
    }
}
