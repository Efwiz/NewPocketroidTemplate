package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.InterfaceC0414b;
import com.android.tools.r8.dex.code.InterfaceC0424d;
import com.android.tools.r8.graph.AbstractC0632d;
import com.android.tools.r8.graph.AbstractC0677i3;
import com.android.tools.r8.graph.C0592a;
import com.android.tools.r8.graph.C0607b;
import com.android.tools.r8.graph.C0625c;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0692k3;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.InterfaceC0694k5;
import com.android.tools.r8.shaking.C3733i;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.wZ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3296wZ extends com.android.tools.r8.graph.J5 {
    static final /* synthetic */ boolean f = true;
    final /* synthetic */ C3365xZ e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C3296wZ(C3365xZ c3365xZ, com.android.tools.r8.graph.o5 o5Var) {
        super(c3365xZ.a(), o5Var);
        this.e = c3365xZ;
    }

    @Override // com.android.tools.r8.graph.J5
    public final void a(C0684j2 c0684j2) {
    }

    @Override // com.android.tools.r8.graph.J5
    public final void b(C0684j2 c0684j2) {
    }

    @Override // com.android.tools.r8.graph.J5
    public final void b(C0767v2 c0767v2) {
    }

    @Override // com.android.tools.r8.graph.J5
    public final void c(C0767v2 c0767v2) {
    }

    @Override // com.android.tools.r8.graph.J5
    public final void d(C0767v2 c0767v2) {
    }

    @Override // com.android.tools.r8.graph.J5
    public final void e(C0684j2 c0684j2) {
    }

    @Override // com.android.tools.r8.graph.J5
    public final void f(C0767v2 c0767v2) {
    }

    @Override // com.android.tools.r8.graph.J5
    public final void g(C0684j2 c0684j2) {
    }

    @Override // com.android.tools.r8.graph.J5
    public final void h(C0684j2 c0684j2) {
    }

    private void a(com.android.tools.r8.graph.Y0 y0, boolean z, boolean z2, C1890e6 c1890e6) {
        com.android.tools.r8.graph.o5 o5VarA;
        AbstractC0677i3 abstractC0677i3C = ((C3733i) this.e.a.f()).c(y0);
        if (abstractC0677i3C.x()) {
            com.android.tools.r8.graph.m5 m5VarQ = abstractC0677i3C.q();
            com.android.tools.r8.graph.T0 t0E = m5VarQ.e();
            if (t0E.E0() != z || this.a.a(((com.android.tools.r8.graph.o5) d()).e()) || !(abstractC0677i3C instanceof C0692k3) || abstractC0677i3C.a((InterfaceC0694k5) d(), this.e.a).b() || ((C3733i) this.e.a.f()).c((com.android.tools.r8.graph.o5) d())) {
                this.e.f.remove(t0E);
                if (((C3733i) this.e.a.f()).a(this.e.a, m5VarQ)) {
                    synchronized (t0E) {
                        ConcurrentHashMap concurrentHashMap = this.e.d;
                        C0625c c0625c = C0625c.a;
                        concurrentHashMap.put(t0E, c0625c);
                        this.e.e.put(t0E, c0625c);
                    }
                    return;
                }
                return;
            }
            if (c1890e6 != null) {
                if (c1890e6.c() && !m5VarQ.e().X0().g().f().g()) {
                    Iterator it = c1890e6.b().iterator();
                    do {
                        if (!it.hasNext()) {
                            ((CN) this.e.c.computeIfAbsent(m5VarQ.e(), com.android.tools.r8.utils.Z1.a(new wZ$$ExternalSyntheticLambda0()))).add((com.android.tools.r8.graph.o5) d());
                            return;
                        }
                        C0684j2 c0684j2C = this.a.E().c(this.a.n(), (C0684j2) it.next());
                        o5VarA = c0684j2C.a(C0729q2.a(this.a.c(c0684j2C.B0())));
                        if (o5VarA == null) {
                            if (!f) {
                                throw new AssertionError();
                            }
                            break;
                        }
                    } while (o5VarA.e().E0());
                }
                if (c1890e6.d()) {
                    return;
                }
            }
            if (((C3733i) this.e.a.f()).a(this.e.a, m5VarQ)) {
                if (m5VarQ.getAccessFlags().o() != z) {
                    a(t0E);
                } else if (z2) {
                    C3365xZ c3365xZ = this.e;
                    a(t0E, c3365xZ.e, c3365xZ.d);
                } else {
                    C3365xZ c3365xZ2 = this.e;
                    a(t0E, c3365xZ2.d, c3365xZ2.e);
                }
            }
            if (this.e.f.contains(t0E) || (!z2 && this.e.g.contains(t0E))) {
                this.e.h.add((com.android.tools.r8.graph.o5) d());
            }
        }
    }

    @Override // com.android.tools.r8.graph.J5
    public final void c(com.android.tools.r8.graph.Y0 y0) {
        a(y0, false, true, null);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void e(com.android.tools.r8.graph.Y0 y0) {
        a(y0, true, false, null);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void g(com.android.tools.r8.graph.Y0 y0) {
        a(y0, true, true, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC0632d b(com.android.tools.r8.graph.T0 t0) {
        return new C0592a();
    }

    private void a(com.android.tools.r8.graph.T0 t0, ConcurrentHashMap concurrentHashMap, ConcurrentHashMap concurrentHashMap2) {
        synchronized (t0) {
            C0607b c0607b = C0607b.a;
            AbstractC0632d abstractC0632d = (AbstractC0632d) concurrentHashMap2.getOrDefault(t0, c0607b);
            abstractC0632d.getClass();
            if (abstractC0632d instanceof C0607b) {
                AbstractC0632d abstractC0632d2 = (AbstractC0632d) concurrentHashMap.computeIfAbsent(t0, new Function() { // from class: com.android.tools.r8.internal.wZ$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return C3296wZ.b((com.android.tools.r8.graph.T0) obj);
                    }
                });
                if (!f) {
                    abstractC0632d2.getClass();
                    if (!(abstractC0632d2 instanceof C0592a)) {
                        throw new AssertionError();
                    }
                }
                abstractC0632d2.a().a(t0.getReference(), (com.android.tools.r8.graph.o5) d());
            } else if (!(abstractC0632d instanceof C0625c)) {
                C0625c c0625c = C0625c.a;
                concurrentHashMap.put(t0, c0625c);
                concurrentHashMap2.put(t0, c0625c);
            } else {
                boolean z = f;
                if (!z) {
                    AbstractC0632d abstractC0632d3 = (AbstractC0632d) concurrentHashMap.getOrDefault(t0, c0607b);
                    abstractC0632d3.getClass();
                    if (!(abstractC0632d3 instanceof C0625c)) {
                        throw new AssertionError();
                    }
                }
                if (!z) {
                    AbstractC0632d abstractC0632d4 = (AbstractC0632d) concurrentHashMap2.getOrDefault(t0, c0607b);
                    abstractC0632d4.getClass();
                    if (!(abstractC0632d4 instanceof C0625c)) {
                        throw new AssertionError();
                    }
                }
            }
        }
    }

    private void a(com.android.tools.r8.graph.T0 t0) {
        synchronized (t0) {
            ConcurrentHashMap concurrentHashMap = this.e.d;
            C0625c c0625c = C0625c.a;
            concurrentHashMap.put(t0, c0625c);
            this.e.e.put(t0, c0625c);
        }
    }

    @Override // com.android.tools.r8.graph.J5
    public final void a(com.android.tools.r8.graph.Y0 y0) {
        a(y0, false, false, null);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void a(InterfaceC0414b interfaceC0414b) {
        a(interfaceC0414b.getField(), false, false, ((com.android.tools.r8.graph.o5) d()).e().a1().a(interfaceC0414b));
    }

    @Override // com.android.tools.r8.graph.J5
    public final void a(InterfaceC0424d interfaceC0424d) {
        a(interfaceC0424d.getField(), true, false, ((com.android.tools.r8.graph.o5) d()).e().a1().a(interfaceC0424d));
    }
}
