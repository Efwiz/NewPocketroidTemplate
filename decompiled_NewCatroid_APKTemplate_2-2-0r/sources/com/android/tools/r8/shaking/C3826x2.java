package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC2963s10;
import com.android.tools.r8.internal.C1507Xl;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.x2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3826x2 {
    private final AbstractC1639am a;

    /* JADX INFO: renamed from: com.android.tools.r8.shaking.x2$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        private final C1507Xl a;

        private a() {
            this.a = AbstractC1639am.g();
        }

        public a a(AbstractC3833y2 abstractC3833y2) {
            this.a.a(abstractC3833y2);
            return this;
        }

        C3826x2 a() {
            return new C3826x2(this.a.a());
        }
    }

    static {
        AbstractC1639am.i();
    }

    private C3826x2(AbstractC1639am abstractC1639am) {
        this.a = abstractC1639am;
    }

    public static a a() {
        return new a();
    }

    public final AbstractC1639am b() {
        return this.a;
    }

    public final boolean c() {
        return this.a.size() == 0;
    }

    public boolean a(C0767v2 c0767v2) {
        AbstractC2963s10 it = this.a.iterator();
        while (it.hasNext()) {
            if (((AbstractC3833y2) it.next()).a(c0767v2)) {
                return true;
            }
        }
        return false;
    }
}
