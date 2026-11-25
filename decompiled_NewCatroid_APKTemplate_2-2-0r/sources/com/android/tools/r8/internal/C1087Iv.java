package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Iv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1087Iv extends AbstractC2188hw {
    private C1087Iv() {
        super();
    }

    @Override // com.android.tools.r8.internal.AbstractC1116Jv
    final String a() {
        return "class";
    }

    final Object b() {
        return AbstractC2496lv.a(C2424kx.a());
    }

    final Object a(Object obj, String str) {
        if (str.equals("classFromBinding") && (obj instanceof String)) {
            return AbstractC2496lv.a((String) obj);
        }
        if (str.equals("classConstant") && (obj instanceof CZ)) {
            return AbstractC2496lv.a(C2424kx.a(((CZ) obj).a()));
        }
        if (str.equals("className") && (obj instanceof String)) {
            return AbstractC2496lv.a(C2424kx.a((String) obj));
        }
        return null;
    }
}
