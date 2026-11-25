package com.reandroid.dex.base;

import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.arsc.item.IntegerReference;

/* JADX INFO: loaded from: classes7.dex */
public interface IntegerPair {
    IntegerReference getFirst();

    IntegerReference getSecond();

    static IntegerPair of(final IntegerReference integerReference, final IntegerReference integerReference2) {
        return new IntegerPair() { // from class: com.reandroid.dex.base.IntegerPair.1
            @Override // com.reandroid.dex.base.IntegerPair
            public IntegerReference getFirst() {
                return integerReference;
            }

            @Override // com.reandroid.dex.base.IntegerPair
            public IntegerReference getSecond() {
                return integerReference2;
            }

            public String toString() {
                return "(" + getFirst() + ", " + getSecond() + Constant.AFTER_QUTO;
            }
        };
    }
}
