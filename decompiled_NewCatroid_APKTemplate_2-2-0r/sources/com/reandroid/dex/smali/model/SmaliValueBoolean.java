package com.reandroid.dex.smali.model;

import com.reandroid.dex.smali.SmaliParseException;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.value.DexValueType;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliValueBoolean extends SmaliValue {
    private boolean value;

    public boolean getValue() {
        return this.value;
    }

    public void setValue(boolean z) {
        this.value = z;
    }

    @Override // com.reandroid.dex.smali.model.SmaliValue
    public DexValueType<?> getValueType() {
        return DexValueType.BOOLEAN;
    }

    @Override // com.reandroid.dex.smali.model.Smali, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        if (getValue()) {
            smaliWriter.append("true");
        } else {
            smaliWriter.append("false");
        }
    }

    @Override // com.reandroid.dex.smali.model.SmaliValue, com.reandroid.dex.smali.SmaliParser
    public void parse(SmaliReader smaliReader) throws IOException {
        smaliReader.skipSpaces();
        if (smaliReader.startsWith(new byte[]{102, 97, 108, 115, 101})) {
            smaliReader.skip(5);
            setValue(false);
        } else {
            if (smaliReader.startsWith(new byte[]{116, 114, 117, 101})) {
                smaliReader.skip(4);
                setValue(true);
                return;
            }
            throw new SmaliParseException("Not boolean value", smaliReader);
        }
    }
}
