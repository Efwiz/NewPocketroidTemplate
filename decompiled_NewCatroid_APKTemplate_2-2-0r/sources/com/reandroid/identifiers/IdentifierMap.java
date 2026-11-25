package com.reandroid.identifiers;

import com.reandroid.identifiers.Identifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: loaded from: classes11.dex */
class IdentifierMap<CHILD extends Identifier> extends Identifier implements Comparator<CHILD> {
    private final Map<Integer, CHILD> idMap;
    private boolean mCaseInsensitive;
    private final Object mLock;
    private int maxId;
    private final Map<String, CHILD> nameMap;

    public IdentifierMap(int i, String str) {
        super(i, str);
        this.mLock = new Object();
        this.idMap = new HashMap();
        this.nameMap = new HashMap();
        this.mCaseInsensitive = CASE_INSENSITIVE_FS;
    }

    public int getMaxId() {
        return this.maxId;
    }

    public List<CHILD> listDuplicates() {
        ArrayList arrayList = new ArrayList();
        HashMap map = new HashMap();
        for (CHILD child : getItems()) {
            String name2 = child.getName();
            if (isCaseInsensitive()) {
                name2 = name2.toLowerCase();
            }
            if (map.containsKey(name2)) {
                arrayList.add(child);
                arrayList.add(map.get(name2));
            } else {
                map.put(name2, child);
            }
        }
        arrayList.sort(this);
        return arrayList;
    }

    public boolean hasDuplicates() {
        HashSet hashSet = new HashSet();
        Iterator<CHILD> it = getItems().iterator();
        while (it.hasNext()) {
            String name2 = it.next().getName();
            if (hashSet.contains(name2)) {
                return true;
            }
            hashSet.add(name2);
        }
        return false;
    }

    public List<String> listNames() {
        ArrayList arrayList = new ArrayList(size());
        Iterator<CHILD> it = list().iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getName());
        }
        return arrayList;
    }

    public List<CHILD> list() {
        ArrayList arrayList = new ArrayList(getItems());
        arrayList.sort(this);
        return arrayList;
    }

    public Collection<CHILD> getItems() {
        Collection<CHILD> collectionValues;
        synchronized (this.mLock) {
            collectionValues = this.idMap.values();
        }
        return collectionValues;
    }

    public void clear() {
        synchronized (this.mLock) {
            this.idMap.clear();
            this.nameMap.clear();
        }
    }

    public CHILD getByTag(Object obj) {
        for (CHILD child : getItems()) {
            if (Objects.equals(obj, child.getTag())) {
                return child;
            }
        }
        return null;
    }

    public int size() {
        int size;
        synchronized (this.mLock) {
            size = this.idMap.size();
        }
        return size;
    }

    public CHILD get(String str) {
        CHILD child;
        synchronized (this.mLock) {
            child = this.nameMap.get(str);
        }
        return child;
    }

    public CHILD get(int i) {
        CHILD child;
        synchronized (this.mLock) {
            child = this.idMap.get(Integer.valueOf(i));
        }
        return child;
    }

    public void remove(CHILD child) {
        synchronized (this.mLock) {
            try {
                if (child == null) {
                    return;
                }
                this.idMap.remove(Integer.valueOf(child.getId()));
                this.nameMap.remove(child.getName());
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public CHILD add(CHILD child) {
        synchronized (this.mLock) {
            try {
                if (child == null) {
                    return null;
                }
                child.setParent(this);
                Integer numValueOf = Integer.valueOf(child.getId());
                CHILD child2 = this.idMap.get(numValueOf);
                if (child2 != null) {
                    if (child2.getName() == null) {
                        child2.setName(child.getName());
                        addNameMap(child2);
                    }
                    return child2;
                }
                this.idMap.put(numValueOf, child);
                if (numValueOf.intValue() > this.maxId) {
                    this.maxId = numValueOf.intValue();
                }
                addNameMap(child);
                return child;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void reloadNameMap() {
        synchronized (this.mLock) {
            this.nameMap.clear();
            Iterator<CHILD> it = this.idMap.values().iterator();
            while (it.hasNext()) {
                addNameMap(it.next());
            }
        }
    }

    private void addNameMap(CHILD child) {
        String name2 = child.getName();
        if (name2 != null && this.nameMap.get(name2) == null) {
            this.nameMap.put(name2, child);
        }
    }

    private boolean isCaseInsensitive() {
        return this.mCaseInsensitive;
    }

    void setCaseInsensitive(boolean z) {
        this.mCaseInsensitive = z;
    }

    @Override // java.util.Comparator
    public int compare(CHILD child, CHILD child2) {
        return child.compareTo(child2);
    }

    @Override // com.reandroid.identifiers.Identifier
    public String toString() {
        return super.toString() + " entries = " + size();
    }
}
