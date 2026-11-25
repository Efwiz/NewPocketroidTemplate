package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.iw, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2266iw extends AbstractC1057Hv {
    private final String b;
    private final Consumer c;
    private final ArrayList d = new ArrayList();

    public C2266iw(Consumer consumer, String str) {
        this.b = str;
        this.c = consumer;
    }

    @Override // com.android.tools.r8.internal.AbstractC1057Hv, com.android.tools.r8.internal.AbstractC2509m2
    public final void a(String str, Object obj) {
        if (obj instanceof String) {
            this.d.add((String) obj);
        } else {
            super.a(str, obj);
            throw null;
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1057Hv
    public final String c() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final void a() {
        super.a();
        this.c.accept(this.d);
    }
}
