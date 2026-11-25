package com.reandroid.dex.value;

import com.reandroid.dex.base.UsageMarker;
import com.reandroid.dex.data.AnnotationItem;
import com.reandroid.dex.id.StringId;
import com.reandroid.dex.key.StringKey;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.sections.SectionType;

/* JADX INFO: loaded from: classes11.dex */
public class StringValue extends SectionIdValue<StringId> {
    public StringValue() {
        super(SectionType.STRING_ID, DexValueType.STRING);
    }

    @Override // com.reandroid.dex.value.SectionIdValue, com.reandroid.dex.value.SectionValue, com.reandroid.dex.key.KeyItem
    public StringKey getKey() {
        return (StringKey) super.getKey();
    }

    public String getString() {
        StringKey key = getKey();
        if (key != null) {
            return key.getString();
        }
        return null;
    }

    public void setString(String str) {
        setKey(StringKey.create(str));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.reandroid.dex.value.SectionIdValue
    public void updateUsageType(StringId stringId) {
        AnnotationItem annotationItem;
        super.updateUsageType(stringId);
        if (stringId == null || !stringId.containsUsage(UsageMarker.USAGE_ANNOTATION) || stringId.containsUsage(UsageMarker.USAGE_SIGNATURE_TYPE) || (annotationItem = (AnnotationItem) getParentInstance(AnnotationItem.class)) == null || !TypeKey.DALVIK_Signature.equals(annotationItem.getTypeKey())) {
            return;
        }
        stringId.addUsageType(UsageMarker.USAGE_SIGNATURE_TYPE);
    }

    @Override // com.reandroid.dex.value.SectionValue, com.reandroid.dex.value.DexValueBlock
    public DexValueType<?> getValueType() {
        return DexValueType.STRING;
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public TypeKey getDataTypeKey() {
        return TypeKey.STRING;
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public String getData() {
        return getString();
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public void setData(Object obj) {
        setString((String) obj);
    }
}
