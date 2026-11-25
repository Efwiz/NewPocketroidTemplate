package com.android.tools.r8.internal;

import java.util.Map;
import java.util.function.BiConsumer;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class PS extends AbstractC2178hm {
    static final AbstractC2178hm h = new PS(AbstractC2178hm.d, null, 0);
    final transient Map.Entry[] e;
    private final transient C2410km[] f;
    private final transient int g;

    private PS(Map.Entry[] entryArr, C2410km[] c2410kmArr, int i) {
        this.e = entryArr;
        this.f = c2410kmArr;
        this.g = i;
    }

    static AbstractC2178hm a(int i, Map.Entry[] entryArr) {
        AbstractC3123uM.b(i, entryArr.length);
        if (i == 0) {
            return (PS) h;
        }
        Map.Entry[] entryArr2 = i == entryArr.length ? entryArr : new C2410km[i];
        int iA = AbstractC0900Ck.a(i, 1.2d);
        C2410km[] c2410kmArr = new C2410km[iA];
        int i2 = iA - 1;
        for (int i3 = 0; i3 < i; i3++) {
            Map.Entry entry = entryArr[i3];
            Object key = entry.getKey();
            Object value = entry.getValue();
            AbstractC2625nb.a(key, value);
            int iA2 = AbstractC0900Ck.a(key.hashCode()) & i2;
            C2410km c2410kmB = c2410kmArr[iA2];
            C2410km c2410km = c2410kmB == null ? ((entry instanceof C2410km) && ((C2410km) entry).d()) ? (C2410km) entry : new C2410km(key, value) : new C2330jm(key, value, c2410kmB);
            c2410kmArr[iA2] = c2410km;
            entryArr2[i3] = c2410km;
            int i4 = 0;
            while (c2410kmB != null) {
                if (key.equals(c2410kmB.a)) {
                    throw AbstractC2178hm.a(ASTExpr.DEFAULT_MAP_KEY_NAME, c2410km, c2410kmB);
                }
                i4++;
                c2410kmB = c2410kmB.b();
            }
            if (i4 > 8) {
                return C1024Gt.a(i, entryArr);
            }
        }
        return new PS(entryArr2, c2410kmArr, i2);
    }

    @Override // java.util.Map
    public final void forEach(BiConsumer biConsumer) {
        int i = AbstractC3123uM.a;
        biConsumer.getClass();
        for (Map.Entry entry : this.e) {
            biConsumer.accept(entry.getKey(), entry.getValue());
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm, java.util.Map
    public final Object get(Object obj) {
        C2410km[] c2410kmArr = this.f;
        int i = this.g;
        if (obj != null && c2410kmArr != null) {
            for (C2410km c2410kmB = c2410kmArr[i & AbstractC0900Ck.a(obj.hashCode())]; c2410kmB != null; c2410kmB = c2410kmB.b()) {
                if (obj.equals(c2410kmB.a)) {
                    return c2410kmB.b;
                }
            }
        }
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    final AbstractC1048Hm k() {
        return new C2486lm(this, AbstractC1639am.a(this.e));
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    final AbstractC1048Hm l() {
        return new NS(this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    final AbstractC1271Pl m() {
        return new OS(this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    final boolean o() {
        return false;
    }

    @Override // java.util.Map
    public final int size() {
        return this.e.length;
    }
}
