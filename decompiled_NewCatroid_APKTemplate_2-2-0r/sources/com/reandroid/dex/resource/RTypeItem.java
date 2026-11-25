package com.reandroid.dex.resource;

import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.model.DexClass;
import com.reandroid.dex.smali.SmaliWriter;
import java.io.IOException;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;

/* JADX INFO: loaded from: classes11.dex */
public abstract class RTypeItem {
    private final DexClass dexClass;

    public abstract void appendJavaEntries(SmaliWriter smaliWriter) throws IOException;

    public abstract boolean isValid();

    public RTypeItem(DexClass dexClass) {
        this.dexClass = dexClass;
    }

    public String getName() {
        return getKey().getSimpleInnerName();
    }

    public TypeKey getKey() {
        return getDexClass().getKey();
    }

    public DexClass getDexClass() {
        return this.dexClass;
    }

    public void appendJava(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.appendModifiers(getDexClass().getAccessFlags());
        smaliWriter.append(ExternalAnnotationProvider.CLASS_PREFIX);
        smaliWriter.append((CharSequence) getName());
        smaliWriter.append(" {");
        smaliWriter.indentPlus();
        appendJavaEntries(smaliWriter);
        smaliWriter.indentMinus();
        smaliWriter.newLine();
        smaliWriter.append('}');
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        return getKey().equals(((RTypeItem) obj).getKey());
    }

    public int hashCode() {
        return getKey().hashCode();
    }

    public String toString() {
        return getKey().toString();
    }
}
