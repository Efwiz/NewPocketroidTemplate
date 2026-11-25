package com.reandroid.arsc.array;

import com.reandroid.arsc.item.IntegerItem;
import com.reandroid.arsc.item.StringItem;
import com.reandroid.arsc.pool.StringPool;
import com.reandroid.json.JSONArray;
import com.reandroid.json.JSONConvert;
import com.reandroid.json.JSONObject;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.ObjectsUtil;
import java.util.Comparator;
import java.util.Iterator;

/* JADX INFO: loaded from: classes10.dex */
public abstract class StringArray<T extends StringItem> extends OffsetBlockArray<T> implements JSONConvert<JSONArray> {
    private boolean mUtf8;

    public StringArray(OffsetArray offsetArray, IntegerItem integerItem, IntegerItem integerItem2, boolean z) {
        super(offsetArray, integerItem, integerItem2);
        this.mUtf8 = z;
    }

    public void setUtf8(boolean z) {
        if (this.mUtf8 == z) {
            return;
        }
        this.mUtf8 = z;
        for (T t : this) {
            if (t != null) {
                t.setUtf8(z);
            }
        }
    }

    public boolean isUtf8() {
        return this.mUtf8;
    }

    @Override // com.reandroid.arsc.base.BlockArray, com.reandroid.arsc.container.BlockList
    public void onPreRemove(T t) {
        ((StringPool) getParentInstance(StringPool.class)).onStringRemoved(t);
        t.onRemoved();
        super.onPreRemove(t);
    }

    private StyleArray getStyleArray() {
        StringPool stringPool = (StringPool) getParentInstance(StringPool.class);
        if (stringPool != null) {
            return stringPool.getStyleArray();
        }
        return (StyleArray) ObjectsUtil.cast(null);
    }

    public void sort() {
        sort(CompareUtil.getComparableComparator());
    }

    @Override // com.reandroid.arsc.container.BlockList
    public boolean sort(Comparator<? super T> comparator) {
        boolean zSort = super.sort(comparator);
        getStyleArray().sort();
        return zSort;
    }

    @Override // com.reandroid.arsc.container.BlockList
    protected void onPreRefresh() {
        sort();
        super.onPreRefresh();
    }

    @Override // com.reandroid.json.JSONConvert
    public JSONArray toJson() {
        return toJson(true);
    }

    public JSONArray toJson(boolean z) {
        if (size() == 0) {
            return null;
        }
        JSONArray jSONArray = new JSONArray();
        Iterator<T> it = m2755lambda$listItems$0$comreandroidarscbaseBlockArray(true);
        int i = 0;
        while (it.hasNext()) {
            T next = it.next();
            if (!z || next.hasStyle()) {
                JSONObject json = next.toJson();
                if (json != null) {
                    jSONArray.put(i, json);
                    i++;
                }
            }
        }
        if (i == 0) {
            return null;
        }
        return jSONArray;
    }

    @Override // com.reandroid.json.JSONConvert
    public void fromJson(JSONArray jSONArray) {
        int length = jSONArray != null ? jSONArray.length() : 0;
        ensureSize(length);
        if (length == 0) {
            return;
        }
        for (int i = 0; i < length; i++) {
            ((StringItem) get(i)).fromJson(jSONArray.getJSONObject(i));
        }
    }
}
