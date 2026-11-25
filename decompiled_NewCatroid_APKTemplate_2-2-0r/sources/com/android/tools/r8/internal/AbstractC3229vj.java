package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.T0$$ExternalSyntheticLambda1;
import com.android.tools.r8.graph.W0$$ExternalSyntheticLambda12;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vj, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC3229vj {
    static final /* synthetic */ boolean f = true;
    protected TreeMap a;
    protected TreeMap b;
    protected TreeMap c;
    String d;
    String e;

    private AbstractC3229vj(C0767v2 c0767v2) {
        this.a = new TreeMap(Comparator.comparing(new T0$$ExternalSyntheticLambda1()));
        this.b = new TreeMap(Comparator.comparing(new W0$$ExternalSyntheticLambda12()));
        this.c = new TreeMap(Comparator.comparing(new W0$$ExternalSyntheticLambda12()));
        String strR0 = c0767v2.r0();
        this.d = strR0;
        int iLastIndexOf = strR0.lastIndexOf(46);
        this.e = iLastIndexOf > 0 ? this.d.substring(0, iLastIndexOf) : "";
    }

    protected static String a(String str, String str2) {
        if (str.startsWith(str2) && str.length() > str2.length() && str.charAt(str2.length()) == '.') {
            return str.substring(str.lastIndexOf(46) + 1);
        }
        return null;
    }

    protected final String a(String str) {
        String str2 = this.e;
        String strA = a(str, str2);
        if (strA == null) {
            List<String> listA = com.android.tools.r8.utils.Y2.a(str2, '.');
            if (listA.size() > 2) {
                strA = a(str, str2.substring(0, (str2.length() - ((String) com.android.tools.r8.utils.V1.b(listA)).length()) - 1));
            } else {
                strA = null;
            }
        }
        if (strA == null) {
            strA = a(str, CommonClassNames.DEFAULT_PACKAGE);
        }
        if (strA == null) {
            strA = a(str, "java.util.function");
        }
        if (strA != null) {
            str = strA;
        }
        return str.replace('$', '.');
    }

    protected final String a(C0767v2 c0767v2) {
        if (c0767v2.W0()) {
            return c0767v2.r0();
        }
        return a(c0767v2.r0());
    }

    protected static String a(com.android.tools.r8.graph.Q2 q2) {
        ArrayList arrayList = new ArrayList();
        if (q2.m()) {
            arrayList.add("public");
        }
        if (q2.l()) {
            arrayList.add("protected");
        }
        if (q2.j()) {
            if (f) {
                arrayList.add("private");
            } else {
                throw new AssertionError();
            }
        }
        if (q2.i()) {
            if (f) {
                arrayList.add("/* package */");
            } else {
                throw new AssertionError();
            }
        }
        if (q2.o()) {
            arrayList.add("static");
        }
        if (q2.h()) {
            arrayList.add("final");
        }
        return String.join(" ", arrayList);
    }

    protected static String a(C0725p4 c0725p4) {
        ArrayList arrayList = new ArrayList();
        if (c0725p4.m()) {
            arrayList.add("public");
        }
        if (c0725p4.l()) {
            arrayList.add("protected");
        }
        if (c0725p4.j()) {
            if (f) {
                arrayList.add("private");
            } else {
                throw new AssertionError();
            }
        }
        if (c0725p4.i()) {
            if (f) {
                arrayList.add("/* package */");
            } else {
                throw new AssertionError();
            }
        }
        if (c0725p4.W()) {
            arrayList.add("abstract");
        }
        if (c0725p4.o()) {
            arrayList.add("static");
        }
        if (c0725p4.h()) {
            arrayList.add("final");
        }
        return String.join(" ", arrayList);
    }

    public String a(com.android.tools.r8.graph.W0 w0) {
        C0737r2 c0737r2 = w0.getReference().h;
        StringBuilder sb = new StringBuilder();
        int i = (w0.S1() || w0.f.Y()) ? 1 : 0;
        sb.append("(");
        C0767v2[] c0767v2Arr = c0737r2.f.a;
        int length = c0767v2Arr.length;
        int i2 = 0;
        int i3 = 0;
        boolean z = true;
        while (i2 < length) {
            C0767v2 c0767v2 = c0767v2Arr[i2];
            if (!z) {
                sb.append(", ");
            }
            if (w0.q1()) {
                String string = "p" + i3;
                for (C0753u.a aVar : w0.a1().K().J0()) {
                    if (aVar.b() == i) {
                        if (!f && aVar.c().a.toString().equals("this")) {
                            throw new AssertionError();
                        }
                        string = aVar.c().a.toString();
                    }
                }
                sb.append(a(c0767v2)).append(" ").append(string);
            } else {
                sb.append(a(c0767v2)).append(" p").append(i3);
            }
            i += c0767v2.Z0() ? 2 : 1;
            i3++;
            i2++;
            z = false;
        }
        sb.append(Constant.AFTER_QUTO);
        return sb.toString();
    }
}
