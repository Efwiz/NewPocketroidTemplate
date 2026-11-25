package com.android.tools.r8.dex;

import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.C2732or;
import com.android.tools.r8.naming.AbstractC3649o0;
import com.android.tools.r8.utils.C3970u0;
import java.util.NoSuchElementException;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class c0 {
    static final /* synthetic */ boolean h = true;
    protected final String a;
    private final StringBuilder b = new StringBuilder();
    private boolean c = false;
    private int d = 0;
    private int e = 0;
    private final C2732or f;
    final /* synthetic */ d0 g;

    public c0(d0 d0Var, String str) {
        this.g = d0Var;
        this.a = str;
        this.f = a() ? new C2732or() : null;
    }

    protected abstract boolean a();

    protected abstract boolean a(char c);

    protected final boolean a(int i) {
        return i == this.a.length();
    }

    protected abstract boolean a(int i, int i2);

    protected abstract char b();

    protected abstract boolean b(int i, int i2);

    public final String c() {
        if (h || this.c) {
            return this.b.toString();
        }
        throw new AssertionError();
    }

    public final boolean d() {
        while (true) {
            if (a(this.e) || Character.isJavaIdentifierPart(this.a.charAt(this.e))) {
                if (!a(this.e)) {
                    boolean z = h;
                    if (!z && a() && !this.f.isEmpty()) {
                        throw new AssertionError();
                    }
                    if (!z && !Character.isJavaIdentifierPart(this.a.charAt(this.e))) {
                        throw new AssertionError();
                    }
                    int i = this.e;
                    this.e = i + 1;
                    while (!a(this.e)) {
                        char cCharAt = this.a.charAt(this.e);
                        if (Character.isJavaIdentifierPart(cCharAt)) {
                            if (a() && a(cCharAt) && b(i, this.e)) {
                                this.f.a(this.e);
                            }
                            this.e++;
                        } else {
                            if (cCharAt != b() || a(this.e + 1) || !Character.isJavaIdentifierPart(this.a.charAt(this.e + 1))) {
                                break;
                            }
                            if (a() && a(cCharAt) && b(i, this.e)) {
                                this.f.a(this.e);
                            }
                            this.e += 2;
                        }
                    }
                    if (a() && a(this.e) && b(i, this.e)) {
                        this.f.a(this.e);
                    }
                    boolean zA = b(i, this.e) && c(i, this.e);
                    if (!zA && a()) {
                        while (!this.f.isEmpty() && !zA) {
                            C2732or c2732or = this.f;
                            if (c2732or.isEmpty()) {
                                throw new NoSuchElementException();
                            }
                            int iH = c2732or.h(c2732or.size() - 1);
                            if (!h && !b(i, iH)) {
                                throw new AssertionError();
                            }
                            zA = a(i, iH);
                        }
                    }
                    if (a()) {
                        while (!this.f.isEmpty()) {
                            C2732or c2732or2 = this.f;
                            if (c2732or2.isEmpty()) {
                                throw new NoSuchElementException();
                            }
                            c2732or2.h(c2732or2.size() - 1);
                        }
                    }
                }
                if (a(this.e)) {
                    if (this.c) {
                        int i2 = this.d;
                        int length = this.a.length();
                        if (i2 < length) {
                            this.b.append((CharSequence) this.a, i2, length);
                        }
                    } else {
                        boolean z2 = h;
                        if (!z2 && this.d != 0) {
                            throw new AssertionError();
                        }
                        if (!z2 && !this.b.toString().isEmpty()) {
                            throw new AssertionError();
                        }
                    }
                    return this.c;
                }
            } else {
                this.e++;
            }
        }
    }

    protected final boolean c(int i, int i2) {
        String strSubstring = this.a.substring(i, i2);
        if (b() != '.') {
            strSubstring = strSubstring.replace(b(), '.');
        }
        C0759u2 c0759u2F = this.g.b.f(C3970u0.I(strSubstring));
        C0767v2 c0767v2E = c0759u2F != null ? this.g.b.e(c0759u2F) : null;
        if (c0767v2E == null) {
            return false;
        }
        d0 d0Var = this.g;
        AbstractC3649o0 abstractC3649o0 = d0Var.d;
        AbstractC1476Wj abstractC1476Wj = d0Var.c;
        abstractC1476Wj.getClass();
        C0759u2 c0759u2C = abstractC3649o0.c(abstractC1476Wj.a(AbstractC1476Wj.e(), c0767v2E));
        if (c0759u2F.equals(c0759u2C)) {
            return false;
        }
        String strB = C3970u0.b(c0759u2C.r0());
        int i3 = this.d;
        if (i3 < i) {
            this.b.append((CharSequence) this.a, i3, i);
        }
        if (b() != '.') {
            strB = strB.replace('.', b());
        }
        this.b.append(strB);
        this.d = i2;
        this.c = true;
        return true;
    }
}
