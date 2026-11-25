package com.reandroid.dex.value;

import com.reandroid.dex.data.AnnotationItem;
import com.reandroid.dex.key.DataKey;
import com.reandroid.dex.smali.model.SmaliValue;
import com.reandroid.dex.smali.model.SmaliValueAnnotation;

/* JADX INFO: loaded from: classes11.dex */
public class AnnotationValue extends DexValueBlock<AnnotationItem> {
    public AnnotationValue() {
        super(new AnnotationItem(true), DexValueType.ANNOTATION);
    }

    public AnnotationItem get() {
        return getValueContainer();
    }

    public DataKey<AnnotationItem> getKey() {
        return get().getKey();
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public DexValueType<?> getValueType() {
        return DexValueType.ANNOTATION;
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public String getAsString() {
        return get().toString();
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public void merge(DexValueBlock<?> dexValueBlock) {
        super.merge(dexValueBlock);
        AnnotationItem annotationItem = ((AnnotationValue) dexValueBlock).get();
        AnnotationItem annotationItem2 = get();
        annotationItem2.setType(annotationItem.getTypeKey());
        annotationItem2.merge(annotationItem);
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public void fromSmali(SmaliValue smaliValue) {
        get().fromSmali(((SmaliValueAnnotation) smaliValue).getValue());
    }
}
