package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class BS extends AbstractC3472z00 {
    private static final AS d = new AS(WH.c());
    private static final AS e = new AS(WH.a());
    final WH c;

    BS(WH wh) {
        this.c = wh;
    }

    static BS Q() {
        return d;
    }

    @Override // com.android.tools.r8.internal.AbstractC3472z00
    public final boolean I() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC3472z00
    public final WH N() {
        return this.c;
    }

    public AbstractC3472z00 P() {
        return a(this.c.b(WH.b()));
    }

    public abstract C0767v2 a(C0722p1 c0722p1);

    public abstract BS a(BS bs, C0702m c0702m);

    public abstract BS a(WH wh);

    public BS b(WH wh) {
        return a(N().a(wh));
    }

    @Override // com.android.tools.r8.internal.AbstractC3472z00
    public final BS d() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC3472z00
    public boolean equals(Object obj) {
        throw new C3405y10("Should be implemented on each sub type");
    }

    @Override // com.android.tools.r8.internal.AbstractC3472z00
    public int hashCode() {
        throw new C3405y10("Should be implemented on each sub type");
    }
}
