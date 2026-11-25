package org.jetbrains.kotlin.com.intellij.openapi.util;

import java.lang.ref.SoftReference;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class ThreadLocalCachedByteArray {
    private final ThreadLocal<SoftReference<byte[]>> myThreadLocal = new ThreadLocal<>();

    public byte[] getBuffer(int i) {
        byte[] bArr = (byte[]) org.jetbrains.kotlin.com.intellij.reference.SoftReference.dereference(this.myThreadLocal.get());
        if (bArr != null && bArr.length > i) {
            return bArr;
        }
        byte[] bArrNewByteArray = ArrayUtil.newByteArray(i);
        this.myThreadLocal.set(new SoftReference<>(bArrNewByteArray));
        return bArrNewByteArray;
    }
}
