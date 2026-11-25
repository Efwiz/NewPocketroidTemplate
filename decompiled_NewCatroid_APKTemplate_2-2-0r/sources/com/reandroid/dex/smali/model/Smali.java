package com.reandroid.dex.smali.model;

import com.reandroid.common.Origin;
import com.reandroid.dex.smali.SmaliFormat;
import com.reandroid.dex.smali.SmaliParser;
import com.reandroid.dex.smali.SmaliWriter;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public abstract class Smali implements SmaliFormat, SmaliParser {
    private Origin origin;
    private Smali parent;

    @Override // com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
    }

    public Origin getOrigin() {
        return this.origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public Smali getParent() {
        return this.parent;
    }

    public <T extends Smali> T getParent(Class<T> cls) {
        T t = (T) getParent();
        if (t == null) {
            return null;
        }
        return t.getClass() == cls ? t : (T) t.getParent(cls);
    }

    public <T extends Smali> T getParentInstance(Class<T> cls) {
        T t = (T) getParent();
        if (t == null) {
            return null;
        }
        return cls.isInstance(t) ? t : (T) t.getParentInstance(cls);
    }

    void setParent(Smali smali) {
        if (smali == this) {
            throw new RuntimeException("Cyclic parent set");
        }
        this.parent = smali;
    }

    public String toDebugString() {
        Origin origin = getOrigin();
        if (origin != null) {
            return origin.toString();
        }
        try {
            return toString();
        } catch (Throwable th) {
            return th.getMessage();
        }
    }

    public String toString() {
        return SmaliWriter.toStringSafe(this);
    }
}
