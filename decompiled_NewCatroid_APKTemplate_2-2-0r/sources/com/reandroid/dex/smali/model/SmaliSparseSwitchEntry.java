package com.reandroid.dex.smali.model;

import com.reandroid.dex.smali.SmaliParseException;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliWriter;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliSparseSwitchEntry extends SmaliSwitchEntry {
    private final SmaliValueInteger smaliValue;

    public SmaliSparseSwitchEntry() {
        SmaliValueInteger smaliValueInteger = new SmaliValueInteger();
        this.smaliValue = smaliValueInteger;
        smaliValueInteger.setParent(this);
    }

    public int getValue() {
        return this.smaliValue.getValue();
    }

    public void setValue(int i) {
        this.smaliValue.setValue(i);
    }

    public SmaliValueInteger getSmaliValue() {
        return this.smaliValue;
    }

    @Override // com.reandroid.dex.smali.model.Smali, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.appendOptional(getSmaliValue());
        smaliWriter.append(" -> ");
        getLabel().append(smaliWriter);
    }

    @Override // com.reandroid.dex.smali.SmaliParser
    public void parse(SmaliReader smaliReader) throws IOException {
        smaliReader.skipWhitespacesOrComment();
        getSmaliValue().parse(smaliReader);
        smaliReader.skipSpaces();
        SmaliParseException.expect(smaliReader, '-');
        SmaliParseException.expect(smaliReader, '>');
        smaliReader.skipSpaces();
        getLabel().parse(smaliReader);
    }
}
