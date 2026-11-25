package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class VY implements KV {
    protected static final Predicate i = new Predicate() { // from class: com.android.tools.r8.internal.VY$$ExternalSyntheticLambda0
        @Override // java.util.function.Predicate
        public final boolean test(Object obj) {
            return VY.d((C2102gl) obj);
        }
    };
    protected static final Predicate j = new Predicate() { // from class: com.android.tools.r8.internal.VY$$ExternalSyntheticLambda1
        @Override // java.util.function.Predicate
        public final boolean test(Object obj) {
            return VY.e((C2102gl) obj);
        }
    };
    static final /* synthetic */ boolean k = true;
    protected final C0767v2 a;
    protected final C0737r2 b;
    private final int d;
    private int[] e;
    private final AbstractC1915eM.c h;
    private int c = 0;
    private ArrayList f = new ArrayList();
    private ArrayList g = new ArrayList();

    protected VY(C0767v2 c0767v2, C0684j2 c0684j2, AbstractC1915eM abstractC1915eM, C0684j2 c0684j3) {
        if (!k && c0684j2 == null) {
            throw new AssertionError();
        }
        this.a = c0767v2;
        C0737r2 c0737r2 = c0684j2.h;
        this.b = c0737r2;
        this.d = c0767v2 != null ? a(EnumC1812d20.OBJECT) : -1;
        C0767v2[] c0767v2Arr = c0737r2.f.a;
        int length = c0767v2Arr.length;
        this.e = new int[length];
        for (int i2 = 0; i2 < length; i2++) {
            this.e[i2] = a(EnumC1812d20.a(c0767v2Arr[i2]));
        }
        this.h = ((AbstractC1915eM.c.a) ((AbstractC1915eM.c.a) AbstractC1915eM.c.x().a(0).a(c0684j3).a(abstractC1915eM)).a(true)).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean d(C2102gl c2102gl) {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean e(C2102gl c2102gl) {
        return true;
    }

    @Override // com.android.tools.r8.internal.KV
    public final com.android.tools.r8.graph.X a(int i2) {
        return null;
    }

    @Override // com.android.tools.r8.internal.KV
    public final com.android.tools.r8.graph.X a(int i2, int i3) {
        return null;
    }

    @Override // com.android.tools.r8.internal.KV
    public final void a(C2102gl c2102gl) {
    }

    @Override // com.android.tools.r8.internal.KV
    public final void a(C2102gl c2102gl, int i2, int i3, boolean z) {
    }

    @Override // com.android.tools.r8.internal.KV
    public final C3038t6 b(C2102gl c2102gl) {
        return null;
    }

    @Override // com.android.tools.r8.internal.KV
    public final com.android.tools.r8.graph.X c(int i2) {
        return null;
    }

    @Override // com.android.tools.r8.internal.KV
    public final boolean c() {
        return true;
    }

    @Override // com.android.tools.r8.internal.KV
    public final void clear() {
        this.f = null;
        this.g = null;
        this.e = null;
    }

    @Override // com.android.tools.r8.internal.KV
    public final boolean d() {
        return true;
    }

    @Override // com.android.tools.r8.internal.KV
    public final int e(int i2) {
        return i2;
    }

    @Override // com.android.tools.r8.internal.KV
    public final boolean f(int i2) {
        return true;
    }

    @Override // com.android.tools.r8.internal.KV
    public final int g(int i2) {
        return i2;
    }

    protected final int h(int i2) {
        boolean z = k;
        if (!z && i2 < 0) {
            throw new AssertionError();
        }
        if (z || i2 < this.e.length) {
            return this.e[i2];
        }
        throw new AssertionError();
    }

    protected final int i() {
        return this.f.size();
    }

    protected abstract void j();

    @Override // com.android.tools.r8.internal.KV
    public final void b() {
        boolean z = k;
        if (!z && !this.f.isEmpty()) {
            throw new AssertionError();
        }
        j();
        if (!z && this.f.isEmpty()) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.internal.KV
    public final AbstractC1915eM f() {
        return this.h;
    }

    protected final int g() {
        boolean z = k;
        if (!z && this.a == null) {
            throw new AssertionError();
        }
        if (z || this.d >= 0) {
            return this.d;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.KV
    public final void c(C2102gl c2102gl) {
        c2102gl.a(0, c2102gl.h(), new VY$$ExternalSyntheticLambda2());
    }

    @Override // com.android.tools.r8.internal.KV
    public final int d(int i2) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.KV
    public final int e() {
        return this.f.size();
    }

    protected final int h() {
        return this.f.size() - 1;
    }

    protected final void a(Consumer consumer) {
        a(consumer, i);
    }

    @Override // com.android.tools.r8.internal.KV
    public final AbstractC1915eM b(int i2) {
        return AbstractC1915eM.v();
    }

    protected final void a(Consumer consumer, Predicate predicate) {
        this.f.add(consumer);
        this.g.add(predicate);
    }

    protected final int a(EnumC1812d20 enumC1812d20) {
        int i2 = this.c;
        this.c = enumC1812d20.c() + i2;
        return i2;
    }

    @Override // com.android.tools.r8.internal.KV
    public final int a(int i2, C2102gl c2102gl) {
        if (((Predicate) this.g.get(i2)).test(c2102gl) || i2 == this.f.size() - 1) {
            return i2;
        }
        return -1;
    }

    @Override // com.android.tools.r8.internal.KV
    public final void a(C2102gl c2102gl, int i2, boolean z) {
        ((Consumer) this.f.get(i2)).accept(c2102gl);
    }

    @Override // com.android.tools.r8.internal.KV
    public final void a(int i2, int i3, int i4, C2102gl c2102gl) {
        throw new C3405y10("Unexpected call to resolveAndBuildSwitch");
    }

    @Override // com.android.tools.r8.internal.KV
    public final void a(int i2, int i3, C2102gl c2102gl) {
        throw new C3405y10("Unexpected call to resolveAndBuildNewArrayFilledData");
    }
}
