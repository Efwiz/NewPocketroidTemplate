package com.reandroid.dex.smali.model;

import com.reandroid.dex.smali.SmaliParseException;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.value.DexValueType;
import com.reandroid.utils.HexUtil;
import java.io.IOException;
import okhttp3.internal.ws.WebSocketProtocol;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliValueShort extends SmaliValueNumber<Short> {
    private short value;

    @Override // com.reandroid.dex.smali.model.SmaliValueNumber
    public int getWidth() {
        return 2;
    }

    public SmaliValueShort() {
        this((short) 0);
    }

    public SmaliValueShort(short s) {
        this.value = s;
    }

    public short getValue() {
        return this.value;
    }

    public void setValue(short s) {
        this.value = s;
    }

    @Override // com.reandroid.dex.smali.model.SmaliValueNumber
    public Short getNumber() {
        return Short.valueOf(getValue());
    }

    @Override // com.reandroid.dex.smali.model.SmaliValueNumber
    public void setNumber(Short sh) {
        setValue(sh.shortValue());
    }

    @Override // com.reandroid.dex.smali.model.SmaliValueNumber
    public int unsignedInt() {
        return getValue() & 65535;
    }

    @Override // com.reandroid.dex.smali.model.SmaliValueNumber
    public long unsignedLong() {
        return ((long) getValue()) & WebSocketProtocol.PAYLOAD_SHORT_MAX;
    }

    @Override // com.reandroid.dex.smali.model.SmaliValue
    public DexValueType<?> getValueType() {
        return DexValueType.SHORT;
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
            setValue(HexUtil.parseHexShort(smaliReader.readStringForNumber()));
        } catch (NumberFormatException e) {
            smaliReader.position(iPosition);
            throw new SmaliParseException(e.getMessage(), smaliReader);
        }
    }
}
