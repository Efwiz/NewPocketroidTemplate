package com.reandroid.arsc.coder;

/* JADX INFO: loaded from: classes10.dex */
public abstract class CoderComplexData extends Coder {
    abstract ComplexUnit parseUnit(String str);

    @Override // com.reandroid.arsc.coder.Coder
    public EncodeResult encode(String str) {
        Float f;
        ComplexUnit unit = parseUnit(str);
        if (unit == null || (f = parseFloat(str.substring(0, str.length() - unit.getSymbol().length()))) == null) {
            return null;
        }
        return new EncodeResult(unit.getValueType(), ComplexUtil.encodeComplex(f.floatValue(), unit));
    }

    @Override // com.reandroid.arsc.coder.Coder
    boolean canStartWith(char c) {
        return isNumberStart(c);
    }
}
