package com.android.tools.r8.shaking;

import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.v3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3815v3 {
    private static final AbstractC3815v3 a = new C3796s3(new O3.b("*"));
    static final /* synthetic */ boolean b = true;

    private AbstractC3815v3() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(String str, int i, String str2, int i2, List list, int i3) {
        while (i < str.length()) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '*') {
                O3 o3 = (O3) list.get(i3);
                if (!b && !o3.e()) {
                    throw new AssertionError();
                }
                O3.b bVarB = o3.b();
                for (int i4 = i2; i4 <= str2.length(); i4++) {
                    bVarB.a(str2.substring(i2, i4));
                    if (a(str, i + 1, str2, i4, list, i3 + 1)) {
                        return true;
                    }
                }
                return false;
            }
            if (cCharAt == '<') {
                O3 o4 = (O3) list.get(i3);
                if (!b && !o4.d()) {
                    throw new AssertionError();
                }
                String strG = o4.a().g();
                if (strG == null || str2.length() < strG.length() + i2 || !strG.equals(str2.substring(i2, strG.length() + i2))) {
                    return false;
                }
                int length = strG.length() + i2;
                i3++;
                i = str.indexOf(">", i);
                i2 = length;
            } else {
                if (cCharAt != '?') {
                    if (i2 != str2.length()) {
                        int i5 = i2 + 1;
                        if (cCharAt == str2.charAt(i2)) {
                            i2 = i5;
                        }
                    }
                    return false;
                }
                O3 o5 = (O3) list.get(i3);
                if (!b && !o5.e()) {
                    throw new AssertionError();
                }
                if (i2 == str2.length()) {
                    return false;
                }
                int i6 = i2 + 1;
                o5.b().a(str2.substring(i2, i6));
                i3++;
                i2 = i6;
            }
            i++;
        }
        return i2 == str2.length();
    }

    public abstract boolean a(String str);

    protected AbstractC3815v3 b() {
        return this;
    }

    protected Iterable a() {
        return new v3$$ExternalSyntheticLambda0();
    }

    static Iterable a(AbstractC3815v3 abstractC3815v3) {
        return abstractC3815v3 == null ? new v3$$ExternalSyntheticLambda0() : abstractC3815v3.a();
    }

    public static AbstractC3815v3 a(N2.a aVar) {
        if (aVar.a.equals("*")) {
            return a;
        }
        if (aVar.b.isEmpty()) {
            return new C3809u3(aVar.a);
        }
        return new C3803t3(aVar);
    }
}
