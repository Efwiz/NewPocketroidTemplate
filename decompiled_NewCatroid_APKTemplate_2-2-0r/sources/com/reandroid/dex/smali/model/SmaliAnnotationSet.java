package com.reandroid.dex.smali.model;

import com.reandroid.dex.smali.SmaliDirective;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliWriter;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliAnnotationSet extends SmaliSet<SmaliAnnotationItem> {
    @Override // com.reandroid.dex.smali.model.SmaliSet, com.reandroid.dex.smali.model.Smali, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.appendAll(iterator());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.reandroid.dex.smali.model.SmaliSet
    public SmaliAnnotationItem createNext(SmaliReader smaliReader) {
        smaliReader.skipWhitespacesOrComment();
        SmaliDirective smaliDirective = SmaliDirective.parse(smaliReader, false);
        if (smaliDirective != SmaliDirective.ANNOTATION && smaliDirective != SmaliDirective.SUB_ANNOTATION) {
            return null;
        }
        if (smaliDirective.isEnd(smaliReader)) {
            SmaliDirective.parse(smaliReader);
            return null;
        }
        return new SmaliAnnotationItem();
    }

    public static SmaliAnnotationSet read(SmaliReader smaliReader) throws IOException {
        SmaliAnnotationSet smaliAnnotationSet = new SmaliAnnotationSet();
        smaliAnnotationSet.parse(smaliReader);
        if (smaliAnnotationSet.isEmpty()) {
            return null;
        }
        return smaliAnnotationSet;
    }
}
