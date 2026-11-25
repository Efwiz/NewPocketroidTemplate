package org.apache.commons.lang3.builder;

/* JADX INFO: loaded from: classes13.dex */
final class IDKey {

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    private final int f464id;
    private final Object value;

    IDKey(Object obj) {
        this.f464id = System.identityHashCode(obj);
        this.value = obj;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof IDKey)) {
            return false;
        }
        IDKey iDKey = (IDKey) obj;
        return this.f464id == iDKey.f464id && this.value == iDKey.value;
    }

    public int hashCode() {
        return this.f464id;
    }
}
