package com.reandroid.arsc.coder;

import com.reandroid.arsc.value.ValueType;

/* JADX INFO: loaded from: classes10.dex */
public class CoderNullAttribute extends Coder {
    public static final CoderNullAttribute INS = new CoderNullAttribute();

    @Override // com.reandroid.arsc.coder.Coder
    boolean canStartWith(char c) {
        return c == '?';
    }

    @Override // com.reandroid.arsc.coder.Coder
    public EncodeResult encode(String str) {
        if (str.equals("?null")) {
            return new EncodeResult(ValueType.ATTRIBUTE, 0);
        }
        return null;
    }

    @Override // com.reandroid.arsc.coder.Coder
    public String decode(int i) {
        if (i == 0) {
            return "?null";
        }
        return null;
    }

    @Override // com.reandroid.arsc.coder.Coder
    public ValueType getValueType() {
        return ValueType.ATTRIBUTE;
    }
}
