package org.apache.xerces.impl.xs.traversers;

/* JADX INFO: loaded from: classes13.dex */
abstract class Container {
    static final int THRESHOLD = 5;
    int pos = 0;
    OneAttr[] values;

    Container() {
    }

    static Container getContainer(int i) {
        return i > 5 ? new LargeContainer(i) : new SmallContainer(i);
    }

    abstract OneAttr get(String str);

    abstract void put(String str, OneAttr oneAttr);
}
