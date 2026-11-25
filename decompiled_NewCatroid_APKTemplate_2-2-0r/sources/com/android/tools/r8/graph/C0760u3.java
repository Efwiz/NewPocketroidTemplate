package com.android.tools.r8.graph;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.graph.u3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0760u3 {
    static final /* synthetic */ boolean d = true;
    private final C0702m a;
    private final C0729q2 b;
    private final int c;

    private C0760u3(C0702m c0702m, int i, C0729q2 c0729q2) {
        this.a = c0702m;
        this.c = i;
        this.b = c0729q2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C0791w3.a a(C0706m3.g gVar, C0738r3.a aVar) {
        C0791w3.a aVarA;
        C0791w3.a aVarA2;
        C0791w3.a aVarA3;
        this.b.r.getClass();
        if (!gVar.a.isEmpty()) {
            aVar = aVar.a(com.android.tools.r8.utils.V1.a((Collection) gVar.a, (Function) new C0761u3$$ExternalSyntheticLambda0()));
        }
        Iterator<C0706m3.f> it = gVar.a.iterator();
        do {
            if (!it.hasNext()) {
                aVarA = C0791w3.a.VALID;
                break;
            }
            C0706m3.f next = it.next();
            aVarA = a(next.b, aVar);
            if (!aVarA.b()) {
                Iterator<C0706m3.e> it2 = next.c.iterator();
                do {
                    if (!it2.hasNext()) {
                        aVarA = C0791w3.a.VALID;
                        break;
                    }
                    aVarA = a(it2.next(), aVar);
                } while (aVarA == C0791w3.a.VALID);
            }
        } while (!aVarA.b());
        if (aVarA.b()) {
            return aVarA;
        }
        Iterator<C0706m3.i> it3 = gVar.b.iterator();
        do {
            if (!it3.hasNext()) {
                aVarA2 = C0791w3.a.VALID;
                break;
            }
            aVarA2 = a(it3.next(), aVar);
        } while (!aVarA2.b());
        if (aVarA2.b()) {
            return aVarA2;
        }
        Iterator it4 = gVar.d.iterator();
        do {
            if (!it4.hasNext()) {
                aVarA3 = C0791w3.a.VALID;
                break;
            }
            aVarA3 = a((C0706m3.i) it4.next(), aVar);
        } while (!aVarA3.b());
        if (aVarA3.b()) {
            return aVarA3;
        }
        C0706m3.h hVarE = gVar.e();
        if (!hVarE.a()) {
            aVarA3 = a(hVarE.b(), aVar);
            if (aVarA3.b()) {
            }
        }
        return aVarA3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C0791w3.a a(C0706m3.i iVar, C0738r3.a aVar) {
        iVar.getClass();
        if (iVar instanceof C0712n3) {
            return C0791w3.a.VALID;
        }
        C0706m3.e eVarD = iVar.d();
        if (!eVarD.a() && !eVarD.n()) {
            if (eVarD.o()) {
                if (aVar.b(eVarD.i().b)) {
                    return C0791w3.a.VALID;
                }
                if (!d && !AbstractC0768v3.a(this.c)) {
                    throw new AssertionError();
                }
                return C0791w3.a.INVALID_TYPE_VARIABLE_UNDEFINED;
            }
            if (eVarD.l()) {
                return a(eVarD.g().b, aVar);
            }
            if (!d && !eVarD.m()) {
                throw new AssertionError();
            }
            C0706m3.c cVarH = eVarD.h();
            return a(cVarH.c, cVarH.b, aVar);
        }
        return C0791w3.a.VALID;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C0791w3.a a(List list, C0767v2 c0767v2, C0738r3.a aVar) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            C0791w3.a aVarA = a((C0706m3.e) it.next(), aVar);
            if (aVarA.b()) {
                if (d || AbstractC0768v3.a(this.c)) {
                    return aVarA;
                }
                throw new AssertionError();
            }
        }
        AbstractC0727q0 abstractC0727q0D = this.a.f().d(c0767v2);
        if (abstractC0727q0D == null) {
            return C0791w3.a.VALID;
        }
        if (list.isEmpty()) {
            return C0791w3.a.VALID;
        }
        if (list.size() != abstractC0727q0D.r.a.size()) {
            if (!d && !AbstractC0768v3.a(this.c)) {
                throw new AssertionError();
            }
            return C0791w3.a.INVALID_APPLICATION_COUNT;
        }
        return C0791w3.a.VALID;
    }
}
