package com.android.tools.r8.internal;

import java.util.ArrayList;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class LE extends ArrayList {
    final /* synthetic */ ME a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    LE(ME me) {
        super(0);
        this.a = me;
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean add(Object obj) {
        this.a.s = obj;
        return super.add(obj);
    }
}
