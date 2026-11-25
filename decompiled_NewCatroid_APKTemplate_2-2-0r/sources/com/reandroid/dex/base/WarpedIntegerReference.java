package com.reandroid.dex.base;

import com.reandroid.arsc.item.IntegerReference;

/* JADX INFO: loaded from: classes7.dex */
public class WarpedIntegerReference implements IntegerReference {
    private IntegerReference reference;

    public WarpedIntegerReference(IntegerReference integerReference) {
        this.reference = integerReference;
    }

    public WarpedIntegerReference() {
        this(null);
    }

    public IntegerReference getReference() {
        return this.reference;
    }

    public void setReference(IntegerReference integerReference) {
        if (integerReference != this) {
            this.reference = integerReference;
        }
    }

    @Override // com.reandroid.arsc.item.IntegerReference
    public void set(int i) {
        IntegerReference integerReference = this.reference;
        if (integerReference != null) {
            integerReference.set(i);
        }
    }

    @Override // com.reandroid.arsc.item.IntegerReference
    public int get() {
        IntegerReference integerReference = this.reference;
        if (integerReference != null) {
            return integerReference.get();
        }
        return -1;
    }

    public String toString() {
        if (this.reference == null) {
            return "NULL";
        }
        return Integer.toString(get());
    }
}
