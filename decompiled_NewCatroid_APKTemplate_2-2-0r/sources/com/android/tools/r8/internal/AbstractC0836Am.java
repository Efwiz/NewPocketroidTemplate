package com.android.tools.r8.internal;

import java.util.Arrays;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Am, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0836Am extends AbstractC1271Pl implements InterfaceC1600aG {
    public static final /* synthetic */ int d = 0;
    private transient AbstractC1639am b;
    private transient AbstractC1048Hm c;

    AbstractC0836Am() {
    }

    public static C3386xm g() {
        return new C3386xm(AB.f());
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    public final AbstractC1639am a() {
        AbstractC1639am abstractC1639am = this.b;
        if (abstractC1639am != null) {
            return abstractC1639am;
        }
        AbstractC1639am abstractC1639amA = super.a();
        this.b = abstractC1639amA;
        return abstractC1639amA;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(Object obj) {
        return b(obj) > 0;
    }

    @Override // java.util.Collection, com.android.tools.r8.internal.InterfaceC1600aG
    public final boolean equals(Object obj) {
        return AbstractC1987fG.a(this, obj);
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    /* JADX INFO: renamed from: f */
    public final AbstractC2963s10 iterator() {
        return new C3309wm(entrySet().iterator());
    }

    @Override // java.util.Collection, com.android.tools.r8.internal.InterfaceC1600aG
    public int hashCode() {
        return OU.a((Set) entrySet());
    }

    @Override // com.android.tools.r8.internal.InterfaceC1600aG
    /* JADX INFO: renamed from: i, reason: merged with bridge method [inline-methods] */
    public abstract AbstractC1048Hm I();

    @Override // com.android.tools.r8.internal.InterfaceC1600aG
    /* JADX INFO: renamed from: j, reason: merged with bridge method [inline-methods] */
    public final AbstractC1048Hm entrySet() {
        AbstractC1048Hm c3526zm = this.c;
        if (c3526zm == null) {
            c3526zm = isEmpty() ? SS.h : new C3526zm(this, 0);
            this.c = c3526zm;
        }
        return c3526zm;
    }

    abstract ZF j(int i);

    @Override // java.util.AbstractCollection
    public final String toString() {
        return entrySet().toString();
    }

    @Override // com.android.tools.r8.internal.InterfaceC1600aG
    public final int a(Object obj, int i) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.InterfaceC1600aG
    public final boolean a(int i, Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final int a(int i, Object[] objArr) {
        AbstractC2963s10 it = entrySet().iterator();
        while (it.hasNext()) {
            ZF zf = (ZF) it.next();
            Arrays.fill(objArr, i, zf.a() + i, zf.b());
            i += zf.a();
        }
        return i;
    }
}
