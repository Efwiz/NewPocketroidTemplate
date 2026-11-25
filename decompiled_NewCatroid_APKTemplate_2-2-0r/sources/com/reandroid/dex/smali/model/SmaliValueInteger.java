package com.reandroid.dex.smali.model;

import com.reandroid.dex.smali.SmaliParseException;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.value.DexValueType;
import com.reandroid.utils.HexUtil;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliValueInteger extends SmaliValueNumber<Integer> {
    private int value;

    @Override // com.reandroid.dex.smali.model.SmaliValueNumber
    public int getWidth() {
        return 4;
    }

    public SmaliValueInteger() {
        this(0);
    }

    public SmaliValueInteger(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int i) {
        this.value = i;
    }

    @Override // com.reandroid.dex.smali.model.SmaliValueNumber
    public Integer getNumber() {
        return Integer.valueOf(getValue());
    }

    @Override // com.reandroid.dex.smali.model.SmaliValueNumber
    public void setNumber(Integer num) {
        setValue(num.intValue());
    }

    @Override // com.reandroid.dex.smali.model.SmaliValueNumber
    public int unsignedInt() {
        return getValue();
    }

    @Override // com.reandroid.dex.smali.model.SmaliValueNumber
    public long unsignedLong() {
        return ((long) getValue()) & 4294967295L;
    }

    @Override // com.reandroid.dex.smali.model.SmaliValue
    public DexValueType<?> getValueType() {
        return DexValueType.INT;
    }

    @Override // com.reandroid.dex.smali.model.Smali, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.appendHex(getValue());
    }

    @Override // com.reandroid.dex.smali.model.SmaliValue, com.reandroid.dex.smali.SmaliParser
    public void parse(SmaliReader smaliReader) throws IOException {
        smaliReader.skipSpaces();
        try {
            setValue(HexUtil.parseHexInteger(smaliReader.readStringForNumber()));
        } catch (NumberFormatException unused) {
            smaliReader.position(smaliReader.position());
            throw new SmaliParseException("Invalid hex integer", smaliReader);
        }
    }
}
