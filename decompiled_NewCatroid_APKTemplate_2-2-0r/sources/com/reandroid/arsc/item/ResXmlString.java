package com.reandroid.arsc.item;

import com.reandroid.arsc.chunk.xml.ResXmlDocument;
import com.reandroid.utils.CompareUtil;

/* JADX INFO: loaded from: classes10.dex */
public class ResXmlString extends StringItem {
    private ResXmlID mResXmlID;

    @Override // com.reandroid.arsc.item.StringItem
    void ensureStringLinkUnlocked() {
    }

    public ResXmlString(boolean z) {
        super(z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ResXmlID getOrCreateResXmlID() {
        ResXmlID resXmlID = getResXmlID();
        if (resXmlID != null) {
            return resXmlID;
        }
        linkResourceIdInternal((ResXmlID) ((ResXmlDocument) getParentInstance(ResXmlDocument.class)).getResXmlIDMap().getResXmlIDArray().createNext());
        return getResXmlID();
    }

    public ResXmlID getResXmlID() {
        return this.mResXmlID;
    }

    public boolean hasResourceId() {
        return getResourceId() != 0;
    }

    public int getResourceId() {
        ResXmlID resXmlID = getResXmlID();
        if (resXmlID == null) {
            return 0;
        }
        return resXmlID.get();
    }

    public void setResourceId(int i) {
        if (i == 0) {
            unLinkResourceIdInternal();
        } else {
            getOrCreateResXmlID().set(i);
        }
    }

    public void unLinkResourceIdInternal() {
        ResXmlID resXmlID = this.mResXmlID;
        if (resXmlID != null) {
            this.mResXmlID = null;
            resXmlID.setResXmlStringInternal(null);
        }
    }

    public void linkResourceIdInternal(ResXmlID resXmlID) {
        if (resXmlID == null) {
            throw new NullPointerException("Can not link null id item");
        }
        ResXmlID resXmlID2 = this.mResXmlID;
        if (resXmlID2 == resXmlID) {
            return;
        }
        if (resXmlID2 != null) {
            throw new IllegalStateException("Style item is already linked");
        }
        this.mResXmlID = resXmlID;
        resXmlID.setResXmlStringInternal(this);
    }

    @Override // com.reandroid.arsc.item.StringItem
    public boolean hasReference() {
        if (super.hasReference()) {
            return true;
        }
        ResXmlID resXmlID = getResXmlID();
        if (resXmlID != null) {
            return resXmlID.hasReference();
        }
        return false;
    }

    @Override // com.reandroid.arsc.item.StringItem
    public boolean merge(StringItem stringItem) {
        if (!super.merge(stringItem)) {
            return false;
        }
        int resourceId = ((ResXmlString) stringItem).getResourceId();
        if (resourceId == 0) {
            return true;
        }
        getOrCreateResXmlID().set(resourceId);
        return true;
    }

    @Override // com.reandroid.arsc.item.StringItem, java.lang.Comparable
    public int compareTo(StringItem stringItem) {
        if (!(stringItem instanceof ResXmlString)) {
            return -1;
        }
        if (stringItem == this) {
            return 0;
        }
        int resourceId = getResourceId();
        int resourceId2 = ((ResXmlString) stringItem).getResourceId();
        int iCompare = CompareUtil.compare(resourceId == 0, resourceId2 == 0);
        if (iCompare != 0) {
            return iCompare;
        }
        int iCompareUnsigned = CompareUtil.compareUnsigned(resourceId, resourceId2);
        return iCompareUnsigned != 0 ? iCompareUnsigned : super.compareTo(stringItem);
    }
}
