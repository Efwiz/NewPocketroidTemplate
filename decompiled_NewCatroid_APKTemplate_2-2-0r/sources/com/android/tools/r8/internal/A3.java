package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.profile.art.ArtProfileMethodRuleBuilder;
import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.utils.C3889e2;
import com.android.tools.r8.utils.InterfaceC3905h3;
import com.android.tools.r8.utils.InterfaceC3910i3;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class A3 extends K3 implements InterfaceC2197i1 {
    static final /* synthetic */ boolean c = true;
    private final C0684j2 a;
    private final C3 b;

    A3(C0684j2 c0684j2, C3 c3) {
        if (!c && c3.c() == 0) {
            throw new AssertionError();
        }
        this.a = c0684j2;
        this.b = c3;
    }

    public static a d() {
        return new a(null);
    }

    @Override // com.android.tools.r8.internal.K3
    public final void a(InterfaceC3905h3 interfaceC3905h3, InterfaceC3905h3 interfaceC3905h4) throws Throwable {
        interfaceC3905h4.accept(this);
    }

    @Override // com.android.tools.r8.internal.K3
    public final AbstractC0744s2 c() {
        return e();
    }

    public C0684j2 e() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        A3 a3 = (A3) obj;
        return this.a.equals(a3.a) && this.b.equals(a3.b);
    }

    public C3 f() {
        return this.b;
    }

    public final MethodReference getMethodReference() {
        return this.a.E0();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2197i1
    /* JADX INFO: renamed from: getReference */
    public final C0684j2 c() {
        return e();
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return this.b.toString() + this.a.q0();
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a extends J3 implements ArtProfileMethodRuleBuilder, InterfaceC2125h1 {
        static final /* synthetic */ boolean d = true;
        private final C0722p1 a;
        private C0684j2 b;
        private C3.a c = C3.a();

        a(C0722p1 c0722p1) {
            this.a = c0722p1;
        }

        @Override // com.android.tools.r8.internal.InterfaceC2125h1
        public final InterfaceC2125h1 a(InterfaceC2125h1 interfaceC2125h1) {
            this.c.a((a) interfaceC2125h1);
            return this;
        }

        public final a b(C0684j2 c0684j2) {
            this.b = c0684j2;
            return this;
        }

        @Override // com.android.tools.r8.internal.J3
        final a c() {
            return this;
        }

        @Override // com.android.tools.r8.internal.J3
        /* JADX INFO: renamed from: e, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
        public A3 d() {
            return new A3(this.b, this.c.a());
        }

        final C3.a f() {
            return this.c;
        }

        @Override // com.android.tools.r8.profile.art.ArtProfileMethodRuleBuilder
        public final ArtProfileMethodRuleBuilder setMethodReference(MethodReference methodReference) {
            if (!d && this.a == null) {
                throw new AssertionError();
            }
            this.b = C3889e2.a(methodReference, this.a);
            return this;
        }

        @Override // com.android.tools.r8.profile.art.ArtProfileMethodRuleBuilder
        public final ArtProfileMethodRuleBuilder setMethodRuleInfo(Consumer consumer) {
            this.c.b();
            return a((Consumer<? super C3.a>) consumer);
        }

        @Override // com.android.tools.r8.internal.InterfaceC2125h1
        public final InterfaceC2125h1 a(InterfaceC2125h1 interfaceC2125h1, Runnable runnable) {
            int iC = this.c.c();
            this.c.a((a) interfaceC2125h1);
            if (this.c.c() != iC) {
                runnable.run();
            }
            return this;
        }

        @Override // com.android.tools.r8.internal.InterfaceC2125h1
        public final a a(A3 a3) {
            this.c.a(a3.f());
            return this;
        }

        @Override // com.android.tools.r8.internal.InterfaceC2125h1
        public final InterfaceC2125h1 a() {
            this.c.f();
            return this;
        }

        @Override // com.android.tools.r8.internal.InterfaceC2125h1
        public final InterfaceC2125h1 a(C0684j2 c0684j2) {
            this.b = c0684j2;
            return this;
        }

        public a a(Consumer<? super C3.a> consumer) {
            consumer.accept(this.c);
            return this;
        }
    }

    @Override // com.android.tools.r8.internal.K3
    public final Object a(InterfaceC3910i3 interfaceC3910i3, InterfaceC3910i3 interfaceC3910i4) {
        return interfaceC3910i4.apply(this);
    }

    @Override // com.android.tools.r8.internal.K3
    public final void a(OutputStreamWriter outputStreamWriter) throws IOException {
        C3 c3 = this.b;
        if (c3.isHot()) {
            outputStreamWriter.write(72);
        }
        if (c3.isStartup()) {
            outputStreamWriter.write(83);
        }
        if (c3.isPostStartup()) {
            outputStreamWriter.write(80);
        }
        outputStreamWriter.write(this.a.q0());
    }
}
