package com.thoughtworks.xstream.converters.javabean;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/javabean/ComparingPropertySorter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class ComparingPropertySorter implements PropertySorter {
    private final Comparator comparator;

    public ComparingPropertySorter(Comparator comparator) {
        this.comparator = comparator;
    }

    @Override // com.thoughtworks.xstream.converters.javabean.PropertySorter
    public Map sort(Class cls, Map map) {
        TreeMap treeMap = new TreeMap(this.comparator);
        treeMap.putAll(map);
        return treeMap;
    }
}
