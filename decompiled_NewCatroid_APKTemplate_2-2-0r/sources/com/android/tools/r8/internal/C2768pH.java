package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.TreeSet;

/* JADX INFO: renamed from: com.android.tools.r8.internal.pH, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2768pH extends AbstractC2839qH<C2768pH> implements Comparable<C2768pH> {
    static final /* synthetic */ boolean g = true;
    private int b;
    private final TreeSet c;
    private final TreeSet d;
    private final TreeSet e;
    private final TreeSet f;

    public C2768pH(com.android.tools.r8.graph.o5 o5Var) {
        super(o5Var);
        this.b = 0;
        this.c = new TreeSet();
        this.d = new TreeSet();
        this.e = new TreeSet();
        this.f = new TreeSet();
    }

    private void h(C2768pH c2768pH) {
        synchronized (this.e) {
            this.e.remove(c2768pH);
        }
        synchronized (c2768pH.f) {
            c2768pH.f.remove(this);
        }
    }

    public boolean b(C2768pH c2768pH) {
        return this.c.contains(c2768pH);
    }

    public final void c() {
        boolean z = g;
        if (!z && !this.d.isEmpty()) {
            throw new AssertionError();
        }
        if (!z && !this.e.isEmpty()) {
            throw new AssertionError();
        }
        Iterator it = this.c.iterator();
        while (it.hasNext()) {
            boolean zRemove = ((C2768pH) it.next()).d.remove(this);
            if (!g && !zRemove) {
                throw new AssertionError();
            }
        }
        Iterator it2 = this.f.iterator();
        while (it2.hasNext()) {
            boolean zRemove2 = ((C2768pH) it2.next()).e.remove(this);
            if (!g && !zRemove2) {
                throw new AssertionError();
            }
        }
    }

    @Override // java.lang.Comparable
    public final int compareTo(C2768pH c2768pH) {
        return b().getReference().compareTo(c2768pH.b().getReference());
    }

    public final void d() {
        boolean z = g;
        if (!z && !this.c.isEmpty()) {
            throw new AssertionError();
        }
        if (!z && !this.f.isEmpty()) {
            throw new AssertionError();
        }
        Iterator it = this.d.iterator();
        while (it.hasNext()) {
            boolean zRemove = ((C2768pH) it.next()).c.remove(this);
            if (!g && !zRemove) {
                throw new AssertionError();
            }
        }
        Iterator it2 = this.e.iterator();
        while (it2.hasNext()) {
            boolean zRemove2 = ((C2768pH) it2.next()).f.remove(this);
            if (!g && !zRemove2) {
                throw new AssertionError();
            }
        }
    }

    public final TreeSet e() {
        return this.c;
    }

    public final void f(C2768pH c2768pH) {
        boolean zRemove = this.d.remove(c2768pH);
        boolean z = g;
        if (!z && !zRemove) {
            throw new AssertionError();
        }
        boolean zRemove2 = c2768pH.c.remove(this);
        if (!z && !zRemove2) {
            throw new AssertionError();
        }
        if (!z && this.e.contains(c2768pH)) {
            throw new AssertionError();
        }
    }

    public final void g(C2768pH c2768pH) {
        boolean zRemove = this.e.remove(c2768pH);
        boolean z = g;
        if (!z && !zRemove) {
            throw new AssertionError();
        }
        boolean zRemove2 = c2768pH.f.remove(this);
        if (!z && !zRemove2) {
            throw new AssertionError();
        }
        if (!z && this.d.contains(c2768pH)) {
            throw new AssertionError();
        }
    }

    public boolean i() {
        return this.c.isEmpty() && this.f.isEmpty();
    }

    public final boolean j() {
        return this.d.isEmpty() && this.e.isEmpty();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("MethodNode for: ");
        sb.append(b().w());
        sb.append(" (");
        sb.append(this.c.size());
        sb.append(" callees, ");
        sb.append(this.d.size());
        sb.append(" callers, invoke count ");
        sb.append(this.b);
        sb.append(").");
        sb.append(System.lineSeparator());
        if (this.c.size() > 0) {
            sb.append("Callees:");
            sb.append(System.lineSeparator());
            for (C2768pH c2768pH : this.c) {
                sb.append("  ");
                sb.append(c2768pH.b().w());
                sb.append(System.lineSeparator());
            }
        }
        if (this.d.size() > 0) {
            sb.append("Callers:");
            sb.append(System.lineSeparator());
            for (C2768pH c2768pH2 : this.d) {
                sb.append("  ");
                sb.append(c2768pH2.b().w());
                sb.append(System.lineSeparator());
            }
        }
        return sb.toString();
    }

    @Override // com.android.tools.r8.internal.AbstractC2839qH
    public final void a(AbstractC2839qH abstractC2839qH) {
        boolean zAdd;
        C2768pH c2768pH = (C2768pH) abstractC2839qH;
        if (c2768pH != this) {
            synchronized (this.d) {
                if (this.d.contains(c2768pH)) {
                    return;
                }
                synchronized (this.e) {
                    zAdd = this.e.add(c2768pH);
                }
                if (zAdd) {
                    synchronized (c2768pH.f) {
                        c2768pH.f.add(this);
                    }
                }
            }
        }
    }

    public final boolean e(C2768pH c2768pH) {
        return this.f.contains(c2768pH);
    }

    public final int f() {
        return this.b;
    }

    public final TreeSet g() {
        return this.e;
    }

    public final TreeSet h() {
        return this.f;
    }

    public final boolean c(C2768pH c2768pH) {
        return this.d.contains(c2768pH);
    }

    public final boolean d(C2768pH c2768pH) {
        return this.e.contains(c2768pH);
    }

    public void a(C2768pH c2768pH) {
        a(c2768pH, false);
    }

    @Override // com.android.tools.r8.internal.AbstractC2839qH
    public final void a(C2768pH c2768pH, boolean z) {
        boolean zAdd;
        if (c2768pH != this && !z) {
            synchronized (this.d) {
                zAdd = this.d.add(c2768pH);
                this.b++;
            }
            if (zAdd) {
                synchronized (c2768pH.c) {
                    c2768pH.c.add(this);
                }
                h(c2768pH);
                return;
            }
            return;
        }
        synchronized (this.d) {
            this.b++;
        }
    }
}
