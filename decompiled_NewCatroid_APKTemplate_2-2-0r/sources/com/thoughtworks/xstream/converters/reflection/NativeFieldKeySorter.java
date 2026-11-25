package com.thoughtworks.xstream.converters.reflection;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/reflection/NativeFieldKeySorter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class NativeFieldKeySorter implements FieldKeySorter {
    @Override // com.thoughtworks.xstream.converters.reflection.FieldKeySorter
    public Map sort(Class cls, Map map) {
        TreeMap treeMap = new TreeMap(new Comparator() { // from class: com.thoughtworks.xstream.converters.reflection.NativeFieldKeySorter.1
            @Override // java.util.Comparator
            public int compare(Object obj, Object obj2) {
                FieldKey fieldKey = (FieldKey) obj;
                FieldKey fieldKey2 = (FieldKey) obj2;
                int depth = fieldKey.getDepth() - fieldKey2.getDepth();
                return depth == 0 ? fieldKey.getOrder() - fieldKey2.getOrder() : depth;
            }
        });
        treeMap.putAll(map);
        return treeMap;
    }
}
