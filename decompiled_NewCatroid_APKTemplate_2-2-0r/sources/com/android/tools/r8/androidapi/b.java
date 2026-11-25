package com.android.tools.r8.androidapi;

import com.android.tools.r8.graph.AbstractC0642e2;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.EnumC3891f;
import com.android.tools.r8.utils.O2;
import java.util.Iterator;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class b {
    private final h.a[] a = new h.a[EnumC3891f.M.d() + 1];

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a extends b {
        private final f b;
        private final h.a c;
        private final O2 d;

        public a(C0702m c0702m) {
            this.b = f.a(c0702m, this);
            this.c = a(c0702m.Y().B0());
            this.d = c0702m.a0();
        }

        @Override // com.android.tools.r8.androidapi.b
        public final boolean a() {
            return true;
        }

        @Override // com.android.tools.r8.androidapi.b
        public final void b() {
            this.b.a().forEach(new Consumer() { // from class: com.android.tools.r8.androidapi.b$a$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.b((AbstractC0744s2) obj);
                }
            });
        }

        @Override // com.android.tools.r8.androidapi.b
        public final h a(Iterable iterable, j jVar) {
            h hVarA = this.c;
            Iterator it = iterable.iterator();
            while (it.hasNext()) {
                hVarA = this.b.a((C0767v2) it.next(), hVarA, jVar);
            }
            return hVarA;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void b(AbstractC0744s2 abstractC0744s2) {
            this.d.warning(new AndroidApiUnknownReferenceDiagnostic(abstractC0744s2));
        }

        @Override // com.android.tools.r8.androidapi.b
        public final h a(AbstractC0744s2 abstractC0744s2, h hVar) {
            return this.b.a(abstractC0744s2, hVar);
        }

        @Override // com.android.tools.r8.androidapi.b
        public final h a(AbstractC0642e2 abstractC0642e2, j jVar) {
            return this.b.a(abstractC0642e2, jVar);
        }
    }

    public b() {
        for (EnumC3891f enumC3891f : EnumC3891f.values()) {
            if (enumC3891f != EnumC3891f.ANDROID_PLATFORM && enumC3891f != EnumC3891f.MASTER) {
                this.a[enumC3891f.d()] = new h.a(enumC3891f);
            }
        }
    }

    public final h.a a(EnumC3891f enumC3891f) {
        if (enumC3891f == EnumC3891f.ANDROID_PLATFORM) {
            return h.S();
        }
        return enumC3891f == EnumC3891f.MASTER ? h.n() : this.a[enumC3891f.d()];
    }

    public abstract h a(AbstractC0642e2 abstractC0642e2, j jVar);

    public abstract h a(AbstractC0744s2 abstractC0744s2, h hVar);

    public abstract h a(Iterable iterable, j jVar);

    public abstract boolean a();

    public void b() {
    }

    public h a(AbstractC0744s2 abstractC0744s2) {
        return a(abstractC0744s2, h.f());
    }

    public static b a(C0702m<?> c0702m) {
        if (c0702m.Y().a().a) {
            return new a(c0702m);
        }
        return new c();
    }

    public h a(A1 a1) {
        if (a1.B0() == EnumC3891f.ANDROID_PLATFORM) {
            return h.S();
        }
        if (a1.B0() == EnumC3891f.MASTER) {
            return h.n();
        }
        return new h.a(a1.B0());
    }
}
