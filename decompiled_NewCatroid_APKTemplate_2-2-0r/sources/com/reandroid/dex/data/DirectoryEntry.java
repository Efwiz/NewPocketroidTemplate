package com.reandroid.dex.data;

import com.reandroid.arsc.base.BlockRefresh;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.dex.base.DexBlockItem;
import com.reandroid.dex.base.UsageMarker;
import com.reandroid.dex.data.DataItem;
import com.reandroid.dex.data.DefIndex;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.sections.SectionType;
import java.io.IOException;
import java.util.Objects;

/* JADX INFO: loaded from: classes11.dex */
public class DirectoryEntry<DEFINITION extends DefIndex, VALUE extends DataItem> extends DexBlockItem implements BlockRefresh, Comparable<DirectoryEntry<?, ?>> {
    public static final int SIZE = 8;
    private DEFINITION mDefinition;
    private Key mDefinitionKey;
    private VALUE mValue;
    private final SectionType<VALUE> sectionType;

    public DirectoryEntry(SectionType<VALUE> sectionType) {
        super(8);
        this.sectionType = sectionType;
    }

    public int getDefinitionIndexValue() {
        return getInteger(getBytesInternal(), 0);
    }

    public void setDefinitionIndexValue(int i) {
        if (i == getDefinitionIndexValue()) {
            return;
        }
        putInteger(getBytesInternal(), 0, i);
    }

    public int getValueOffset() {
        return getInteger(getBytesInternal(), 4);
    }

    public void setValueOffset(int i) {
        putInteger(getBytesInternal(), 4, i);
    }

    public DEFINITION getDefinition() {
        return this.mDefinition;
    }

    public void link(DEFINITION definition) {
        if (definition == null || this.mDefinition != null) {
            return;
        }
        Key definitionKey = getDefinitionKey();
        if (definitionKey != null) {
            if (definitionKey.equals(definition.getKey())) {
                this.mDefinition = definition;
                setDefinitionIndexValue(definition.getDefinitionIndex());
                return;
            }
            return;
        }
        if (getDefinitionIndexValue() == definition.getDefinitionIndex()) {
            this.mDefinition = definition;
        }
    }

    public void setDefinition(DEFINITION definition) {
        this.mDefinition = definition;
        setDefinitionKey(definition != null ? definition.getKey() : null);
    }

    public Key getDefinitionKey() {
        DefIndex definition = getDefinition();
        if (definition != null) {
            this.mDefinitionKey = definition.getKey();
        }
        return this.mDefinitionKey;
    }

    public void setDefinitionKey(Key key) {
        this.mDefinitionKey = key;
    }

    public VALUE getValue() {
        return this.mValue;
    }

    public void setValue(Key key) {
        setValue((DataItem) getOrCreateSection(this.sectionType).getPool().getOrCreate(key));
    }

    public void setValue(VALUE value) {
        this.mValue = value;
        setValueOffset(value != null ? value.getOffset() : 0);
    }

    public void set(DEFINITION definition, VALUE value) {
        setDefinition(definition);
        setValue(value);
    }

    public Key getValueKey() {
        DataItem value = getValue();
        if (value != null) {
            return value.getKey();
        }
        return null;
    }

    @Override // com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        super.onReadBytes(blockReader);
        cacheItem();
    }

    private void cacheItem() {
        VALUE value = (VALUE) getSectionItem(this.sectionType, getValueOffset());
        this.mValue = value;
        if (value != null) {
            value.addUsageType(UsageMarker.USAGE_ANNOTATION);
        }
    }

    @Override // com.reandroid.arsc.base.BlockRefresh
    public void refresh() {
        DefIndex definition = getDefinition();
        if (definition != null) {
            setDefinitionIndexValue(definition.getDefinitionIndex());
        }
        DataItem dataItemRefreshValue = refreshValue();
        setValueOffset(dataItemRefreshValue != null ? dataItemRefreshValue.getOffset() : 0);
    }

    private VALUE refreshValue() {
        DataItem value = getValue();
        if (value == null) {
            return null;
        }
        VALUE value2 = (VALUE) value.getReplace();
        this.mValue = value2;
        if (value2 != null) {
            value2.addUsageType(UsageMarker.USAGE_ANNOTATION);
        }
        return value2;
    }

    public boolean equalsDefIndex(int i) {
        return getDefinitionIndexValue() == i;
    }

    public boolean equalsDefIndex(DefIndex defIndex) {
        DefIndex definition;
        return (defIndex == null || (definition = getDefinition()) == null || definition != defIndex) ? false : true;
    }

    public boolean equalsValue(VALUE value) {
        return Objects.equals(getValue(), value);
    }

    public boolean matchesDefinition(Key key) {
        return Objects.equals(getDefinitionKey(), key);
    }

    public boolean matchesValue(Key key) {
        return Objects.equals(getValueKey(), key);
    }

    @Override // java.lang.Comparable
    public int compareTo(DirectoryEntry<?, ?> directoryEntry) {
        return compareDefIndex(getDefinition(), directoryEntry.getDefinition());
    }

    public void merge(DirectoryEntry<DEFINITION, VALUE> directoryEntry) {
        if (directoryEntry == this) {
            return;
        }
        setDefinitionKey(directoryEntry.getDefinitionKey());
        setValue(directoryEntry.getValueKey());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DirectoryEntry directoryEntry = (DirectoryEntry) obj;
        return Objects.equals(getDefinitionKey(), directoryEntry.getDefinitionKey()) && Objects.equals(getValue(), directoryEntry.getValue());
    }

    public int hashCode() {
        Key definitionKey = getDefinitionKey();
        int iHashCode = definitionKey != null ? definitionKey.hashCode() + 31 : 31;
        DataItem value = getValue();
        int i = iHashCode * 31;
        return value != null ? i + value.hashCode() : i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        DefIndex definition = getDefinition();
        if (definition != null) {
            sb.append(definition.getKey());
        } else {
            sb.append(getDefinitionIndexValue());
        }
        sb.append(" (");
        DataItem value = getValue();
        if (value != null) {
            sb.append(value);
        } else {
            sb.append(getValueOffset());
        }
        sb.append(')');
        return sb.toString();
    }

    public static int compareDefIndex(DefIndex defIndex, DefIndex defIndex2) {
        if (defIndex == defIndex2) {
            return 0;
        }
        if (defIndex == null) {
            return 1;
        }
        if (defIndex2 == null) {
            return -1;
        }
        return Integer.compare(defIndex.getDefinitionIndex(), defIndex2.getDefinitionIndex());
    }
}
