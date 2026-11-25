package com.hivemq.client.internal.util.collections;

import com.hivemq.client.internal.util.Checks;
import java.util.Arrays;

/* JADX INFO: loaded from: classes10.dex */
class ImmutableIntArray implements ImmutableIntList {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final int[] array;

    ImmutableIntArray(int... iArr) {
        this.array = iArr;
    }

    @Override // com.hivemq.client.internal.util.collections.ImmutableIntList
    public int size() {
        return this.array.length;
    }

    @Override // com.hivemq.client.internal.util.collections.ImmutableIntList
    public int get(int i) {
        int[] iArr = this.array;
        return iArr[Checks.index(i, iArr.length)];
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ImmutableIntList)) {
            return false;
        }
        if (obj instanceof ImmutableIntArray) {
            return Arrays.equals(this.array, ((ImmutableIntArray) obj).array);
        }
        ImmutableIntList immutableIntList = (ImmutableIntList) obj;
        if (this.array.length != immutableIntList.size()) {
            return false;
        }
        int i = 0;
        while (true) {
            int[] iArr = this.array;
            if (i >= iArr.length) {
                return true;
            }
            if (iArr[i] != immutableIntList.get(i)) {
                return false;
            }
            i++;
        }
    }

    public int hashCode() {
        return Arrays.hashCode(this.array);
    }

    public String toString() {
        return Arrays.toString(this.array);
    }
}
