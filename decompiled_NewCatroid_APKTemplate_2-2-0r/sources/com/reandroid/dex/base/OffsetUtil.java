package com.reandroid.dex.base;

import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.arsc.base.OffsetSupplier;
import com.reandroid.arsc.item.IntegerReference;

/* JADX INFO: loaded from: classes7.dex */
public class OffsetUtil {
    public static IntegerPair from(final IntegerReference integerReference, final OffsetSupplier offsetSupplier) {
        return new IntegerPair() { // from class: com.reandroid.dex.base.OffsetUtil.1
            @Override // com.reandroid.dex.base.IntegerPair
            public IntegerReference getFirst() {
                return integerReference;
            }

            @Override // com.reandroid.dex.base.IntegerPair
            public IntegerReference getSecond() {
                return offsetSupplier.getOffsetReference();
            }

            public String toString() {
                return "(" + getFirst() + ", " + getSecond() + Constant.AFTER_QUTO;
            }
        };
    }
}
