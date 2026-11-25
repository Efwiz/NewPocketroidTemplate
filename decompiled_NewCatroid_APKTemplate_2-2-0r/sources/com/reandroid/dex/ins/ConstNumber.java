package com.reandroid.dex.ins;

import com.reandroid.arsc.item.IntegerReference;

/* JADX INFO: loaded from: classes11.dex */
public interface ConstNumber extends IntegerReference, RegistersSet {
    @Override // com.reandroid.dex.ins.RegistersSet
    int getRegister();

    @Override // com.reandroid.dex.ins.RegistersSet
    void setRegister(int i);
}
