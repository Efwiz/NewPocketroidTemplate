package com.reandroid.arsc.coder;

import com.reandroid.arsc.value.ValueType;

/* JADX INFO: loaded from: classes10.dex */
public class CoderDimension extends CoderComplexData {
    public static final CoderDimension INS = new CoderDimension();

    @Override // com.reandroid.arsc.coder.CoderComplexData
    ComplexUnit parseUnit(String str) {
        return UnitDimension.fromPostfix(str);
    }

    @Override // com.reandroid.arsc.coder.Coder
    public String decode(int i) {
        return ComplexUtil.decodeComplex(false, i);
    }

    @Override // com.reandroid.arsc.coder.Coder
    public ValueType getValueType() {
        return ValueType.DIMENSION;
    }
}
