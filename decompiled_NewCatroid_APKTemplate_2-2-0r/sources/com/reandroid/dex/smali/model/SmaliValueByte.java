package com.reandroid.dex.smali.model;

import com.reandroid.dex.smali.SmaliParseException;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.value.DexValueType;
import com.reandroid.utils.HexUtil;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliValueByte extends SmaliValueNumber<Byte> {
    private byte value;

    @Override // com.reandroid.dex.smali.model.SmaliValueNumber
    public int getWidth() {
        return 1;
    }

    public SmaliValueByte() {
        this((byte) 0);
    }

    public SmaliValueByte(byte b) {
        this.value = b;
    }

    public byte getValue() {
        return this.value;
    }

    public void setValue(byte b) {
        this.value = b;
    }

    @Override // com.reandroid.dex.smali.model.SmaliValueNumber
    public Byte getNumber() {
        return Byte.valueOf(getValue());
    }

    @Override // com.reandroid.dex.smali.model.SmaliValueNumber
    public void setNumber(Byte b) {
        setValue(b.byteValue());
    }

    @Override // com.reandroid.dex.smali.model.SmaliValueNumber
    public int unsignedInt() {
        return getValue() & 255;
    }

    @Override // com.reandroid.dex.smali.model.SmaliValueNumber
    public long unsignedLong() {
        return ((long) getValue()) & 255;
    }

    @Override // com.reandroid.dex.smali.model.SmaliValue
    public DexValueType<?> getValueType() {
        return DexValueType.BYTE;
    }

    @Override // com.reandroid.dex.smali.model.Smali, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.appendHex(getValue());
    }

    @Override // com.reandroid.dex.smali.model.SmaliValue, com.reandroid.dex.smali.SmaliParser
    public void parse(SmaliReader smaliReader) throws IOException {
        smaliReader.skipSpaces();
        int iPosition = smaliReader.position();
        try {
            setValue(HexUtil.parseHexByte(smaliReader.readStringForNumber()));
        } catch (NumberFormatException e) {
            smaliReader.position(iPosition);
            throw new SmaliParseException(e.getMessage(), smaliReader);
        }
    }
}
