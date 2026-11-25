package com.android.tools.r8.internal;

import com.android.tools.r8.utils.EnumC3891f;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Xk, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1506Xk implements InterfaceC2089gZ {
    static final /* synthetic */ boolean g = true;
    private EnumC3891f a;
    private String b;
    private String c;
    private String d;
    private Boolean e;
    private List f;

    C1506Xk() {
    }

    public final C1506Xk a(EnumC3891f enumC3891f) {
        this.a = enumC3891f;
        return this;
    }

    public final C1506Xk b(String str) {
        this.d = str;
        return this;
    }

    public final C1506Xk c(String str) {
        this.b = str.replace('.', '/');
        return this;
    }

    public final C1506Xk a(String str) {
        this.c = str;
        return this;
    }

    public final C1506Xk b(boolean z) {
        this.e = Boolean.valueOf(z);
        return this;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2089gZ
    public final InterfaceC2089gZ a(boolean z) {
        this.e = Boolean.valueOf(z);
        return this;
    }

    public final C1506Xk a(List list) {
        this.f = list;
        return this;
    }

    public final C1533Yk a() {
        boolean z = g;
        if (!z && this.b == null) {
            throw new AssertionError();
        }
        if (z || this.e != null) {
            return new C1533Yk(this.a, this.b, this.c, this.d, this.e.booleanValue(), this.f);
        }
        throw new AssertionError();
    }
}
