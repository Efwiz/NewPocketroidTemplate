package com.reandroid.dex.model;

import com.reandroid.dex.data.AnnotationElement;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.value.DexValueType;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class DexAnnotationElement extends Dex {
    private final AnnotationElement annotationElement;
    private final DexAnnotation dexAnnotation;

    public DexAnnotationElement(DexAnnotation dexAnnotation, AnnotationElement annotationElement) {
        this.dexAnnotation = dexAnnotation;
        this.annotationElement = annotationElement;
    }

    public String getName() {
        return getAnnotationElement().getName();
    }

    public void setName(String str) {
        getAnnotationElement().setName(str);
    }

    public DexValue getValue() {
        return DexValue.create(this, getAnnotationElement().getValue());
    }

    public DexValue getOrCreateValue(DexValueType<?> dexValueType) {
        return DexValue.create(this, getAnnotationElement().getOrCreateValue(dexValueType));
    }

    @Override // com.reandroid.dex.model.Dex
    public void removeSelf() {
        getAnnotationElement().removeSelf();
    }

    public AnnotationElement getAnnotationElement() {
        return this.annotationElement;
    }

    public DexAnnotation getDexAnnotation() {
        return this.dexAnnotation;
    }

    @Override // com.reandroid.dex.model.Dex
    public boolean uses(Key key) {
        return getAnnotationElement().uses(key);
    }

    @Override // com.reandroid.dex.model.Dex
    public DexClassRepository getClassRepository() {
        return getDexAnnotation().getClassRepository();
    }

    @Override // com.reandroid.dex.model.Dex, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        getAnnotationElement().append(smaliWriter);
    }

    public static DexAnnotationElement create(DexAnnotation dexAnnotation, AnnotationElement annotationElement) {
        if (dexAnnotation == null || annotationElement == null) {
            return null;
        }
        return new DexAnnotationElement(dexAnnotation, annotationElement);
    }
}
