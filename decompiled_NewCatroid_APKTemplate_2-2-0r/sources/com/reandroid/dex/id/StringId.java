package com.reandroid.dex.id;

import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.dex.base.UsageMarker;
import com.reandroid.dex.common.SectionTool;
import com.reandroid.dex.data.StringData;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.StringKey;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.utils.collection.EmptyIterator;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public class StringId extends IdItem implements IntegerReference, Comparable<StringId> {
    private StringData stringData;

    @Override // com.reandroid.dex.id.IdItem
    void cacheItems() {
    }

    public StringId() {
        super(4);
    }

    @Override // com.reandroid.dex.id.IdItem, com.reandroid.dex.common.IdUsageIterator
    public Iterator<IdItem> usedIds() {
        return EmptyIterator.of();
    }

    @Override // com.reandroid.dex.id.IdItem, com.reandroid.dex.common.SectionItem
    public SectionType<StringId> getSectionType() {
        return SectionType.STRING_ID;
    }

    @Override // com.reandroid.dex.id.IdItem, com.reandroid.dex.common.SectionItem, com.reandroid.dex.key.KeyItem
    public StringKey getKey() {
        StringData stringData = getStringData();
        if (stringData != null) {
            return stringData.getKey();
        }
        return null;
    }

    @Override // com.reandroid.dex.id.IdItem, com.reandroid.dex.key.ModifiableKeyItem
    public void setKey(Key key) {
        StringKey key2 = getKey();
        ensureStringData().setKey(key);
        if (key2 == null || key2.equals(getKey())) {
            return;
        }
        keyChanged(key2);
    }

    @Override // com.reandroid.dex.id.IdItem, com.reandroid.dex.common.SectionItem
    public void removeSelf() {
        StringData stringData = this.stringData;
        if (stringData != null) {
            int usageType = stringData.getUsageType() - 1;
            stringData.clearUsageType();
            stringData.addUsageType(usageType);
            if (usageType == 0) {
                stringData.removeSelf(this);
            }
        }
        super.removeSelf();
        this.stringData = null;
    }

    public StringData getStringData() {
        return this.stringData;
    }

    public void linkStringData(StringData stringData) {
        StringData stringData2 = this.stringData;
        if (stringData2 == stringData) {
            return;
        }
        if (stringData2 != null) {
            throw new IllegalArgumentException("String data already linked");
        }
        this.stringData = stringData;
        stringData.addUsageType(stringData.getUsageType() + 1);
    }

    private StringData ensureStringData() {
        StringData stringData = getStringData();
        if (stringData != null) {
            return stringData;
        }
        StringData stringData2 = (StringData) getOrCreateSection(SectionType.STRING_DATA).createItem();
        linkStringData(stringData2);
        return stringData2;
    }

    @Override // com.reandroid.arsc.item.IntegerReference
    public void set(int i) {
        putInteger(getBytesInternal(), 0, i);
    }

    @Override // com.reandroid.arsc.item.IntegerReference
    public int get() {
        return getInteger(getBytesInternal(), 0);
    }

    @Override // com.reandroid.arsc.base.BlockRefresh
    public void refresh() {
        StringData stringData = this.stringData;
        set(stringData.getOffset());
        int usageType = stringData.getUsageType();
        stringData.clearUsageType();
        stringData.addUsageType(usageType + 1);
    }

    public String getQuotedString() {
        StringData stringData = getStringData();
        if (stringData != null) {
            return stringData.getQuotedString();
        }
        return null;
    }

    public String getString() {
        StringData stringData = getStringData();
        if (stringData != null) {
            return stringData.getString();
        }
        return null;
    }

    public void setString(String str) {
        StringKey key = getKey();
        ensureStringData().setString(str);
        if (key == null || key.equals(getKey())) {
            return;
        }
        keyChanged(key);
    }

    @Override // com.reandroid.dex.common.SectionItem
    protected void keyChanged(Key key) {
        super.keyChanged(key);
        StringKey key2 = getKey();
        if (key2 != null) {
            key2.setSignature(containsUsage(UsageMarker.USAGE_SIGNATURE_TYPE));
        }
    }

    @Override // com.reandroid.dex.common.SectionItem, com.reandroid.dex.base.UsageMarker
    public void addUsageType(int i) {
        super.addUsageType(i);
        if (i == UsageMarker.USAGE_SIGNATURE_TYPE) {
            getKey().setSignature(true);
        }
    }

    @Override // com.reandroid.dex.common.SectionItem, com.reandroid.dex.base.UsageMarker
    public void clearUsageType() {
        super.clearUsageType();
        StringKey key = getKey();
        if (key != null) {
            key.setSignature(false);
        }
    }

    @Override // com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        StringData stringData = getStringData();
        if (stringData != null) {
            stringData.append(smaliWriter);
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(StringId stringId) {
        if (stringId == null) {
            return -1;
        }
        if (stringId == this) {
            return 0;
        }
        return SectionTool.compareIdx(getStringData(), stringId.getStringData());
    }

    public String toString() {
        StringData stringData = this.stringData;
        if (stringData != null) {
            return stringData.toString();
        }
        return Integer.toString(get());
    }

    public static boolean equals(StringId stringId, StringId stringId2) {
        if (stringId == stringId2) {
            return true;
        }
        if (stringId == null) {
            return false;
        }
        return StringData.equals(stringId.getStringData(), stringId2.getStringData());
    }
}
