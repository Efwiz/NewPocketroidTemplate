package com.android.tools.r8.graph;

import androidx.exifinterface.media.ExifInterface;
import com.android.tools.r8.naming.AbstractC3649o0;
import com.android.tools.r8.utils.C3970u0;
import com.beust.jcommander.Parameters;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.slf4j.Marker;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class A3 implements G3 {
    static final /* synthetic */ boolean d = true;
    private final AbstractC3649o0 a;
    private final Predicate b;
    private final StringBuilder c = new StringBuilder();

    public A3(AbstractC3649o0 abstractC3649o0, Predicate<C0767v2> predicate) {
        this.a = abstractC3649o0;
        this.b = predicate;
    }

    @Override // com.android.tools.r8.graph.G3
    public final C0767v2 a(C0767v2 c0767v2) {
        return c0767v2;
    }

    public C0706m3.e b(C0706m3.e eVar) {
        a(eVar, false);
        return eVar;
    }

    @Override // com.android.tools.r8.graph.G3
    public final List c(List list) {
        if (list.isEmpty()) {
            return list;
        }
        this.c.append("<");
        list.forEach(new Consumer() { // from class: com.android.tools.r8.graph.A3$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0706m3.f) obj);
            }
        });
        this.c.append(">");
        return list;
    }

    @Override // com.android.tools.r8.graph.G3
    public final List d(List list) {
        this.c.append("(");
        list.forEach(new Consumer() { // from class: com.android.tools.r8.graph.A3$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0706m3.i) obj);
            }
        });
        this.c.append(Constant.AFTER_QUTO);
        return list;
    }

    @Override // com.android.tools.r8.graph.G3
    public final List e(List list) {
        list.forEach(new Consumer() { // from class: com.android.tools.r8.graph.A3$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b((C0706m3.c) obj);
            }
        });
        return list;
    }

    public String toString() {
        return this.c.toString();
    }

    public C0706m3.b a(C0706m3.b bVar) {
        return bVar.a(this);
    }

    @Override // com.android.tools.r8.graph.G3
    public final List b(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            C0706m3.i iVar = (C0706m3.i) it.next();
            this.c.append("^");
            a(iVar);
        }
        return list;
    }

    public C0706m3.g a(C0706m3.g gVar) {
        return gVar.a(this);
    }

    @Override // com.android.tools.r8.graph.G3
    public final C0706m3.h a(C0706m3.h hVar) {
        if (hVar.a()) {
            this.c.append(ExifInterface.GPS_MEASUREMENT_INTERRUPTED);
        } else {
            a(hVar.a);
        }
        return hVar;
    }

    public final C0706m3.c b(C0706m3.c cVar) {
        a((C0706m3.e) cVar, false);
        return cVar;
    }

    public final C0706m3.e c(C0706m3.e eVar) {
        this.c.append(":");
        a(eVar, false);
        return eVar;
    }

    @Override // com.android.tools.r8.graph.G3
    public final C0706m3.e a(C0706m3.e eVar) {
        this.c.append(":");
        if (eVar.a()) {
            return eVar;
        }
        a(eVar, false);
        return eVar;
    }

    @Override // com.android.tools.r8.graph.G3
    public final List a(List list) {
        list.forEach(new Consumer() { // from class: com.android.tools.r8.graph.A3$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.c((C0706m3.e) obj);
            }
        });
        return list;
    }

    @Override // com.android.tools.r8.graph.G3
    public final C0706m3.c a(C0706m3.c cVar) {
        a((C0706m3.e) cVar, false);
        return cVar;
    }

    @Override // com.android.tools.r8.graph.G3
    public final C0706m3.i a(C0706m3.i iVar) {
        iVar.getClass();
        if (iVar instanceof C0712n3) {
            this.c.append(iVar.c().a.b1());
        } else {
            a(iVar.d(), false);
        }
        return iVar;
    }

    @Override // com.android.tools.r8.graph.G3
    public final C0706m3.c a(C0706m3.c cVar, C0706m3.c cVar2) {
        a((C0706m3.e) cVar, true);
        return cVar;
    }

    @Override // com.android.tools.r8.graph.G3
    public final List a(C0767v2 c0767v2, C0767v2 c0767v3, List list) {
        if (list.isEmpty()) {
            return list;
        }
        this.c.append("<");
        Iterator it = list.iterator();
        while (it.hasNext()) {
            C0706m3.e eVar = (C0706m3.e) it.next();
            C0706m3.k kVarJ = eVar.j();
            if (kVarJ != C0706m3.k.b) {
                if (!d && kVarJ == C0706m3.k.a) {
                    throw new AssertionError();
                }
                this.c.append(kVarJ == C0706m3.k.d ? Marker.ANY_NON_NULL_MARKER : Parameters.DEFAULT_OPTION_PREFIXES);
            }
            a((C0706m3.i) eVar);
        }
        this.c.append(">");
        return list;
    }

    public final C0706m3.f a(C0706m3.f fVar) {
        this.c.append(fVar.a);
        return fVar.a(this);
    }

    private void a(C0706m3.e eVar, boolean z) {
        if (eVar.n()) {
            this.c.append("*");
            return;
        }
        if (eVar.o()) {
            this.c.append(ExifInterface.GPS_DIRECTION_TRUE).append(eVar.i().b).append(";");
            return;
        }
        if (eVar.l()) {
            this.c.append("[");
            eVar.g().a(this);
            return;
        }
        boolean z2 = d;
        if (!z2 && !eVar.m()) {
            throw new AssertionError();
        }
        C0706m3.c cVarH = eVar.h();
        if (cVarH.a()) {
            return;
        }
        C0706m3.c cVar = cVarH.d;
        if (cVar != null) {
            a((C0706m3.e) cVar, true);
        }
        String string = this.a.c(cVarH.b).toString();
        C0706m3.c cVar2 = cVarH.d;
        if (cVar2 == null) {
            this.c.append("L").append(C3970u0.f(string));
        } else {
            C0767v2 c0767v2 = cVar2.b;
            String strA = C3970u0.a(this.a.c(c0767v2).toString(), string);
            if (strA == null && this.b.test(cVarH.b)) {
                if (!z2 && !string.equals(cVarH.b.b1())) {
                    throw new AssertionError();
                }
                strA = C3970u0.a(c0767v2.b1(), string);
            }
            if (strA == null) {
                return;
            } else {
                this.c.append(".").append(strA);
            }
        }
        a(null, null, cVarH.c);
        if (z) {
            return;
        }
        this.c.append(";");
    }
}
