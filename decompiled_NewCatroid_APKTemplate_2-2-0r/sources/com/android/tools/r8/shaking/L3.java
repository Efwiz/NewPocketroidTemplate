package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.C1303Qq;
import com.android.tools.r8.utils.C3970u0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class L3 extends G3 {
    static final /* synthetic */ boolean d = true;
    private final String a;
    private final List b;
    private final G3.a c;

    private L3(N2.a aVar, G3.a aVar2) {
        super();
        this.a = aVar.a;
        this.b = aVar.b;
        this.c = aVar2;
    }

    @Override // com.android.tools.r8.shaking.G3
    protected final G3 a(C0722p1 c0722p1) {
        C1303Qq c1303Qq = new C1303Qq(16);
        ArrayList arrayList = new ArrayList();
        Iterator it = this.b.iterator();
        while (it.hasNext()) {
            O3 o3F = ((O3) it.next()).f();
            if (o3F.d()) {
                O3.a aVarA = o3F.a();
                c1303Qq.a(aVarA.b, aVarA.g());
            } else {
                arrayList.add(o3F);
            }
        }
        if (c1303Qq.isEmpty()) {
            return new L3(new N2.a(this.a, arrayList), this.c);
        }
        String str = this.a;
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int i2 = 0;
        while (i < str.length()) {
            if (str.charAt(i) == '<') {
                int i3 = i + 1;
                int i4 = i3;
                while (i4 < str.length() && str.charAt(i4) != '>') {
                    i4++;
                }
                if (i4 == str.length()) {
                    break;
                }
                String strSubstring = str.substring(i3, i4);
                if (!strSubstring.isEmpty()) {
                    char[] cArr = com.android.tools.r8.utils.Y2.a;
                    int i5 = 0;
                    while (true) {
                        if (i5 >= strSubstring.length()) {
                            String str2 = (String) c1303Qq.get(Integer.valueOf(strSubstring).intValue());
                            if (str2 != null) {
                                sb.append(str.substring(i2, i));
                                sb.append(str2);
                                i2 = i4 + 1;
                                i = i4;
                                break;
                            }
                            break;
                        }
                        if (!Character.isDigit(strSubstring.charAt(i5))) {
                            break;
                        }
                        i5++;
                    }
                }
            }
            i++;
        }
        if (!d && i != str.length()) {
            throw new AssertionError();
        }
        if (i2 < i) {
            sb.append(str.substring(i2));
        }
        String string = sb.toString();
        return !string.contains("*") ? new G3.b(c0722p1.e(C3970u0.H(string))) : new L3(new N2.a(string, arrayList), this.c);
    }

    @Override // com.android.tools.r8.shaking.G3
    public final boolean b(C0767v2 c0767v2) {
        boolean zA = a(this.a, 0, c0767v2.r0(), 0, this.b, 0, this.c);
        if (!zA) {
            this.b.forEach(new L3$$ExternalSyntheticLambda0());
        }
        return zA;
    }

    @Override // com.android.tools.r8.shaking.G3
    protected final Iterable d() {
        return this.b;
    }

    @Override // com.android.tools.r8.shaking.G3
    public final boolean equals(Object obj) {
        if (!(obj instanceof L3)) {
            return false;
        }
        L3 l3 = (L3) obj;
        return this.c.equals(l3.c) && this.a.equals(l3.a);
    }

    @Override // com.android.tools.r8.shaking.G3
    public final int hashCode() {
        return this.c.hashCode() + (this.a.hashCode() * 7);
    }

    @Override // com.android.tools.r8.shaking.G3
    public final String toString() {
        return this.a;
    }

    /* JADX WARN: Code duplicated, block: B:110:0x0164  */
    /* JADX WARN: Code duplicated, block: B:113:0x016b  */
    /* JADX WARN: Code duplicated, block: B:128:0x01c1 A[LOOP:1: B:111:0x0165->B:128:0x01c1, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:152:0x01c0 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:154:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:78:0x0114  */
    /* JADX WARN: Code duplicated, block: B:80:0x011d A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:81:0x011e  */
    /* JADX WARN: Code duplicated, block: B:83:0x0122  */
    /* JADX WARN: Code duplicated, block: B:95:0x013f  */
    /* JADX WARN: Code duplicated, block: B:98:0x0147  */
    private static boolean a(String str, int i, String str2, int i2, List list, int i3, G3.a aVar) {
        int i4;
        int i5;
        char c;
        int i6;
        int i7;
        char c2;
        int i8;
        int length;
        int length2;
        int i9;
        int iIndexOf = i;
        int i10 = i2;
        int i11 = i3;
        while (iIndexOf < str.length()) {
            char cCharAt = str.charAt(iIndexOf);
            if (cCharAt == '*') {
                O3 o3 = (O3) list.get(i11);
                if (!d && !o3.e()) {
                    throw new AssertionError();
                }
                O3.b bVarB = o3.b();
                int i12 = iIndexOf + 1;
                boolean z = str.length() > i12 && str.charAt(i12) == '*';
                boolean z2 = z && str.length() > (i9 = iIndexOf + 2) && str.charAt(i9) == '*';
                if (!z2) {
                    if (z) {
                        i5 = iIndexOf + 2;
                    } else {
                        i4 = i12;
                    }
                    c = '[';
                    if (i4 == str.length()) {
                        bVarB.a(str2.substring(i10));
                        if (z2) {
                            return true;
                        }
                        if (z) {
                            return (aVar == G3.a.a && (length2 = str2.length()) >= 2 && str2.charAt(length2 + (-1)) == ']' && str2.charAt(length2 - 2) == '[') ? false : true;
                        }
                        if (str2.indexOf(46, i10) != -1) {
                            return false;
                        }
                        return (aVar == G3.a.a && (length = str2.length()) >= 2 && str2.charAt(length + (-1)) == ']' && str2.charAt(length - 2) == '[') ? false : true;
                    }
                    i6 = i10;
                    while (i6 < str2.length()) {
                        bVarB.a(str2.substring(i10, i6));
                        if (z && str2.charAt(i6) == '.') {
                            return a(str, i4, str2, i6, list, i11 + 1, aVar);
                        }
                        if (aVar != G3.a.b && str2.charAt(i6) == c) {
                            return a(str, i4, str2, i6, list, i11 + 1, aVar);
                        }
                        i7 = i6;
                        c2 = c;
                        i8 = i4;
                        if (a(str, i4, str2, i6, list, i11 + 1, aVar)) {
                            return true;
                        }
                        i6 = i7 + 1;
                        i4 = i8;
                        c = c2;
                    }
                    bVarB.a(str2.substring(i10));
                    return a(str, i4, str2, str2.length(), list, i11 + 1, aVar);
                }
                i5 = iIndexOf + 3;
                i4 = i5;
                c = '[';
                if (i4 == str.length()) {
                    bVarB.a(str2.substring(i10));
                    if (z2) {
                        return true;
                    }
                    if (z) {
                        if (aVar == G3.a.a) {
                        }
                    }
                    if (str2.indexOf(46, i10) != -1) {
                        return false;
                    }
                    if (aVar == G3.a.a) {
                    }
                }
                i6 = i10;
                while (i6 < str2.length()) {
                    bVarB.a(str2.substring(i10, i6));
                    if (z) {
                    }
                    if (aVar != G3.a.b) {
                    }
                    i7 = i6;
                    c2 = c;
                    i8 = i4;
                    if (a(str, i4, str2, i6, list, i11 + 1, aVar)) {
                        return true;
                    }
                    i6 = i7 + 1;
                    i4 = i8;
                    c = c2;
                }
                bVarB.a(str2.substring(i10));
                return a(str, i4, str2, str2.length(), list, i11 + 1, aVar);
            }
            if (cCharAt == '<') {
                O3 o4 = (O3) list.get(i11);
                if (!d && !o4.d()) {
                    throw new AssertionError();
                }
                String strG = o4.a().g();
                if (strG == null || str2.length() < strG.length() + i10 || !strG.equals(str2.substring(i10, strG.length() + i10))) {
                    return false;
                }
                int length3 = strG.length() + i10;
                i11++;
                iIndexOf = str.indexOf(">", iIndexOf);
                i10 = length3;
            } else {
                if (cCharAt != '?') {
                    if (i10 != str2.length()) {
                        int i13 = i10 + 1;
                        if (cCharAt == str2.charAt(i10)) {
                            i10 = i13;
                        }
                    }
                    return false;
                }
                O3 o5 = (O3) list.get(i11);
                if (!d && !o5.e()) {
                    throw new AssertionError();
                }
                if (i10 == str2.length() || str2.charAt(i10) == '.') {
                    return false;
                }
                int i14 = i10 + 1;
                o5.b().a(str2.substring(i10, i14));
                i11++;
                i10 = i14;
            }
            iIndexOf++;
        }
        return i10 == str2.length();
    }
}
