package com.reandroid.dex.smali.model;

import com.reandroid.dex.debug.DebugElementType;
import com.reandroid.dex.key.StringKey;
import com.reandroid.dex.smali.SmaliParseException;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliWriter;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliDebugSetSourceFile extends SmaliDebugElement {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private StringKey f253name;

    public StringKey getName() {
        return this.f253name;
    }

    public void setName(StringKey stringKey) {
        this.f253name = stringKey;
    }

    @Override // com.reandroid.dex.smali.model.SmaliDebug, com.reandroid.dex.smali.model.Smali, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        super.append(smaliWriter);
        smaliWriter.append(", ");
        smaliWriter.appendOptional(getName());
    }

    @Override // com.reandroid.dex.smali.model.SmaliDebug, com.reandroid.dex.smali.model.SmaliCode, com.reandroid.dex.smali.SmaliParser
    public void parse(SmaliReader smaliReader) throws IOException {
        super.parse(smaliReader);
        smaliReader.skipWhitespacesOrComment();
        SmaliParseException.expect(smaliReader, ',');
        smaliReader.skipWhitespacesOrComment();
        setName(StringKey.read(smaliReader));
    }

    @Override // com.reandroid.dex.smali.model.SmaliDebugElement
    public DebugElementType<?> getDebugElementType() {
        return DebugElementType.SET_SOURCE_FILE;
    }
}
