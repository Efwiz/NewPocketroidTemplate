package com.android.tools.r8.graph;

/* JADX INFO: renamed from: com.android.tools.r8.graph.t3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract /* synthetic */ class AbstractC0751t3 {
    static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[((C0791w3.a[]) C0791w3.a.values().clone()).length];
        a = iArr;
        try {
            iArr[C0791w3.a.INVALID_APPLICATION_COUNT.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            a[C0791w3.a.INVALID_INTERFACE_COUNT.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            a[C0791w3.a.INVALID_SUPER_TYPE.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            a[C0791w3.a.INVALID_TYPE_VARIABLE_UNDEFINED.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
    }
}
