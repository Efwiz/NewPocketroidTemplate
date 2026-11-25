package com.reandroid.dex.smali.model;

import com.reandroid.dex.smali.SmaliParseException;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.value.DexValueType;
import com.reandroid.utils.HexUtil;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliValueLong extends SmaliValueNumber<Long> {
    private long value;

    @Override // com.reandroid.dex.smali.model.SmaliValueNumber
    public int getWidth() {
        return 8;
    }

    public SmaliValueLong() {
        this(0L);
    }

    public SmaliValueLong(long j) {
        this.value = j;
    }

    public long getValue() {
        return this.value;
    }

    public void setValue(long j) {
        this.value = j;
    }

    @Override // com.reandroid.dex.smali.model.SmaliValueNumber
    public Long getNumber() {
        return Long.valueOf(getValue());
    }

    @Override // com.reandroid.dex.smali.model.SmaliValueNumber
    public void setNumber(Long l) {
        setValue(l.longValue());
    }

    @Override // com.reandroid.dex.smali.model.SmaliValueNumber
    public int unsignedInt() {
        return (int) getValue();
    }

    @Override // com.reandroid.dex.smali.model.SmaliValueNumber
    public long unsignedLong() {
        return getValue();
    }

    @Override // com.reandroid.dex.smali.model.SmaliValue
    public DexValueType<?> getValueType() {
        return DexValueType.LONG;
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
            setValue(HexUtil.parseHexLong(smaliReader.readStringForNumber()));
        } catch (NumberFormatException e) {
            smaliReader.position(iPosition);
            throw new SmaliParseException(e.getMessage(), smaliReader);
        }
    }
}
