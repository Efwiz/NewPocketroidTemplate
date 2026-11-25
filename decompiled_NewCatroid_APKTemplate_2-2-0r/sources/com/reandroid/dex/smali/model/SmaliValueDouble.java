package com.reandroid.dex.smali.model;

import com.reandroid.dex.smali.SmaliParseException;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.value.DexValueType;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliValueDouble extends SmaliValueNumber<Double> {
    private double value;

    @Override // com.reandroid.dex.smali.model.SmaliValueNumber
    public int getWidth() {
        return 8;
    }

    public SmaliValueDouble() {
        this(0.0d);
    }

    public SmaliValueDouble(double d) {
        this.value = d;
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double d) {
        this.value = d;
    }

    @Override // com.reandroid.dex.smali.model.SmaliValueNumber
    public Double getNumber() {
        return Double.valueOf(getValue());
    }

    @Override // com.reandroid.dex.smali.model.SmaliValueNumber
    public void setNumber(Double d) {
        setValue(d.doubleValue());
    }

    @Override // com.reandroid.dex.smali.model.SmaliValueNumber
    public int unsignedInt() {
        return (int) getNumber().longValue();
    }

    @Override // com.reandroid.dex.smali.model.SmaliValueNumber
    public long unsignedLong() {
        return getNumber().longValue();
    }

    @Override // com.reandroid.dex.smali.model.SmaliValue
    public DexValueType<?> getValueType() {
        return DexValueType.DOUBLE;
    }

    @Override // com.reandroid.dex.smali.model.Smali, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.append(getValue());
    }

    @Override // com.reandroid.dex.smali.model.SmaliValue, com.reandroid.dex.smali.SmaliParser
    public void parse(SmaliReader smaliReader) throws IOException {
        smaliReader.skipSpaces();
        int iPosition = smaliReader.position();
        try {
            setValue(Double.parseDouble(smaliReader.readStringForNumber()));
        } catch (NumberFormatException e) {
            smaliReader.position(iPosition);
            throw new SmaliParseException(e.getMessage(), smaliReader);
        }
    }
}
