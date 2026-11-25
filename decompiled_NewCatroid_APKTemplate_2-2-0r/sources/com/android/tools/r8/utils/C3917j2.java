package com.android.tools.r8.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.utils.j2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class C3917j2 {
    static final /* synthetic */ boolean c = true;
    private final Collection a;
    private boolean b = false;

    private C3917j2(ArrayList arrayList) {
        this.a = arrayList;
    }

    public final void a(com.android.tools.r8.naming.mappinginformation.d dVar) {
        if (!c && this.b) {
            throw new AssertionError();
        }
        this.a.add(dVar);
    }

    public final void a(Consumer consumer) {
        this.b = true;
        this.a.forEach(consumer);
        this.a.clear();
    }

    public static C3917j2 a(ArrayList arrayList) {
        return new C3917j2(arrayList);
    }

    public final boolean a() {
        return this.a.isEmpty();
    }
}
