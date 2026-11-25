package com.android.tools.r8.internal;

import com.android.dx.rop.code.RegisterSpec;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.huawei.hms.ml.language.common.utils.Constant;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.aC, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1595aC extends ZB {
    static final /* synthetic */ boolean i = true;
    private final RB c;
    private final StringBuilder d;
    private final int e;
    private final int f;
    private int g;
    private VB h;

    public C1595aC(RB rb) {
        super(rb);
        this.d = new StringBuilder();
        int iMax = 0;
        this.g = 0;
        this.c = rb;
        this.e = Math.max(c(-rb.a()).length(), c(rb.c() - 1).length());
        WB it = rb.iterator();
        while (it.hasNext()) {
            iMax = Math.max(iMax, YB.a(it.next().b()).length());
        }
        this.f = iMax;
    }

    private void a(List list) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            this.d.append(list.get(i2).toString()).append(StringUtil.SPACE);
        }
    }

    private static String c(int i2) {
        return i2 < 0 ? "--" : new StringBuilder().append(i2).toString();
    }

    private StringBuilder h() {
        return this.d.append(d(this.g)).append(" <- ");
    }

    @Override // com.android.tools.r8.internal.ZB
    public final int b() {
        return this.g;
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void d() {
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void e() {
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void f() {
        throw new Z00("Printing of instruction missing: " + YB.a(this.h.b()));
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void g() {
    }

    public final String i() {
        for (int i2 = 0; i2 < this.c.a(); i2++) {
            if (this.g > 0) {
                this.d.append("\n");
            }
            com.android.tools.r8.utils.Y2.a(this.d, c(a()), this.e);
            this.d.append(':');
            com.android.tools.r8.utils.Y2.a(this.d, Integer.toString(0), this.e);
            this.d.append(": ");
            com.android.tools.r8.utils.Y2.b(this.d, "ARG", this.f);
            this.d.append(StringUtil.SPACE);
            h();
            this.g++;
        }
        this.c.forEach(new Consumer() { // from class: com.android.tools.r8.internal.aC$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((VB) obj);
            }
        });
        return this.d.toString();
    }

    private static String d(int i2) {
        return RegisterSpec.PREFIX + i2;
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void b(EnumC2527mI enumC2527mI, Object obj, Object obj2) {
        h().append(obj.toString()).append(StringUtil.SPACE).append(obj2.toString()).append(StringUtil.SPACE).append(enumC2527mI);
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void c() {
        h().append("null");
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void e(Object obj) {
        a(Arrays.asList(obj));
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void c(Object obj) {
        a(Arrays.asList(obj));
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void d(Object obj) {
        a(Arrays.asList(obj));
    }

    @Override // com.android.tools.r8.internal.ZB, com.android.tools.r8.internal.UB
    public final void a(VB vb) {
        this.h = vb;
        if (!i && vb.l() != a()) {
            throw new AssertionError();
        }
        int iD = vb.d();
        int i2 = iD == 0 ? 1 : iD + 2;
        String strA = YB.a(vb.b());
        if (this.g > 0) {
            this.d.append("\n");
        }
        com.android.tools.r8.utils.Y2.a(this.d, c(a()), this.e);
        this.d.append(':');
        com.android.tools.r8.utils.Y2.a(this.d, Integer.toString(i2), this.e);
        this.d.append(": ");
        com.android.tools.r8.utils.Y2.b(this.d, strA, this.f);
        this.d.append(StringUtil.SPACE);
        super.a(vb);
        this.g++;
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void b(int i2) {
        this.d.append(c(i2));
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void b(Object obj) {
        h().append(obj.toString());
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void b(C0684j2 c0684j2, ArrayList arrayList) {
        if (!c0684j2.I0().Y0()) {
            h();
        }
        a((List) arrayList);
        this.d.append(c0684j2);
    }

    public final void b(com.android.tools.r8.graph.Y0 y0) {
        this.d.append(y0);
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void a(int i2) {
        h().append(i2);
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void a(C0759u2 c0759u2) {
        h().append("str(").append(c0759u2).append(Constant.AFTER_QUTO);
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void a(int i2, Object obj) {
        h();
        a(Arrays.asList(obj));
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void a(EnumC1076Il enumC1076Il, int i2, Object obj) {
        this.d.append(obj.toString()).append(StringUtil.SPACE).append(c(i2));
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void a(C0767v2 c0767v2) {
        h().append(c0767v2);
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void a(com.android.tools.r8.graph.Y0 y0) {
        h();
        b(y0);
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void a(com.android.tools.r8.graph.Y0 y0, Object obj) {
        h();
        this.d.append(y0).append(StringUtil.SPACE);
        a(Arrays.asList(obj));
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void a(com.android.tools.r8.graph.Y0 y0, Object obj, Object obj2) {
        this.d.append(y0).append(StringUtil.SPACE);
        a(Arrays.asList(obj, obj2));
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void a(C0767v2 c0767v2, Object obj) {
        h();
        this.d.append(c0767v2).append(StringUtil.SPACE);
        a(Arrays.asList(obj));
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void a(Object obj) {
        h().append(obj.toString());
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void a(C0767v2 c0767v2, ArrayList arrayList) {
        h();
        a((List) arrayList);
        this.d.append(c0767v2);
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void a(int i2, Object obj, Object obj2) {
        h();
        a(Arrays.asList(obj, obj2));
    }
}
