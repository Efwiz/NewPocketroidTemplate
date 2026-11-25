package com.koushikdutta.async.util;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

/* JADX INFO: loaded from: classes10.dex */
public class HashList<T> {
    Hashtable<String, TaggedList<T>> internal = new Hashtable<>();

    public Set<String> keySet() {
        return this.internal.keySet();
    }

    public synchronized <V> V tag(String str) {
        TaggedList<T> taggedList = this.internal.get(str);
        if (taggedList == null) {
            return null;
        }
        return (V) taggedList.tag();
    }

    public synchronized <V> void tag(String str, V v) {
        TaggedList<T> taggedList = this.internal.get(str);
        if (taggedList == null) {
            taggedList = new TaggedList<>();
            this.internal.put(str, taggedList);
        }
        taggedList.tag(v);
    }

    public synchronized ArrayList<T> remove(String str) {
        return this.internal.remove(str);
    }

    public synchronized int size() {
        return this.internal.size();
    }

    public synchronized ArrayList<T> get(String str) {
        return this.internal.get(str);
    }

    public synchronized boolean contains(String str) {
        ArrayList<T> arrayList;
        arrayList = get(str);
        return arrayList != null && arrayList.size() > 0;
    }

    public synchronized void add(String str, T t) {
        ArrayList<T> taggedList = get(str);
        if (taggedList == null) {
            taggedList = new TaggedList<>();
            this.internal.put(str, (TaggedList<T>) taggedList);
        }
        taggedList.add(t);
    }

    public synchronized T pop(String str) {
        TaggedList<T> taggedList = this.internal.get(str);
        if (taggedList == null) {
            return null;
        }
        if (taggedList.size() == 0) {
            return null;
        }
        return taggedList.remove(taggedList.size() - 1);
    }

    public synchronized boolean removeItem(String str, T t) {
        TaggedList<T> taggedList = this.internal.get(str);
        if (taggedList == null) {
            return false;
        }
        taggedList.remove(t);
        return taggedList.size() == 0;
    }
}
