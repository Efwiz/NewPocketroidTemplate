package com.android.tools.r8.synthesis;

import com.android.tools.r8.Version;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC0860Bk;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.InterfaceC3524zk;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.Reference;
import com.android.tools.r8.utils.C3970u0;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class L {
    static final /* synthetic */ boolean V = true;
    public static final /* synthetic */ int W = 0;
    public final b A;
    public final b B;
    public final b C;
    public final b D;
    public final b E;
    public final b F;
    public final b G;
    public final b H;
    public final b I;
    public final b J;
    public final b K;
    public final b L;
    public final b M;
    public final b N;
    public final b O;
    public final b P;
    public final b Q;
    public final b R;
    public final b S;
    private final List T;
    private String U;
    private K a;
    public final b b;
    public final b c;
    public final b d;
    public final b e;
    public final b f;
    public final b g;
    public final b h;
    public final b i;
    public final b j;
    public final b k;
    public final b l;
    public final b m;
    public final b n;
    public final b o;
    public final b p;
    public final b q;
    public final b r;
    public final b s;
    public final b t;
    public final b u;
    public final b v;
    public final b w;
    public final b x;
    public final b y;
    public final b z;

    /* JADX WARN: $VALUES field not found */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    static final class a {
        public static final a a = new a("INTERNAL", 0);
        public static final a b = new a("EXTERNAL", 1);

        private a(String str, int i) {
            super(str, i);
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static abstract class b implements com.android.tools.r8.utils.structural.s<b> {
        private final int a;
        private final String b;

        b(int i, String str) {
            this.a = i;
            this.b = str;
        }

        public O a() {
            return null;
        }

        public abstract void a(InterfaceC3524zk interfaceC3524zk);

        public String b() {
            return this.b;
        }

        public final int c() {
            return this.a;
        }

        public abstract boolean d();

        public abstract boolean e();

        public final boolean equals(Object obj) {
            return com.android.tools.r8.utils.structural.k.a(this, obj);
        }

        public abstract boolean g();

        public abstract boolean h();

        public final int hashCode() {
            return this.a;
        }

        public abstract boolean i();

        @Override // com.android.tools.r8.utils.structural.s, java.lang.Comparable
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public final int compareTo(b bVar) {
            return Integer.compare(this.a, bVar.a);
        }
    }

    public L() {
        K k = new K();
        this.a = k;
        this.b = k.a();
        this.c = this.a.a();
        this.d = this.a.a();
        this.e = this.a.a();
        this.f = this.a.b();
        this.g = this.a.b();
        this.h = this.a.b();
        this.i = this.a.a("$EnumUnboxingLocalUtility");
        this.j = this.a.a("$EnumUnboxingSharedUtility");
        this.k = this.a.a("$-CC");
        this.l = this.a.a("$-EL");
        this.m = this.a.a("RetargetClass");
        this.n = this.a.a("RetargetInterface");
        this.o = this.a.a("$Wrapper");
        this.a.a("");
        this.p = this.a.a("$VivifiedWrapper");
        this.q = this.a.a("-IA");
        this.r = this.a.a("$$IA$1");
        this.s = this.a.a("$$IA$2");
        this.t = this.a.a("$$IA$3");
        this.u = this.a.a("$EnumConversion");
        this.v = this.a.b("Lambda");
        this.w = this.a.b("ThreadLocal");
        this.x = this.a.c("$IA");
        this.y = this.a.c("$Condy");
        this.z = this.a.e("CheckNotZero");
        this.A = this.a.e("Record");
        this.B = this.a.e("Backport");
        this.C = this.a.d("BackportWithForwarding");
        this.D = this.a.d("StaticInterfaceCall");
        this.E = this.a.e("ToStringIfNotNull");
        this.F = this.a.e("ThrowCCEIfNotNull");
        this.G = this.a.e("ThrowIAE");
        this.H = this.a.e("ThrowICCE");
        this.I = this.a.e("ThrowNSME");
        this.J = this.a.e("ThrowRTE");
        this.K = this.a.e("TwrCloseResource");
        this.L = this.a.e("ServiceLoad");
        this.M = this.a.d("Outline");
        this.N = this.a.d("CovariantOutline");
        this.O = this.a.d("APIConversion");
        this.P = this.a.d("APIConversionParameters");
        this.Q = this.a.d("$CollectionConversion");
        this.R = this.a.e("ApiModelOutline");
        this.S = this.a.d("ApiModelOutline");
        this.U = null;
        this.T = this.a.c();
        this.a = null;
    }

    public final String a() {
        if (this.U == null) {
            InterfaceC3524zk interfaceC3524zkA = AbstractC0860Bk.b().a();
            interfaceC3524zkA.a(Version.getVersionString(), StandardCharsets.UTF_8);
            for (b bVar : this.T) {
                interfaceC3524zkA.a(bVar.a);
                interfaceC3524zkA.a(bVar.b(), StandardCharsets.UTF_8);
                bVar.a(interfaceC3524zkA);
            }
            this.U = interfaceC3524zkA.a().toString();
        }
        return this.U;
    }

    public Collection<b> b() {
        return this.T;
    }

    static String b(C0767v2 c0767v2, b bVar) {
        String strA1 = c0767v2.a1();
        if (bVar.e()) {
            return strA1;
        }
        int iLastIndexOf = strA1.lastIndexOf(bVar.d() ? bVar.b : "$$");
        if (iLastIndexOf >= 0) {
            return strA1.substring(0, iLastIndexOf);
        }
        throw new C3405y10("Unexpected failure to compute a synthetic prefix for " + strA1);
    }

    public final b a(int i) {
        if (i <= 0 || i > this.T.size()) {
            return null;
        }
        return (b) this.T.get(i - 1);
    }

    static String a(C0767v2 c0767v2, b bVar) {
        if (!V && bVar.e()) {
            throw new AssertionError();
        }
        String strA1 = c0767v2.a1();
        int iIndexOf = strA1.indexOf(bVar.d() ? bVar.b : "$$ExternalSynthetic");
        if (iIndexOf >= 0) {
            return strA1.substring(0, iIndexOf);
        }
        throw new C3405y10("Unexpected failure to determine the context of synthetic class: " + strA1);
    }

    static C0767v2 a(b bVar, C3846e c3846e, C0722p1 c0722p1) {
        if (V || bVar.d()) {
            return c0722p1.e(a("", bVar, c3846e.c().E0(), ""));
        }
        throw new AssertionError();
    }

    static C0767v2 a(b bVar, C3846e c3846e, String str, C0702m c0702m) {
        if (!V && bVar.d()) {
            throw new AssertionError();
        }
        return c0702m.a().e(a("$$InternalSynthetic", bVar, c3846e.a(c0702m.Y().t0).E0(), str));
    }

    public static String a(String str, b bVar, String str2, String str3) {
        return C3970u0.l(str2 + str + bVar.b + str3);
    }

    public static boolean a(ClassReference classReference) {
        a(classReference.getDescriptor());
        return true;
    }

    public static void a(String str) {
        if (!V && str.contains("$$InternalSynthetic")) {
            throw new AssertionError(str);
        }
    }

    static String a(a aVar) {
        if (!V && aVar == null) {
            throw new AssertionError();
        }
        if (aVar == a.a) {
            return "$$InternalSynthetic";
        }
        return "$$ExternalSynthetic";
    }

    static ClassReference a(ClassReference classReference, b bVar, String str) {
        return Reference.classFromDescriptor(a("$$ExternalSynthetic", bVar, classReference.getBinaryName(), str));
    }

    static boolean a(ClassReference classReference, a aVar, b bVar) {
        String typeName = classReference.getTypeName();
        if (bVar.d()) {
            if (V || aVar == null) {
                return classReference.getBinaryName().endsWith(bVar.b);
            }
            throw new AssertionError();
        }
        String strA = a(aVar);
        int iLastIndexOf = typeName.lastIndexOf(strA);
        if (iLastIndexOf >= 0) {
            return a(bVar, typeName, iLastIndexOf, strA, aVar == a.b);
        }
        return false;
    }

    private static boolean a(b bVar, String str, int i, String str2, boolean z) {
        int length = bVar.b.length() + str2.length() + i;
        if (length >= str.length()) {
            return false;
        }
        if (!str.substring(i, length).equals(str2 + bVar.b)) {
            return false;
        }
        if (z) {
            String strSubstring = str.substring(length);
            if (strSubstring.isEmpty()) {
                return false;
            }
            if ('0' == strSubstring.charAt(0)) {
                if (strSubstring.length() != 1) {
                    return false;
                }
            } else {
                for (int i2 = 0; i2 < strSubstring.length(); i2++) {
                    if (!Character.isDigit(strSubstring.charAt(i2))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
