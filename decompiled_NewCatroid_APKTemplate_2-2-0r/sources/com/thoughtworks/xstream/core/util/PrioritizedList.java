package com.thoughtworks.xstream.core.util;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/util/PrioritizedList.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class PrioritizedList {
    private final Set set = new TreeSet();
    private int lowestPriority = Integer.MAX_VALUE;
    private int lastId = 0;

    public void add(Object obj, int i) {
        if (this.lowestPriority > i) {
            this.lowestPriority = i;
        }
        Set set = this.set;
        int i2 = this.lastId + 1;
        this.lastId = i2;
        set.add(new PrioritizedItem(obj, i, i2));
    }

    public Iterator iterator() {
        return new PrioritizedItemIterator(this.set.iterator());
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/util/PrioritizedList$PrioritizedItem.class
      classes11.dex
     */
    private static class PrioritizedItem implements Comparable {

        /* JADX INFO: renamed from: id, reason: collision with root package name */
        final int f265id;
        final int priority;
        final Object value;

        public PrioritizedItem(Object obj, int i, int i2) {
            this.value = obj;
            this.priority = i;
            this.f265id = i2;
        }

        @Override // java.lang.Comparable
        public int compareTo(Object obj) {
            PrioritizedItem prioritizedItem = (PrioritizedItem) obj;
            int i = this.priority;
            int i2 = prioritizedItem.priority;
            return i != i2 ? i2 - i : prioritizedItem.f265id - this.f265id;
        }

        public boolean equals(Object obj) {
            return this.f265id == ((PrioritizedItem) obj).f265id;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/util/PrioritizedList$PrioritizedItemIterator.class
      classes11.dex
     */
    private static class PrioritizedItemIterator implements Iterator {
        private Iterator iterator;

        public PrioritizedItemIterator(Iterator it) {
            this.iterator = it;
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override // java.util.Iterator
        public Object next() {
            return ((PrioritizedItem) this.iterator.next()).value;
        }
    }
}
