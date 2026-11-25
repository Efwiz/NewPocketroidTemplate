package com.reandroid.arsc.array;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.base.BlockArray;
import com.reandroid.arsc.chunk.xml.ResXmlAttribute;
import com.reandroid.arsc.header.HeaderBlock;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.ShortItem;
import com.reandroid.json.JSONArray;
import com.reandroid.json.JSONConvert;
import com.reandroid.utils.collection.ArrayCollection;
import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class ResXmlAttributeArray extends BlockArray<ResXmlAttribute> implements Comparator<ResXmlAttribute>, JSONConvert<JSONArray> {
    private static final ResXmlAttribute[] EMPTY = new ResXmlAttribute[0];
    private final ShortItem mAttributeCount;
    private final ShortItem mAttributeStart;
    private final ShortItem mAttributesUnitSize;
    private final HeaderBlock mHeaderBlock;

    public ResXmlAttributeArray(HeaderBlock headerBlock, ShortItem shortItem, ShortItem shortItem2, ShortItem shortItem3) {
        this.mHeaderBlock = headerBlock;
        this.mAttributeStart = shortItem;
        this.mAttributeCount = shortItem2;
        this.mAttributesUnitSize = shortItem3;
    }

    public int removeUndefinedAttributes() {
        List<ResXmlAttribute> listListUndefined = listUndefined();
        Iterator<ResXmlAttribute> it = listListUndefined.iterator();
        while (it.hasNext()) {
            it.next().removeSelf();
        }
        return listListUndefined.size();
    }

    public List<ResXmlAttribute> listUndefined() {
        ArrayCollection arrayCollection = new ArrayCollection(size());
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            ResXmlAttribute resXmlAttribute = (ResXmlAttribute) it.next();
            if (resXmlAttribute != null && resXmlAttribute.isUndefined()) {
                arrayCollection.add(resXmlAttribute);
            }
        }
        return arrayCollection;
    }

    public void setAttributesUnitSize(int i) {
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            ((ResXmlAttribute) it.next()).setAttributesUnitSize(i);
        }
        this.mAttributesUnitSize.set(i);
    }

    public void sortAttributes() {
        sort(this);
    }

    private void refreshCount() {
        this.mAttributeCount.set(size());
    }

    private void refreshStart() {
        Block parent = getParent();
        if (parent == null) {
            return;
        }
        this.mAttributeStart.set(parent.countUpTo(this) - this.mHeaderBlock.countBytes());
    }

    @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
    public ResXmlAttribute newInstance() {
        return new ResXmlAttribute(this.mAttributesUnitSize.unsignedInt());
    }

    @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
    public ResXmlAttribute[] newArrayInstance(int i) {
        if (i == 0) {
            return EMPTY;
        }
        return new ResXmlAttribute[i];
    }

    @Override // com.reandroid.arsc.container.BlockList
    protected void onRefreshed() {
        refreshCount();
        refreshStart();
    }

    @Override // com.reandroid.arsc.base.BlockArray, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        blockReader.seek(this.mHeaderBlock.getHeaderSize() + this.mAttributeStart.get());
        setSize(this.mAttributeCount.get());
        int iUnsignedInt = this.mAttributesUnitSize.unsignedInt();
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            ResXmlAttribute resXmlAttribute = (ResXmlAttribute) it.next();
            int position = blockReader.getPosition();
            resXmlAttribute.readBytes(blockReader);
            blockReader.offset(iUnsignedInt - (blockReader.getPosition() - position));
        }
    }

    @Override // com.reandroid.arsc.base.BlockArray
    public void clear() {
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            ((ResXmlAttribute) it.next()).onRemoved();
        }
        super.clear();
    }

    @Override // java.util.Comparator
    public int compare(ResXmlAttribute resXmlAttribute, ResXmlAttribute resXmlAttribute2) {
        return resXmlAttribute.compareTo(resXmlAttribute2);
    }

    @Override // com.reandroid.json.JSONConvert
    public JSONArray toJson() {
        sortAttributes();
        JSONArray jSONArray = new JSONArray();
        Iterator<ResXmlAttribute> it = listItems().iterator();
        int i = 0;
        while (it.hasNext()) {
            jSONArray.put(i, it.next().toJson());
            i++;
        }
        return jSONArray;
    }

    @Override // com.reandroid.json.JSONConvert
    public void fromJson(JSONArray jSONArray) {
        clear();
        if (jSONArray == null) {
            return;
        }
        int length = jSONArray.length();
        ensureSize(length);
        for (int i = 0; i < length; i++) {
            ((ResXmlAttribute) get(i)).fromJson(jSONArray.getJSONObject(i));
        }
    }
}
