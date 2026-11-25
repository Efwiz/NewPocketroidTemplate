package org.apache.xerces.impl.xs.traversers;

/* JADX INFO: loaded from: classes13.dex */
class SmallContainer extends Container {
    String[] keys;

    SmallContainer(int i) {
        this.keys = new String[i];
        this.values = new OneAttr[i];
    }

    @Override // org.apache.xerces.impl.xs.traversers.Container
    OneAttr get(String str) {
        for (int i = 0; i < this.pos; i++) {
            if (this.keys[i].equals(str)) {
                return this.values[i];
            }
        }
        return null;
    }

    @Override // org.apache.xerces.impl.xs.traversers.Container
    void put(String str, OneAttr oneAttr) {
        this.keys[this.pos] = str;
        OneAttr[] oneAttrArr = this.values;
        int i = this.pos;
        this.pos = i + 1;
        oneAttrArr[i] = oneAttr;
    }
}
