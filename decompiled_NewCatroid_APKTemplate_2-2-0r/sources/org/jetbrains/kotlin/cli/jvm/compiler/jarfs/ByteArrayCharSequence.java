package org.jetbrains.kotlin.cli.jvm.compiler.jarfs;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ByteArrayCharSequence.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\r\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\f\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0096\u0002J\u0011\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0005H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0005H\u0016J\u0018\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u0005H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/jarfs/ByteArrayCharSequence;", "", "bytes", "", "start", "", "end", "([BII)V", "length", "getLength", "()I", Namer.EQUALS_METHOD_NAME, "", "other", "", "get", "", "index", "hashCode", "subSequence", "startIndex", "endIndex", "toString", "", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ByteArrayCharSequence implements CharSequence {
    private final byte[] bytes;
    private final int end;
    private final int start;

    public ByteArrayCharSequence(byte[] bytes, int i, int i2) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        this.bytes = bytes;
        this.start = i;
        this.end = i2;
    }

    @Override // java.lang.CharSequence
    public final /* bridge */ char charAt(int i) {
        return get(i);
    }

    @Override // java.lang.CharSequence
    public final /* bridge */ int length() {
        return getLength();
    }

    public /* synthetic */ ByteArrayCharSequence(byte[] bArr, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(bArr, (i3 & 2) != 0 ? 0 : i, (i3 & 4) != 0 ? bArr.length : i2);
    }

    public int hashCode() {
        throw new IllegalStateException("Do not try computing hashCode ByteArrayCharSequence".toString());
    }

    public boolean equals(Object other) {
        throw new IllegalStateException("Do not try comparing ByteArrayCharSequence".toString());
    }

    public int getLength() {
        return this.end - this.start;
    }

    public char get(int index) {
        return (char) this.bytes[index + this.start];
    }

    @Override // java.lang.CharSequence
    public CharSequence subSequence(int startIndex, int endIndex) {
        if (startIndex == 0 && endIndex == length()) {
            return this;
        }
        byte[] bArr = this.bytes;
        int i = this.start;
        return new ByteArrayCharSequence(bArr, startIndex + i, i + endIndex);
    }

    @Override // java.lang.CharSequence
    public String toString() {
        char[] cArr = new char[length()];
        int length = length();
        for (int i = 0; i < length; i++) {
            cArr[i] = (char) this.bytes[this.start + i];
        }
        return new String(cArr);
    }
}
