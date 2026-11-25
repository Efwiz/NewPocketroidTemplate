package com.thoughtworks.xstream.core.util;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/util/WeakCache.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class WeakCache extends AbstractMap {
    private final Map map;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/util/WeakCache$Visitor.class
      classes11.dex
     */
    private interface Visitor {
        Object visit(Object obj);
    }

    public WeakCache() {
        this(new WeakHashMap());
    }

    public WeakCache(Map map) {
        this.map = map;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Object get(Object obj) {
        Reference reference = (Reference) this.map.get(obj);
        if (reference != null) {
            return reference.get();
        }
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Object put(Object obj, Object obj2) {
        Reference reference = (Reference) this.map.put(obj, createReference(obj2));
        if (reference == null) {
            return null;
        }
        return reference.get();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Object remove(Object obj) {
        Reference reference = (Reference) this.map.remove(obj);
        if (reference == null) {
            return null;
        }
        return reference.get();
    }

    protected Reference createReference(Object obj) {
        return new WeakReference(obj);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsValue(final Object obj) {
        return ((Boolean) iterate(new Visitor() { // from class: com.thoughtworks.xstream.core.util.WeakCache.1
            @Override // com.thoughtworks.xstream.core.util.WeakCache.Visitor
            public Object visit(Object obj2) {
                if (obj2.equals(obj)) {
                    return Boolean.TRUE;
                }
                return null;
            }
        }, 0)) == Boolean.TRUE;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        if (this.map.size() == 0) {
            return 0;
        }
        final int[] iArr = {0};
        iterate(new Visitor() { // from class: com.thoughtworks.xstream.core.util.WeakCache.2
            @Override // com.thoughtworks.xstream.core.util.WeakCache.Visitor
            public Object visit(Object obj) {
                int[] iArr2 = iArr;
                iArr2[0] = iArr2[0] + 1;
                return null;
            }
        }, 0);
        return iArr[0];
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Collection values() {
        final ArrayList arrayList = new ArrayList();
        if (this.map.size() != 0) {
            iterate(new Visitor() { // from class: com.thoughtworks.xstream.core.util.WeakCache.3
                @Override // com.thoughtworks.xstream.core.util.WeakCache.Visitor
                public Object visit(Object obj) {
                    arrayList.add(obj);
                    return null;
                }
            }, 0);
        }
        return arrayList;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set entrySet() {
        final HashSet hashSet = new HashSet();
        if (this.map.size() != 0) {
            iterate(new Visitor() { // from class: com.thoughtworks.xstream.core.util.WeakCache.4
                @Override // com.thoughtworks.xstream.core.util.WeakCache.Visitor
                public Object visit(Object obj) {
                    final Map.Entry entry = (Map.Entry) obj;
                    hashSet.add(new Map.Entry() { // from class: com.thoughtworks.xstream.core.util.WeakCache.4.1
                        @Override // java.util.Map.Entry
                        public Object getKey() {
                            return entry.getKey();
                        }

                        @Override // java.util.Map.Entry
                        public Object getValue() {
                            return ((Reference) entry.getValue()).get();
                        }

                        @Override // java.util.Map.Entry
                        public Object setValue(Object obj2) {
                            Reference reference = (Reference) entry.setValue(WeakCache.this.createReference(obj2));
                            if (reference != null) {
                                return reference.get();
                            }
                            return null;
                        }
                    });
                    return null;
                }
            }, 2);
        }
        return hashSet;
    }

    private Object iterate(Visitor visitor, int i) {
        Iterator it = this.map.entrySet().iterator();
        Object objVisit = null;
        while (objVisit == null && it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object obj = ((Reference) entry.getValue()).get();
            if (obj == null) {
                it.remove();
            } else if (i == 0) {
                objVisit = visitor.visit(obj);
            } else if (i == 1) {
                objVisit = visitor.visit(entry.getKey());
            } else if (i == 2) {
                objVisit = visitor.visit(entry);
            }
        }
        return objVisit;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object obj) {
        return this.map.containsKey(obj);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        this.map.clear();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set keySet() {
        return this.map.keySet();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean equals(Object obj) {
        return this.map.equals(obj);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int hashCode() {
        return this.map.hashCode();
    }

    @Override // java.util.AbstractMap
    public String toString() {
        return this.map.toString();
    }
}
