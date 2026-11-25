package com.reandroid.dex.smali.model;

import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.smali.SmaliDirective;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliWriter;
import io.netty.util.internal.StringUtil;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliCodeCatch extends SmaliCodeExceptionHandler {
    private TypeKey type;

    public TypeKey getType() {
        return this.type;
    }

    public void setType(TypeKey typeKey) {
        this.type = typeKey;
    }

    @Override // com.reandroid.dex.smali.SmaliRegion
    public SmaliDirective getSmaliDirective() {
        return SmaliDirective.CATCH;
    }

    @Override // com.reandroid.dex.smali.model.SmaliCodeExceptionHandler
    public void appendType(SmaliWriter smaliWriter) throws IOException {
        getType().append(smaliWriter);
        smaliWriter.append(StringUtil.SPACE);
    }

    @Override // com.reandroid.dex.smali.model.SmaliCodeExceptionHandler
    void parseType(SmaliReader smaliReader) throws IOException {
        setType(TypeKey.read(smaliReader));
    }
}
