package org.apache.xerces.impl.xs.traversers;

import java.util.Hashtable;

/* JADX INFO: loaded from: classes13.dex */
class LargeContainer extends Container {
    Hashtable items;

    LargeContainer(int i) {
        this.items = new Hashtable((i * 2) + 1);
        this.values = new OneAttr[i];
    }

    @Override // org.apache.xerces.impl.xs.traversers.Container
    OneAttr get(String str) {
        return (OneAttr) this.items.get(str);
    }

    @Override // org.apache.xerces.impl.xs.traversers.Container
    void put(String str, OneAttr oneAttr) {
        this.items.put(str, oneAttr);
        OneAttr[] oneAttrArr = this.values;
        int i = this.pos;
        this.pos = i + 1;
        oneAttrArr[i] = oneAttr;
    }
}
