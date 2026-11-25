package com.reandroid.dex.smali.model;

import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.smali.SmaliDirective;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliRegion;
import com.reandroid.dex.smali.SmaliWriter;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliInterface extends Smali implements SmaliRegion {
    private TypeKey type;

    public TypeKey getType() {
        return this.type;
    }

    public void setType(TypeKey typeKey) {
        this.type = typeKey;
    }

    public String getTypeName() {
        TypeKey typeKey = this.type;
        if (typeKey != null) {
            return typeKey.getTypeName();
        }
        return null;
    }

    @Override // com.reandroid.dex.smali.SmaliRegion
    public SmaliDirective getSmaliDirective() {
        return SmaliDirective.IMPLEMENTS;
    }

    @Override // com.reandroid.dex.smali.model.Smali, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        getSmaliDirective().append(smaliWriter);
        smaliWriter.append((CharSequence) getType().getTypeName());
    }

    @Override // com.reandroid.dex.smali.SmaliParser
    public void parse(SmaliReader smaliReader) throws IOException {
        SmaliDirective.parse(smaliReader);
        setType(TypeKey.read(smaliReader));
    }
}
