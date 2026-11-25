package com.android.tools.r8.internal;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

/* JADX INFO: renamed from: com.android.tools.r8.internal.bu, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1722bu extends C1799cu {
    private static final Writer o = new C1647au();
    private static final C1434Ut p = new C1434Ut("closed");
    private final ArrayList l;
    private String m;
    private AbstractC1249Ot n;

    public C1722bu() {
        super(o);
        this.l = new ArrayList();
        this.n = C1306Qt.a;
    }

    private void a(AbstractC1249Ot abstractC1249Ot) {
        if (this.m != null) {
            if (!(abstractC1249Ot instanceof C1306Qt) || h()) {
                ArrayList arrayList = this.l;
                ((C1333Rt) ((AbstractC1249Ot) arrayList.get(arrayList.size() - 1))).a(this.m, abstractC1249Ot);
            }
            this.m = null;
            return;
        }
        if (this.l.isEmpty()) {
            this.n = abstractC1249Ot;
            return;
        }
        ArrayList arrayList2 = this.l;
        AbstractC1249Ot abstractC1249Ot2 = (AbstractC1249Ot) arrayList2.get(arrayList2.size() - 1);
        if (!(abstractC1249Ot2 instanceof C1222Nt)) {
            throw new IllegalStateException();
        }
        ((C1222Nt) abstractC1249Ot2).a(abstractC1249Ot);
    }

    @Override // com.android.tools.r8.internal.C1799cu
    public final void b(String str) {
        if (this.l.isEmpty() || this.m != null) {
            throw new IllegalStateException();
        }
        ArrayList arrayList = this.l;
        if (!(((AbstractC1249Ot) arrayList.get(arrayList.size() - 1)) instanceof C1333Rt)) {
            throw new IllegalStateException();
        }
        this.m = str;
    }

    @Override // com.android.tools.r8.internal.C1799cu, java.io.Closeable, java.lang.AutoCloseable
    public final void close() throws IOException {
        if (!this.l.isEmpty()) {
            throw new IOException("Incomplete document");
        }
        this.l.add(p);
    }

    @Override // com.android.tools.r8.internal.C1799cu
    public final void d() {
        C1222Nt c1222Nt = new C1222Nt();
        a(c1222Nt);
        this.l.add(c1222Nt);
    }

    @Override // com.android.tools.r8.internal.C1799cu
    public final void e() {
        C1333Rt c1333Rt = new C1333Rt();
        a(c1333Rt);
        this.l.add(c1333Rt);
    }

    @Override // com.android.tools.r8.internal.C1799cu
    public final void f() {
        if (this.l.isEmpty() || this.m != null) {
            throw new IllegalStateException();
        }
        ArrayList arrayList = this.l;
        if (!(((AbstractC1249Ot) arrayList.get(arrayList.size() - 1)) instanceof C1222Nt)) {
            throw new IllegalStateException();
        }
        ArrayList arrayList2 = this.l;
        arrayList2.remove(arrayList2.size() - 1);
    }

    @Override // com.android.tools.r8.internal.C1799cu, java.io.Flushable
    public final void flush() {
    }

    @Override // com.android.tools.r8.internal.C1799cu
    public final void g() {
        if (this.l.isEmpty() || this.m != null) {
            throw new IllegalStateException();
        }
        ArrayList arrayList = this.l;
        if (!(((AbstractC1249Ot) arrayList.get(arrayList.size() - 1)) instanceof C1333Rt)) {
            throw new IllegalStateException();
        }
        ArrayList arrayList2 = this.l;
        arrayList2.remove(arrayList2.size() - 1);
    }

    @Override // com.android.tools.r8.internal.C1799cu
    public final C1799cu l() {
        a(C1306Qt.a);
        return this;
    }

    public final AbstractC1249Ot o() {
        if (this.l.isEmpty()) {
            return this.n;
        }
        throw new IllegalStateException("Expected one JSON element but was " + this.l);
    }

    @Override // com.android.tools.r8.internal.C1799cu
    public final void d(String str) {
        if (str == null) {
            a(C1306Qt.a);
        } else {
            a(new C1434Ut(str));
        }
    }

    @Override // com.android.tools.r8.internal.C1799cu
    public final void d(boolean z) {
        a(new C1434Ut(Boolean.valueOf(z)));
    }

    @Override // com.android.tools.r8.internal.C1799cu
    public final void a(Boolean bool) {
        if (bool == null) {
            a(C1306Qt.a);
        } else {
            a(new C1434Ut(bool));
        }
    }

    @Override // com.android.tools.r8.internal.C1799cu
    public final void a(Number number) {
        if (number == null) {
            a(C1306Qt.a);
            return;
        }
        if (!j()) {
            double dDoubleValue = number.doubleValue();
            if (Double.isNaN(dDoubleValue) || Double.isInfinite(dDoubleValue)) {
                throw new IllegalArgumentException("JSON forbids NaN and infinities: " + number);
            }
        }
        a(new C1434Ut(number));
    }

    @Override // com.android.tools.r8.internal.C1799cu
    public final void a(long j) {
        a(new C1434Ut((Number) Long.valueOf(j)));
    }
}
