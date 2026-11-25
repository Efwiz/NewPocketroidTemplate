package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.InterfaceC3880d;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.hc, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2168hc extends AbstractC3425yH {
    static final /* synthetic */ boolean b = true;
    private Set a;

    AbstractC2168hc(Set set) {
        this.a = set;
    }

    @Override // com.android.tools.r8.internal.AbstractC3122uL
    public final AbstractC2168hc a() {
        return this;
    }

    final boolean b(C0702m c0702m) {
        Set set = this.a;
        if (set != null) {
            int size = set.size();
            c0702m.Y().c().getClass();
            if (size > 10) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC3122uL
    public final boolean d() {
        return true;
    }

    public C1497Xb g() {
        return null;
    }

    public C1624ac h() {
        return null;
    }

    public C2548mc i() {
        return null;
    }

    public AbstractC2862qc j() {
        return null;
    }

    public abstract AbstractC3122uL k();

    public final Set l() {
        if (!this.a.isEmpty()) {
            return new HashSet(this.a);
        }
        if (b || this.a == Collections.emptySet()) {
            return this.a;
        }
        throw new AssertionError();
    }

    public final Set m() {
        if (b || this.a.isEmpty() || (this.a instanceof HashSet)) {
            return this.a;
        }
        throw new AssertionError();
    }

    public final boolean n() {
        return !this.a.isEmpty();
    }

    final void o() {
        this.a = Collections.emptySet();
    }

    public boolean p() {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC3122uL
    public final AbstractC3122uL a(C0702m c0702m, AbstractC3122uL abstractC3122uL, C0767v2 c0767v2, FW fw, InterfaceC3880d interfaceC3880d) {
        if (abstractC3122uL.c()) {
            return this;
        }
        if (abstractC3122uL instanceof C2582n10) {
            return abstractC3122uL;
        }
        AbstractC2168hc abstractC2168hcA = abstractC3122uL.a();
        if (!p()) {
            return i().a(c0702m, abstractC2168hcA.i(), c0767v2, interfaceC3880d);
        }
        if (b || abstractC2168hcA.p()) {
            return j().a(c0702m, abstractC2168hcA.j(), c0767v2, interfaceC3880d);
        }
        throw new AssertionError();
    }

    final boolean a(AbstractC2168hc abstractC2168hc) {
        if (abstractC2168hc.a.isEmpty()) {
            return false;
        }
        if (this.a.isEmpty()) {
            if (!b && this.a != Collections.emptySet()) {
                throw new AssertionError();
            }
            this.a = new HashSet();
        }
        return this.a.addAll(abstractC2168hc.a);
    }
}
