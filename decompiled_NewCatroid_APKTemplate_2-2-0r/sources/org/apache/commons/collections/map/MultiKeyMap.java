package org.apache.commons.collections.map;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections.IterableMap;
import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.keyvalue.MultiKey;

/* JADX INFO: loaded from: classes12.dex */
public class MultiKeyMap implements IterableMap, Serializable {
    private static final long serialVersionUID = -1788199231038721040L;
    protected final AbstractHashedMap map;

    public static MultiKeyMap decorate(AbstractHashedMap abstractHashedMap) {
        if (abstractHashedMap == null) {
            throw new IllegalArgumentException("Map must not be null");
        }
        if (abstractHashedMap.size() > 0) {
            throw new IllegalArgumentException("Map must be empty");
        }
        return new MultiKeyMap(abstractHashedMap);
    }

    public MultiKeyMap() {
        this.map = new HashedMap();
    }

    protected MultiKeyMap(AbstractHashedMap abstractHashedMap) {
        this.map = abstractHashedMap;
    }

    public Object get(Object obj, Object obj2) {
        int iHash = hash(obj, obj2);
        AbstractHashedMap.HashEntry[] hashEntryArr = this.map.data;
        AbstractHashedMap abstractHashedMap = this.map;
        for (AbstractHashedMap.HashEntry hashEntry = hashEntryArr[abstractHashedMap.hashIndex(iHash, abstractHashedMap.data.length)]; hashEntry != null; hashEntry = hashEntry.next) {
            if (hashEntry.hashCode == iHash && isEqualKey(hashEntry, obj, obj2)) {
                return hashEntry.getValue();
            }
        }
        return null;
    }

    public boolean containsKey(Object obj, Object obj2) {
        int iHash = hash(obj, obj2);
        AbstractHashedMap.HashEntry[] hashEntryArr = this.map.data;
        AbstractHashedMap abstractHashedMap = this.map;
        for (AbstractHashedMap.HashEntry hashEntry = hashEntryArr[abstractHashedMap.hashIndex(iHash, abstractHashedMap.data.length)]; hashEntry != null; hashEntry = hashEntry.next) {
            if (hashEntry.hashCode == iHash && isEqualKey(hashEntry, obj, obj2)) {
                return true;
            }
        }
        return false;
    }

    public Object put(Object obj, Object obj2, Object obj3) {
        int iHash = hash(obj, obj2);
        AbstractHashedMap abstractHashedMap = this.map;
        int iHashIndex = abstractHashedMap.hashIndex(iHash, abstractHashedMap.data.length);
        for (AbstractHashedMap.HashEntry hashEntry = this.map.data[iHashIndex]; hashEntry != null; hashEntry = hashEntry.next) {
            if (hashEntry.hashCode == iHash && isEqualKey(hashEntry, obj, obj2)) {
                Object value = hashEntry.getValue();
                this.map.updateEntry(hashEntry, obj3);
                return value;
            }
        }
        this.map.addMapping(iHashIndex, iHash, new MultiKey(obj, obj2), obj3);
        return null;
    }

    @Override // java.util.Map
    public Object remove(Object obj, Object obj2) {
        int iHash = hash(obj, obj2);
        AbstractHashedMap abstractHashedMap = this.map;
        int iHashIndex = abstractHashedMap.hashIndex(iHash, abstractHashedMap.data.length);
        AbstractHashedMap.HashEntry hashEntry = null;
        for (AbstractHashedMap.HashEntry hashEntry2 = this.map.data[iHashIndex]; hashEntry2 != null; hashEntry2 = hashEntry2.next) {
            if (hashEntry2.hashCode == iHash && isEqualKey(hashEntry2, obj, obj2)) {
                Object value = hashEntry2.getValue();
                this.map.removeMapping(hashEntry2, iHashIndex, hashEntry);
                return value;
            }
            hashEntry = hashEntry2;
        }
        return null;
    }

    protected int hash(Object obj, Object obj2) {
        int iHashCode = obj != null ? obj.hashCode() : 0;
        if (obj2 != null) {
            iHashCode ^= obj2.hashCode();
        }
        int i = iHashCode + (~(iHashCode << 9));
        int i2 = i ^ (i >>> 14);
        int i3 = i2 + (i2 << 4);
        return i3 ^ (i3 >>> 10);
    }

    protected boolean isEqualKey(AbstractHashedMap.HashEntry hashEntry, Object obj, Object obj2) {
        MultiKey multiKey = (MultiKey) hashEntry.getKey();
        if (multiKey.size() != 2) {
            return false;
        }
        if (obj == null) {
            if (multiKey.getKey(0) != null) {
                return false;
            }
        } else if (!obj.equals(multiKey.getKey(0))) {
            return false;
        }
        Object key = multiKey.getKey(1);
        if (obj2 == null) {
            if (key != null) {
                return false;
            }
        } else if (!obj2.equals(key)) {
            return false;
        }
        return true;
    }

    public Object get(Object obj, Object obj2, Object obj3) {
        int iHash = hash(obj, obj2, obj3);
        AbstractHashedMap.HashEntry[] hashEntryArr = this.map.data;
        AbstractHashedMap abstractHashedMap = this.map;
        for (AbstractHashedMap.HashEntry hashEntry = hashEntryArr[abstractHashedMap.hashIndex(iHash, abstractHashedMap.data.length)]; hashEntry != null; hashEntry = hashEntry.next) {
            if (hashEntry.hashCode == iHash && isEqualKey(hashEntry, obj, obj2, obj3)) {
                return hashEntry.getValue();
            }
        }
        return null;
    }

    public boolean containsKey(Object obj, Object obj2, Object obj3) {
        int iHash = hash(obj, obj2, obj3);
        AbstractHashedMap.HashEntry[] hashEntryArr = this.map.data;
        AbstractHashedMap abstractHashedMap = this.map;
        for (AbstractHashedMap.HashEntry hashEntry = hashEntryArr[abstractHashedMap.hashIndex(iHash, abstractHashedMap.data.length)]; hashEntry != null; hashEntry = hashEntry.next) {
            if (hashEntry.hashCode == iHash && isEqualKey(hashEntry, obj, obj2, obj3)) {
                return true;
            }
        }
        return false;
    }

    public Object put(Object obj, Object obj2, Object obj3, Object obj4) {
        int iHash = hash(obj, obj2, obj3);
        AbstractHashedMap abstractHashedMap = this.map;
        int iHashIndex = abstractHashedMap.hashIndex(iHash, abstractHashedMap.data.length);
        for (AbstractHashedMap.HashEntry hashEntry = this.map.data[iHashIndex]; hashEntry != null; hashEntry = hashEntry.next) {
            if (hashEntry.hashCode == iHash && isEqualKey(hashEntry, obj, obj2, obj3)) {
                Object value = hashEntry.getValue();
                this.map.updateEntry(hashEntry, obj4);
                return value;
            }
        }
        this.map.addMapping(iHashIndex, iHash, new MultiKey(obj, obj2, obj3), obj4);
        return null;
    }

    public Object remove(Object obj, Object obj2, Object obj3) {
        int iHash = hash(obj, obj2, obj3);
        AbstractHashedMap abstractHashedMap = this.map;
        int iHashIndex = abstractHashedMap.hashIndex(iHash, abstractHashedMap.data.length);
        AbstractHashedMap.HashEntry hashEntry = null;
        for (AbstractHashedMap.HashEntry hashEntry2 = this.map.data[iHashIndex]; hashEntry2 != null; hashEntry2 = hashEntry2.next) {
            if (hashEntry2.hashCode == iHash && isEqualKey(hashEntry2, obj, obj2, obj3)) {
                Object value = hashEntry2.getValue();
                this.map.removeMapping(hashEntry2, iHashIndex, hashEntry);
                return value;
            }
            hashEntry = hashEntry2;
        }
        return null;
    }

    protected int hash(Object obj, Object obj2, Object obj3) {
        int iHashCode = obj != null ? obj.hashCode() : 0;
        if (obj2 != null) {
            iHashCode ^= obj2.hashCode();
        }
        if (obj3 != null) {
            iHashCode ^= obj3.hashCode();
        }
        int i = iHashCode + (~(iHashCode << 9));
        int i2 = i ^ (i >>> 14);
        int i3 = i2 + (i2 << 4);
        return i3 ^ (i3 >>> 10);
    }

    protected boolean isEqualKey(AbstractHashedMap.HashEntry hashEntry, Object obj, Object obj2, Object obj3) {
        MultiKey multiKey = (MultiKey) hashEntry.getKey();
        if (multiKey.size() != 3) {
            return false;
        }
        if (obj == null) {
            if (multiKey.getKey(0) != null) {
                return false;
            }
        } else if (!obj.equals(multiKey.getKey(0))) {
            return false;
        }
        if (obj2 == null) {
            if (multiKey.getKey(1) != null) {
                return false;
            }
        } else if (!obj2.equals(multiKey.getKey(1))) {
            return false;
        }
        Object key = multiKey.getKey(2);
        if (obj3 == null) {
            if (key != null) {
                return false;
            }
        } else if (!obj3.equals(key)) {
            return false;
        }
        return true;
    }

    public Object get(Object obj, Object obj2, Object obj3, Object obj4) {
        int iHash = hash(obj, obj2, obj3, obj4);
        AbstractHashedMap.HashEntry[] hashEntryArr = this.map.data;
        AbstractHashedMap abstractHashedMap = this.map;
        for (AbstractHashedMap.HashEntry hashEntry = hashEntryArr[abstractHashedMap.hashIndex(iHash, abstractHashedMap.data.length)]; hashEntry != null; hashEntry = hashEntry.next) {
            if (hashEntry.hashCode == iHash && isEqualKey(hashEntry, obj, obj2, obj3, obj4)) {
                return hashEntry.getValue();
            }
        }
        return null;
    }

    public boolean containsKey(Object obj, Object obj2, Object obj3, Object obj4) {
        int iHash = hash(obj, obj2, obj3, obj4);
        AbstractHashedMap.HashEntry[] hashEntryArr = this.map.data;
        AbstractHashedMap abstractHashedMap = this.map;
        for (AbstractHashedMap.HashEntry hashEntry = hashEntryArr[abstractHashedMap.hashIndex(iHash, abstractHashedMap.data.length)]; hashEntry != null; hashEntry = hashEntry.next) {
            if (hashEntry.hashCode == iHash && isEqualKey(hashEntry, obj, obj2, obj3, obj4)) {
                return true;
            }
        }
        return false;
    }

    public Object put(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        int iHash = hash(obj, obj2, obj3, obj4);
        AbstractHashedMap abstractHashedMap = this.map;
        int iHashIndex = abstractHashedMap.hashIndex(iHash, abstractHashedMap.data.length);
        for (AbstractHashedMap.HashEntry hashEntry = this.map.data[iHashIndex]; hashEntry != null; hashEntry = hashEntry.next) {
            if (hashEntry.hashCode == iHash && isEqualKey(hashEntry, obj, obj2, obj3, obj4)) {
                Object value = hashEntry.getValue();
                this.map.updateEntry(hashEntry, obj5);
                return value;
            }
        }
        this.map.addMapping(iHashIndex, iHash, new MultiKey(obj, obj2, obj3, obj4), obj5);
        return null;
    }

    public Object remove(Object obj, Object obj2, Object obj3, Object obj4) {
        int iHash = hash(obj, obj2, obj3, obj4);
        AbstractHashedMap abstractHashedMap = this.map;
        int iHashIndex = abstractHashedMap.hashIndex(iHash, abstractHashedMap.data.length);
        AbstractHashedMap.HashEntry hashEntry = null;
        for (AbstractHashedMap.HashEntry hashEntry2 = this.map.data[iHashIndex]; hashEntry2 != null; hashEntry2 = hashEntry2.next) {
            if (hashEntry2.hashCode == iHash && isEqualKey(hashEntry2, obj, obj2, obj3, obj4)) {
                Object value = hashEntry2.getValue();
                this.map.removeMapping(hashEntry2, iHashIndex, hashEntry);
                return value;
            }
            hashEntry = hashEntry2;
        }
        return null;
    }

    protected int hash(Object obj, Object obj2, Object obj3, Object obj4) {
        int iHashCode = obj != null ? obj.hashCode() : 0;
        if (obj2 != null) {
            iHashCode ^= obj2.hashCode();
        }
        if (obj3 != null) {
            iHashCode ^= obj3.hashCode();
        }
        if (obj4 != null) {
            iHashCode ^= obj4.hashCode();
        }
        int i = iHashCode + (~(iHashCode << 9));
        int i2 = i ^ (i >>> 14);
        int i3 = i2 + (i2 << 4);
        return i3 ^ (i3 >>> 10);
    }

    protected boolean isEqualKey(AbstractHashedMap.HashEntry hashEntry, Object obj, Object obj2, Object obj3, Object obj4) {
        MultiKey multiKey = (MultiKey) hashEntry.getKey();
        if (multiKey.size() != 4) {
            return false;
        }
        if (obj == null) {
            if (multiKey.getKey(0) != null) {
                return false;
            }
        } else if (!obj.equals(multiKey.getKey(0))) {
            return false;
        }
        if (obj2 == null) {
            if (multiKey.getKey(1) != null) {
                return false;
            }
        } else if (!obj2.equals(multiKey.getKey(1))) {
            return false;
        }
        Object key = multiKey.getKey(2);
        if (obj3 == null) {
            if (key != null) {
                return false;
            }
        } else if (!obj3.equals(key)) {
            return false;
        }
        Object key2 = multiKey.getKey(3);
        if (obj4 == null) {
            if (key2 != null) {
                return false;
            }
        } else if (!obj4.equals(key2)) {
            return false;
        }
        return true;
    }

    public Object get(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        int iHash = hash(obj, obj2, obj3, obj4, obj5);
        AbstractHashedMap.HashEntry[] hashEntryArr = this.map.data;
        AbstractHashedMap abstractHashedMap = this.map;
        for (AbstractHashedMap.HashEntry hashEntry = hashEntryArr[abstractHashedMap.hashIndex(iHash, abstractHashedMap.data.length)]; hashEntry != null; hashEntry = hashEntry.next) {
            if (hashEntry.hashCode == iHash && isEqualKey(hashEntry, obj, obj2, obj3, obj4, obj5)) {
                return hashEntry.getValue();
            }
        }
        return null;
    }

    public boolean containsKey(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        int iHash = hash(obj, obj2, obj3, obj4, obj5);
        AbstractHashedMap.HashEntry[] hashEntryArr = this.map.data;
        AbstractHashedMap abstractHashedMap = this.map;
        for (AbstractHashedMap.HashEntry hashEntry = hashEntryArr[abstractHashedMap.hashIndex(iHash, abstractHashedMap.data.length)]; hashEntry != null; hashEntry = hashEntry.next) {
            if (hashEntry.hashCode == iHash && isEqualKey(hashEntry, obj, obj2, obj3, obj4, obj5)) {
                return true;
            }
        }
        return false;
    }

    public Object put(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        int iHash = hash(obj, obj2, obj3, obj4, obj5);
        AbstractHashedMap abstractHashedMap = this.map;
        int iHashIndex = abstractHashedMap.hashIndex(iHash, abstractHashedMap.data.length);
        for (AbstractHashedMap.HashEntry hashEntry = this.map.data[iHashIndex]; hashEntry != null; hashEntry = hashEntry.next) {
            if (hashEntry.hashCode == iHash && isEqualKey(hashEntry, obj, obj2, obj3, obj4, obj5)) {
                Object value = hashEntry.getValue();
                this.map.updateEntry(hashEntry, obj6);
                return value;
            }
        }
        this.map.addMapping(iHashIndex, iHash, new MultiKey(obj, obj2, obj3, obj4, obj5), obj6);
        return null;
    }

    public Object remove(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        int iHash = hash(obj, obj2, obj3, obj4, obj5);
        AbstractHashedMap abstractHashedMap = this.map;
        int iHashIndex = abstractHashedMap.hashIndex(iHash, abstractHashedMap.data.length);
        AbstractHashedMap.HashEntry hashEntry = null;
        for (AbstractHashedMap.HashEntry hashEntry2 = this.map.data[iHashIndex]; hashEntry2 != null; hashEntry2 = hashEntry2.next) {
            if (hashEntry2.hashCode == iHash && isEqualKey(hashEntry2, obj, obj2, obj3, obj4, obj5)) {
                Object value = hashEntry2.getValue();
                this.map.removeMapping(hashEntry2, iHashIndex, hashEntry);
                return value;
            }
            hashEntry = hashEntry2;
        }
        return null;
    }

    protected int hash(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        int iHashCode = obj != null ? obj.hashCode() : 0;
        if (obj2 != null) {
            iHashCode ^= obj2.hashCode();
        }
        if (obj3 != null) {
            iHashCode ^= obj3.hashCode();
        }
        if (obj4 != null) {
            iHashCode ^= obj4.hashCode();
        }
        if (obj5 != null) {
            iHashCode ^= obj5.hashCode();
        }
        int i = iHashCode + (~(iHashCode << 9));
        int i2 = i ^ (i >>> 14);
        int i3 = i2 + (i2 << 4);
        return i3 ^ (i3 >>> 10);
    }

    protected boolean isEqualKey(AbstractHashedMap.HashEntry hashEntry, Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        MultiKey multiKey = (MultiKey) hashEntry.getKey();
        if (multiKey.size() != 5) {
            return false;
        }
        if (obj == null) {
            if (multiKey.getKey(0) != null) {
                return false;
            }
        } else if (!obj.equals(multiKey.getKey(0))) {
            return false;
        }
        if (obj2 == null) {
            if (multiKey.getKey(1) != null) {
                return false;
            }
        } else if (!obj2.equals(multiKey.getKey(1))) {
            return false;
        }
        Object key = multiKey.getKey(2);
        if (obj3 == null) {
            if (key != null) {
                return false;
            }
        } else if (!obj3.equals(key)) {
            return false;
        }
        Object key2 = multiKey.getKey(3);
        if (obj4 == null) {
            if (key2 != null) {
                return false;
            }
        } else if (!obj4.equals(key2)) {
            return false;
        }
        Object key3 = multiKey.getKey(4);
        if (obj5 == null) {
            if (key3 != null) {
                return false;
            }
        } else if (!obj5.equals(key3)) {
            return false;
        }
        return true;
    }

    public boolean removeAll(Object obj) {
        MapIterator mapIterator = mapIterator();
        boolean z = false;
        while (mapIterator.hasNext()) {
            MultiKey multiKey = (MultiKey) mapIterator.next();
            if (multiKey.size() >= 1) {
                Object key = multiKey.getKey(0);
                if (obj == null) {
                    if (key == null) {
                        mapIterator.remove();
                        z = true;
                    }
                } else if (obj.equals(key)) {
                    mapIterator.remove();
                    z = true;
                }
            }
        }
        return z;
    }

    /* JADX WARN: Code duplicated, block: B:25:0x0032 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:26:0x0038 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:27:0x002f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:28:0x0038 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:34:0x0006 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:36:0x0006 A[SYNTHETIC] */
    public boolean removeAll(Object obj, Object obj2) {
        Object key;
        MapIterator mapIterator = mapIterator();
        boolean z = false;
        while (mapIterator.hasNext()) {
            MultiKey multiKey = (MultiKey) mapIterator.next();
            if (multiKey.size() >= 2) {
                Object key2 = multiKey.getKey(0);
                if (obj == null) {
                    if (key2 == null) {
                        key = multiKey.getKey(1);
                        if (obj2 == null) {
                            if (key == null) {
                                mapIterator.remove();
                                z = true;
                            }
                        } else if (obj2.equals(key)) {
                            mapIterator.remove();
                            z = true;
                        }
                    }
                } else if (obj.equals(key2)) {
                    key = multiKey.getKey(1);
                    if (obj2 == null) {
                        if (key == null) {
                            mapIterator.remove();
                            z = true;
                        }
                    } else if (obj2.equals(key)) {
                        mapIterator.remove();
                        z = true;
                    }
                }
            }
        }
        return z;
    }

    /* JADX WARN: Code duplicated, block: B:31:0x0032 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:32:0x0038 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:33:0x0042 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:34:0x0048 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:35:0x003f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:36:0x0048 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:37:0x002f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:38:0x0038 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:44:0x0006 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:45:0x0006 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:47:0x0006 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:48:0x0006 A[SYNTHETIC] */
    public boolean removeAll(Object obj, Object obj2, Object obj3) {
        Object key;
        Object key2;
        MapIterator mapIterator = mapIterator();
        boolean z = false;
        while (mapIterator.hasNext()) {
            MultiKey multiKey = (MultiKey) mapIterator.next();
            if (multiKey.size() >= 3) {
                Object key3 = multiKey.getKey(0);
                if (obj == null) {
                    if (key3 == null) {
                        key = multiKey.getKey(1);
                        if (obj2 == null) {
                            if (key == null) {
                                key2 = multiKey.getKey(2);
                                if (obj3 == null) {
                                    if (key2 == null) {
                                        mapIterator.remove();
                                        z = true;
                                    }
                                } else if (obj3.equals(key2)) {
                                    mapIterator.remove();
                                    z = true;
                                }
                            }
                        } else if (obj2.equals(key)) {
                            key2 = multiKey.getKey(2);
                            if (obj3 == null) {
                                if (key2 == null) {
                                    mapIterator.remove();
                                    z = true;
                                }
                            } else if (obj3.equals(key2)) {
                                mapIterator.remove();
                                z = true;
                            }
                        }
                    }
                } else if (obj.equals(key3)) {
                    key = multiKey.getKey(1);
                    if (obj2 == null) {
                        if (key == null) {
                            key2 = multiKey.getKey(2);
                            if (obj3 == null) {
                                if (key2 == null) {
                                    mapIterator.remove();
                                    z = true;
                                }
                            } else if (obj3.equals(key2)) {
                                mapIterator.remove();
                                z = true;
                            }
                        }
                    } else if (obj2.equals(key)) {
                        key2 = multiKey.getKey(2);
                        if (obj3 == null) {
                            if (key2 == null) {
                                mapIterator.remove();
                                z = true;
                            }
                        } else if (obj3.equals(key2)) {
                            mapIterator.remove();
                            z = true;
                        }
                    }
                }
            }
        }
        return z;
    }

    /* JADX WARN: Code duplicated, block: B:37:0x0032 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:38:0x0038 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:39:0x0042 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:40:0x0048 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:41:0x0052 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:42:0x0058 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:43:0x004f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:44:0x0058 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:45:0x003f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:46:0x0048 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:47:0x002f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:48:0x0038 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:54:0x0006 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:55:0x0006 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:56:0x0006 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:58:0x0006 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:59:0x0006 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:60:0x0006 A[SYNTHETIC] */
    public boolean removeAll(Object obj, Object obj2, Object obj3, Object obj4) {
        Object key;
        Object key2;
        Object key3;
        MapIterator mapIterator = mapIterator();
        boolean z = false;
        while (mapIterator.hasNext()) {
            MultiKey multiKey = (MultiKey) mapIterator.next();
            if (multiKey.size() >= 4) {
                Object key4 = multiKey.getKey(0);
                if (obj == null) {
                    if (key4 == null) {
                        key = multiKey.getKey(1);
                        if (obj2 == null) {
                            if (key == null) {
                                key2 = multiKey.getKey(2);
                                if (obj3 == null) {
                                    if (key2 == null) {
                                        key3 = multiKey.getKey(3);
                                        if (obj4 == null) {
                                            if (key3 == null) {
                                                mapIterator.remove();
                                                z = true;
                                            }
                                        } else if (obj4.equals(key3)) {
                                            mapIterator.remove();
                                            z = true;
                                        }
                                    }
                                } else if (obj3.equals(key2)) {
                                    key3 = multiKey.getKey(3);
                                    if (obj4 == null) {
                                        if (key3 == null) {
                                            mapIterator.remove();
                                            z = true;
                                        }
                                    } else if (obj4.equals(key3)) {
                                        mapIterator.remove();
                                        z = true;
                                    }
                                }
                            }
                        } else if (obj2.equals(key)) {
                            key2 = multiKey.getKey(2);
                            if (obj3 == null) {
                                if (key2 == null) {
                                    key3 = multiKey.getKey(3);
                                    if (obj4 == null) {
                                        if (key3 == null) {
                                            mapIterator.remove();
                                            z = true;
                                        }
                                    } else if (obj4.equals(key3)) {
                                        mapIterator.remove();
                                        z = true;
                                    }
                                }
                            } else if (obj3.equals(key2)) {
                                key3 = multiKey.getKey(3);
                                if (obj4 == null) {
                                    if (key3 == null) {
                                        mapIterator.remove();
                                        z = true;
                                    }
                                } else if (obj4.equals(key3)) {
                                    mapIterator.remove();
                                    z = true;
                                }
                            }
                        }
                    }
                } else if (obj.equals(key4)) {
                    key = multiKey.getKey(1);
                    if (obj2 == null) {
                        if (key == null) {
                            key2 = multiKey.getKey(2);
                            if (obj3 == null) {
                                if (key2 == null) {
                                    key3 = multiKey.getKey(3);
                                    if (obj4 == null) {
                                        if (key3 == null) {
                                            mapIterator.remove();
                                            z = true;
                                        }
                                    } else if (obj4.equals(key3)) {
                                        mapIterator.remove();
                                        z = true;
                                    }
                                }
                            } else if (obj3.equals(key2)) {
                                key3 = multiKey.getKey(3);
                                if (obj4 == null) {
                                    if (key3 == null) {
                                        mapIterator.remove();
                                        z = true;
                                    }
                                } else if (obj4.equals(key3)) {
                                    mapIterator.remove();
                                    z = true;
                                }
                            }
                        }
                    } else if (obj2.equals(key)) {
                        key2 = multiKey.getKey(2);
                        if (obj3 == null) {
                            if (key2 == null) {
                                key3 = multiKey.getKey(3);
                                if (obj4 == null) {
                                    if (key3 == null) {
                                        mapIterator.remove();
                                        z = true;
                                    }
                                } else if (obj4.equals(key3)) {
                                    mapIterator.remove();
                                    z = true;
                                }
                            }
                        } else if (obj3.equals(key2)) {
                            key3 = multiKey.getKey(3);
                            if (obj4 == null) {
                                if (key3 == null) {
                                    mapIterator.remove();
                                    z = true;
                                }
                            } else if (obj4.equals(key3)) {
                                mapIterator.remove();
                                z = true;
                            }
                        }
                    }
                }
            }
        }
        return z;
    }

    protected void checkKey(Object obj) {
        if (obj == null) {
            throw new NullPointerException("Key must not be null");
        }
        if (!(obj instanceof MultiKey)) {
            throw new ClassCastException("Key must be a MultiKey");
        }
    }

    public Object clone() {
        return new MultiKeyMap((AbstractHashedMap) this.map.clone());
    }

    @Override // java.util.Map
    public Object put(Object obj, Object obj2) {
        checkKey(obj);
        return this.map.put(obj, obj2);
    }

    @Override // java.util.Map
    public void putAll(Map map) {
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            checkKey(it.next());
        }
        this.map.putAll(map);
    }

    @Override // org.apache.commons.collections.IterableMap
    public MapIterator mapIterator() {
        return this.map.mapIterator();
    }

    @Override // java.util.Map
    public int size() {
        return this.map.size();
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override // java.util.Map
    public boolean containsKey(Object obj) {
        return this.map.containsKey(obj);
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        return this.map.containsValue(obj);
    }

    @Override // java.util.Map
    public Object get(Object obj) {
        return this.map.get(obj);
    }

    @Override // java.util.Map
    public Object remove(Object obj) {
        return this.map.remove(obj);
    }

    @Override // java.util.Map
    public void clear() {
        this.map.clear();
    }

    @Override // java.util.Map
    public Set keySet() {
        return this.map.keySet();
    }

    @Override // java.util.Map
    public Collection values() {
        return this.map.values();
    }

    @Override // java.util.Map
    public Set entrySet() {
        return this.map.entrySet();
    }

    @Override // java.util.Map
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        return this.map.equals(obj);
    }

    @Override // java.util.Map
    public int hashCode() {
        return this.map.hashCode();
    }

    public String toString() {
        return this.map.toString();
    }
}
