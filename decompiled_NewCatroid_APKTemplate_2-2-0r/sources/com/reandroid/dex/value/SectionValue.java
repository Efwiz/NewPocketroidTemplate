package com.reandroid.dex.value;

import com.reandroid.arsc.io.BlockReader;
import com.reandroid.dex.base.UsageMarker;
import com.reandroid.dex.common.SectionItem;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.KeyItem;
import com.reandroid.dex.reference.DexReference;
import com.reandroid.dex.sections.Section;
import com.reandroid.dex.sections.SectionList;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.smali.SmaliFormat;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.smali.model.SmaliValue;
import com.reandroid.dex.smali.model.SmaliValueKey;
import com.reandroid.utils.HexUtil;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.util.Objects;

/* JADX INFO: loaded from: classes11.dex */
public abstract class SectionValue<T extends SectionItem> extends DexValueBlock<NumberValue> implements SmaliFormat, KeyItem, DexReference<T> {
    private T mData;
    private final SectionType<T> sectionType;

    public abstract Key getKey();

    abstract T getReplacement(T t);

    abstract int getSectionValue(T t);

    @Override // com.reandroid.dex.value.DexValueBlock
    public abstract DexValueType<?> getValueType();

    abstract void updateUsageType(T t);

    public SectionValue(SectionType<T> sectionType, DexValueType<?> dexValueType) {
        super(new NumberValue(), dexValueType);
        this.sectionType = sectionType;
    }

    @Override // com.reandroid.dex.reference.DexReference
    public T getItem() {
        return this.mData;
    }

    @Override // com.reandroid.dex.reference.DexReference
    public void setItem(T t) {
        if (t == this.mData) {
            return;
        }
        this.mData = t;
        set(getSectionValue(t));
        updateUsageType(t);
    }

    @Override // com.reandroid.dex.reference.DexReference
    public void setItem(Key key) {
        setItem(getOrCreateSection().getOrCreate(key));
    }

    @Override // com.reandroid.dex.reference.DexReference
    public SectionType<T> getSectionType() {
        return this.sectionType;
    }

    @Override // com.reandroid.arsc.item.IntegerReference
    public int get() {
        return (int) getValueContainer().getUnsignedNumber();
    }

    @Override // com.reandroid.arsc.item.IntegerReference
    public void set(int i) {
        NumberValue valueContainer = getValueContainer();
        valueContainer.setUnsignedNumber(((long) i) & 4294967295L);
        setValueSize(valueContainer.getSize() - 1);
    }

    Section<T> getSection() {
        SectionList sectionList = (SectionList) getParentInstance(SectionList.class);
        if (sectionList != null) {
            return sectionList.getSection(getSectionType());
        }
        return null;
    }

    private Section<T> getOrCreateSection() {
        SectionList sectionList = (SectionList) getParentInstance(SectionList.class);
        if (sectionList != null) {
            return sectionList.getOrCreateSection(getSectionType());
        }
        throw new NullPointerException("Null parent SectionList");
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public void merge(DexValueBlock<?> dexValueBlock) {
        super.merge(dexValueBlock);
        setItem(((SectionValue) dexValueBlock).getKey());
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public void fromSmali(SmaliValue smaliValue) {
        setItem(((SmaliValueKey) smaliValue).getValue());
    }

    @Override // com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        getValueTypeItem().onReadBytes(blockReader);
        NumberValue valueContainer = getValueContainer();
        valueContainer.setSize(getValueSize() + 1);
        valueContainer.readBytes(blockReader);
        pullItem();
    }

    @Override // com.reandroid.arsc.base.BlockContainer
    protected void onPreRefresh() {
        refreshItem();
    }

    private void refreshItem() {
        T t = (T) getReplacement(this.mData);
        this.mData = t;
        set(getSectionValue(t));
        updateUsageType(t);
    }

    @Override // com.reandroid.dex.reference.DexReference
    public void pullItem() {
        Section<T> section = getSection();
        if (section != null) {
            T t = (T) section.getSectionItem(get());
            this.mData = t;
            updateUsageType(t);
        }
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public String getAsString() {
        Key key = getKey();
        if (key != null) {
            return key.toString();
        }
        return null;
    }

    @Override // com.reandroid.dex.value.DexValueBlock, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        UsageMarker item = getItem();
        if (item == null) {
            smaliWriter.append("value error: ");
            smaliWriter.append((CharSequence) getSectionType().getName());
            smaliWriter.append(StringUtil.SPACE);
            smaliWriter.append((CharSequence) HexUtil.toHex(get(), getValueSize()));
            return;
        }
        ((SmaliFormat) item).append(smaliWriter);
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public int hashCode() {
        Key key = getKey();
        if (key != null) {
            return key.hashCode();
        }
        return 0;
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(getKey(), ((SectionValue) obj).getKey());
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public String toString() {
        return SmaliWriter.toStringSafe(this);
    }
}
