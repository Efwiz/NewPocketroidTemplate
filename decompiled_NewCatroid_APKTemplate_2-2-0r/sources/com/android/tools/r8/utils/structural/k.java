package com.android.tools.r8.utils.structural;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public interface k<T> {
    static {
        boolean z = j.a;
    }

    /* JADX WARN: Incorrect types in method signature: <T::Lcom/android/tools/r8/utils/structural/k<TT;>;>(TT;Ljava/lang/Object;)Z */
    static boolean a(k kVar, Object obj) {
        if (!j.a && kVar == null) {
            throw new AssertionError();
        }
        if (kVar == obj) {
            return true;
        }
        if (obj == null || kVar.getClass() != obj.getClass()) {
            return false;
        }
        return kVar.isEqualTo((k) obj);
    }

    boolean isEqualTo(Object obj);
}
