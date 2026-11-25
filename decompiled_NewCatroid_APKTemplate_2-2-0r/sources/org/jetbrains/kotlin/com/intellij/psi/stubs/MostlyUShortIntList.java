package org.jetbrains.kotlin.com.intellij.psi.stubs;

import java.util.function.IntUnaryOperator;
import org.jetbrains.kotlin.com.intellij.util.containers.UnsignedShortArrayList;

/* JADX INFO: loaded from: classes3.dex */
final class MostlyUShortIntList implements IntUnaryOperator {
    private final UnsignedShortArrayList myList;
    private StrippedIntOpenHashMap myMap;

    MostlyUShortIntList(int i) {
        this.myList = new UnsignedShortArrayList(i);
    }

    void add(int i) {
        if (i < 0 || i >= 65535) {
            initMap().put(this.myList.size(), i);
            i = 65535;
        }
        this.myList.add(i);
    }

    void set(int i, int i2) {
        if (i2 < 0 || i2 >= 65535) {
            initMap().put(i, i2);
            i2 = 65535;
        }
        this.myList.setQuick(i, i2);
    }

    private StrippedIntOpenHashMap initMap() {
        if (this.myMap == null) {
            this.myMap = new StrippedIntOpenHashMap();
        }
        return this.myMap;
    }

    @Override // java.util.function.IntUnaryOperator
    public int applyAsInt(int i) {
        return get(i);
    }

    public int get(int i) {
        int quick = this.myList.getQuick(i);
        return quick == 65535 ? this.myMap.get(i, 0) : quick;
    }

    int size() {
        return this.myList.size();
    }

    void trimToSize() {
        this.myList.trimToSize();
    }
}
