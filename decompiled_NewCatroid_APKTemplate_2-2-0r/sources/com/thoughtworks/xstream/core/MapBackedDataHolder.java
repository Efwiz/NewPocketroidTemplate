package com.thoughtworks.xstream.core;

import com.thoughtworks.xstream.converters.DataHolder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/MapBackedDataHolder.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class MapBackedDataHolder implements DataHolder {
    private final Map map;

    public MapBackedDataHolder() {
        this(new HashMap());
    }

    public MapBackedDataHolder(Map map) {
        this.map = map;
    }

    @Override // com.thoughtworks.xstream.converters.DataHolder
    public Object get(Object obj) {
        return this.map.get(obj);
    }

    @Override // com.thoughtworks.xstream.converters.DataHolder
    public void put(Object obj, Object obj2) {
        this.map.put(obj, obj2);
    }

    @Override // com.thoughtworks.xstream.converters.DataHolder
    public Iterator keys() {
        return Collections.unmodifiableCollection(this.map.keySet()).iterator();
    }
}
