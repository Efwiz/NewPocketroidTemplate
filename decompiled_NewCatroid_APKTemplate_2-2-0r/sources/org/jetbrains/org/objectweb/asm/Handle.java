package org.jetbrains.org.objectweb.asm;

/* JADX INFO: loaded from: classes6.dex */
public final class Handle {
    private final String descriptor;
    private final boolean isInterface;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f1005name;
    private final String owner;
    private final int tag;

    public Handle(int i, String str, String str2, String str3, boolean z) {
        this.tag = i;
        this.owner = str;
        this.f1005name = str2;
        this.descriptor = str3;
        this.isInterface = z;
    }

    public int getTag() {
        return this.tag;
    }

    public String getOwner() {
        return this.owner;
    }

    public String getName() {
        return this.f1005name;
    }

    public String getDesc() {
        return this.descriptor;
    }

    public boolean isInterface() {
        return this.isInterface;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Handle)) {
            return false;
        }
        Handle handle = (Handle) obj;
        return this.tag == handle.tag && this.isInterface == handle.isInterface && this.owner.equals(handle.owner) && this.f1005name.equals(handle.f1005name) && this.descriptor.equals(handle.descriptor);
    }

    public int hashCode() {
        return this.tag + (this.isInterface ? 64 : 0) + (this.owner.hashCode() * this.f1005name.hashCode() * this.descriptor.hashCode());
    }

    public String toString() {
        return this.owner + '.' + this.f1005name + this.descriptor + " (" + this.tag + (this.isInterface ? " itf" : "") + ')';
    }
}
