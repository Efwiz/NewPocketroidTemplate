package com.android.tools.r8.naming;

import com.android.tools.r8.position.Position;
import com.android.tools.r8.utils.O2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.naming.i, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3629i extends AbstractC3625g {
    private final String a;
    private final String b;
    private final Position c;
    private final O2 d;
    private final HashMap e;
    private final HashMap f;
    private final HashMap g;

    private C3629i(String str, String str2, Position position, O2 o2) {
        this.e = new HashMap();
        this.f = new HashMap();
        this.g = new HashMap();
        this.a = str2;
        this.b = str;
        this.c = position;
        this.d = o2;
    }

    @Override // com.android.tools.r8.naming.AbstractC3625g
    public final Q a(Q.c cVar) {
        return null;
    }

    @Override // com.android.tools.r8.naming.AbstractC3625g
    public final C3634k.b a(N0 n0, Q.b bVar, N0 n1, String str) {
        return null;
    }

    @Override // com.android.tools.r8.naming.AbstractC3625g
    public final void a(Consumer consumer, com.android.tools.r8.naming.mappinginformation.e eVar) {
    }

    @Override // com.android.tools.r8.naming.AbstractC3625g
    public final AbstractC3625g a(Q q) {
        if (q.i()) {
            Q.b bVarB = q.b().b();
            if (bVarB.e()) {
                ((List) this.e.computeIfAbsent(bVarB, new Function() { // from class: com.android.tools.r8.naming.i$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return C3629i.a((Q.b) obj);
                    }
                })).add(q);
            } else if (this.f.put(bVarB, q) != null) {
                this.d.error(w0.a(bVarB.toString(), this.a, q.f()));
            }
        } else {
            Q.a aVarA = q.b().a();
            if (!aVarA.e() && this.g.put(aVarA, q) != null) {
                this.d.error(w0.a(aVarA.toString(), this.a, q.f()));
            }
        }
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List a(Q.b bVar) {
        return new ArrayList(2);
    }

    public final C3631j a() {
        return new C3631j(this.b, this.a, this.c, this.e, this.f, this.g);
    }
}
