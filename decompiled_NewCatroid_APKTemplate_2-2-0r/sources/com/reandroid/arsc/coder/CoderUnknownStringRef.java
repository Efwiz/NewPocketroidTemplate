package com.reandroid.arsc.coder;

import com.reandroid.arsc.value.ValueType;
import com.reandroid.utils.HexUtil;

/* JADX INFO: loaded from: classes10.dex */
public class CoderUnknownStringRef extends Coder {
    private static final char PREFIX_CHAR = 's';
    public static final CoderUnknownStringRef INS = new CoderUnknownStringRef();
    private static final String PREFIX = "string-reference@0x";
    private static final int LENGTH = PREFIX.length() + 8;

    @Override // com.reandroid.arsc.coder.Coder
    boolean canStartWith(char c) {
        return c == 's';
    }

    @Override // com.reandroid.arsc.coder.Coder
    public EncodeResult encode(String str) {
        Integer hex;
        if (str == null || str.length() != LENGTH || !str.startsWith(PREFIX) || (hex = parseHex(str.substring(PREFIX.length() + 1))) == null) {
            return null;
        }
        return new EncodeResult(ValueType.STRING, hex.intValue());
    }

    @Override // com.reandroid.arsc.coder.Coder
    public String decode(int i) {
        return HexUtil.toHex8(PREFIX, i);
    }

    @Override // com.reandroid.arsc.coder.Coder
    public ValueType getValueType() {
        return ValueType.STRING;
    }
}
