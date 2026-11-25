package org.jf.dexlib2.base.reference;

import org.jf.dexlib2.formatter.DexFormatter;
import org.jf.dexlib2.iface.reference.TypeReference;

/* JADX INFO: loaded from: classes6.dex */
public abstract class BaseTypeReference extends BaseReference implements TypeReference {
    @Override // org.jf.dexlib2.iface.reference.TypeReference
    public int hashCode() {
        return getType().hashCode();
    }

    @Override // org.jf.dexlib2.iface.reference.TypeReference
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof TypeReference) {
            return getType().equals(((TypeReference) obj).getType());
        }
        if (obj instanceof CharSequence) {
            return getType().equals(obj.toString());
        }
        return false;
    }

    @Override // org.jf.dexlib2.iface.reference.TypeReference, java.lang.Comparable
    public int compareTo(CharSequence charSequence) {
        return getType().compareTo(charSequence.toString());
    }

    @Override // java.lang.CharSequence
    public int length() {
        return getType().length();
    }

    @Override // java.lang.CharSequence
    public char charAt(int i) {
        return getType().charAt(i);
    }

    @Override // java.lang.CharSequence
    public CharSequence subSequence(int i, int i2) {
        return getType().subSequence(i, i2);
    }

    @Override // java.lang.CharSequence
    public String toString() {
        return DexFormatter.INSTANCE.getType(this);
    }
}
