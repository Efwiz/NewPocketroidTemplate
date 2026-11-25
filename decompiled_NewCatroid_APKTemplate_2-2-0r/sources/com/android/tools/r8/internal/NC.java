package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class NC extends AbstractC1578a1 implements HC {
    final /* synthetic */ QC a;

    private NC(QC qc) {
        this.a = qc;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        this.a.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        QC qc;
        long j;
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        if (entry.getKey() == null || !(entry.getKey() instanceof Long)) {
            return false;
        }
        long jLongValue = ((Long) entry.getKey()).longValue();
        Object value = entry.getValue();
        if (jLongValue == 0) {
            QC qc2 = this.a;
            return qc2.d && qc2.b[qc2.e] == value;
        }
        long[] jArr = this.a.a;
        int iB = (int) AbstractC3307wk.b(jLongValue);
        QC qc3 = this.a;
        int i = iB & qc3.c;
        long j2 = jArr[i];
        if (j2 == 0) {
            return false;
        }
        if (jLongValue == j2) {
            return qc3.b[i] == value;
        }
        do {
            qc = this.a;
            i = (i + 1) & qc.c;
            j = jArr[i];
            if (j == 0) {
                return false;
            }
        } while (jLongValue != j);
        return qc.b[i] == value;
    }

    @Override // com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final InterfaceC1912eK iterator() {
        return new JC(this.a, 0);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        if (entry.getKey() == null || !(entry.getKey() instanceof Long)) {
            return false;
        }
        long jLongValue = ((Long) entry.getKey()).longValue();
        Object value = entry.getValue();
        if (jLongValue == 0) {
            QC qc = this.a;
            if (!qc.d || qc.b[qc.e] != value) {
                return false;
            }
            QC.a(qc);
            return true;
        }
        long[] jArr = this.a.a;
        int iB = (int) AbstractC3307wk.b(jLongValue);
        QC qc2 = this.a;
        int i = iB & qc2.c;
        long j = jArr[i];
        if (j == 0) {
            return false;
        }
        if (j == jLongValue) {
            if (qc2.b[i] != value) {
                return false;
            }
            qc2.e(i);
            return true;
        }
        while (true) {
            QC qc3 = this.a;
            i = (i + 1) & qc3.c;
            long j2 = jArr[i];
            if (j2 == 0) {
                return false;
            }
            if (j2 == jLongValue && qc3.b[i] == value) {
                qc3.e(i);
                return true;
            }
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.a.g;
    }

    /* synthetic */ NC(QC qc, int i) {
        this(qc);
    }
}
