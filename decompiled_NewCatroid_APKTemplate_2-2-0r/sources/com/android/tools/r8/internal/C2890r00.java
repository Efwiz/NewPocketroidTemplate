package com.android.tools.r8.internal;

import java.io.IOException;
import java.util.HashMap;

/* JADX INFO: renamed from: com.android.tools.r8.internal.r00, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2890r00 extends EZ {
    private final HashMap a = new HashMap();
    private final HashMap b = new HashMap();

    public C2890r00(Class cls) {
        try {
            for (Enum r4 : (Enum[]) cls.getEnumConstants()) {
                String strName = r4.name();
                DU du = (DU) cls.getField(strName).getAnnotation(DU.class);
                if (du != null) {
                    strName = du.value();
                    for (String str : du.alternate()) {
                        this.a.put(str, r4);
                    }
                }
                this.a.put(strName, r4);
                this.b.put(r4, strName);
            }
        } catch (NoSuchFieldException e) {
            throw new AssertionError(e);
        }
    }

    @Override // com.android.tools.r8.internal.EZ
    public final void a(C1799cu c1799cu, Object obj) throws IOException {
        Enum r3 = (Enum) obj;
        c1799cu.d(r3 == null ? null : (String) this.b.get(r3));
    }

    @Override // com.android.tools.r8.internal.EZ
    public final Object a(C1486Wt c1486Wt) throws IOException {
        if (c1486Wt.t() == 9) {
            c1486Wt.q();
            return null;
        }
        return (Enum) this.a.get(c1486Wt.r());
    }
}
