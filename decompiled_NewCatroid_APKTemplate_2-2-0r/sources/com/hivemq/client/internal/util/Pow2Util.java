package com.hivemq.client.internal.util;

/* JADX INFO: loaded from: classes10.dex */
public final class Pow2Util {
    public static int roundToPowerOf2Bits(int i) {
        return 32 - Integer.numberOfLeadingZeros(i - 1);
    }

    private Pow2Util() {
    }
}
