package com.reandroid.dex.smali.model;

import com.reandroid.dex.common.Register;
import com.reandroid.dex.common.RegistersTable;
import com.reandroid.dex.smali.SmaliParseException;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliWriter;
import java.io.IOException;
import net.bytebuddy.asm.Advice;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliRegister extends Smali {
    private int number;
    private boolean parameter;

    public Register toRegister() {
        return toRegister(null);
    }

    public Register toRegister(RegistersTable registersTable) {
        return new Register(getNumber(), isParameter(), registersTable);
    }

    public boolean isParameter() {
        return this.parameter;
    }

    public void setParameter(boolean z) {
        this.parameter = z;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int i) {
        this.number = i;
    }

    @Override // com.reandroid.dex.smali.model.Smali, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        if (isParameter()) {
            smaliWriter.append(Advice.OffsetMapping.ForOrigin.Renderer.ForPropertyName.SYMBOL);
        } else {
            smaliWriter.append('v');
        }
        smaliWriter.appendInteger(getNumber());
    }

    @Override // com.reandroid.dex.smali.SmaliParser
    public void parse(SmaliReader smaliReader) throws IOException {
        smaliReader.skipSpaces();
        setParameter(SmaliParseException.expect(smaliReader, 'v', Advice.OffsetMapping.ForOrigin.Renderer.ForPropertyName.SYMBOL) == 'p');
        setNumber(smaliReader.readInteger());
    }
}
