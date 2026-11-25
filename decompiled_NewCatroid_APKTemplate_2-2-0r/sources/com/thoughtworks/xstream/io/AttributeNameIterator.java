package com.thoughtworks.xstream.io;

import java.util.Iterator;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/AttributeNameIterator.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class AttributeNameIterator implements Iterator {
    private final int count;
    private int current;
    private final HierarchicalStreamReader reader;

    public AttributeNameIterator(HierarchicalStreamReader hierarchicalStreamReader) {
        this.reader = hierarchicalStreamReader;
        this.count = hierarchicalStreamReader.getAttributeCount();
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.current < this.count;
    }

    @Override // java.util.Iterator
    public Object next() {
        HierarchicalStreamReader hierarchicalStreamReader = this.reader;
        int i = this.current;
        this.current = i + 1;
        return hierarchicalStreamReader.getAttributeName(i);
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
