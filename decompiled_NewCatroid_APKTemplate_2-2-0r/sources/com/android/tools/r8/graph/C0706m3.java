package com.android.tools.r8.graph;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.CM;
import com.android.tools.r8.internal.InterfaceC3205vM;
import com.android.tools.r8.naming.AbstractC3649o0;
import com.android.tools.r8.origin.Origin;
import java.lang.reflect.GenericSignatureFormatError;
import java.util.List;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.graph.m3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0706m3 {
    private static final List a = AbstractC1639am.i();
    private static final List b;
    private static final List c;
    private static final List d;
    private static final c e;

    /* JADX INFO: renamed from: com.android.tools.r8.graph.m3$c */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class c extends e {
        static final /* synthetic */ boolean e = true;
        final C0767v2 b;
        final List<e> c;
        final c d;

        public c(C0767v2 c0767v2) {
            this(c0767v2, C0706m3.b, 0);
        }

        @Override // com.android.tools.r8.graph.C0706m3.e
        /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
        public final c a(k kVar) {
            boolean z = e;
            if (!z && kVar == k.a) {
                throw new AssertionError();
            }
            if (z || b()) {
                return new c(this.b, this.c, this.d, kVar);
            }
            throw new AssertionError();
        }

        @Override // com.android.tools.r8.graph.C0706m3.i
        public final a f() {
            return new a(this, k.a);
        }

        @Override // com.android.tools.r8.graph.C0706m3.e
        public final c h() {
            return this;
        }

        @Override // com.android.tools.r8.graph.C0706m3.e
        public final boolean m() {
            return true;
        }

        public final C0767v2 q() {
            return this.b;
        }

        public List<e> r() {
            return this.c;
        }

        public c(C0767v2 c0767v2, List list) {
            this(c0767v2, list, null, k.a);
        }

        public final c a(G3 g3) {
            if (a()) {
                return this;
            }
            C0767v2 c0767v2A = g3.a(this.b);
            if (c0767v2A == null) {
                return null;
            }
            List<e> listA = g3.a(this.b, c0767v2A, this.c);
            c cVar = this.d;
            c cVarA = cVar != null ? g3.a(cVar, this) : null;
            return (this.b == c0767v2A && this.c == listA && this.d == cVarA) ? this : new c(c0767v2A, listA, cVarA, j());
        }

        public c(C0767v2 c0767v2, List list, int i) {
            this(c0767v2, list, null, k.a);
        }

        private c(C0767v2 c0767v2, List list, c cVar, k kVar) {
            super(kVar);
            boolean z = e;
            if (!z && c0767v2 == null) {
                throw new AssertionError();
            }
            if (!z && list == null) {
                throw new AssertionError();
            }
            this.b = c0767v2;
            this.c = list;
            this.d = cVar;
            if (!z && c0767v2 == C0722p1.S5 && kVar != k.a) {
                throw new AssertionError();
            }
            if (!z && !list.stream().allMatch(new Predicate() { // from class: com.android.tools.r8.graph.m3$c$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((C0706m3.e) obj).k();
                }
            })) {
                throw new AssertionError();
            }
            if (!z && !list.stream().allMatch(new Predicate() { // from class: com.android.tools.r8.graph.m3$c$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((C0706m3.e) obj).b();
                }
            })) {
                throw new AssertionError();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX INFO: renamed from: com.android.tools.r8.graph.m3$d */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public interface d<T extends O0> {
        default boolean a() {
            return !b();
        }

        boolean b();
    }

    /* JADX INFO: renamed from: com.android.tools.r8.graph.m3$e */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static abstract class e extends i implements d<T0> {
        private final k a;

        private e(k kVar) {
            this.a = kVar;
        }

        public static e p() {
            return C0706m3.e;
        }

        public abstract e a(k kVar);

        @Override // com.android.tools.r8.graph.C0706m3.d
        public boolean b() {
            return this != C0706m3.e;
        }

        @Override // com.android.tools.r8.graph.C0706m3.i
        public final e d() {
            return this;
        }

        @Override // com.android.tools.r8.graph.C0706m3.i
        public final boolean e() {
            return true;
        }

        public a g() {
            return null;
        }

        public c h() {
            return null;
        }

        public j i() {
            return null;
        }

        public k j() {
            return this.a;
        }

        public final boolean k() {
            return this.a != k.a;
        }

        public boolean l() {
            return false;
        }

        public boolean m() {
            return false;
        }

        public boolean n() {
            return false;
        }

        public boolean o() {
            return false;
        }

        public String toString() {
            AbstractC3649o0 abstractC3649o0A = AbstractC3649o0.a();
            InterfaceC3205vM interfaceC3205vMB = CM.b();
            if (a()) {
                return null;
            }
            A3 a3 = new A3(abstractC3649o0A, interfaceC3205vMB);
            a3.a((i) this);
            return a3.toString();
        }
    }

    /* JADX INFO: renamed from: com.android.tools.r8.graph.m3$f */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class f {
        static final /* synthetic */ boolean d = true;
        final String a;
        final e b;
        final List<e> c;

        f(String str, e eVar, List list) {
            this.a = str;
            this.b = eVar;
            this.c = list;
            boolean z = d;
            if (!z && eVar == null) {
                throw new AssertionError();
            }
            if (!z && list == null) {
                throw new AssertionError();
            }
        }

        public final String a() {
            return this.a;
        }

        public final f a(G3 g3) {
            e eVarA = g3.a(this.b);
            List<e> listA = g3.a(this.c);
            if (this.b == eVarA && this.c == listA) {
                return this;
            }
            String str = this.a;
            if (eVarA == null) {
                eVarA = e.p();
            }
            return new f(str, eVarA, listA);
        }
    }

    /* JADX INFO: renamed from: com.android.tools.r8.graph.m3$h */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class h {
        static final h b = new h(null);
        final i a;

        h(i iVar) {
            this.a = iVar;
        }

        public boolean a() {
            return this.a == null;
        }

        public i b() {
            return this.a;
        }
    }

    /* JADX INFO: renamed from: com.android.tools.r8.graph.m3$i */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static abstract class i {
        public C0712n3 c() {
            return null;
        }

        public e d() {
            return null;
        }

        public boolean e() {
            return false;
        }

        public a f() {
            return null;
        }
    }

    /* JADX INFO: renamed from: com.android.tools.r8.graph.m3$j */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class j extends e {
        static final /* synthetic */ boolean c = true;
        final String b;

        private j(String str, k kVar) {
            super(kVar);
            if (!c && str == null) {
                throw new AssertionError();
            }
            this.b = str;
        }

        @Override // com.android.tools.r8.graph.C0706m3.e
        public final e a(k kVar) {
            if (c || kVar != k.a) {
                return new j(this.b, kVar);
            }
            throw new AssertionError();
        }

        @Override // com.android.tools.r8.graph.C0706m3.i
        public final a f() {
            return new a(this, k.a);
        }

        @Override // com.android.tools.r8.graph.C0706m3.e
        public final j i() {
            return this;
        }

        @Override // com.android.tools.r8.graph.C0706m3.e
        public final boolean o() {
            return true;
        }
    }

    /* JADX WARN: $VALUES field not found */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX INFO: renamed from: com.android.tools.r8.graph.m3$k */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static final class k {
        public static final k a = new k("NOT_AN_ARGUMENT", 0);
        public static final k b = new k("NONE", 1);
        public static final k c = new k("NEGATIVE", 2);
        public static final k d = new k("POSITIVE", 3);

        private k(String str, int i) {
            super(str, i);
        }
    }

    static {
        AbstractC1639am abstractC1639amI = AbstractC1639am.i();
        b = abstractC1639amI;
        c = AbstractC1639am.i();
        d = AbstractC1639am.i();
        e = new c(C0722p1.S5, abstractC1639amI);
    }

    public static List a() {
        return b;
    }

    public static e b(String str, String str2, Origin origin, C0722p1 c0722p1, DiagnosticsHandler diagnosticsHandler) {
        if (str2 == null || str2.isEmpty()) {
            return e;
        }
        try {
            return new C0724p3(c0722p1).b(str2);
        } catch (GenericSignatureFormatError e2) {
            diagnosticsHandler.warning(C0811y3.b(str2, str, origin, e2));
            return e;
        }
    }

    public static g c(String str, String str2, Origin origin, C0722p1 c0722p1, DiagnosticsHandler diagnosticsHandler) {
        if (str2 == null || str2.isEmpty()) {
            return g.e;
        }
        try {
            return new C0724p3(c0722p1).c(str2);
        } catch (GenericSignatureFormatError e2) {
            diagnosticsHandler.warning(C0811y3.c(str2, str, origin, e2));
            return g.e;
        }
    }

    /* JADX INFO: renamed from: com.android.tools.r8.graph.m3$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a extends e {
        static final /* synthetic */ boolean c = true;
        final i b;

        private a(i iVar, k kVar) {
            super(kVar);
            if (!c && iVar == null) {
                throw new AssertionError();
            }
            this.b = iVar;
        }

        @Override // com.android.tools.r8.graph.C0706m3.e
        public final e a(k kVar) {
            if (c || kVar != k.a) {
                return new a(this.b, kVar);
            }
            throw new AssertionError();
        }

        @Override // com.android.tools.r8.graph.C0706m3.i
        public final a f() {
            return new a(this, k.a);
        }

        @Override // com.android.tools.r8.graph.C0706m3.e
        public final a g() {
            return this;
        }

        @Override // com.android.tools.r8.graph.C0706m3.e
        public final boolean l() {
            return true;
        }

        public final a a(G3 g3) {
            i iVarA = g3.a(this.b);
            if (iVarA == null) {
                return null;
            }
            return this.b == iVarA ? this : new a(iVarA, j());
        }
    }

    public static b a(String str, String str2, Origin origin, C0722p1 c0722p1, DiagnosticsHandler diagnosticsHandler) {
        if (str2 == null || str2.isEmpty()) {
            return b.d;
        }
        try {
            return new C0724p3(c0722p1).a(str2);
        } catch (GenericSignatureFormatError e2) {
            diagnosticsHandler.warning(C0811y3.a(str2, str, origin, e2));
            return b.d;
        }
    }

    /* JADX INFO: renamed from: com.android.tools.r8.graph.m3$g */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class g implements d<W0> {
        private static final g e;
        static final /* synthetic */ boolean f = true;
        final List<f> a;
        final List<i> b;
        final h c;
        final List d;

        static {
            List list = C0706m3.a;
            List list2 = C0706m3.d;
            e = new g(list, list2, h.b, list2);
        }

        g(List list, List list2, h hVar, List list3) {
            boolean z = f;
            if (!z && list == null) {
                throw new AssertionError();
            }
            if (!z && list2 == null) {
                throw new AssertionError();
            }
            if (!z && hVar == null) {
                throw new AssertionError();
            }
            if (!z && list3 == null) {
                throw new AssertionError();
            }
            this.a = list;
            this.b = list2;
            this.c = hVar;
            this.d = list3;
        }

        public static g d() {
            return e;
        }

        public i a(int i) {
            if (this.b.isEmpty() || i < 0 || i >= this.b.size()) {
                return null;
            }
            return this.b.get(i);
        }

        @Override // com.android.tools.r8.graph.C0706m3.d
        public boolean b() {
            return this != e;
        }

        public final List c() {
            return this.a;
        }

        public h e() {
            return this.c;
        }

        public String toString() {
            AbstractC3649o0 abstractC3649o0A = AbstractC3649o0.a();
            InterfaceC3205vM interfaceC3205vMB = CM.b();
            if (a()) {
                return null;
            }
            A3 a3 = new A3(abstractC3649o0A, interfaceC3205vMB);
            a3.a(this);
            return a3.toString();
        }

        public final g a(G3 g3) {
            if (a()) {
                return this;
            }
            List<f> listC = g3.c(this.a);
            List<i> listD = g3.d(this.b);
            h hVarA = g3.a(this.c);
            List listB = g3.b(this.d);
            return (this.a == listC && this.b == listD && this.c == hVarA && this.d == listB) ? this : new g(listC, listD, hVarA, listB);
        }
    }

    /* JADX INFO: renamed from: com.android.tools.r8.graph.m3$b */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class b implements d<AbstractC0727q0> {
        final List<f> a;
        final c b;
        final List<c> c;
        static final /* synthetic */ boolean e = true;
        private static final b d = new b(C0706m3.a, C0706m3.e, C0706m3.c);

        b(List list, c cVar, List list2) {
            boolean z = e;
            if (!z && list == null) {
                throw new AssertionError();
            }
            if (!z && cVar == null) {
                throw new AssertionError();
            }
            if (!z && list2 == null) {
                throw new AssertionError();
            }
            this.a = list;
            this.b = cVar;
            this.c = list2;
        }

        public static C0718o3 c() {
            return new C0718o3();
        }

        public static b f() {
            return d;
        }

        public final b a(G3 g3) {
            if (a()) {
                return this;
            }
            List<f> listC = g3.c(this.a);
            c cVarA = g3.a(this.b);
            List<c> listE = g3.e(this.c);
            return (this.a == listC && this.b == cVarA && this.c == listE) ? this : new b(listC, cVarA, listE);
        }

        @Override // com.android.tools.r8.graph.C0706m3.d
        public boolean b() {
            return this != d;
        }

        public final List d() {
            return this.a;
        }

        public final /* bridge */ /* synthetic */ boolean e() {
            return true;
        }

        public final c g() {
            return this.b;
        }

        public final List h() {
            return this.c;
        }

        public String toString() {
            AbstractC3649o0 abstractC3649o0A = AbstractC3649o0.a();
            InterfaceC3205vM interfaceC3205vMB = CM.b();
            if (a()) {
                return null;
            }
            A3 a3 = new A3(abstractC3649o0A, interfaceC3205vMB);
            a3.a(this);
            return a3.toString();
        }

        public final List a(C0767v2 c0767v2) {
            if (!e && !b()) {
                throw new AssertionError();
            }
            c cVar = this.b;
            if (cVar.b == c0767v2) {
                return cVar.c;
            }
            for (c cVar2 : this.c) {
                if (cVar2.b == c0767v2) {
                    return cVar2.c;
                }
            }
            return null;
        }
    }
}
