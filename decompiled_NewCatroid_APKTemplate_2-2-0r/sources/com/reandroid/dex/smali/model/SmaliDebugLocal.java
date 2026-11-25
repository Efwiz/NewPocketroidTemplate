package com.reandroid.dex.smali.model;

import com.reandroid.dex.debug.DebugElementType;
import com.reandroid.dex.debug.DebugStartLocal;
import com.reandroid.dex.key.StringKey;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.smali.SmaliParseException;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliWriter;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliDebugLocal extends SmaliDebugRegister {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private StringKey f252name;
    private StringKey signature;
    private TypeKey type;

    public StringKey getName() {
        return this.f252name;
    }

    public void setName(StringKey stringKey) {
        this.f252name = stringKey;
    }

    public TypeKey getType() {
        return this.type;
    }

    public void setType(TypeKey typeKey) {
        this.type = typeKey;
    }

    public StringKey getSignature() {
        return this.signature;
    }

    public void setSignature(StringKey stringKey) {
        this.signature = stringKey;
    }

    public boolean isExtended() {
        return this.signature != null;
    }

    @Override // com.reandroid.dex.smali.model.SmaliDebugRegister, com.reandroid.dex.smali.model.SmaliDebugElement
    public DebugElementType<? extends DebugStartLocal> getDebugElementType() {
        if (isExtended()) {
            return DebugElementType.START_LOCAL_EXTENDED;
        }
        return DebugElementType.START_LOCAL;
    }

    @Override // com.reandroid.dex.smali.model.SmaliDebugRegister, com.reandroid.dex.smali.model.SmaliDebug, com.reandroid.dex.smali.model.Smali, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        super.append(smaliWriter);
        smaliWriter.append(", ");
        smaliWriter.appendOptional(getName());
        smaliWriter.append(':');
        smaliWriter.appendOptional(getType());
        StringKey signature = getSignature();
        if (signature != null) {
            smaliWriter.append(", ");
            signature.append(smaliWriter);
        }
    }

    @Override // com.reandroid.dex.smali.model.SmaliDebugRegister, com.reandroid.dex.smali.model.SmaliDebug, com.reandroid.dex.smali.model.SmaliCode, com.reandroid.dex.smali.SmaliParser
    public void parse(SmaliReader smaliReader) throws IOException {
        super.parse(smaliReader);
        smaliReader.skipWhitespacesOrComment();
        SmaliParseException.expect(smaliReader, ',');
        smaliReader.skipWhitespacesOrComment();
        setName(StringKey.read(smaliReader));
        smaliReader.skipSpaces();
        SmaliParseException.expect(smaliReader, ':');
        smaliReader.skipSpaces();
        setType(TypeKey.read(smaliReader));
        smaliReader.skipSpaces();
        if (smaliReader.get() == 44) {
            smaliReader.skip(1);
            smaliReader.skipWhitespacesOrComment();
            setSignature(StringKey.read(smaliReader));
        }
    }
}
