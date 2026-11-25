package com.reandroid.dex.smali.model;

import com.reandroid.dex.key.TypeListKey;
import com.reandroid.dex.smali.SmaliDirective;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliRegion;
import com.reandroid.dex.smali.SmaliWriter;
import java.io.IOException;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliInterfaceSet extends SmaliSet<SmaliInterface> implements SmaliRegion, Iterable<SmaliInterface> {
    public TypeListKey getKey() {
        int size = size();
        if (size == 0) {
            return null;
        }
        String[] strArr = new String[size];
        for (int i = 0; i < size; i++) {
            strArr[i] = get(i).getTypeName();
        }
        return new TypeListKey(strArr);
    }

    @Override // com.reandroid.dex.smali.SmaliRegion
    public SmaliDirective getSmaliDirective() {
        return SmaliDirective.IMPLEMENTS;
    }

    @Override // com.reandroid.dex.smali.model.SmaliSet, com.reandroid.dex.smali.model.Smali, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        if (isEmpty()) {
            return;
        }
        smaliWriter.newLine();
        smaliWriter.newLine();
        smaliWriter.newLine();
        smaliWriter.appendComment(Namer.METADATA_SUPERTYPES);
        smaliWriter.newLine();
        smaliWriter.appendAll(iterator());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.reandroid.dex.smali.model.SmaliSet
    public SmaliInterface createNext(SmaliReader smaliReader) {
        smaliReader.skipWhitespacesOrComment();
        if (SmaliDirective.parse(smaliReader, false) != getSmaliDirective()) {
            return null;
        }
        return new SmaliInterface();
    }

    public static SmaliInterfaceSet read(SmaliReader smaliReader) throws IOException {
        SmaliInterfaceSet smaliInterfaceSet = new SmaliInterfaceSet();
        smaliInterfaceSet.parse(smaliReader);
        if (smaliInterfaceSet.isEmpty()) {
            return null;
        }
        return smaliInterfaceSet;
    }
}
