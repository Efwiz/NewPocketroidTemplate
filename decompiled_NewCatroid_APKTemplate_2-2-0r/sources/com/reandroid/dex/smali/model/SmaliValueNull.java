package com.reandroid.dex.smali.model;

import com.reandroid.dex.smali.SmaliParseException;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.value.DexValueType;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliValueNull extends SmaliValue {
    @Override // com.reandroid.dex.smali.model.SmaliValue
    public DexValueType<?> getValueType() {
        return DexValueType.NULL;
    }

    @Override // com.reandroid.dex.smali.model.Smali, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.append("null");
    }

    @Override // com.reandroid.dex.smali.model.SmaliValue, com.reandroid.dex.smali.SmaliParser
    public void parse(SmaliReader smaliReader) throws IOException {
        smaliReader.skipSpaces();
        if (smaliReader.startsWith(new byte[]{110, 117, 108, 108})) {
            smaliReader.skip(4);
            return;
        }
        throw new SmaliParseException("Not 'null' value", smaliReader);
    }
}
