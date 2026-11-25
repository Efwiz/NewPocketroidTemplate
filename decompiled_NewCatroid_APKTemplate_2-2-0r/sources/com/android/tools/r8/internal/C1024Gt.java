package com.android.tools.r8.internal;

import com.huawei.hms.framework.common.ContainerUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Gt, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1024Gt extends AbstractC2178hm {
    private final transient Map e;
    private final transient AbstractC1639am f;

    C1024Gt(HashMap map, AbstractC1639am abstractC1639am) {
        this.e = map;
        this.f = abstractC1639am;
    }

    static AbstractC2178hm a(int i, Map.Entry[] entryArr) {
        HashMap mapB = RD.b(i);
        for (int i2 = 0; i2 < i; i2++) {
            Map.Entry entry = entryArr[i2];
            AbstractC2178hm abstractC2178hm = PS.h;
            C2410km c2410km = ((entry instanceof C2410km) && ((C2410km) entry).d()) ? (C2410km) entry : new C2410km(entry.getKey(), entry.getValue());
            entryArr[i2] = c2410km;
            Object objPutIfAbsent = mapB.putIfAbsent(c2410km.a, c2410km.getValue());
            if (objPutIfAbsent != null) {
                Map.Entry entry2 = entryArr[i2];
                String strValueOf = String.valueOf(entry2.getKey());
                String strValueOf2 = String.valueOf(objPutIfAbsent);
                throw AbstractC2178hm.a(ASTExpr.DEFAULT_MAP_KEY_NAME, entry2, new StringBuilder(strValueOf2.length() + strValueOf.length() + 1).append(strValueOf).append(ContainerUtils.KEY_VALUE_DELIMITER).append(strValueOf2).toString());
            }
        }
        return new C1024Gt(mapB, AbstractC1639am.b(i, entryArr));
    }

    @Override // java.util.Map
    public final void forEach(final BiConsumer biConsumer) {
        int i = AbstractC3123uM.a;
        biConsumer.getClass();
        this.f.forEach(new Consumer() { // from class: com.android.tools.r8.internal.Gt$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1024Gt.a(biConsumer, (Map.Entry) obj);
            }
        });
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm, java.util.Map
    public final Object get(Object obj) {
        return this.e.get(obj);
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    final AbstractC1048Hm k() {
        return new C2486lm(this, this.f);
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    final AbstractC1048Hm l() {
        return new C2638nm(this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    final AbstractC1271Pl m() {
        return new C2872qm(this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    final boolean o() {
        return false;
    }

    @Override // java.util.Map
    public final int size() {
        return this.f.size();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(BiConsumer biConsumer, Map.Entry entry) {
        biConsumer.accept(entry.getKey(), entry.getValue());
    }
}
