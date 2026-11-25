package com.reandroid.arsc.coder;

import com.reandroid.arsc.value.ValueType;
import com.reandroid.utils.HexUtil;

/* JADX INFO: loaded from: classes10.dex */
public class CoderUnknownAttributeId extends Coder {
    public static final CoderUnknownAttributeId INS = new CoderUnknownAttributeId();
    private static final int LENGTH = 11;
    private static final String PREFIX = "?0x";
    private static final char PREFIX_CHAR = '?';

    @Override // com.reandroid.arsc.coder.Coder
    boolean canStartWith(char c) {
        return c == '?';
    }

    @Override // com.reandroid.arsc.coder.Coder
    public EncodeResult encode(String str) {
        Integer hex;
        if (str == null || str.length() != 11 || !str.startsWith(PREFIX) || (hex = parseHex(str.substring(1))) == null) {
            return null;
        }
        return new EncodeResult(getValueType(), hex.intValue());
    }

    @Override // com.reandroid.arsc.coder.Coder
    public String decode(int i) {
        return HexUtil.toHex8(PREFIX, i);
    }

    @Override // com.reandroid.arsc.coder.Coder
    public ValueType getValueType() {
        return ValueType.ATTRIBUTE;
    }
}
