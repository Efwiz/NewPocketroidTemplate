package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.h3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3732h3 extends C3737i3 {

    /* JADX INFO: renamed from: com.android.tools.r8.shaking.h3$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a extends C3737i3.a<C3732h3, a> {
        protected a() {
        }

        @Override // com.android.tools.r8.shaking.B2.a
        public final B2.a e() {
            return this;
        }

        public C3732h3 f() {
            return new C3732h3(this.a, d(), this.d, b(), this.f, this.g, this.h, this.i, this.j, c(), this.l, this.m, this.n, this.p, this.q.a());
        }
    }

    protected C3732h3(Origin origin, Position position, String str, List list, C3774o2 c3774o2, C3774o2 c3774o3, boolean z, I2 i2, AbstractC3833y2 abstractC3833y2, List list2, G3 g3, boolean z2, List list3, EnumC3758l3 enumC3758l3, C3742j3 c3742j3) {
        super(origin, position, str, list, c3774o2, c3774o3, z, i2, abstractC3833y2, list2, g3, z2, list3, enumC3758l3, c3742j3);
    }

    public static a I() {
        return new a();
    }

    @Override // com.android.tools.r8.shaking.C3737i3, com.android.tools.r8.shaking.S2, com.android.tools.r8.shaking.B2
    public final boolean equals(Object obj) {
        if (obj instanceof C3732h3) {
            return super.equals((C3732h3) obj);
        }
        return false;
    }

    @Override // com.android.tools.r8.shaking.S2
    public final C3732h3 t() {
        return this;
    }

    static void a(StringBuilder sb, String str, Object obj) {
        if (obj == null) {
            return;
        }
        String string = obj.toString();
        if (string.isEmpty()) {
            return;
        }
        if (str != null) {
            sb.append(str);
        }
        sb.append(string);
        sb.append(" ");
    }

    protected C3732h3 a(final C0722p1 c0722p1) {
        return new C3732h3(l(), m(), n(), G3.a(c0722p1, b()), a(), k(), e(), d(), c() == null ? null : c().a(c0722p1), G3.a(c0722p1, f()), g() == null ? null : g().a(c0722p1), h(), j() == null ? null : (List) j().stream().map(new Function() { // from class: com.android.tools.r8.shaking.h3$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3732h3.a(c0722p1, (C3775o3) obj);
            }
        }).collect(Collectors.toList()), H(), G());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C3775o3 a(C0722p1 c0722p1, C3775o3 c3775o3) {
        return c3775o3.a(c0722p1);
    }

    public static C3732h3 a(Consumer<C3742j3.a> consumer) {
        a aVarI = I();
        aVarI.a = new C3727g3(Origin.root());
        aVarI.i = I2.b;
        aVarI.a(AbstractC3833y2.a(G3.b()));
        aVarI.c(AbstractC1639am.c(C3775o3.b()));
        aVarI.a(EnumC3758l3.KEEP);
        consumer.accept(aVarI.q);
        C3732h3 c3732h3F = aVarI.f();
        c3732h3F.D();
        return c3732h3F;
    }
}
