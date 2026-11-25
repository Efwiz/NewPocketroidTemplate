package com.android.tools.r8.graph;

import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class L4 extends J4 {
    static final L4 d = new L4(Collections.emptyList());

    private L4(List list) {
        super(list);
    }

    static L4 a(List list) {
        return list.isEmpty() ? d : new L4(list);
    }

    @Override // com.android.tools.r8.graph.I4
    public final boolean v() {
        return true;
    }
}
