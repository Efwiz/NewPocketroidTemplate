package com.android.tools.r8.internal;

import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Kv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1144Kv extends AbstractC2188hw {
    private C1144Kv() {
        super();
    }

    @Override // com.android.tools.r8.internal.AbstractC1116Jv
    final String a() {
        return PsiKeyword.EXTENDS;
    }

    final Object b() {
        return AbstractC2884qw.a();
    }

    final Object a(Object obj, String str) {
        if (str.equals("extendsClassConstant") && (obj instanceof CZ)) {
            return AbstractC2884qw.c().a(C2424kx.a(((CZ) obj).a())).a();
        }
        if (str.equals("extendsClassName") && (obj instanceof String)) {
            return AbstractC2884qw.c().a(C2424kx.a((String) obj)).a();
        }
        return null;
    }
}
