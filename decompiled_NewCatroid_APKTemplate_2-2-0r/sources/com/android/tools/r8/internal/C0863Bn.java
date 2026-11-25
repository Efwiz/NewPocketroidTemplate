package com.android.tools.r8.internal;

import java.util.ListIterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Bn, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0863Bn implements Iterable {
    private int a;
    private AbstractC3097u b;
    private AbstractC3097u c;
    AbstractC3097u[] d;

    public final AbstractC3097u a() {
        return this.b;
    }

    public final AbstractC3097u b() {
        return this.c;
    }

    @Override // java.lang.Iterable
    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
    public final ListIterator iterator() {
        return new C0837An(this);
    }

    public final void d() {
        for (AbstractC3097u abstractC3097u = this.b; abstractC3097u != null; abstractC3097u = abstractC3097u.e) {
            if (abstractC3097u instanceof C3338xA) {
                ((C3338xA) abstractC3097u).e();
            }
        }
    }

    public final AbstractC3097u[] e() {
        AbstractC3097u abstractC3097u = this.b;
        AbstractC3097u[] abstractC3097uArr = new AbstractC3097u[this.a];
        int i = 0;
        while (abstractC3097u != null) {
            abstractC3097uArr[i] = abstractC3097u;
            abstractC3097u.f = i;
            abstractC3097u = abstractC3097u.e;
            i++;
        }
        return abstractC3097uArr;
    }

    public final AbstractC3097u j(int i) {
        if (i < 0 || i >= this.a) {
            throw new IndexOutOfBoundsException();
        }
        if (this.d == null) {
            this.d = e();
        }
        return this.d[i];
    }

    public final int size() {
        return this.a;
    }

    public final void a(AbstractC1899eF abstractC1899eF) {
        for (AbstractC3097u abstractC3097u = this.b; abstractC3097u != null; abstractC3097u = abstractC3097u.e) {
            abstractC3097u.a(abstractC1899eF);
        }
    }

    public final int b(AbstractC3097u abstractC3097u) {
        if (this.d == null) {
            this.d = e();
        }
        return abstractC3097u.f;
    }

    public final void c(AbstractC3097u abstractC3097u, AbstractC3097u abstractC3097u2) {
        AbstractC3097u abstractC3097u3 = abstractC3097u.e;
        abstractC3097u2.e = abstractC3097u3;
        if (abstractC3097u3 != null) {
            abstractC3097u3.d = abstractC3097u2;
        } else {
            this.c = abstractC3097u2;
        }
        AbstractC3097u abstractC3097u4 = abstractC3097u.d;
        abstractC3097u2.d = abstractC3097u4;
        if (abstractC3097u4 != null) {
            abstractC3097u4.e = abstractC3097u2;
        } else {
            this.b = abstractC3097u2;
        }
        AbstractC3097u[] abstractC3097uArr = this.d;
        if (abstractC3097uArr != null) {
            int i = abstractC3097u.f;
            abstractC3097uArr[i] = abstractC3097u2;
            abstractC3097u2.f = i;
        } else {
            abstractC3097u2.f = 0;
        }
        abstractC3097u.f = -1;
        abstractC3097u.d = null;
        abstractC3097u.e = null;
    }

    public final void a(AbstractC3097u abstractC3097u) {
        this.a++;
        AbstractC3097u abstractC3097u2 = this.c;
        if (abstractC3097u2 == null) {
            this.b = abstractC3097u;
            this.c = abstractC3097u;
        } else {
            abstractC3097u2.e = abstractC3097u;
            abstractC3097u.d = abstractC3097u2;
        }
        this.c = abstractC3097u;
        this.d = null;
        abstractC3097u.f = 0;
    }

    public final void b(AbstractC3097u abstractC3097u, AbstractC3097u abstractC3097u2) {
        this.a++;
        AbstractC3097u abstractC3097u3 = abstractC3097u.d;
        if (abstractC3097u3 == null) {
            this.b = abstractC3097u2;
        } else {
            abstractC3097u3.e = abstractC3097u2;
        }
        abstractC3097u.d = abstractC3097u2;
        abstractC3097u2.e = abstractC3097u;
        abstractC3097u2.d = abstractC3097u3;
        this.d = null;
        abstractC3097u2.f = 0;
    }

    public final void a(AbstractC3097u abstractC3097u, AbstractC3097u abstractC3097u2) {
        this.a++;
        AbstractC3097u abstractC3097u3 = abstractC3097u.e;
        if (abstractC3097u3 == null) {
            this.c = abstractC3097u2;
        } else {
            abstractC3097u3.d = abstractC3097u2;
        }
        abstractC3097u.e = abstractC3097u2;
        abstractC3097u2.e = abstractC3097u3;
        abstractC3097u2.d = abstractC3097u;
        this.d = null;
        abstractC3097u2.f = 0;
    }

    public final void c(AbstractC3097u abstractC3097u) {
        this.a--;
        AbstractC3097u abstractC3097u2 = abstractC3097u.e;
        AbstractC3097u abstractC3097u3 = abstractC3097u.d;
        if (abstractC3097u2 == null) {
            if (abstractC3097u3 == null) {
                this.b = null;
                this.c = null;
            } else {
                abstractC3097u3.e = null;
                this.c = abstractC3097u3;
            }
        } else if (abstractC3097u3 == null) {
            this.b = abstractC3097u2;
            abstractC3097u2.d = null;
        } else {
            abstractC3097u3.e = abstractC3097u2;
            abstractC3097u2.d = abstractC3097u3;
        }
        this.d = null;
        abstractC3097u.f = -1;
        abstractC3097u.d = null;
        abstractC3097u.e = null;
    }
}
