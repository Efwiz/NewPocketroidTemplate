package com.android.tools.r8.utils;

import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC1048Hm;
import com.android.tools.r8.internal.C0862Bm;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.utils.a0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class C3866a0 extends AbstractC3876c0 {
    static final /* synthetic */ boolean d = true;
    private final AbstractC3876c0 b;
    private final Set c;

    C3866a0(com.android.tools.r8.graph.I i, AbstractC3876c0 abstractC3876c0, AbstractC1048Hm abstractC1048Hm) {
        super(i);
        if (!d && (abstractC3876c0 instanceof C3866a0)) {
            throw new AssertionError("Nested Filtering class providers");
        }
        this.b = abstractC3876c0;
        this.c = abstractC1048Hm;
    }

    @Override // com.android.tools.r8.utils.AbstractC3876c0
    public final C3866a0 a(Set set) {
        C0862Bm c0862BmA = AbstractC1048Hm.g().a(this.c);
        Iterator it = set.iterator();
        while (it.hasNext()) {
            c0862BmA.a(it.next());
        }
        return new C3866a0(((AbstractC3876c0) this).a, this.b, c0862BmA.a());
    }

    public final String toString() {
        return this.b + " without " + this.c;
    }

    @Override // com.android.tools.r8.utils.AbstractC3876c0
    public final void a(C0767v2 c0767v2, Consumer consumer) {
        if (this.c.contains(c0767v2)) {
            return;
        }
        this.b.a(c0767v2, consumer);
    }

    @Override // com.android.tools.r8.utils.AbstractC3876c0
    public final Collection a() {
        Collection collectionA = this.b.a();
        collectionA.removeAll(this.c);
        return collectionA;
    }
}
