package org.jetbrains.kotlin.com.google.common.hash;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: classes3.dex */
public interface Hasher extends PrimitiveSink {
    HashCode hash();

    @Deprecated
    int hashCode();

    @Override // org.jetbrains.kotlin.com.google.common.hash.PrimitiveSink
    Hasher putBoolean(boolean z);

    @Override // org.jetbrains.kotlin.com.google.common.hash.PrimitiveSink
    Hasher putByte(byte b);

    @Override // org.jetbrains.kotlin.com.google.common.hash.PrimitiveSink
    Hasher putBytes(ByteBuffer byteBuffer);

    @Override // org.jetbrains.kotlin.com.google.common.hash.PrimitiveSink
    Hasher putBytes(byte[] bArr);

    @Override // org.jetbrains.kotlin.com.google.common.hash.PrimitiveSink
    Hasher putBytes(byte[] bArr, int i, int i2);

    @Override // org.jetbrains.kotlin.com.google.common.hash.PrimitiveSink
    Hasher putChar(char c);

    @Override // org.jetbrains.kotlin.com.google.common.hash.PrimitiveSink
    Hasher putDouble(double d);

    @Override // org.jetbrains.kotlin.com.google.common.hash.PrimitiveSink
    Hasher putFloat(float f);

    @Override // org.jetbrains.kotlin.com.google.common.hash.PrimitiveSink
    Hasher putInt(int i);

    @Override // org.jetbrains.kotlin.com.google.common.hash.PrimitiveSink
    Hasher putLong(long j);

    <T> Hasher putObject(T t, Funnel<? super T> funnel);

    @Override // org.jetbrains.kotlin.com.google.common.hash.PrimitiveSink
    Hasher putShort(short s);

    @Override // org.jetbrains.kotlin.com.google.common.hash.PrimitiveSink
    Hasher putString(CharSequence charSequence, Charset charset);

    @Override // org.jetbrains.kotlin.com.google.common.hash.PrimitiveSink
    Hasher putUnencodedChars(CharSequence charSequence);
}
