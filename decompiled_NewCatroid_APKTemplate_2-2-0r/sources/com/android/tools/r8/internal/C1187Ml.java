package com.android.tools.r8.internal;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ml, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1187Ml extends C1946em {
    @Override // com.android.tools.r8.internal.C1946em
    public final C1946em a(Comparator comparator) {
        throw null;
    }

    @Override // com.android.tools.r8.internal.C1946em
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final AbstractC1214Nl a() {
        int i = this.c;
        if (i == 0) {
            return LS.k;
        }
        if (i == 1) {
            return new C3439yV(this.b[0].getKey(), this.b[0].getValue());
        }
        if (this.a != null) {
            if (this.d) {
                this.b = (Map.Entry[]) Arrays.copyOf(this.b, i);
            }
            Map.Entry[] entryArr = this.b;
            int i2 = this.c;
            Comparator comparator = this.a;
            OK c1063Ib = comparator instanceof OK ? (OK) comparator : new C1063Ib(comparator);
            KD kd = LD.a;
            c1063Ib.getClass();
            Arrays.sort(entryArr, 0, i2, new P5(kd, c1063Ib));
        }
        this.d = true;
        return LS.a(this.c, this.b);
    }

    @Override // com.android.tools.r8.internal.C1946em
    public final C1946em a(Object obj, Object obj2) {
        super.a(obj, obj2);
        return this;
    }

    public final void a(String str, String str2) {
        super.a((Object) str, (Object) str2);
    }
}
