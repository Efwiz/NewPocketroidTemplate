package org.apache.commons.compress.archivers.dump;

/* JADX INFO: loaded from: classes13.dex */
final class Dirent {
    private final int ino;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f440name;
    private final int parentIno;
    private final int type;

    Dirent(int i, int i2, int i3, String str) {
        this.ino = i;
        this.parentIno = i2;
        this.type = i3;
        this.f440name = str;
    }

    int getIno() {
        return this.ino;
    }

    String getName() {
        return this.f440name;
    }

    int getParentIno() {
        return this.parentIno;
    }

    int getType() {
        return this.type;
    }

    public String toString() {
        return String.format("[%d]: %s", Integer.valueOf(this.ino), this.f440name);
    }
}
