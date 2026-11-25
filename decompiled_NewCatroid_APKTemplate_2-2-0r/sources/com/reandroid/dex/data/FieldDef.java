package com.reandroid.dex.data;

import com.reandroid.dex.common.AccessFlag;
import com.reandroid.dex.common.Modifier;
import com.reandroid.dex.id.FieldId;
import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.key.FieldKey;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.smali.SmaliDirective;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.smali.model.SmaliField;
import com.reandroid.dex.smali.model.SmaliValue;
import com.reandroid.dex.value.DexValueBlock;
import com.reandroid.dex.value.DexValueType;
import com.reandroid.utils.collection.SingleIterator;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public class FieldDef extends Def<FieldId> {
    private DexValueBlock<?> staticInitialValue;

    public FieldDef() {
        super(0, SectionType.FIELD_ID);
    }

    @Override // com.reandroid.dex.data.Def, com.reandroid.dex.data.DefIndex
    public FieldKey getKey() {
        return (FieldKey) super.getKey();
    }

    public DexValueBlock<?> getStaticInitialValue() {
        return getStaticInitialValue(false);
    }

    public DexValueBlock<?> getStaticInitialValue(boolean z) {
        StaticFieldDefArray staticFieldDefArray;
        DexValueBlock<?> linkedStaticInitialValue = getLinkedStaticInitialValue();
        if (linkedStaticInitialValue == null || (staticFieldDefArray = (StaticFieldDefArray) getParentInstance(StaticFieldDefArray.class)) == null) {
            return linkedStaticInitialValue;
        }
        if (z) {
            staticFieldDefArray.linkUniqueStaticValues();
        } else {
            staticFieldDefArray.linkStaticValues();
        }
        return getLinkedStaticInitialValue();
    }

    DexValueBlock<?> getLinkedStaticInitialValue() {
        return this.staticInitialValue;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [T1 extends com.reandroid.dex.value.DexValueBlock<?>, com.reandroid.dex.value.DexValueBlock, com.reandroid.dex.value.DexValueBlock<?>] */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    public <T1 extends DexValueBlock<?>> T1 getOrCreateStaticValue(DexValueType<T1> dexValueType) {
        ?? r0 = this.staticInitialValue;
        if (r0 != 0 && r0.is((DexValueType<?>) dexValueType)) {
            return r0;
        }
        T1 t1 = (T1) getClassId().getOrCreateStaticValue(dexValueType, getIndex());
        holdStaticInitialValue(t1);
        return t1;
    }

    @Override // com.reandroid.dex.common.IdDefinition
    public Iterator<? extends Modifier> getAccessFlags() {
        return AccessFlag.valuesOfField(getAccessFlagsValue());
    }

    void holdStaticInitialValue(DexValueBlock<?> dexValueBlock) {
        this.staticInitialValue = dexValueBlock;
    }

    @Override // com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.newLine();
        getSmaliDirective().append(smaliWriter);
        smaliWriter.appendModifiers(getModifiers());
        getId().append(smaliWriter, false);
        DexValueBlock<?> staticInitialValue = getStaticInitialValue();
        if (staticInitialValue != null) {
            smaliWriter.append(" = ");
            staticInitialValue.append(smaliWriter);
        }
        Iterator<AnnotationSet> annotations = getAnnotations(true);
        if (annotations.hasNext()) {
            smaliWriter.indentPlus();
            smaliWriter.appendAllWithDoubleNewLine(annotations);
            smaliWriter.indentMinus();
            getSmaliDirective().appendEnd(smaliWriter);
        }
    }

    @Override // com.reandroid.dex.data.Def, com.reandroid.dex.common.IdUsageIterator
    public Iterator<IdItem> usedIds() {
        return SingleIterator.of(getId());
    }

    public void fromSmali(SmaliField smaliField) {
        setKey(smaliField.getKey());
        setAccessFlagsValue(smaliField.getAccessFlagsValue());
        if (smaliField.hasAnnotation()) {
            AnnotationSet annotationSet = (AnnotationSet) getOrCreateSection(SectionType.ANNOTATION_SET).createItem();
            annotationSet.fromSmali(smaliField.getAnnotation());
            addAnnotationSet(annotationSet);
        }
        SmaliValue value = smaliField.getValue();
        if (value != null) {
            getOrCreateStaticValue(value.getValueType()).fromSmali(value);
        }
    }

    @Override // com.reandroid.dex.smali.SmaliRegion
    public SmaliDirective getSmaliDirective() {
        return SmaliDirective.FIELD;
    }

    public String toString() {
        if (isReading()) {
            return getSmaliDirective() + " " + getKey();
        }
        if (getId() != null) {
            return SmaliWriter.toStringSafe(this);
        }
        return getSmaliDirective() + " " + Modifier.toString(getAccessFlags()) + " " + getRelativeIdValue();
    }
}
