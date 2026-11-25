package com.android.tools.r8.internal;

import java.util.Collection;

/* JADX INFO: renamed from: com.android.tools.r8.internal.cm, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1790cm extends AbstractC3232vm {
    C1790cm(int i, AbstractC2178hm abstractC2178hm) {
        super(i, abstractC2178hm);
    }

    @Override // com.android.tools.r8.internal.VF
    public final Collection get(Object obj) {
        AbstractC1639am abstractC1639am = (AbstractC1639am) this.e.get(obj);
        if (abstractC1639am != null) {
            return abstractC1639am;
        }
        int i = AbstractC1639am.b;
        return MS.d;
    }
}
