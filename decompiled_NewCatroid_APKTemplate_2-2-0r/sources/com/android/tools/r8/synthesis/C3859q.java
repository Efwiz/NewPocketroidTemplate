package com.android.tools.r8.synthesis;

import com.android.tools.r8.internal.AbstractC2734ot;
import com.android.tools.r8.internal.InterfaceC3205vM;
import com.android.tools.r8.shaking.N1;
import com.android.tools.r8.utils.AbstractC3938n2;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.synthesis.q, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3859q {
    static final /* synthetic */ boolean d = true;
    private final List a;
    private final AbstractC3855m b;
    private final AbstractC3938n2 c;

    private C3859q(AbstractC3855m abstractC3855m, List list, AbstractC3938n2 abstractC3938n2) {
        boolean z = d;
        if (!z && abstractC3855m == null) {
            throw new AssertionError();
        }
        if (!z && list == null) {
            throw new AssertionError();
        }
        if (!z && abstractC3938n2 == null) {
            throw new AssertionError();
        }
        this.a = list;
        this.b = abstractC3855m;
        this.c = abstractC3938n2;
    }

    public final void a(Consumer consumer) {
        consumer.accept(this.b);
        this.a.forEach(consumer);
    }

    public final void b(Consumer consumer) {
        this.a.forEach(consumer);
    }

    public final String toString() {
        return "EquivalenceGroup{ size = " + (this.a.size() + 1) + ", repr = " + this.b + " }";
    }

    final AbstractC3855m a() {
        return this.b;
    }

    final boolean a(final N1 n1) {
        return this.b.d().a(n1) || AbstractC2734ot.b(this.a, new InterfaceC3205vM() { // from class: com.android.tools.r8.synthesis.q$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return C3859q.a(n1, (AbstractC3855m) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(N1 n1, AbstractC3855m abstractC3855m) {
        return abstractC3855m.d().a(n1);
    }
}
