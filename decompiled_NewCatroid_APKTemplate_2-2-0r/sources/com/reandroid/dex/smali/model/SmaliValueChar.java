package com.reandroid.dex.smali.model;

import com.reandroid.dex.common.DexUtils;
import com.reandroid.dex.smali.SmaliParseException;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.value.DexValueType;
import com.reandroid.utils.HexUtil;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliValueChar extends SmaliValue {
    private char value;

    public char getValue() {
        return this.value;
    }

    public void setValue(char c) {
        this.value = c;
    }

    @Override // com.reandroid.dex.smali.model.SmaliValue
    public DexValueType<?> getValueType() {
        return DexValueType.CHAR;
    }

    @Override // com.reandroid.dex.smali.model.Smali, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        DexUtils.appendSingleQuotedChar(smaliWriter, getValue());
    }

    @Override // com.reandroid.dex.smali.model.SmaliValue, com.reandroid.dex.smali.SmaliParser
    public void parse(SmaliReader smaliReader) throws IOException {
        smaliReader.skipSpaces();
        if (smaliReader.read() != 39) {
            smaliReader.skip(-1);
            throw new SmaliParseException("Missing start \"'\"", smaliReader);
        }
        char ascii = smaliReader.readASCII();
        if (ascii == '\\') {
            ascii = smaliReader.readASCII();
            if (ascii == 'u') {
                try {
                    ascii = (char) HexUtil.parseHex(smaliReader.readString(4));
                } catch (NumberFormatException unused) {
                    smaliReader.skip(-4);
                    throw new SmaliParseException("Invalid four-char hex encoded char", smaliReader);
                }
            } else if (ascii == 'b') {
                ascii = '\b';
            } else if (ascii == 'f') {
                ascii = '\f';
            } else if (ascii == 'n') {
                ascii = '\n';
            } else if (ascii == 'r') {
                ascii = '\r';
            } else if (ascii == 't') {
                ascii = '\t';
            }
        }
        setValue(ascii);
        if (smaliReader.read() == 39) {
            return;
        }
        smaliReader.skip(-2);
        throw new SmaliParseException("Missing end \"'\"", smaliReader);
    }
}
