package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import org.luaj.vm2.Lua;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Vn, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1454Vn {
    static final /* synthetic */ boolean g = true;
    private final C0702m a;
    private final CR b;
    private int c;
    private final ArrayList d = new ArrayList();
    private final C0986Fk e;
    private final EnumC0928Dk f;

    public C1454Vn(C0702m c0702m, CR cr, C0986Fk c0986Fk, EnumC0928Dk enumC0928Dk) {
        this.a = c0702m;
        this.b = cr;
        this.e = c0986Fk;
        this.f = enumC0928Dk;
        a();
    }

    private void a() {
        this.c = 0;
        this.d.add(new ArrayList());
    }

    public final void b(com.android.tools.r8.graph.o5 o5Var) {
        ((List) com.android.tools.r8.utils.V1.b(this.d)).add(o5Var);
    }

    public final C1454Vn a(com.android.tools.r8.graph.o5 o5Var) {
        int iW0 = o5Var.e().a1().w0();
        int i = this.c + iW0;
        int i2 = this.a.Y().n1.i0;
        if (i2 <= -1) {
            i2 = Lua.MASK_NOT_Bx;
        }
        if (i > i2 / 2 && this.c > 0) {
            a();
        }
        ((List) com.android.tools.r8.utils.V1.b(this.d)).add(o5Var);
        this.c += iW0;
        return this;
    }

    public final List a(final C2688oE c2688oE) {
        if (g || this.d.stream().noneMatch(new Vn$$ExternalSyntheticLambda0())) {
            return com.android.tools.r8.utils.V1.a((Collection) this.d, new Function() { // from class: com.android.tools.r8.internal.Vn$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.a(c2688oE, (List) obj);
                }
            });
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C1480Wn a(C2688oE c2688oE, List list) {
        return new C1480Wn(this.a, this.b, c2688oE, list, this.e, this.f, null);
    }

    public final C1480Wn a(C2688oE c2688oE, C1353Sn c1353Sn) {
        boolean z = g;
        if (!z && !this.d.stream().noneMatch(new Vn$$ExternalSyntheticLambda0())) {
            throw new AssertionError();
        }
        if (!z && this.d.size() != 1) {
            throw new AssertionError();
        }
        return new C1480Wn(this.a, this.b, c2688oE, (List) com.android.tools.r8.utils.V1.a(this.d), this.e, this.f, c1353Sn);
    }
}
