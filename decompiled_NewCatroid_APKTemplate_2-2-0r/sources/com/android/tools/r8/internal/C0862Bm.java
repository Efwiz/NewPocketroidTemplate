package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Bm, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0862Bm extends AbstractC1241Ol {
    private AbstractC1016Gm a;
    boolean b;

    public C0862Bm() {
        this(0);
    }

    public final C0862Bm a(Object... objArr) {
        for (Object obj : objArr) {
            a(obj);
        }
        return this;
    }

    C0862Bm(int i) {
        this.a = new C0988Fm(4);
    }

    public final C0862Bm a(Set set) {
        Iterator it = set.iterator();
        while (it.hasNext()) {
            a(it.next());
        }
        return this;
    }

    public final C0862Bm a(Object obj) {
        int i = AbstractC3123uM.a;
        obj.getClass();
        if (this.b) {
            this.a = this.a.b();
            this.b = false;
        }
        this.a = this.a.a(obj);
        return this;
    }

    public final AbstractC1048Hm a() {
        this.b = true;
        AbstractC1016Gm abstractC1016GmC = this.a.c();
        this.a = abstractC1016GmC;
        return abstractC1016GmC.a();
    }
}
