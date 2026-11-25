package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class GD extends AbstractC3069tZ {
    GD(Iterator it) {
        super(it);
    }

    @Override // com.android.tools.r8.internal.AbstractC3069tZ
    final Object a(Object obj) {
        return ((Map.Entry) obj).getValue();
    }
}
