package org.jetbrains.kotlin.js.inline;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FunctionReader.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\r\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\f\n\u0002\b\u0005\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0002\u0010\u0006J\u0011\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0004H\u0096\u0002J\u0018\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0004H\u0016R\u0014\u0010\u0007\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/ShallowSubSequence;", "", "underlying", "start", "", "end", "(Ljava/lang/CharSequence;II)V", "length", "getLength", "()I", "get", "", "index", "subSequence", "startIndex", "endIndex", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ShallowSubSequence implements CharSequence {
    private final int length;
    private final int start;
    private final CharSequence underlying;

    public ShallowSubSequence(CharSequence underlying, int i, int i2) {
        Intrinsics.checkNotNullParameter(underlying, "underlying");
        this.underlying = underlying;
        this.start = i;
        this.length = i2 - i;
    }

    @Override // java.lang.CharSequence
    public final /* bridge */ char charAt(int i) {
        return get(i);
    }

    @Override // java.lang.CharSequence
    public final /* bridge */ int length() {
        return getLength();
    }

    public int getLength() {
        return this.length;
    }

    public char get(int index) {
        if (index < 0 || index >= length()) {
            throw new IndexOutOfBoundsException(index + " is out of bounds 0.." + length());
        }
        return this.underlying.charAt(index + this.start);
    }

    @Override // java.lang.CharSequence
    public CharSequence subSequence(int startIndex, int endIndex) {
        CharSequence charSequence = this.underlying;
        int i = this.start;
        return new ShallowSubSequence(charSequence, startIndex + i, i + endIndex);
    }
}
