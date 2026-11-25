package com.android.tools.r8.internal;

import java.util.HashSet;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Fw, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0998Fw {
    private final HashSet a = G1.b();
    private final HashSet b = G1.b();
    private AbstractC2450lF c = AbstractC2450lF.a();
    private AbstractC2450lF d = AbstractC2450lF.a();
    private AbstractC2450lF e = AbstractC2450lF.a();

    AbstractC0998Fw() {
    }

    public final AbstractC0998Fw a(C1027Gw c1027Gw) {
        this.a.clear();
        this.b.clear();
        this.a.addAll(c1027Gw.a);
        this.c = c1027Gw.b;
        this.d = c1027Gw.c;
        return e();
    }

    public final AbstractC2450lF b() {
        return this.d;
    }

    public final AbstractC2450lF c() {
        return this.c;
    }

    public final AbstractC2450lF d() {
        return this.e;
    }

    public abstract AbstractC0998Fw e();

    public final void b(boolean z) {
        this.c = AbstractC2450lF.a(z);
        e();
    }

    public final void c(boolean z) {
        this.e = AbstractC2450lF.a(z);
        e();
    }

    public final Set a() {
        if (this.a.isEmpty() && this.b.isEmpty()) {
            return G1.a();
        }
        G1 g1 = G1.PUBLIC;
        HashSet hashSet = new HashSet();
        if (this.a.isEmpty()) {
            hashSet.addAll(G1.a());
        } else {
            hashSet.addAll(this.a);
        }
        hashSet.removeAll(this.b);
        if (hashSet.isEmpty()) {
            throw new C2883qv("Empty access visibility pattern will never match a member");
        }
        return hashSet;
    }

    public final void a(G1 g1, boolean z) {
        (z ? this.a : this.b).add(g1);
        e();
    }

    public final void a(boolean z) {
        this.d = AbstractC2450lF.a(z);
        e();
    }
}
