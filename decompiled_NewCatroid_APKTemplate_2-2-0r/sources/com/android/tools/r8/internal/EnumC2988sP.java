package com.android.tools.r8.internal;

import com.reandroid.apkeditor.Util;

/* JADX INFO: renamed from: com.android.tools.r8.internal.sP, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public enum EnumC2988sP implements InterfaceC2880qs {
    /* JADX INFO: Fake field, exist only in values array */
    EF5("INTERNAL"),
    /* JADX INFO: Fake field, exist only in values array */
    EF13("PRIVATE"),
    /* JADX INFO: Fake field, exist only in values array */
    EF21(Util.EDIT_TYPE_PROTECTED),
    /* JADX INFO: Fake field, exist only in values array */
    EF29("PUBLIC"),
    /* JADX INFO: Fake field, exist only in values array */
    EF37("PRIVATE_TO_THIS"),
    /* JADX INFO: Fake field, exist only in values array */
    EF45("LOCAL");

    private final int a;

    EnumC2988sP(String str) {
        this.a = i;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2880qs
    public final int a() {
        return this.a;
    }
}
