package com.reandroid.arsc.item;

/* JADX INFO: loaded from: classes10.dex */
public class NumberIntegerReference implements IntegerReference {
    private int value;

    public NumberIntegerReference(int i) {
        this.value = i;
    }

    public NumberIntegerReference() {
        this(0);
    }

    @Override // com.reandroid.arsc.item.IntegerReference
    public int get() {
        return this.value;
    }

    @Override // com.reandroid.arsc.item.IntegerReference
    public void set(int i) {
        this.value = i;
    }

    public String toString() {
        return Integer.toString(get());
    }
}
