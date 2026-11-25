package com.hivemq.client.internal.util.collections;

import okhttp3.HttpUrl;

/* JADX INFO: loaded from: classes10.dex */
class ImmutableEmptyIntList implements ImmutableIntList {
    static final ImmutableEmptyIntList INSTANCE = new ImmutableEmptyIntList();

    public int hashCode() {
        return 1;
    }

    @Override // com.hivemq.client.internal.util.collections.ImmutableIntList
    public int size() {
        return 0;
    }

    private ImmutableEmptyIntList() {
    }

    @Override // com.hivemq.client.internal.util.collections.ImmutableIntList
    public int get(int i) {
        throw new IndexOutOfBoundsException("Empty int list");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ImmutableIntList) && ((ImmutableIntList) obj).size() == 0;
    }

    public String toString() {
        return HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
    }
}
