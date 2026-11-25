package com.hivemq.client.internal.util;

/* JADX INFO: loaded from: classes10.dex */
public class ByteArray {
    protected final byte[] array;

    protected int getStart() {
        return 0;
    }

    public ByteArray(byte[] bArr) {
        this.array = bArr;
    }

    public int length() {
        return getEnd() - getStart();
    }

    protected int getEnd() {
        return this.array.length;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ByteArray)) {
            return false;
        }
        ByteArray byteArray = (ByteArray) obj;
        return ByteArrayUtil.equals(this.array, getStart(), getEnd(), byteArray.array, byteArray.getStart(), byteArray.getEnd());
    }

    public final int hashCode() {
        return ByteArrayUtil.hashCode(this.array, getStart(), getEnd());
    }
}
