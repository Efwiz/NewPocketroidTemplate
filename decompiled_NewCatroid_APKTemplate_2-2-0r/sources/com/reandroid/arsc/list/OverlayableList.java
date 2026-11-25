package com.reandroid.arsc.list;

import com.reandroid.arsc.chunk.Overlayable;
import com.reandroid.arsc.container.BlockList;
import com.reandroid.json.JSONArray;
import com.reandroid.json.JSONConvert;
import java.util.Iterator;

/* JADX INFO: loaded from: classes10.dex */
public class OverlayableList extends BlockList<Overlayable> implements JSONConvert<JSONArray> {
    public Overlayable get(String str) {
        for (Overlayable overlayable : getChildes()) {
            if (str.equals(overlayable.getName())) {
                return overlayable;
            }
        }
        return null;
    }

    @Override // com.reandroid.json.JSONConvert
    public JSONArray toJson() {
        if (size() == 0) {
            return null;
        }
        JSONArray jSONArray = new JSONArray();
        Iterator<Overlayable> it = getChildes().iterator();
        while (it.hasNext()) {
            jSONArray.put(it.next().toJson());
        }
        return jSONArray;
    }

    @Override // com.reandroid.json.JSONConvert
    public void fromJson(JSONArray jSONArray) {
        if (jSONArray == null) {
            return;
        }
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            Overlayable overlayable = new Overlayable();
            add(overlayable);
            overlayable.fromJson(jSONArray.getJSONObject(i));
        }
    }

    public void merge(OverlayableList overlayableList) {
        if (overlayableList == null || overlayableList == this) {
            return;
        }
        for (Overlayable overlayable : overlayableList.getChildes()) {
            Overlayable overlayable2 = get(overlayable.getName());
            if (overlayable2 == null) {
                overlayable2 = new Overlayable();
                add(overlayable2);
            }
            overlayable2.merge(overlayable);
        }
    }
}
