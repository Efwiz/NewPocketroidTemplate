package org.jetbrains.kotlin.com.fasterxml.aalto.in;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ByteBasedPName extends PName {
    protected final int mHash;

    public abstract boolean equals(int i, int i2);

    public abstract boolean equals(int[] iArr, int i);

    public abstract boolean hashEquals(int i, int i2, int i3);

    public abstract boolean hashEquals(int i, int[] iArr, int i2);

    protected ByteBasedPName(String str, String str2, String str3, int i) {
        super(str, str2, str3);
        this.mHash = i;
    }

    @Override // org.jetbrains.kotlin.com.fasterxml.aalto.in.PName
    public int hashCode() {
        return this.mHash;
    }
}
