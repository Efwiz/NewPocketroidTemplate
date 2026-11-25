package com.reandroid.arsc.coder;

import com.reandroid.arsc.value.ValueType;

/* JADX INFO: loaded from: classes10.dex */
public class CoderFloat extends Coder {
    public static final CoderFloat INS = new CoderFloat();

    @Override // com.reandroid.arsc.coder.Coder
    public EncodeResult encode(String str) {
        Float f;
        if (str.indexOf(46) > 0 && (f = parseFloat(str)) != null) {
            return new EncodeResult(ValueType.FLOAT, Float.floatToIntBits(f.floatValue()));
        }
        return null;
    }

    @Override // com.reandroid.arsc.coder.Coder
    public String decode(int i) {
        return Float.toString(Float.intBitsToFloat(i));
    }

    @Override // com.reandroid.arsc.coder.Coder
    public ValueType getValueType() {
        return ValueType.FLOAT;
    }

    @Override // com.reandroid.arsc.coder.Coder
    boolean canStartWith(char c) {
        return isNumberStart(c);
    }
}
