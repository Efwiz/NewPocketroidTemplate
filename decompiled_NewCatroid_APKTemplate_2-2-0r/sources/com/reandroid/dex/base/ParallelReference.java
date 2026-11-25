package com.reandroid.dex.base;

import com.reandroid.arsc.base.BlockRefresh;
import com.reandroid.arsc.item.IntegerReference;

/* JADX INFO: loaded from: classes7.dex */
public class ParallelReference implements IntegerReference, BlockRefresh {
    private final IntegerReference reference1;
    private IntegerReference reference2;

    public ParallelReference(IntegerReference integerReference, IntegerReference integerReference2) {
        this.reference1 = integerReference;
        this.reference2 = integerReference2;
    }

    public ParallelReference(IntegerReference integerReference) {
        this(integerReference, null);
    }

    public IntegerReference getReference2() {
        return this.reference2;
    }

    public void setReference2(IntegerReference integerReference) {
        if (integerReference != this) {
            this.reference2 = integerReference;
        }
    }

    @Override // com.reandroid.arsc.item.IntegerReference
    public void set(int i) {
        this.reference1.set(i);
        IntegerReference integerReference = this.reference2;
        if (integerReference != null) {
            integerReference.set(i);
        }
    }

    @Override // com.reandroid.arsc.item.IntegerReference
    public int get() {
        return this.reference1.get();
    }

    @Override // com.reandroid.arsc.base.BlockRefresh
    public void refresh() {
        set(get());
    }

    public int get2() {
        IntegerReference integerReference = this.reference2;
        if (integerReference != null) {
            return integerReference.get();
        }
        return this.reference1.get();
    }

    public String toString() {
        int i = this.reference1.get();
        IntegerReference integerReference = this.reference2;
        if (integerReference == null) {
            return Integer.toString(i);
        }
        if (i == integerReference.get()) {
            return Integer.toString(i);
        }
        return "reference1=" + this.reference1 + ", reference2=" + this.reference2;
    }
}
