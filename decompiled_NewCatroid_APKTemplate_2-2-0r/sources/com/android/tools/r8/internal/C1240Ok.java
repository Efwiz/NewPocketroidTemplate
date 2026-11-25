package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0719o4;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ok, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1240Ok extends C0 {
    static final /* synthetic */ boolean j = true;
    private C0719o4 c;
    private C0767v2 d;
    private C0767v2 e;
    private C0759u2 f;
    private ArrayList g;
    private C0684j2 h;
    private C0725p4 i;

    public C1240Ok(C0722p1 c0722p1) {
        super(c0722p1);
    }

    private boolean e() {
        return this.h != null;
    }

    @Override // com.android.tools.r8.internal.AbstractC3402y0
    protected final void a(int i) {
        if (!j && e()) {
            throw new AssertionError();
        }
        this.c.a(i);
    }

    @Override // com.android.tools.r8.internal.C0
    protected final void b(C0767v2 c0767v2) {
        if (!j && e()) {
            throw new AssertionError();
        }
        this.e = c0767v2;
    }

    public final C0725p4 c() {
        if (j || e()) {
            return this.i;
        }
        throw new AssertionError();
    }

    public final C0684j2 d() {
        if (j || e()) {
            return this.h;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.C0
    protected final void a(C0759u2 c0759u2) {
        if (!j && e()) {
            throw new AssertionError();
        }
        this.f = c0759u2;
    }

    @Override // com.android.tools.r8.internal.C0
    protected final void b() {
        this.c = C0725p4.O();
        this.d = null;
        this.e = null;
        this.f = null;
        this.g = new ArrayList();
        this.h = null;
        this.i = null;
    }

    @Override // com.android.tools.r8.internal.C0
    protected final void c(C0767v2 c0767v2) {
        if (!j && e()) {
            throw new AssertionError();
        }
        this.d = c0767v2;
    }

    @Override // com.android.tools.r8.internal.C0
    protected final void a() {
        this.h = this.a.a(this.e, this.a.a(this.d, this.g), this.f);
        this.i = (C0725p4) this.c.a();
    }

    @Override // com.android.tools.r8.internal.C0
    protected final void a(C0767v2 c0767v2) {
        if (!j && e()) {
            throw new AssertionError();
        }
        this.g.add(c0767v2);
    }
}
