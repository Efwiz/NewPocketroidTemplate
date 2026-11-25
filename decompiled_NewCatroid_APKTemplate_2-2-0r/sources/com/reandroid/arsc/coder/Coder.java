package com.reandroid.arsc.coder;

import com.reandroid.arsc.value.ValueType;
import com.reandroid.utils.HexUtil;

/* JADX INFO: loaded from: classes10.dex */
public abstract class Coder {
    static boolean isNumberStart(char c) {
        return c == '-' || (c <= '9' && c >= '0');
    }

    abstract boolean canStartWith(char c);

    public abstract String decode(int i);

    public abstract EncodeResult encode(String str);

    public abstract ValueType getValueType();

    static Integer parseInteger(String str) {
        try {
            return Integer.valueOf(Integer.parseInt(str));
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    static Integer parseHex(String str) {
        try {
            return Integer.valueOf(HexUtil.parseHex(str));
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    static Float parseFloat(String str) {
        try {
            return Float.valueOf(Float.parseFloat(str));
        } catch (NumberFormatException unused) {
            return null;
        }
    }
}
