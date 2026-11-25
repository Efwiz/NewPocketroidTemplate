package com.android.tools.r8.shaking;

import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC2963s10;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.z3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3840z3 {
    static final /* synthetic */ boolean c = true;
    private final boolean a;
    private final AbstractC1639am b;

    private C3840z3(AbstractC1639am abstractC1639am, boolean z) {
        this.a = z;
        if (abstractC1639am.isEmpty()) {
            this.b = AbstractC1639am.c(E3.b());
        } else {
            if (!c && !z) {
                throw new AssertionError();
            }
            this.b = abstractC1639am;
        }
    }

    public static C3834y3 a() {
        return new C3834y3();
    }

    public final boolean b() {
        return this.a;
    }

    public boolean a(String str) {
        if (!this.a) {
            return false;
        }
        AbstractC2963s10 it = this.b.iterator();
        while (it.hasNext()) {
            if (((E3) it.next()).a(str)) {
                return true;
            }
        }
        return false;
    }
}
