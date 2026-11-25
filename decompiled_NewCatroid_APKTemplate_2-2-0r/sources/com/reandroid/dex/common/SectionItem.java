package com.reandroid.dex.common;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.item.BlockItem;
import com.reandroid.dex.base.UsageMarker;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.sections.SectionList;
import com.reandroid.dex.sections.SectionType;

/* JADX INFO: loaded from: classes7.dex */
public class SectionItem extends BlockItem implements EditableItem, SectionTool, UsageMarker {
    private Key mLastKey;
    private SectionItem mReplace;
    private int mUsageType;

    @Override // com.reandroid.dex.common.EditableItem
    public void editInternal(Block block) {
    }

    public Key getKey() {
        return null;
    }

    public SectionItem(int i) {
        super(i);
    }

    public int getIdx() {
        throw new RuntimeException("Not applicable for: " + getClass());
    }

    public SectionType<? extends SectionItem> getSectionType() {
        throw new RuntimeException("Not implemented for " + getClass());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T1 extends SectionItem> T1 getReplace() {
        SectionItem sectionItem = this.mReplace;
        if (sectionItem == null) {
            if (isRemovedInternal()) {
                return null;
            }
            return this;
        }
        return (T1) sectionItem.getReplace();
    }

    boolean isRemovedInternal() {
        return getParent() == null;
    }

    public void setReplace(SectionItem sectionItem) {
        if (sectionItem == this) {
            return;
        }
        if (sectionItem == null) {
            this.mReplace = null;
            return;
        }
        if (getClass() != sectionItem.getClass()) {
            throw new IllegalArgumentException("Incompatible replace: " + getClass() + ", " + sectionItem.getClass());
        }
        if (sectionItem.getParent() == null) {
            sectionItem = null;
        } else {
            if (sectionItem.mReplace == this) {
                throw new IllegalStateException("Cyclic replace set: " + getKey());
            }
            sectionItem.addUsageType(getUsageType());
        }
        this.mReplace = sectionItem;
    }

    public boolean isRemoved() {
        return getParent() == null;
    }

    protected <T1 extends Key> T1 checkKey(T1 t1) {
        T1 t2 = (T1) this.mLastKey;
        if (t2 != null && t2.equals(t1)) {
            return t2;
        }
        this.mLastKey = t1;
        keyChanged(t2);
        return t1;
    }

    protected void keyChanged(Key key) {
        SectionList sectionList;
        if (key == null || (sectionList = getSectionList()) == null) {
            return;
        }
        sectionList.keyChangedInternal(this, getSectionType(), key);
    }

    public boolean isSameContext(SectionItem sectionItem) {
        return getSectionList() == sectionItem.getSectionList();
    }

    public boolean isSameContext(SectionList sectionList) {
        return getSectionList() == sectionList;
    }

    @Override // com.reandroid.dex.base.UsageMarker
    public int getUsageType() {
        return this.mUsageType;
    }

    @Override // com.reandroid.dex.base.UsageMarker
    public void addUsageType(int i) {
        this.mUsageType |= i;
        SectionItem replace = getReplace();
        if (replace == null || replace == this) {
            return;
        }
        replace.mUsageType = i | replace.mUsageType;
    }

    @Override // com.reandroid.dex.base.UsageMarker
    public boolean containsUsage(int i) {
        if (i == 0) {
            return this.mUsageType == 0;
        }
        return (this.mUsageType & i) == i;
    }

    @Override // com.reandroid.dex.base.UsageMarker
    public void clearUsageType() {
        this.mUsageType = UsageMarker.USAGE_NONE;
    }

    public void removeSelf() {
        throw new RuntimeException("Not implemented");
    }

    public boolean equalsKey(SectionItem sectionItem) {
        Key key;
        if (sectionItem == this) {
            return true;
        }
        if (sectionItem == null || getSectionType() != sectionItem.getSectionType() || (key = getKey()) == null) {
            return false;
        }
        return key.equals(sectionItem.getKey());
    }

    public boolean equalsKey(Key key) {
        Key key2;
        if (key == null || (key2 = getKey()) == null) {
            return false;
        }
        return key2.equals(key);
    }
}
