package com.android.tools.r8.naming;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.internal.AbstractC2734ot;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.CZ;
import com.android.tools.r8.internal.InterfaceC3205vM;
import com.android.tools.r8.internal.RK;
import com.android.tools.r8.internal.UT$$ExternalSyntheticLambda5;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.references.TypeReference;
import com.android.tools.r8.utils.C3892f0;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.Y2;
import com.huawei.hms.ml.language.common.utils.Constant;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class Q implements P, Comparable<Q> {
    private static final List e = Collections.emptyList();
    private final c a;
    private final c b;
    private final Position c;
    private List d = e;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a extends c {
        static final /* synthetic */ boolean d = true;
        public final String c;

        public a(String str, String str2) {
            super(str);
            this.c = str2;
        }

        @Override // com.android.tools.r8.naming.Q.c
        public final a a() {
            return this;
        }

        @Override // com.android.tools.r8.naming.Q.c
        public final c b(String str) {
            if (!d && e()) {
                throw new AssertionError();
            }
            return new a(str + "." + this.a, this.c);
        }

        @Override // com.android.tools.r8.naming.Q.c
        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return this.a.equals(aVar.a) && this.c.equals(aVar.c);
        }

        @Override // com.android.tools.r8.naming.Q.c
        public final int f() {
            return 2;
        }

        @Override // com.android.tools.r8.naming.Q.c
        public final int hashCode() {
            return this.c.hashCode() + (this.a.hashCode() * 31);
        }

        @Override // com.android.tools.r8.naming.Q.c
        public final String toString() {
            return this.c + " " + this.a;
        }

        public static a a(Y0 y0) {
            return new a(y0.f.r0(), y0.h.r0());
        }

        public Y0 a(C0722p1 c0722p1, C0767v2 c0767v2) {
            return c0722p1.a(c0767v2, c0722p1.e(C3970u0.H(this.c)), c0722p1.c(this.a));
        }

        @Override // com.android.tools.r8.naming.Q.c
        final c a(String str) {
            return new a(str, this.c);
        }

        @Override // com.android.tools.r8.naming.Q.c
        public final c a(Function function, String str) {
            return new a(str, C3970u0.a(function, this.c));
        }

        @Override // com.android.tools.r8.naming.Q.c
        final void a(StringWriter stringWriter) throws IOException {
            stringWriter.append((CharSequence) this.c);
            stringWriter.append(StringUtil.SPACE);
            stringWriter.append((CharSequence) this.a);
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class b extends c {
        static final /* synthetic */ boolean e = true;
        public final String c;
        public final String[] d;

        public b(String str, String str2, String[] strArr) {
            super(str);
            this.c = str2;
            this.d = strArr;
        }

        @Override // com.android.tools.r8.naming.Q.c
        public b b() {
            return this;
        }

        @Override // com.android.tools.r8.naming.Q.c
        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof b)) {
                return false;
            }
            b bVar = (b) obj;
            return this.c.equals(bVar.c) && this.a.equals(bVar.a) && Arrays.equals(this.d, bVar.d);
        }

        @Override // com.android.tools.r8.naming.Q.c
        public final int f() {
            return 1;
        }

        @Override // com.android.tools.r8.naming.Q.c
        public final int hashCode() {
            return ((this.a.hashCode() + (this.c.hashCode() * 17)) * 31) + Arrays.hashCode(this.d);
        }

        public String i() {
            StringBuilder sb = new StringBuilder("(");
            for (String str : this.d) {
                sb.append(C3970u0.H(str));
            }
            sb.append(')');
            sb.append(C3970u0.H(this.c));
            return sb.toString();
        }

        public b j() {
            if (e || e()) {
                return new b(h(), this.c, this.d);
            }
            throw new AssertionError();
        }

        public final c k() {
            return new b(h(), this.c, this.d);
        }

        @Override // com.android.tools.r8.naming.Q.c
        public String toString() {
            return this.c + " " + this.a + "(" + String.join(",", this.d) + Constant.AFTER_QUTO;
        }

        public static b a(C0684j2 c0684j2) {
            return a(c0684j2, false);
        }

        @Override // com.android.tools.r8.naming.Q.c
        /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
        public final b a(final Function function, String str) {
            return new b(str, C3970u0.a(function, this.c), com.android.tools.r8.utils.B.a(this.d, new Function() { // from class: com.android.tools.r8.naming.Q$b$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C3970u0.a(function, (String) obj);
                }
            }));
        }

        public static b a(C0684j2 c0684j2, boolean z) {
            String[] strArr = new String[c0684j2.F0()];
            C0767v2[] c0767v2Arr = c0684j2.h.f.a;
            for (int i = 0; i < c0767v2Arr.length; i++) {
                strArr[i] = c0767v2Arr[i].r0();
            }
            return new b(z ? c0684j2.K0() : c0684j2.f.r0(), c0684j2.h.e.r0(), strArr);
        }

        public b(String str, String str2, Collection<String> collection) {
            super(str);
            this.c = str2;
            this.d = (String[]) collection.toArray(Y2.b);
        }

        @Override // com.android.tools.r8.naming.Q.c
        public final c b(String str) {
            return new b(str + "." + this.a, this.c, this.d);
        }

        public static b a(String str, String str2) {
            return new b(str, C3970u0.b(CZ.e(str2).b()), com.android.tools.r8.utils.B.a(CZ.a(str2), new Function() { // from class: com.android.tools.r8.naming.Q$b$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Q.b.a((CZ) obj);
                }
            }));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ String a(CZ cz) {
            return C3970u0.b(cz.b());
        }

        public static b a(MethodReference methodReference) {
            TypeReference returnType = methodReference.getReturnType();
            return new b(methodReference.getMethodName(), returnType == null ? PsiKeyword.VOID : returnType.getTypeName(), C3892f0.a(methodReference.getFormalTypes(), new UT$$ExternalSyntheticLambda5()));
        }

        public C0684j2 a(C0722p1 c0722p1, C0767v2 c0767v2) {
            C0767v2[] c0767v2Arr = new C0767v2[this.d.length];
            int i = 0;
            while (true) {
                String[] strArr = this.d;
                if (i < strArr.length) {
                    c0767v2Arr[i] = c0722p1.e(C3970u0.H(strArr[i]));
                    i++;
                } else {
                    return c0722p1.a(c0767v2, c0722p1.b(c0722p1.e(C3970u0.H(this.c)), c0767v2Arr), c0722p1.c(this.a));
                }
            }
        }

        public static b a(String[] strArr) {
            return new b("<init>", PsiKeyword.VOID, strArr);
        }

        @Override // com.android.tools.r8.naming.Q.c
        final c a(String str) {
            return new b(str, this.c, this.d);
        }

        @Override // com.android.tools.r8.naming.Q.c
        final void a(StringWriter stringWriter) throws IOException {
            stringWriter.append((CharSequence) this.c).append(StringUtil.SPACE).append((CharSequence) this.a).append('(');
            int i = 0;
            while (true) {
                String[] strArr = this.d;
                if (i < strArr.length) {
                    stringWriter.append((CharSequence) strArr[i]);
                    if (i < this.d.length - 1) {
                        stringWriter.append(',');
                    }
                    i++;
                } else {
                    stringWriter.append(')');
                    return;
                }
            }
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static abstract class c {
        static final /* synthetic */ boolean b = true;
        public final String a;

        protected c(String str) {
            this.a = str;
        }

        public a a() {
            return null;
        }

        abstract c a(String str);

        public abstract c a(Function function, String str);

        abstract void a(StringWriter stringWriter);

        public b b() {
            return null;
        }

        public abstract c b(String str);

        public final String c() {
            return this.a;
        }

        public boolean d() {
            return this instanceof b;
        }

        public boolean e() {
            return this.a.indexOf(46) != -1;
        }

        public abstract boolean equals(Object obj);

        public abstract int f();

        public final String g() {
            if (!b && !e()) {
                throw new AssertionError();
            }
            String str = this.a;
            return str.substring(0, str.lastIndexOf(46));
        }

        public final String h() {
            if (!b && !e()) {
                throw new AssertionError();
            }
            String str = this.a;
            return str.substring(str.lastIndexOf(46) + 1);
        }

        public abstract int hashCode();

        public String toString() {
            try {
                StringWriter stringWriter = new StringWriter();
                a(stringWriter);
                return stringWriter.toString();
            } catch (IOException e) {
                throw new C3405y10(e);
            }
        }
    }

    public Q(c cVar, c cVar2, Position position) {
        this.a = cVar;
        this.b = cVar2;
        this.c = position;
    }

    @Override // com.android.tools.r8.naming.P
    public String a() {
        return this.b.a;
    }

    @Override // com.android.tools.r8.naming.P
    public c b() {
        return this.a;
    }

    @Override // com.android.tools.r8.naming.P
    public final boolean c() {
        return AbstractC2734ot.b(this.d, new InterfaceC3205vM() { // from class: com.android.tools.r8.naming.Q$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return ((com.android.tools.r8.naming.mappinginformation.d) obj).n();
            }
        });
    }

    @Override // com.android.tools.r8.naming.P
    public c d() {
        return this.b;
    }

    public final String e() {
        return this.a.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Q)) {
            return false;
        }
        Q q = (Q) obj;
        return this.a.equals(q.a) && this.b.equals(q.b);
    }

    public final Position f() {
        return this.c;
    }

    public final boolean g() {
        for (com.android.tools.r8.naming.mappinginformation.e eVar : this.d) {
            if (eVar.k() || (eVar instanceof RK)) {
                return true;
            }
        }
        return false;
    }

    public final boolean h() {
        return this.a.f() == 2;
    }

    public final int hashCode() {
        return this.b.hashCode() + (this.a.hashCode() * 31);
    }

    public final boolean i() {
        return this.a.f() == 1;
    }

    public final boolean j() {
        for (com.android.tools.r8.naming.mappinginformation.e eVar : this.d) {
            eVar.getClass();
            if (eVar instanceof RK) {
                return true;
            }
        }
        return false;
    }

    public final String toString() {
        return this.a.toString() + " -> " + this.b.c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String b(Q q) {
        return b().toString();
    }

    public final void a(com.android.tools.r8.naming.mappinginformation.d dVar, Consumer consumer) {
        if (this.d == e) {
            this.d = new ArrayList();
        }
        com.android.tools.r8.naming.mappinginformation.e.a(this.d, dVar, consumer);
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public final int compareTo(Q q) {
        return Comparator.comparing(new Function() { // from class: com.android.tools.r8.naming.Q$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Boolean.valueOf(((Q) obj).h());
            }
        }).thenComparing(new Function() { // from class: com.android.tools.r8.naming.Q$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Q) obj).a();
            }
        }).thenComparing(new Q$$ExternalSyntheticLambda3()).thenComparing(new Function() { // from class: com.android.tools.r8.naming.Q$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.b((Q) obj);
            }
        }).compare(this, q);
    }
}
