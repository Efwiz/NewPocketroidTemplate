package com.reandroid.dex.id;

import com.reandroid.dex.base.UsageMarker;
import com.reandroid.dex.data.EncodedArray;
import com.reandroid.dex.key.IdKey;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.MethodHandleKey;
import com.reandroid.dex.key.MethodKey;
import com.reandroid.dex.key.StringKey;
import com.reandroid.dex.reference.DataItemIndirectReference;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.value.DexValueBlock;
import com.reandroid.dex.value.SectionValue;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.collection.EmptyIterator;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

/* JADX INFO: loaded from: classes11.dex */
public class CallSiteId extends IdItem implements Comparable<CallSiteId> {
    private final DataItemIndirectReference<EncodedArray> encodedArrayReference;
    private final IdKey<CallSiteId> mKey;

    @Override // com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
    }

    public CallSiteId() {
        super(4);
        this.encodedArrayReference = new DataItemIndirectReference<>(SectionType.ENCODED_ARRAY, this, 0, UsageMarker.USAGE_CALL_SITE);
        this.mKey = new IdKey<>(this);
    }

    @Override // com.reandroid.dex.id.IdItem, com.reandroid.dex.common.SectionItem, com.reandroid.dex.key.KeyItem
    public IdKey<CallSiteId> getKey() {
        return this.mKey;
    }

    @Override // com.reandroid.dex.id.IdItem, com.reandroid.dex.key.ModifiableKeyItem
    public void setKey(Key key) {
        merge((CallSiteId) ((IdKey) key).getItem());
    }

    public MethodHandle getMethodHandle() {
        return (MethodHandle) getValue(SectionType.METHOD_HANDLE, 0);
    }

    public void setMethodHandle(MethodHandleKey methodHandleKey) {
        getOrCreateValue(SectionType.METHOD_HANDLE, 0, methodHandleKey);
    }

    public String getMethodName() {
        StringId methodNameId = getMethodNameId();
        if (methodNameId != null) {
            return methodNameId.getString();
        }
        return null;
    }

    public void setMethodName(String str) {
        setMethodName(StringKey.create(str));
    }

    public void setMethodName(StringKey stringKey) {
        getOrCreateValue(SectionType.STRING_ID, 1, stringKey);
    }

    public StringId getMethodNameId() {
        return (StringId) getValue(SectionType.STRING_ID, 1);
    }

    public MethodId getMethodId() {
        return (MethodId) getValue(SectionType.METHOD_ID, 2);
    }

    public void setMethodId(MethodKey methodKey) {
        getOrCreateValue(SectionType.METHOD_ID, 2, methodKey);
    }

    private <T1 extends IdItem> T1 getOrCreateValue(SectionType<T1> sectionType, int i, Key key) {
        SectionValue<T1> orCreate = getOrCreateEncodedArray().getOrCreate(sectionType, i);
        orCreate.setItem(key);
        return (T1) orCreate.getItem();
    }

    private <T1 extends IdItem> T1 getValue(SectionType<T1> sectionType, int i) {
        EncodedArray encodedArray = getEncodedArray();
        if (encodedArray == null) {
            return null;
        }
        DexValueBlock<?> dexValueBlock = encodedArray.get(i);
        if (!(dexValueBlock instanceof SectionValue)) {
            return null;
        }
        SectionValue sectionValue = (SectionValue) dexValueBlock;
        if (sectionValue.getSectionType() != sectionType) {
            return null;
        }
        return (T1) sectionValue.getItem();
    }

    public EncodedArray getOrCreateEncodedArray() {
        return (EncodedArray) this.encodedArrayReference.getOrCreate();
    }

    public EncodedArray getEncodedArray() {
        return (EncodedArray) this.encodedArrayReference.getItem();
    }

    @Override // com.reandroid.dex.id.IdItem
    void cacheItems() {
        this.encodedArrayReference.pullItem();
    }

    @Override // com.reandroid.arsc.base.BlockRefresh
    public void refresh() {
        this.encodedArrayReference.refresh();
    }

    @Override // com.reandroid.dex.id.IdItem, com.reandroid.dex.common.IdUsageIterator
    public Iterator<IdItem> usedIds() {
        EncodedArray encodedArray = getEncodedArray();
        if (encodedArray == null) {
            return EmptyIterator.of();
        }
        return encodedArray.usedIds();
    }

    @Override // com.reandroid.dex.id.IdItem, com.reandroid.dex.common.SectionItem
    public SectionType<CallSiteId> getSectionType() {
        return SectionType.CALL_SITE_ID;
    }

    public void merge(CallSiteId callSiteId) {
        if (callSiteId == this) {
            return;
        }
        getOrCreateEncodedArray().merge(callSiteId.getEncodedArray());
    }

    @Override // java.lang.Comparable
    public int compareTo(CallSiteId callSiteId) {
        if (callSiteId == this) {
            return 0;
        }
        if (callSiteId == null) {
            return -1;
        }
        int iCompare = CompareUtil.compare(getMethodHandle(), callSiteId.getMethodHandle());
        if (iCompare != 0) {
            return iCompare;
        }
        int iCompare2 = CompareUtil.compare(getMethodNameId(), callSiteId.getMethodNameId());
        return iCompare2 != 0 ? iCompare2 : CompareUtil.compare(getMethodId(), callSiteId.getMethodId());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(getEncodedArray(), ((CallSiteId) obj).getEncodedArray());
    }

    public int hashCode() {
        EncodedArray encodedArray = getEncodedArray();
        if (encodedArray != null) {
            return encodedArray.hashCode();
        }
        return 0;
    }
}
