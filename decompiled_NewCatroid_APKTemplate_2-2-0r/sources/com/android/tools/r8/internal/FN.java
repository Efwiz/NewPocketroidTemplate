package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.function.BiConsumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class FN implements com.android.tools.r8.naming.M {
    private final LN a;
    private final LinkedHashMap b = new LinkedHashMap();
    private ArrayList c = new ArrayList();

    public FN(LN ln) {
        this.a = ln;
    }

    @Override // com.android.tools.r8.naming.M
    public final String a() {
        String strA = this.a.a();
        if (strA == null) {
            return null;
        }
        if (this.a.e()) {
            ArrayList arrayList = new ArrayList();
            this.c = arrayList;
            this.b.put(strA, arrayList);
        }
        this.c.add(strA);
        return strA;
    }

    @Override // com.android.tools.r8.naming.M
    public final void close() {
        this.a.close();
    }

    public final void a(BiConsumer biConsumer) {
        this.b.forEach(biConsumer);
    }
}
