package com.android.tools.r8.internal;

import com.android.tools.r8.BaseCompilerCommand$$ExternalSyntheticLambda0;
import com.android.tools.r8.utils.AbstractC3874b3;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class D3 {
    static final /* synthetic */ boolean f = true;
    private Collection a = Collections.emptyList();
    private boolean b = AbstractC3874b3.b("com.android.tools.r8.artprofilerewritingcompletenesscheck");
    private boolean c = false;
    private boolean d = false;
    private final com.android.tools.r8.utils.A1 e;

    public D3(com.android.tools.r8.utils.A1 a1) {
        this.e = a1;
    }

    public final List a() {
        if (!f && this.c && (!this.d || !this.a.isEmpty())) {
            throw new AssertionError();
        }
        this.c = true;
        return com.android.tools.r8.utils.V1.a(this.a, new BaseCompilerCommand$$ExternalSyntheticLambda0());
    }

    public final Collection b() {
        return this.a;
    }

    public final com.android.tools.r8.utils.A1 c() {
        return this.e;
    }

    public boolean d() {
        return (!this.b || this.e.C1.d() || this.e.G0().e() || this.e.F0().e()) ? false : true;
    }

    public final boolean e() {
        return this.b;
    }

    public final boolean f() {
        return this.b;
    }

    public final boolean g() {
        return this.b;
    }

    public final boolean h() {
        return this.b;
    }

    public final boolean i() {
        return this.b;
    }

    public final boolean j() {
        return this.b;
    }

    public D3 b(boolean z) {
        this.b = z;
        return this;
    }

    public D3 a(boolean z) {
        this.d = z;
        return this;
    }

    public final void a(List list) {
        this.a = list;
    }
}
