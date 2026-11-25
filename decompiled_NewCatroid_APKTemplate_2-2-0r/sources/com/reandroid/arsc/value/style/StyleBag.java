package com.reandroid.arsc.value.style;

import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.arsc.value.Entry;
import com.reandroid.arsc.value.ResTableMapEntry;
import com.reandroid.arsc.value.ResValueMap;
import com.reandroid.arsc.value.bag.MapBag;
import com.reandroid.xml.XMLUtil;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class StyleBag extends MapBag<Integer, StyleBagItem> {
    private StyleBag(Entry entry) {
        super(entry);
    }

    public String getParentResourceName() {
        Entry entry;
        ResTableMapEntry resTableMapEntry;
        if (getParentId() == 0 || (entry = getEntry()) == null || (resTableMapEntry = entry.getResTableMapEntry()) == null) {
            return null;
        }
        return resTableMapEntry.decodeParentId();
    }

    public int getParentId() {
        return getTableEntry().getParentId();
    }

    public void setParentId(int i) {
        getTableEntry().setParentId(i);
    }

    public int getResourceId() {
        Entry entry = getEntry();
        if (entry == null) {
            return 0;
        }
        return entry.getResourceId();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.reandroid.arsc.value.bag.MapBag
    public StyleBagItem createBagItem(ResValueMap resValueMap, boolean z) {
        if (z) {
            return StyleBagItem.copyOf(resValueMap);
        }
        return StyleBagItem.create(resValueMap);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.reandroid.arsc.value.bag.MapBag
    public ResValueMap newKey(Integer num) {
        ResValueMap resValueMap = new ResValueMap();
        resValueMap.setParent(getMapArray());
        resValueMap.setNameId(num.intValue());
        return resValueMap;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.reandroid.arsc.value.bag.MapBag
    public Integer getKeyFor(ResValueMap resValueMap) {
        return Integer.valueOf(resValueMap.getNameId());
    }

    public static int resolve(TableBlock tableBlock, String str) {
        return tableBlock.getAttrResource(XMLUtil.splitPrefix(str), XMLUtil.splitName(str)).getResourceId();
    }

    @Override // java.util.AbstractMap
    public String toString() {
        StringBuilder sb = new StringBuilder("<");
        String typeName = getTypeName();
        sb.append(typeName);
        sb.append(" name=\"");
        sb.append(getName());
        sb.append("\"");
        String parentResourceName = getParentResourceName();
        if (parentResourceName != null) {
            sb.append(" parent=\"");
            sb.append(parentResourceName);
            sb.append("\"");
        }
        sb.append("\">");
        for (StyleBagItem styleBagItem : values()) {
            sb.append("\n    ");
            sb.append(styleBagItem.toString());
        }
        sb.append("\n</");
        sb.append(typeName);
        sb.append(">");
        return sb.toString();
    }

    public static boolean isStyle(Entry entry) {
        TableBlock tableBlock;
        StyleBag styleBagCreate = create(entry);
        if (styleBagCreate == null || (tableBlock = entry.getPackageBlock().getTableBlock()) == null) {
            return false;
        }
        List<T> childes = styleBagCreate.getMapArray().getChildes();
        if (childes.size() == 0) {
            return false;
        }
        for (T t : childes) {
            if (t == null || tableBlock.getResource(t.getNameId()) == null) {
                return false;
            }
        }
        return true;
    }

    public static StyleBag create(Entry entry) {
        if (entry == null || !entry.isComplex()) {
            return null;
        }
        return new StyleBag(entry);
    }
}
