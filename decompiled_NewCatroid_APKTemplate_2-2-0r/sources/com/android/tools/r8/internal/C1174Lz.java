package com.android.tools.r8.internal;

import java.util.HashSet;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Lz, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1174Lz {
    private final HashSet a;
    private final HashSet b;

    private C1174Lz() {
        this.a = new HashSet();
        this.b = new HashSet();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(List list) {
        com.android.tools.r8.utils.V1.a(list, new com.android.tools.r8.utils.U1() { // from class: com.android.tools.r8.internal.Lz$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.U1
            public final void accept(Object obj, int i) {
                this.f$0.a((C3354xO) obj, i);
            }
        });
    }

    public final C1174Lz a(C3354xO c3354xO, int i) {
        if (!c3354xO.b(AbstractC1543Yu.b)) {
            this.a.add(Integer.valueOf(i));
        }
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(List list) {
        com.android.tools.r8.utils.V1.a(list, new com.android.tools.r8.utils.U1() { // from class: com.android.tools.r8.internal.Lz$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.utils.U1
            public final void accept(Object obj, int i) {
                this.f$0.a((C1998fO) obj, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C1998fO c1998fO, int i) {
        if (c1998fO.b(AbstractC1543Yu.a)) {
            return;
        }
        this.b.add(Integer.valueOf(i));
    }
}
