package com.android.tools.r8.internal;

import java.util.ArrayList;

/* JADX INFO: renamed from: com.android.tools.r8.internal.qF, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2837qF extends AbstractC3116uF {
    public ArrayList b;
    public ArrayList c;
    public ArrayList d;
    public ArrayList e;
    public ArrayList f;
    public ArrayList g;

    @Override // com.android.tools.r8.internal.AbstractC3116uF
    public final void a() {
    }

    @Override // com.android.tools.r8.internal.AbstractC3116uF
    public final void a(String str) {
    }

    @Override // com.android.tools.r8.internal.AbstractC3116uF
    public final void b(String str) {
        if (this.b == null) {
            this.b = new ArrayList(5);
        }
        this.b.add(str);
    }

    @Override // com.android.tools.r8.internal.AbstractC3116uF
    public final void c(String str) {
        if (this.f == null) {
            this.f = new ArrayList(5);
        }
        this.f.add(str);
    }

    @Override // com.android.tools.r8.internal.AbstractC3116uF
    public final void a(int i, String str, String str2) {
        if (this.c == null) {
            this.c = new ArrayList(5);
        }
        this.c.add(new C3047tF());
    }

    @Override // com.android.tools.r8.internal.AbstractC3116uF
    public final void b(String str, int i, String... strArr) {
        if (this.e == null) {
            this.e = new ArrayList(5);
        }
        ArrayList arrayList = this.e;
        P10.a(strArr);
        arrayList.add(new C2907rF());
    }

    @Override // com.android.tools.r8.internal.AbstractC3116uF
    public final void a(String str, int i, String... strArr) {
        if (this.d == null) {
            this.d = new ArrayList(5);
        }
        ArrayList arrayList = this.d;
        P10.a(strArr);
        arrayList.add(new C2524mF());
    }

    @Override // com.android.tools.r8.internal.AbstractC3116uF
    public final void a(String str, String... strArr) {
        if (this.g == null) {
            this.g = new ArrayList(5);
        }
        ArrayList arrayList = this.g;
        P10.a(strArr);
        arrayList.add(new C2978sF());
    }
}
