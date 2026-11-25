package com.reandroid.arsc.coder;

import com.reandroid.arsc.value.ValueType;

/* JADX INFO: loaded from: classes10.dex */
public class CoderInteger extends Coder {
    public static final CoderInteger INS = new CoderInteger();

    @Override // com.reandroid.arsc.coder.Coder
    public EncodeResult encode(String str) {
        Integer integer;
        int iIndexOf = str.indexOf(46);
        if (iIndexOf < 0) {
            iIndexOf = str.indexOf(120);
        }
        if (iIndexOf < 0 && (integer = parseInteger(str)) != null) {
            return new EncodeResult(ValueType.DEC, integer.intValue());
        }
        return null;
    }

    @Override // com.reandroid.arsc.coder.Coder
    public String decode(int i) {
        return Integer.toString(i);
    }

    @Override // com.reandroid.arsc.coder.Coder
    public ValueType getValueType() {
        return ValueType.DEC;
    }

    @Override // com.reandroid.arsc.coder.Coder
    boolean canStartWith(char c) {
        return isNumberStart(c);
    }
}
