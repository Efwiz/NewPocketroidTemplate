package com.android.tools.r8.internal;

import com.android.tools.r8.utils.EnumC3891f;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.eB, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1895eB implements InterfaceC2089gZ {
    private EnumC3891f a;
    private String c;
    private String d;
    private List f;
    private String b = "j$/";
    private boolean e = true;

    C1895eB() {
    }

    public final void a(EnumC3891f enumC3891f) {
        this.a = enumC3891f;
    }

    public final void b(String str) {
        this.d = str;
    }

    public final void c(String str) {
        this.b = str.replace('.', '/');
    }

    public final void a(String str) {
        this.c = str;
    }

    public final void b(boolean z) {
        this.e = z;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2089gZ
    public final InterfaceC2089gZ a(boolean z) {
        this.e = z;
        return this;
    }

    public final void a(ArrayList arrayList) {
        this.f = arrayList;
    }

    public final C1982fB a() {
        return new C1982fB(this.a, this.b, this.c, this.d, this.e, this.f);
    }
}
