package com.android.tools.r8.utils;

import java.util.function.BooleanSupplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class K {
    private boolean a;
    private boolean b = false;

    public K() {
    }

    public final void a(BooleanSupplier booleanSupplier) {
        if (d()) {
            c(booleanSupplier.getAsBoolean());
        }
    }

    public final void b(boolean z) {
        c(z || this.a);
    }

    public void c(boolean z) {
        this.b = true;
        this.a = z;
    }

    public boolean d() {
        return !a();
    }

    public boolean e() {
        return a();
    }

    public void f() {
        c(true);
    }

    public void g() {
        c(false);
    }

    public final Boolean b() {
        boolean zA = a();
        f();
        return Boolean.valueOf(zA);
    }

    public K(boolean z) {
        c(z);
    }

    public boolean a() {
        return this.a;
    }

    public final boolean c() {
        return this.b;
    }

    public final void a(boolean z) {
        c(z && this.a);
    }
}
