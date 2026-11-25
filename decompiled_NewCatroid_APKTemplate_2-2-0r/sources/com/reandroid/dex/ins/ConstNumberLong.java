package com.reandroid.dex.ins;

/* JADX INFO: loaded from: classes11.dex */
public interface ConstNumberLong extends ConstNumber {
    long getLong();

    void set(long j);

    @Override // com.reandroid.arsc.item.IntegerReference
    default int get() {
        long j = getLong();
        int i = (int) j;
        if ((((long) i) & 4294967295L) != j) {
            return 0;
        }
        return i;
    }

    @Override // com.reandroid.arsc.item.IntegerReference
    default void set(int i) {
        set(i);
    }
}
