package com.android.tools.r8.internal;

import com.android.tools.r8.utils.C3912j0;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class WY implements KV {
    private final List a;
    private final AbstractC1915eM b;

    protected WY(AbstractC1639am abstractC1639am, AbstractC1915eM.c cVar) {
        this.a = abstractC1639am;
        this.b = cVar;
    }

    @Override // com.android.tools.r8.internal.KV
    public final int a(int i, C2102gl c2102gl) {
        return -1;
    }

    @Override // com.android.tools.r8.internal.KV
    public final com.android.tools.r8.graph.X a(int i) {
        return null;
    }

    @Override // com.android.tools.r8.internal.KV
    public final com.android.tools.r8.graph.X a(int i, int i2) {
        return null;
    }

    @Override // com.android.tools.r8.internal.KV
    public final void a(C2102gl c2102gl) {
    }

    @Override // com.android.tools.r8.internal.KV
    public final C3038t6 b(C2102gl c2102gl) {
        return null;
    }

    @Override // com.android.tools.r8.internal.KV
    public final void b() {
    }

    @Override // com.android.tools.r8.internal.KV
    public final com.android.tools.r8.graph.X c(int i) {
        return null;
    }

    @Override // com.android.tools.r8.internal.KV
    public final boolean c() {
        return true;
    }

    @Override // com.android.tools.r8.internal.KV
    public final void clear() {
    }

    @Override // com.android.tools.r8.internal.KV
    public final int d(int i) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.KV
    public final int e(int i) {
        return i;
    }

    @Override // com.android.tools.r8.internal.KV
    public final boolean f(int i) {
        return true;
    }

    @Override // com.android.tools.r8.internal.KV
    public final int g(int i) {
        return i;
    }

    @Override // com.android.tools.r8.internal.KV
    public final boolean d() {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.KV
    public final int e() {
        return this.a.size();
    }

    @Override // com.android.tools.r8.internal.KV
    public final AbstractC1915eM f() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.KV
    public final AbstractC1915eM b(int i) {
        return AbstractC1915eM.v();
    }

    @Override // com.android.tools.r8.internal.KV
    public final void c(C2102gl c2102gl) {
        c2102gl.a(0, c2102gl.h(), C3912j0.a());
    }

    @Override // com.android.tools.r8.internal.KV
    public final void a(C2102gl c2102gl, int i, boolean z) {
        ((Consumer) this.a.get(i)).accept(c2102gl);
    }

    @Override // com.android.tools.r8.internal.KV
    public final void a(C2102gl c2102gl, int i, int i2, boolean z) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.KV
    public final void a(int i, int i2, C2102gl c2102gl) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.KV
    public final void a(int i, int i2, int i3, C2102gl c2102gl) {
        throw new C3405y10();
    }
}
