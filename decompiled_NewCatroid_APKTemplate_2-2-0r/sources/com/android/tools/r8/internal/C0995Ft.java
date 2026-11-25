package com.android.tools.r8.internal;

import com.huawei.hms.framework.common.ContainerUtils;
import java.util.HashMap;
import java.util.Map;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ft, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0995Ft extends AbstractC1214Nl {
    private final transient AbstractC1639am e;
    private final Map f;
    private final Map g;
    private transient C0995Ft h;

    private C0995Ft(AbstractC1639am abstractC1639am, Map map, Map map2) {
        this.e = abstractC1639am;
        this.f = map;
        this.g = map2;
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm, java.util.Map
    public final Object get(Object obj) {
        return this.f.get(obj);
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    final AbstractC1048Hm k() {
        return new C2486lm(this, this.e);
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    final AbstractC1048Hm l() {
        return new C2638nm(this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    final boolean o() {
        return false;
    }

    @Override // java.util.Map
    public final int size() {
        return this.e.size();
    }

    @Override // com.android.tools.r8.internal.AbstractC1214Nl
    /* JADX INFO: renamed from: u */
    public final AbstractC1214Nl g() {
        C0995Ft c0995Ft = this.h;
        if (c0995Ft != null) {
            return c0995Ft;
        }
        C0995Ft c0995Ft2 = new C0995Ft(new C0969Et(this, 0), this.g, this.f);
        this.h = c0995Ft2;
        c0995Ft2.h = this;
        return c0995Ft2;
    }

    static AbstractC1214Nl a(int i, Map.Entry[] entryArr) {
        HashMap mapB = RD.b(i);
        HashMap mapB2 = RD.b(i);
        for (int i2 = 0; i2 < i; i2++) {
            Map.Entry entry = entryArr[i2];
            AbstractC2178hm abstractC2178hm = PS.h;
            C2410km c2410km = ((entry instanceof C2410km) && ((C2410km) entry).d()) ? (C2410km) entry : new C2410km(entry.getKey(), entry.getValue());
            entryArr[i2] = c2410km;
            Object objPutIfAbsent = mapB.putIfAbsent(c2410km.a, c2410km.b);
            if (objPutIfAbsent != null) {
                String strValueOf = String.valueOf(c2410km.a);
                String strValueOf2 = String.valueOf(objPutIfAbsent);
                throw AbstractC2178hm.a(ASTExpr.DEFAULT_MAP_KEY_NAME, new StringBuilder(strValueOf2.length() + strValueOf.length() + 1).append(strValueOf).append(ContainerUtils.KEY_VALUE_DELIMITER).append(strValueOf2).toString(), entryArr[i2]);
            }
            Object objPutIfAbsent2 = mapB2.putIfAbsent(c2410km.b, c2410km.a);
            if (objPutIfAbsent2 != null) {
                String strValueOf3 = String.valueOf(objPutIfAbsent2);
                String strValueOf4 = String.valueOf(c2410km.b);
                throw AbstractC2178hm.a("value", new StringBuilder(strValueOf4.length() + strValueOf3.length() + 1).append(strValueOf3).append(ContainerUtils.KEY_VALUE_DELIMITER).append(strValueOf4).toString(), entryArr[i2]);
            }
        }
        return new C0995Ft(AbstractC1639am.b(i, entryArr), mapB, mapB2);
    }
}
