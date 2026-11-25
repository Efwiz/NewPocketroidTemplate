package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xL, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3350xL extends AbstractC3327x1 {
    public abstract AbstractC3274wL a(int i);

    @Override // com.android.tools.r8.internal.InterfaceC2998sZ
    public final AbstractC3327x1 a() {
        return this;
    }

    abstract AbstractC3350xL a(int i, AbstractC3274wL abstractC3274wL);

    @Override // com.android.tools.r8.internal.AbstractC3327x1
    public final AbstractC3327x1 b(C0702m c0702m, AbstractC3327x1 abstractC3327x1) {
        AbstractC3350xL abstractC3350xL = (AbstractC3350xL) abstractC3327x1;
        if (this instanceof D5) {
            return abstractC3350xL;
        }
        abstractC3350xL.getClass();
        if (abstractC3350xL instanceof D5) {
            return this;
        }
        return ((this instanceof C2821q10) || (abstractC3350xL instanceof C2821q10)) ? C2821q10.h() : e().a(abstractC3350xL.e());
    }

    public BH e() {
        return null;
    }

    abstract AbstractC3350xL f();

    public boolean g() {
        return this instanceof C2821q10;
    }
}
