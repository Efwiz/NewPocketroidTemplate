package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Nt, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1222Nt extends AbstractC1249Ot implements Iterable {
    private final ArrayList a = new ArrayList();

    public final void a(String str) {
        this.a.add(str == null ? C1306Qt.a : new C1434Ut(str));
    }

    @Override // com.android.tools.r8.internal.AbstractC1249Ot
    public final int b() {
        if (this.a.size() == 1) {
            return ((AbstractC1249Ot) this.a.get(0)).b();
        }
        throw new IllegalStateException();
    }

    @Override // com.android.tools.r8.internal.AbstractC1249Ot
    public final long e() {
        if (this.a.size() == 1) {
            return ((AbstractC1249Ot) this.a.get(0)).e();
        }
        throw new IllegalStateException();
    }

    public final boolean equals(Object obj) {
        return obj == this || ((obj instanceof C1222Nt) && ((C1222Nt) obj).a.equals(this.a));
    }

    @Override // com.android.tools.r8.internal.AbstractC1249Ot
    public final String f() {
        if (this.a.size() == 1) {
            return ((AbstractC1249Ot) this.a.get(0)).f();
        }
        throw new IllegalStateException();
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return this.a.iterator();
    }

    public final AbstractC1249Ot j(int i) {
        return (AbstractC1249Ot) this.a.get(i);
    }

    public final int size() {
        return this.a.size();
    }

    public final void a(AbstractC1249Ot abstractC1249Ot) {
        if (abstractC1249Ot == null) {
            abstractC1249Ot = C1306Qt.a;
        }
        this.a.add(abstractC1249Ot);
    }

    @Override // com.android.tools.r8.internal.AbstractC1249Ot
    public final boolean a() {
        if (this.a.size() == 1) {
            return ((AbstractC1249Ot) this.a.get(0)).a();
        }
        throw new IllegalStateException();
    }
}
