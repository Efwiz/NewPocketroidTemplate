package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class O extends K implements InterfaceC0993Fr, InterfaceC3018sr {
    protected O() {
    }

    public void a(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator
    public final void add(Object obj) {
        a(((Integer) obj).intValue());
    }

    public void b(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator, com.android.tools.r8.internal.YJ
    public final Object previous() {
        return Integer.valueOf(i());
    }

    @Override // java.util.ListIterator
    public final void set(Object obj) {
        b(((Integer) obj).intValue());
    }
}
