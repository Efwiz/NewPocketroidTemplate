package com.reandroid.arsc.item;

import com.reandroid.arsc.base.Block;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.HexUtil;
import com.reandroid.utils.collection.ArrayCollection;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class ResXmlID extends IntegerItem implements Comparable<ResXmlID> {
    private List<ReferenceItem> mReferencedList;
    private ResXmlString mResXmlString;

    @Override // com.reandroid.arsc.base.Block
    public void onIndexChanged(int i, int i2) {
    }

    public ResXmlID(int i) {
        super(i);
    }

    public ResXmlID() {
        this(0);
    }

    public boolean removeReference(ReferenceItem referenceItem) {
        List<ReferenceItem> list = this.mReferencedList;
        if (list == null) {
            return false;
        }
        boolean zRemove = list.remove(referenceItem);
        if (list.size() != 0) {
            return zRemove;
        }
        this.mReferencedList = null;
        return zRemove;
    }

    public void addReference(ReferenceItem referenceItem) {
        if (referenceItem != null) {
            List arrayCollection = this.mReferencedList;
            if (arrayCollection == null) {
                arrayCollection = new ArrayCollection();
                this.mReferencedList = arrayCollection;
            }
            arrayCollection.add(referenceItem);
        }
    }

    public boolean hasReference() {
        List<ReferenceItem> list = this.mReferencedList;
        return (list == null || list.size() == 0) ? false : true;
    }

    public boolean hasReference(Block block) {
        List<ReferenceItem> list;
        if (block != null && (list = this.mReferencedList) != null) {
            Iterator<ReferenceItem> it = list.iterator();
            while (it.hasNext()) {
                if (it.next().getReferredParent(block.getClass()) == block) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getReferenceCount() {
        List<ReferenceItem> list = this.mReferencedList;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public String getName() {
        ResXmlString resXmlString = getResXmlString();
        if (resXmlString == null) {
            return null;
        }
        return resXmlString.getHtml();
    }

    public ResXmlString getResXmlString() {
        ResXmlString resXmlString = this.mResXmlString;
        if (resXmlString == null || resXmlString.isNull()) {
            return null;
        }
        return resXmlString;
    }

    void setResXmlStringInternal(ResXmlString resXmlString) {
        this.mResXmlString = resXmlString;
    }

    public boolean isEmpty() {
        return getResXmlString() == null || get() == 0;
    }

    @Override // java.lang.Comparable
    public int compareTo(ResXmlID resXmlID) {
        if (resXmlID == null) {
            return -1;
        }
        if (resXmlID == this) {
            return 0;
        }
        ResXmlString resXmlString = getResXmlString();
        ResXmlString resXmlString2 = resXmlID.getResXmlString();
        int iCompare = CompareUtil.compare(resXmlString == null, resXmlString2 == null);
        if (iCompare != 0) {
            return iCompare;
        }
        if (resXmlString == null || resXmlString2 == null) {
            return 0;
        }
        return CompareUtil.compare(resXmlString.getIndex(), resXmlString2.getIndex());
    }

    @Override // com.reandroid.arsc.item.IntegerItem
    public String toString() {
        StringBuilder sb = new StringBuilder("USED-BY=");
        sb.append(getReferenceCount());
        sb.append('{');
        String name2 = getName();
        if (name2 != null) {
            sb.append(name2);
        } else {
            sb.append(getIndex());
        }
        sb.append(':');
        sb.append(HexUtil.toHex8(get()));
        sb.append('}');
        return sb.toString();
    }
}
