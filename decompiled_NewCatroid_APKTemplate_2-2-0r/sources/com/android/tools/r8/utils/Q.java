package com.android.tools.r8.utils;

import com.android.tools.r8.internal.AbstractC1256Pa;
import com.android.tools.r8.internal.AbstractC1899eF;
import com.android.tools.r8.internal.C1303Qq;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class Q extends AbstractC1256Pa {
    private C1303Qq c;
    final /* synthetic */ T d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private Q(T t) {
        super(589824);
        this.d = t;
        this.c = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C1303Qq d(String str) {
        return new C1303Qq();
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void a(int i, int i2, String str, String str2, String str3, String[] strArr) {
        super.a(i, i2, str, str2, str3, strArr);
        this.c = (C1303Qq) this.d.a.computeIfAbsent(str, new Function() { // from class: com.android.tools.r8.utils.Q$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Q.d((String) obj);
            }
        });
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final AbstractC1899eF a(int i, String str, String str2, String str3, String[] strArr) {
        return new S(str + ";;" + str2, this.c);
    }
}
