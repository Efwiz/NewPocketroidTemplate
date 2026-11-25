package com.android.tools.r8.errors;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class a {
    private final ArrayList a = new ArrayList();
    private Origin b;
    private Position c;

    public final a a(Set set) {
        Iterator it = set.iterator();
        while (it.hasNext()) {
            this.a.add(((C0684j2) it.next()).E0());
        }
        return this;
    }

    public final a a(Origin origin) {
        this.b = origin;
        return this;
    }

    public final a a(Position position) {
        this.c = position;
        return this;
    }

    public final AssumeNoSideEffectsRuleForObjectMembersDiagnostic a() {
        return new AssumeNoSideEffectsRuleForObjectMembersDiagnostic(this.a, this.b, this.c);
    }
}
