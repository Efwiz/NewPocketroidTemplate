package com.android.tools.r8.internal;

import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.bO, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public enum EnumC1690bO implements InterfaceC2880qs {
    /* JADX INFO: Fake field, exist only in values array */
    EF5("CLASS"),
    /* JADX INFO: Fake field, exist only in values array */
    EF13(Namer.CLASS_KIND_INTERFACE),
    /* JADX INFO: Fake field, exist only in values array */
    EF21("ENUM_CLASS"),
    /* JADX INFO: Fake field, exist only in values array */
    EF29("ENUM_ENTRY"),
    /* JADX INFO: Fake field, exist only in values array */
    EF37("ANNOTATION_CLASS"),
    /* JADX INFO: Fake field, exist only in values array */
    EF45(Namer.CLASS_KIND_OBJECT),
    /* JADX INFO: Fake field, exist only in values array */
    EF53("COMPANION_OBJECT");

    private final int a;

    EnumC1690bO(String str) {
        this.a = i;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2880qs
    public final int a() {
        return this.a;
    }
}
