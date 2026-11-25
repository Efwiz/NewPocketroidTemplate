package com.thoughtworks.xstream.io.naming;

import java.util.HashMap;
import java.util.Map;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/naming/StaticNameCoder.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class StaticNameCoder implements NameCoder {
    private transient Map attribute2Java;
    private final Map java2Attribute;
    private final Map java2Node;
    private transient Map node2Java;

    public StaticNameCoder(Map map, Map map2) {
        HashMap map3 = new HashMap(map);
        this.java2Node = map3;
        if (map == map2 || map2 == null) {
            this.java2Attribute = map3;
        } else {
            this.java2Attribute = new HashMap(map2);
        }
        readResolve();
    }

    @Override // com.thoughtworks.xstream.io.naming.NameCoder
    public String decodeAttribute(String str) {
        String str2 = (String) this.attribute2Java.get(str);
        return str2 == null ? str : str2;
    }

    @Override // com.thoughtworks.xstream.io.naming.NameCoder
    public String decodeNode(String str) {
        String str2 = (String) this.node2Java.get(str);
        return str2 == null ? str : str2;
    }

    @Override // com.thoughtworks.xstream.io.naming.NameCoder
    public String encodeAttribute(String str) {
        String str2 = (String) this.java2Attribute.get(str);
        return str2 == null ? str : str2;
    }

    @Override // com.thoughtworks.xstream.io.naming.NameCoder
    public String encodeNode(String str) {
        String str2 = (String) this.java2Node.get(str);
        return str2 == null ? str : str2;
    }

    private Object readResolve() {
        Map mapInvertMap = invertMap(this.java2Node);
        this.node2Java = mapInvertMap;
        Map map = this.java2Node;
        Map map2 = this.java2Attribute;
        if (map == map2) {
            this.attribute2Java = mapInvertMap;
        } else {
            this.attribute2Java = invertMap(map2);
        }
        return this;
    }

    private Map invertMap(Map map) {
        HashMap map2 = new HashMap(map.size());
        for (Map.Entry entry : map.entrySet()) {
            map2.put((String) entry.getValue(), (String) entry.getKey());
        }
        return map2;
    }
}
