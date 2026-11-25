package com.reandroid.dex.smali.model;

import com.reandroid.dex.smali.SmaliParseException;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.value.DexValueType;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliValueFloat extends SmaliValueNumber<Float> {
    private float value;
    public static final byte[] POSITIVE_INFINITY = {73, 110, 102, 105, 110, 105, 116, 121, 102};
    public static final byte[] NEGATIVE_INFINITY = {45, 73, 110, 102, 105, 110, 105, 116, 121, 102};
    public static final byte[] NAN = {78, 97, 78, 102};

    @Override // com.reandroid.dex.smali.model.SmaliValueNumber
    public int getWidth() {
        return 4;
    }

    public SmaliValueFloat() {
        this(0.0f);
    }

    public SmaliValueFloat(float f) {
        this.value = f;
    }

    public float getValue() {
        return this.value;
    }

    public void setValue(float f) {
        this.value = f;
    }

    @Override // com.reandroid.dex.smali.model.SmaliValueNumber
    public Float getNumber() {
        return Float.valueOf(getValue());
    }

    @Override // com.reandroid.dex.smali.model.SmaliValueNumber
    public void setNumber(Float f) {
        setValue(f.floatValue());
    }

    @Override // com.reandroid.dex.smali.model.SmaliValueNumber
    public int unsignedInt() {
        return getNumber().intValue();
    }

    @Override // com.reandroid.dex.smali.model.SmaliValueNumber
    public long unsignedLong() {
        return ((long) getNumber().intValue()) & 4294967295L;
    }

    @Override // com.reandroid.dex.smali.model.SmaliValue
    public DexValueType<?> getValueType() {
        return DexValueType.FLOAT;
    }

    @Override // com.reandroid.dex.smali.model.Smali, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.append(getValue());
    }

    @Override // com.reandroid.dex.smali.model.SmaliValue, com.reandroid.dex.smali.SmaliParser
    public void parse(SmaliReader smaliReader) throws IOException {
        float f;
        smaliReader.skipSpaces();
        int iPosition = smaliReader.position();
        try {
            byte[] bArr = POSITIVE_INFINITY;
            if (smaliReader.startsWith(bArr)) {
                smaliReader.skip(bArr.length);
                f = Float.POSITIVE_INFINITY;
            } else {
                byte[] bArr2 = NEGATIVE_INFINITY;
                if (smaliReader.startsWith(bArr2)) {
                    smaliReader.skip(bArr2.length);
                    f = Float.NEGATIVE_INFINITY;
                } else {
                    byte[] bArr3 = NAN;
                    if (smaliReader.startsWith(bArr3)) {
                        smaliReader.skip(bArr3.length);
                        f = Float.NaN;
                    } else {
                        f = Float.parseFloat(smaliReader.readStringForNumber());
                    }
                }
            }
            setValue(f);
        } catch (NumberFormatException e) {
            smaliReader.position(iPosition);
            throw new SmaliParseException(e.getMessage(), smaliReader);
        }
    }
}
