package com.reandroid.dex.model;

import com.reandroid.dex.key.Key;
import com.reandroid.dex.smali.SmaliFormat;
import com.reandroid.dex.smali.SmaliWriter;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public abstract class Dex implements SmaliFormat {
    @Override // com.reandroid.dex.smali.SmaliFormat
    public abstract void append(SmaliWriter smaliWriter) throws IOException;

    public abstract DexClassRepository getClassRepository();

    public abstract void removeSelf();

    public abstract boolean uses(Key key);

    public String toSmaliString() {
        return SmaliWriter.toStringSafe(this);
    }

    public String toString() {
        return toSmaliString();
    }
}
