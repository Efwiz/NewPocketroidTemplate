package com.reandroid.dex.data;

import com.reandroid.arsc.base.Creator;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.dex.common.SectionTool;
import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.id.StringId;
import com.reandroid.dex.key.DataKey;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.MethodKey;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.reference.StringUle128Reference;
import com.reandroid.dex.smali.SmaliFormat;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.smali.model.SmaliAnnotationElement;
import com.reandroid.dex.smali.model.SmaliValue;
import com.reandroid.dex.value.DexValueBlock;
import com.reandroid.dex.value.DexValueType;
import com.reandroid.dex.value.NullValue;
import com.reandroid.utils.collection.CombiningIterator;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

/* JADX INFO: loaded from: classes11.dex */
public class AnnotationElement extends DataItem implements Comparable<AnnotationElement>, SmaliFormat {
    public static final Creator<AnnotationElement> CREATOR = new Creator<AnnotationElement>() { // from class: com.reandroid.dex.data.AnnotationElement.1
        @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
        public AnnotationElement[] newArrayInstance(int i) {
            if (i == 0) {
                return AnnotationElement.EMPTY;
            }
            return new AnnotationElement[i];
        }

        @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
        public AnnotationElement newInstance() {
            return new AnnotationElement();
        }
    };
    private static final AnnotationElement[] EMPTY = new AnnotationElement[0];
    private final StringUle128Reference elementName;
    private final DataKey<AnnotationElement> mKey;

    public AnnotationElement() {
        super(2);
        StringUle128Reference stringUle128Reference = new StringUle128Reference(StringId.USAGE_METHOD_NAME);
        this.elementName = stringUle128Reference;
        addChild(0, stringUle128Reference);
        this.mKey = new DataKey<>(this);
    }

    @Override // com.reandroid.dex.data.DataItem, com.reandroid.dex.common.SectionItem, com.reandroid.dex.key.KeyItem
    public DataKey<AnnotationElement> getKey() {
        return this.mKey;
    }

    public DexValueBlock<?> getValue() {
        return (DexValueBlock) getChildes()[1];
    }

    public <T1 extends DexValueBlock<?>> T1 getValue(DexValueType<T1> dexValueType) {
        T1 t1 = (T1) getValue();
        if (t1 == null || !t1.is(dexValueType)) {
            return null;
        }
        return t1;
    }

    public <T1 extends DexValueBlock<?>> T1 getOrCreateValue(DexValueType<T1> dexValueType) {
        NullValue nullValue = (T1) getValue();
        if (nullValue != null && nullValue != NullValue.PLACE_HOLDER && nullValue.getValueType() == dexValueType) {
            return nullValue;
        }
        T1 t1 = (T1) dexValueType.newInstance();
        setValue(t1);
        return t1;
    }

    public void setValue(DexValueBlock<?> dexValueBlock) {
        addChild(1, dexValueBlock);
    }

    public boolean is(DexValueType<?> dexValueType) {
        return getValueType() == dexValueType;
    }

    public boolean is(MethodKey methodKey) {
        return methodKey != null && methodKey.equalsIgnoreReturnType(getMethodKey());
    }

    public DexValueType<?> getValueType() {
        DexValueBlock<?> value = getValue();
        if (value != null) {
            return value.getValueType();
        }
        return null;
    }

    public String getName() {
        return this.elementName.getString();
    }

    public void setName(String str) {
        this.elementName.setString(str);
    }

    public StringId getNameId() {
        return (StringId) this.elementName.getItem();
    }

    @Override // com.reandroid.dex.common.SectionItemContainer, com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        this.elementName.onReadBytes(blockReader);
        DexValueBlock<?> dexValueBlockCreate = DexValueType.create(blockReader);
        setValue(dexValueBlockCreate);
        dexValueBlockCreate.onReadBytes(blockReader);
    }

    public void replaceKeys(Key key, Key key2) {
        getValue().replaceKeys(key, key2);
    }

    @Override // com.reandroid.dex.data.DataItem, com.reandroid.dex.common.IdUsageIterator
    public Iterator<IdItem> usedIds() {
        return CombiningIterator.singleOne(getNameId(), getValue().usedIds());
    }

    public void merge(AnnotationElement annotationElement) {
        if (annotationElement == this) {
            return;
        }
        setName(annotationElement.getName());
        DexValueBlock<?> value = annotationElement.getValue();
        getOrCreateValue(value.getValueType()).merge(value);
    }

    public void fromSmali(SmaliAnnotationElement smaliAnnotationElement) {
        setName(smaliAnnotationElement.getName());
        SmaliValue value = smaliAnnotationElement.getValue();
        getOrCreateValue(value.getValueType()).fromSmali(value);
    }

    @Override // com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.append((CharSequence) getName());
        smaliWriter.append(" = ");
        getValue().append(smaliWriter);
    }

    @Override // java.lang.Comparable
    public int compareTo(AnnotationElement annotationElement) {
        if (annotationElement == null) {
            return -1;
        }
        if (annotationElement == this) {
            return 0;
        }
        return SectionTool.compareIdx(getNameId(), annotationElement.getNameId());
    }

    public TypeKey getDataTypeKey() {
        DexValueBlock<?> value = getValue();
        if (value != null) {
            return value.getDataTypeKey();
        }
        return null;
    }

    public TypeKey getParentType() {
        AnnotationItem annotationItem = (AnnotationItem) getParentInstance(AnnotationItem.class);
        if (annotationItem != null) {
            return annotationItem.getTypeKey();
        }
        return null;
    }

    public MethodKey getMethodKey() {
        TypeKey dataTypeKey;
        TypeKey parentType = getParentType();
        if (parentType == null || (dataTypeKey = getDataTypeKey()) == null) {
            return null;
        }
        return new MethodKey(parentType, getName(), (String[]) null, dataTypeKey);
    }

    @Override // com.reandroid.dex.common.IdUsageIterator
    public Iterator<Key> usedKeys() {
        return CombiningIterator.singleOne(getMethodKey(), super.usedKeys());
    }

    public int hashCode() {
        String name2 = getName();
        int iHashCode = name2 != null ? name2.hashCode() + 31 : 31;
        DexValueBlock<?> value = getValue();
        int i = iHashCode * 31;
        return value != null ? i + value.hashCode() : i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AnnotationElement annotationElement = (AnnotationElement) obj;
        if (Objects.equals(getName(), annotationElement.getName())) {
            return Objects.equals(getValue(), annotationElement.getValue());
        }
        return false;
    }

    public String toString() {
        return getName() + " = " + getValue();
    }
}
