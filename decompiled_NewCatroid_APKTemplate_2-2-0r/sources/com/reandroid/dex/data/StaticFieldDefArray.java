package com.reandroid.dex.data;

import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.dex.base.DexException;
import com.reandroid.dex.id.ClassId;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.smali.model.SmaliField;
import com.reandroid.dex.value.BooleanValue;
import com.reandroid.dex.value.ByteValue;
import com.reandroid.dex.value.CharValue;
import com.reandroid.dex.value.DexValueBlock;
import com.reandroid.dex.value.DoubleValue;
import com.reandroid.dex.value.FloatValue;
import com.reandroid.dex.value.IntValue;
import com.reandroid.dex.value.LongValue;
import com.reandroid.dex.value.NullValue;
import com.reandroid.dex.value.ShortValue;
import com.reandroid.utils.NumbersUtil;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public class StaticFieldDefArray extends FieldDefArray {
    private EncodedArray mLinkedArray;
    private boolean mValuesLinked;

    public StaticFieldDefArray(IntegerReference integerReference) {
        super(integerReference);
    }

    @Override // com.reandroid.dex.data.DefArray
    public void onPreRemove(FieldDef fieldDef) {
        linkUniqueStaticValues();
        DexValueBlock<?> linkedStaticInitialValue = fieldDef.getLinkedStaticInitialValue();
        EncodedArray staticValues = getStaticValues();
        if (linkedStaticInitialValue != null && staticValues != null) {
            staticValues.remove(linkedStaticInitialValue);
        }
        super.onPreRemove(fieldDef);
    }

    @Override // com.reandroid.dex.data.DefArray
    void onPreSort() {
        super.onPreSort();
        linkUniqueStaticValues();
    }

    @Override // com.reandroid.dex.data.DefArray
    void onPostSort() {
        super.onPostSort();
        sortStaticValues();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void sortStaticValues() {
        linkUniqueStaticValues();
        EncodedArray staticValues = getStaticValues();
        if (staticValues == null) {
            return;
        }
        staticValues.removeAll();
        int count = getCount();
        for (int i = 0; i < count; i++) {
            DexValueBlock<?> linkedStaticInitialValue = ((FieldDef) get(i)).getLinkedStaticInitialValue();
            if (linkedStaticInitialValue != null) {
                ensureArraySize(staticValues, i + 1);
                staticValues.set(i, linkedStaticInitialValue);
            }
        }
        staticValues.trimNull();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void ensureArraySize(EncodedArray encodedArray, int i) {
        int size = encodedArray.size();
        if (i <= size) {
            return;
        }
        while (size < i) {
            encodedArray.add(createFor(((FieldDef) get(size)).getKey().getType()));
            size++;
        }
    }

    void linkUniqueStaticValues() {
        ClassId classId = getClassId();
        if (classId != null) {
            classId.getUniqueStaticValues();
        }
        linkStaticValues();
    }

    /* JADX WARN: Multi-variable type inference failed */
    void linkStaticValues() {
        initStaticValues();
        EncodedArray staticValues = getStaticValues();
        EncodedArray encodedArray = this.mLinkedArray;
        if (staticValues == encodedArray) {
            return;
        }
        this.mLinkedArray = encodedArray;
        replacePlaceHolder(encodedArray);
        int i = 0;
        if (staticValues != null && encodedArray != null) {
            int count = getCount();
            while (i < count) {
                FieldDef fieldDef = (FieldDef) get(i);
                DexValueBlock<?> linkedStaticInitialValue = fieldDef.getLinkedStaticInitialValue();
                fieldDef.holdStaticInitialValue(linkedStaticInitialValue != null ? staticValues.get(linkedStaticInitialValue.getIndex()) : null);
                i++;
            }
            return;
        }
        if (staticValues != null) {
            int count2 = getCount();
            while (i < count2) {
                ((FieldDef) get(i)).holdStaticInitialValue(staticValues.get(i));
                i++;
            }
            return;
        }
        int count3 = getCount();
        while (i < count3) {
            ((FieldDef) get(i)).holdStaticInitialValue(null);
            i++;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void replacePlaceHolder(EncodedArray encodedArray) {
        if (encodedArray == null) {
            return;
        }
        int iMin = NumbersUtil.min(encodedArray.size(), size());
        for (int i = 0; i < iMin; i++) {
            if (encodedArray.get(i) == NullValue.PLACE_HOLDER) {
                encodedArray.set(i, createFor(((FieldDef) get(i)).getKey().getType()));
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void initStaticValues() {
        if (this.mValuesLinked) {
            return;
        }
        EncodedArray staticValues = getStaticValues();
        replacePlaceHolder(staticValues);
        this.mLinkedArray = staticValues;
        if (staticValues != null) {
            int count = getCount();
            for (int i = 0; i < count; i++) {
                ((FieldDef) get(i)).holdStaticInitialValue(staticValues.get(i));
            }
            this.mValuesLinked = staticValues.size() != 0;
        }
    }

    private EncodedArray getStaticValues() {
        ClassId classId = getClassId();
        if (classId != null) {
            return classId.getStaticValues();
        }
        return null;
    }

    @Override // com.reandroid.dex.data.DefArray
    public void setClassId(ClassId classId) {
        if (getClassId() != classId) {
            this.mValuesLinked = false;
        }
        super.setClassId(classId);
        linkStaticValues();
    }

    private void validateValues() {
        EncodedArray staticValues = getStaticValues();
        if (staticValues == null) {
            return;
        }
        staticValues.refresh();
        Iterator<T> it = iterator();
        Iterator<DexValueBlock<?>> it2 = staticValues.iterator();
        while (it.hasNext() && it2.hasNext()) {
            FieldDef fieldDef = (FieldDef) it.next();
            DexValueBlock<?> next = it2.next();
            TypeKey type = fieldDef.getKey().getType();
            if (next != fieldDef.getLinkedStaticInitialValue()) {
                throw new DexException("Different value: " + fieldDef);
            }
            TypeKey dataTypeKey = next.getDataTypeKey();
            boolean zIsPrimitive = type.isPrimitive();
            if (zIsPrimitive != dataTypeKey.isPrimitive() || (zIsPrimitive && !type.equals(dataTypeKey))) {
                throw new DexException("Mismatch on initial value type: " + type + " vs " + dataTypeKey + ",\n " + fieldDef.getKey() + ",\n " + fieldDef);
            }
        }
        if (it2.hasNext()) {
            throw new DexException("Too many values than fields: " + size() + ", values = " + staticValues.size() + ", at " + ((FieldDef) iterator().next()).getKey());
        }
    }

    @Override // com.reandroid.dex.data.DefArray
    public void merge(DefArray<?> defArray) {
        super.merge(defArray);
        linkStaticValues();
    }

    @Override // com.reandroid.dex.data.DefArray, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.setStateWritingFields(true);
        super.append(smaliWriter);
        smaliWriter.setStateWritingFields(false);
    }

    @Override // com.reandroid.dex.data.FieldDefArray
    public void fromSmali(Iterator<SmaliField> it) {
        super.fromSmali(it);
        linkStaticValues();
        validateValues();
    }

    @Override // com.reandroid.dex.data.FieldDefArray
    public void fromSmali(SmaliField smaliField) {
        super.fromSmali(smaliField);
        linkStaticValues();
        validateValues();
    }

    private static DexValueBlock<?> createFor(TypeKey typeKey) {
        DexValueBlock<?> booleanValue;
        if (!typeKey.isPrimitive()) {
            booleanValue = NullValue.PLACE_HOLDER;
        } else if (TypeKey.TYPE_I.equals(typeKey)) {
            booleanValue = new IntValue();
        } else if (TypeKey.TYPE_J.equals(typeKey)) {
            booleanValue = new LongValue();
        } else if (TypeKey.TYPE_D.equals(typeKey)) {
            booleanValue = new DoubleValue();
        } else if (TypeKey.TYPE_F.equals(typeKey)) {
            booleanValue = new FloatValue();
        } else if (TypeKey.TYPE_S.equals(typeKey)) {
            booleanValue = new ShortValue();
        } else if (TypeKey.TYPE_B.equals(typeKey)) {
            booleanValue = new ByteValue();
        } else if (TypeKey.TYPE_C.equals(typeKey)) {
            booleanValue = new CharValue();
        } else if (TypeKey.TYPE_Z.equals(typeKey)) {
            booleanValue = new BooleanValue();
        } else {
            throw new IllegalArgumentException("Undefined: " + typeKey);
        }
        booleanValue.setTemporary(true);
        return booleanValue;
    }
}
