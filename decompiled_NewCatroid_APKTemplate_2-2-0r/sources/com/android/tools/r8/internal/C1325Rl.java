package com.android.tools.r8.internal;

import java.io.Serializable;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Rl, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
class C1325Rl extends AbstractC3325x0 implements Serializable {
    final Object a;
    final Object b;

    C1325Rl(Object obj, Object obj2) {
        this.a = obj;
        this.b = obj2;
    }

    @Override // java.util.Map.Entry
    public final Object getKey() {
        return this.a;
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.AbstractC3325x0, java.util.Map.Entry
    public final Object setValue(Object obj) {
        throw new UnsupportedOperationException();
    }
}
