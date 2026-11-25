package com.android.tools.r8.utils;

import java.util.BitSet;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class I {
    static final /* synthetic */ boolean a = true;

    public static void a(BitSet bitSet, BitSet bitSet2) {
        if (a) {
            return;
        }
        BitSet bitSet3 = (BitSet) bitSet.clone();
        bitSet3.or(bitSet2);
        if (!bitSet2.equals(bitSet3)) {
            throw new AssertionError();
        }
    }
}
