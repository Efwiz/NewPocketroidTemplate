package com.reandroid.dex.smali.model;

import com.reandroid.dex.smali.SmaliDirective;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliRegion;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.value.DexValueType;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliValueAnnotation extends SmaliValue implements SmaliRegion {
    private SmaliAnnotationItem value;

    public SmaliAnnotationItem getValue() {
        return this.value;
    }

    public void setValue(SmaliAnnotationItem smaliAnnotationItem) {
        this.value = smaliAnnotationItem;
        if (smaliAnnotationItem != null) {
            smaliAnnotationItem.setParent(this);
        }
    }

    @Override // com.reandroid.dex.smali.SmaliRegion
    public SmaliDirective getSmaliDirective() {
        return SmaliDirective.SUB_ANNOTATION;
    }

    @Override // com.reandroid.dex.smali.model.SmaliValue
    public DexValueType<?> getValueType() {
        return DexValueType.ANNOTATION;
    }

    @Override // com.reandroid.dex.smali.model.Smali, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.appendOptional(getValue());
    }

    @Override // com.reandroid.dex.smali.model.SmaliValue, com.reandroid.dex.smali.SmaliParser
    public void parse(SmaliReader smaliReader) throws IOException {
        SmaliAnnotationItem smaliAnnotationItem = new SmaliAnnotationItem(true);
        setValue(smaliAnnotationItem);
        smaliAnnotationItem.parse(smaliReader);
    }
}
