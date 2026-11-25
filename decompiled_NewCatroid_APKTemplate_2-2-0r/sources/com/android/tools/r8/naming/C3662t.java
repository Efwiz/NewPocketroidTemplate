package com.android.tools.r8.naming;

import com.android.tools.r8.internal.AbstractC2465lT;
import com.android.tools.r8.utils.A1;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.naming.t, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3662t {
    private static final N0 e = new N0(0, 0);
    private com.android.tools.r8.naming.mappinginformation.b a = null;
    private final C3652q b = new C3652q();
    private C3652q c;
    private final A1 d;

    public C3662t(A1 a1) {
        this.d = a1;
    }

    public final void a(C3605b c3605b) throws Throwable {
        this.c = new C3652q();
        com.android.tools.r8.naming.mappinginformation.b bVarC = c3605b.c();
        if (bVarC == null) {
            throw new MappingComposeException("Composition of mapping files supported from map version 2.2.");
        }
        MapVersion mapVersionQ = bVarC.q();
        if (!AbstractC2465lT.a(mapVersionQ) || mapVersionQ.isUnknown()) {
            throw new MappingComposeException("Composition of mapping files supported from map version " + AbstractC2465lT.a.getName() + ".");
        }
        com.android.tools.r8.naming.mappinginformation.b bVar = this.a;
        if (bVar == null) {
            this.a = bVarC;
        } else {
            this.a = bVarC.b(bVar).b();
        }
        Iterator it = c3605b.b().values().iterator();
        while (it.hasNext()) {
            a(c3605b, (C3634k) it.next());
        }
        this.b.a(this.c, c3605b);
    }

    private void a(C3605b c3605b, C3634k c3634k) throws Throwable {
        String str = c3634k.a;
        String str2 = c3634k.b;
        C3650p c3650p = new C3650p(str, str2, this.b, this.c, this.d);
        C3650p c3650p2 = (C3650p) this.c.a.put(str2, c3650p);
        if (c3650p2 == null) {
            c3650p.a(c3605b, c3634k);
        } else {
            throw new MappingComposeException("Duplicate class mapping. Both '" + c3650p2.a() + "' and '" + str + "' maps to '" + str2 + "'.");
        }
    }

    public final String a() {
        ArrayList arrayList = new ArrayList(this.b.a.values());
        arrayList.sort(Comparator.comparing(new Function() { // from class: com.android.tools.r8.naming.t$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C3650p) obj).a();
            }
        }));
        final StringBuilder sb = new StringBuilder();
        this.b.f.forEach(new Consumer() { // from class: com.android.tools.r8.naming.t$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3662t.a(sb, (String) obj);
            }
        });
        if (this.a != null) {
            sb.append("# ").append(this.a.p()).append("\n");
        }
        com.android.tools.r8.utils.V vA = com.android.tools.r8.utils.V.a(new Consumer() { // from class: com.android.tools.r8.naming.t$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                sb.append((String) obj);
            }
        });
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((C3650p) it.next()).a(vA);
        }
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(StringBuilder sb, String str) {
        sb.append(str).append("\n");
    }
}
