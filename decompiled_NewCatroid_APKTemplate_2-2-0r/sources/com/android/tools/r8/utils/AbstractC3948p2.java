package com.android.tools.r8.utils;

/* JADX INFO: renamed from: com.android.tools.r8.utils.p2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class AbstractC3948p2 {
    static final /* synthetic */ boolean a = true;

    public static Boolean a(C3943o2 c3943o2, String str) {
        if (c3943o2.a().equals(str)) {
            c3943o2.b();
            return Boolean.TRUE;
        }
        if (!a && !str.startsWith("--")) {
            throw new AssertionError();
        }
        if (!c3943o2.a().equals("--no" + str.substring(2))) {
            return null;
        }
        c3943o2.b();
        return Boolean.FALSE;
    }
}
