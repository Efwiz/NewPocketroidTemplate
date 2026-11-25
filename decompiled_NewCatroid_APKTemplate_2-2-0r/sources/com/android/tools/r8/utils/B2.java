package com.android.tools.r8.utils;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.DexFilePerClassFileConsumer;
import com.android.tools.r8.DexIndexedConsumer;
import com.android.tools.r8.ProgramConsumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class B2 {
    static final /* synthetic */ boolean a = true;

    public static com.android.tools.r8.dex.U.a a(ProgramConsumer programConsumer) {
        if (programConsumer instanceof ClassFileConsumer) {
            return com.android.tools.r8.dex.U.a.CF;
        }
        if (a || (programConsumer instanceof DexIndexedConsumer) || (programConsumer instanceof DexFilePerClassFileConsumer)) {
            return com.android.tools.r8.dex.U.a.DEX;
        }
        throw new AssertionError();
    }
}
