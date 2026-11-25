package com.hivemq.client.internal.util.collections;

import com.hivemq.client.internal.util.Checks;

/* JADX INFO: loaded from: classes10.dex */
class ImmutableIntElement implements ImmutableIntList {
    private final int element;

    @Override // com.hivemq.client.internal.util.collections.ImmutableIntList
    public int size() {
        return 1;
    }

    ImmutableIntElement(int i) {
        this.element = i;
    }

    @Override // com.hivemq.client.internal.util.collections.ImmutableIntList
    public int get(int i) {
        Checks.index(i, 1);
        return this.element;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ImmutableIntList)) {
            return false;
        }
        ImmutableIntList immutableIntList = (ImmutableIntList) obj;
        return immutableIntList.size() == 1 && this.element == immutableIntList.get(0);
    }

    public int hashCode() {
        return this.element + 31;
    }

    public String toString() {
        return "[" + this.element + "]";
    }
}
